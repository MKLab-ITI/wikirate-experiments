package simsearch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simsearch.NearDuplicateSearch;
import util.SimpleTokenizer;
import wikirate.Source;

public class JaccardSimilaritySearch implements NearDuplicateSearch {


	public JaccardSimilaritySearch(List<Source> sources){
		this.sources = sources;
	}
	private final List<Source> sources;
	private final SimpleTokenizer tokenizer = new SimpleTokenizer();

	
	public List<Source> findNearestDocuments(String query, int N) {
		Set<String> qTokens = new HashSet<String>(tokenizer.getTokens(query));
		List<Rank> ranks = new ArrayList<Rank>();
		for (int i = 0; i < sources.size(); i++) {
			double sim = jaccardSimilarity(qTokens, sources.get(i));
			ranks.add(new Rank(i,sim));
		}
		Collections.sort(ranks, Collections.reverseOrder());
		int M = sources.size() > N ? N : sources.size();
		List<Source> toReturn = new ArrayList<Source>(M);
		for (int i = 0; i < M; i++){
			toReturn.add(sources.get(ranks.get(i).getIndex()));
		}
		return toReturn;
	}

	
	protected double jaccardSimilarity(Set<String> queryTokens, Source source){
		Set<String> sourceTitleTokens = new HashSet<String>(tokenizer.getTokens(source.getTitle()));
		int intersection = 0;
		for (String token : sourceTitleTokens){
			if (queryTokens.contains(token)){
				intersection++;
			}
		}
		int union = queryTokens.size() + sourceTitleTokens.size() - intersection;
		return ((double)intersection)/((double)union);
	}
	
}
