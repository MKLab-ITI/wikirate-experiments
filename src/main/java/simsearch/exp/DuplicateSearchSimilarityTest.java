package simsearch.exp;

import java.util.ArrayList;
import java.util.List;

import data.DataLoader;
import simsearch.NearDuplicateSearch;
import simsearch.impl.JaccardSimilaritySearch;
import util.SimpleTokenizer;
import util.Tokenizer;
import wikirate.Source;

public class DuplicateSearchSimilarityTest {

	
	
	public static void main(String[] args) {
		String root = args[0];
		DataLoader dLoader = DataLoader.loadDefaultDatasetLoader(root);
		Tokenizer tokenizer = new SimpleTokenizer();
		
		NearDuplicateSearch nnSearch = new JaccardSimilaritySearch(dLoader.getSources(), tokenizer);
		
		int nQueries = dLoader.getSources().size() > 1000 ? 1000 : dLoader.getSources().size();
		List<Source> candidateQueries = dLoader.getSources().subList(0, nQueries);
		List<Source> queryDocuments = new ArrayList<Source>();
		// filter queries
		for (int i = 0; i < candidateQueries.size(); i++){
			if (candidateQueries.get(i).getTitle().length() > 50){
				queryDocuments.add(candidateQueries.get(i));
			}
		}
		
		int numTests = 10;
		for (int i = 0; i < numTests; i++) {
			int idx = (int)Math.round((queryDocuments.size()*Math.random()-1));
			if (idx < 0) idx = 0;
			String query = queryDocuments.get(idx).getTitle();
			
			int topK = 5;
			List<Source> results = nnSearch.findNearestDocuments(query, topK);
			System.out.println("Query document: " + queryDocuments.get(idx));
			for (int x = 0; x < topK; x++) {
				System.out.println("--------> R[" + x + "]: " + results.get(x));
			}
			System.out.println();
		}
		
	}
	
}
