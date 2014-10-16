package simsearch;

import java.util.List;

import wikirate.Source;

public interface NearDuplicateSearch {

	public List<Source> findNearestDocuments(String query, int N);
	
}
