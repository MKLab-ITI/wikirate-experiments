package ner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * This is a specific instantiation of the Stanford NLP library. It assumes that the appropriate model files
 * for POS Tagging and NER are available in the file locations specified by the two arguments on the construction.
 * Further documentation is available on: http://nlp.stanford.edu/software/corenlp.shtml#Usage
 * @author papadop
 *
 */
public class StanfordNlpWrapper {

	
	public StanfordNlpWrapper(String posModel, String nerModel){
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		props.put("pos.model", posModel);
		props.put("ner.model", nerModel);
		props.put("ner.useSUTime", "0");
		pipeline = new StanfordCoreNLP(props);
	}
	protected final StanfordCoreNLP pipeline;
	protected static final Set<String> NOUN_POS = new HashSet<String>();
	protected static final Set<String> PNOUN_POS = new HashSet<String>();
	protected static final Set<String> ALLNOUN_POS = new HashSet<String>();
	static {
		NOUN_POS.add("NN");
		PNOUN_POS.add("NNP");
		ALLNOUN_POS.add("NN");
		ALLNOUN_POS.add("NNP");
	}
	
	public List<CoreMap> getAnnotations(String text){
		Annotation document = new Annotation(text);
	    pipeline.annotate(document);
	    
	    return document.get(SentencesAnnotation.class);
	}
	
	
	public List<String> getNounTokens(CoreMap annotation) {
		return getTokens(annotation, NOUN_POS);
	}
	public List<String> getProperNounTokens(CoreMap annotation) {
		return getTokens(annotation, PNOUN_POS);
	}
	public List<String> getAllNounTokens(CoreMap annotation){
		return getTokens(annotation, ALLNOUN_POS);
	}
	
	public List<String> getTokens(CoreMap annotation, Set<String> posType){
		List<String> outTokens = new ArrayList<String>();
		for (CoreLabel token: annotation.get(TokensAnnotation.class)) {
			String pos = token.get(PartOfSpeechAnnotation.class);
			if (posType.contains(pos)){
				outTokens.add(token.get(TextAnnotation.class));
			}
		}
		return outTokens;
	}
	
	
	public void printCoreMap(CoreMap annotations){
		for (CoreLabel token: annotations.get(TokensAnnotation.class)) {
	         // this is the text of the token
	         String word = token.get(TextAnnotation.class);
	         // this is the POS tag of the token
	         String pos = token.get(PartOfSpeechAnnotation.class);
	         // this is the NER label of the token
	         String ne = token.get(NamedEntityTagAnnotation.class);
	          
	         System.out.println("word = " + word + ", pos=" + pos + ", NE=" + ne);
	    }
	}
}
