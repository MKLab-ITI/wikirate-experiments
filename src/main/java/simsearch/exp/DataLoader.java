package simsearch.exp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.BiMap;

import util.EasyBufferedReader;
import wikirate.Source;

import com.google.common.collect.*;


public class DataLoader {

	
	public static void main(String[] args) {
		simpleTest(args[0]);
	}
	
	public static void simpleTest(String root){
		
		DataLoader dloader = loadDefaultDatasetLoader(root);
		
		System.out.println("dictoniary size: " + dloader.getDictionarySize());
		Set<String> randomTerms = new HashSet<String>();
		for (int i = 0; i < 10; i++) {
			randomTerms.add(dloader.getRandomTerm());
		}
		System.out.println(randomTerms);
		
		
		List<String> topics = dloader.getTopics();
		System.out.println("topics (" + topics.size() + "): " + topics);
		
		List<String> companies = dloader.getCompanies();
		System.out.println("companies (" + companies.size() + "): " + companies);
		
		List<Source> sources = dloader.getSources();
		int N = sources.size() > 3 ? 3 : sources.size();
		System.out.println("sources (" + sources.size() + "): " + sources.subList(0, N));
	}
	
	public static DataLoader loadDefaultDatasetLoader(String root){
		System.out.println("Attempting to load data from directory: " + root);
		return new DataLoader(root + "wikirate-topics.txt", 
				root + "wikirate-companies.txt", root + "wikirate-sources.txt",
				root + "dictionary.txt");
	}
	
	
	public DataLoader(String topicsFile, String companiesFile, String sourcesFile, String dictionary){
		this.topicsFile = topicsFile;
		this.companiesFile = companiesFile;
		this.sourcesFile = sourcesFile;
		terms = HashBiMap.create();
		EasyBufferedReader reader = new EasyBufferedReader(dictionary);
		String line = null;
		int idx = -1;
		while ((line = reader.readLine())!= null){
			terms.put(line, ++idx);
		}
		reader.close();
	}
	
	// members
	private final String topicsFile;
	private final String companiesFile;
	private final String sourcesFile;
	private final BiMap<String, Integer> terms; 
	
	public List<String> getTopics(){
		List<String> topics = new ArrayList<String>();
		EasyBufferedReader reader = new EasyBufferedReader(topicsFile);
		String line = null;
		while ((line = reader.readLine())!=null){
			if (line.trim().length()>1){
				topics.add(line.trim());
			}
		}
		reader.close();
		return topics;
	}
	
	
	public List<String> getCompanies(){
		List<String> companies = new ArrayList<String>();
		EasyBufferedReader reader = new EasyBufferedReader(companiesFile);
		String line = null;
		while ((line = reader.readLine())!=null){
			if (line.trim().length()>1){
				companies.add(line.trim());
			}
		}
		reader.close();
		return companies;
	}
	
	
	public List<Source> getSources(){
		List<Source> sources = new ArrayList<Source>();
		EasyBufferedReader reader = new EasyBufferedReader(sourcesFile);
		String line = null;
		while ((line = reader.readLine())!=null){
			if (line.trim().length()>1){
				String[] parts = line.split("\t");
				if (parts.length < 3){
					continue;
				}
				sources.add(new Source(parts[0], parts[2], parts[1]));
			}
		}
		reader.close();
		return sources;
	}
	
	public int getDictionarySize(){
		return terms.size();
	}
	
	public String getRandomTerm(){
		int idx = (int)Math.round(terms.size() * Math.random());
		if (idx >= terms.size()) return terms.inverse().get(idx-1);
		else return terms.inverse().get(idx);
	}
}
