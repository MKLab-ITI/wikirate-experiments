package simsearch.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simsearch.NearDuplicateSearch;
import simsearch.impl.JaccardSimilaritySearch;
import util.SimpleTokenizer;
import wikirate.Source;

public class DuplicateSearchSimilarityComparison {

	private final SimpleTokenizer tokenizer = new SimpleTokenizer();
	
	public static void main(String[] args) {
		
		DuplicateSearchSimilarityComparison exp = new DuplicateSearchSimilarityComparison();
		
		String root = args[0];
		DataLoader dloader = DataLoader.loadDefaultDatasetLoader(root);
		
		List<Source> wikirateSources = dloader.getSources();
		NearDuplicateSearch nnSearch = new JaccardSimilaritySearch(wikirateSources);
		
		int nQueries = wikirateSources.size() > 1000 ? 1000 : wikirateSources.size();
		List<Source> queryDocuments = wikirateSources.subList(0, nQueries);
		
		
		int[] countCorrect = new int[5];
		int countUsed = 0;
		for (int i = 0; i < queryDocuments.size(); i++){
			Source qSource = queryDocuments.get(i);
			String query = qSource.getTitle();
			
			if (query.length() < 60){
				continue;
			}
			countUsed++;
			
			List<Source> results = nnSearch.findNearestDocuments(query, 1);
			if (results.get(0).getId().equals(qSource.getId())){
				countCorrect[0]++;
			}
			results = nnSearch.findNearestDocuments(exp.permuteTerms(query), 1);
			if (results.get(0).getId().equals(qSource.getId())){
				countCorrect[1]++;
			}
			results = nnSearch.findNearestDocuments(exp.addTerms(query, 1, dloader), 1);
			if (results.get(0).getId().equals(qSource.getId())){
				countCorrect[2]++;
			}
			results = nnSearch.findNearestDocuments(exp.removeTerms(query, 1), 1);
			if (results.get(0).getId().equals(qSource.getId())){
				countCorrect[3]++;
			}
			results = nnSearch.findNearestDocuments(exp.randomSelectTerms(query), 1);
			if (results.get(0).getId().equals(qSource.getId())){
				countCorrect[4]++;
			}
		}
		System.out.println("P@1 for " + countUsed + " queries");
		System.out.println("same query: " + (double)100*countCorrect[0]/((double)countUsed));
		System.out.println("permuted terms: " + (double)100*countCorrect[1]/((double)countUsed));
		System.out.println("added 1 term: " + (double)100*countCorrect[2]/((double)countUsed));
		System.out.println("removed 1 term: " + (double)100*countCorrect[3]/((double)countUsed));
		System.out.println("random re-arrange terms: " + (double)100*countCorrect[4]/((double)countUsed));
		
	}
	
	
	
	
	
	protected String permuteTerms(String text){
		List<String> tokens = tokenizer.getTokens(text);
		Collections.shuffle(tokens);
		return getTextFromTokenList(tokens);
	}
	
	protected String addTerms(String text, int n, DataLoader dloader){
		List<String> tokens = tokenizer.getTokens(text);
		List<String> addedTerms = new ArrayList<String>();
		for (int i = 0; i < n; i++){
			addedTerms.add(dloader.getRandomTerm());
		}
		tokens.addAll(addedTerms);
		return getTextFromTokenList(tokens);
	}
	
	protected String removeTerms(String text, int n){
		List<String> tokens = tokenizer.getTokens(text);
		if (n >= tokens.size()){
			System.out.println("Warning! You want to remove more terms than the terms contained in document: " + text);
			n = tokens.size() - 1;
			System.out.println("Will remove only " + n  + " terms!");
		}
		List<Integer> flags = new ArrayList<Integer>(tokens.size());
		for (int i = 0; i < n; i++){
			flags.add(1); // tokens at this position will be removed	
		}
		for (int i = 0; i < tokens.size()-n; i++){
			flags.add(0); 
		}
		Collections.shuffle(flags);
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < tokens.size(); i++){
			if (flags.get(i) > 0) {
				continue;
			} else {
				if (!first){
					buf.append(" ");
				}
				if (first) {
					first = false;
				}
				buf.append(tokens.get(i));
			}
		}
		return buf.toString();
	}
	
	protected String randomSelectTerms(String text){
		List<String> tokens = tokenizer.getTokens(text);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < tokens.size(); i++) {
			int idx = (int)Math.round(tokens.size() * Math.random());
			if (idx >= tokens.size()) idx = tokens.size() -1;
			if (i > 0) {
				buf.append(" ");
			}
			buf.append(tokens.get(idx));
		}
		return buf.toString();
	}
	
	
	private String getTextFromTokenList(List<String> tokens){
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < tokens.size(); i++){
			if (i > 0){
				buf.append(" ");
			}
			buf.append(tokens.get(i));
		}
		return buf.toString();
	}
}
