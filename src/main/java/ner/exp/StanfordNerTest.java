package ner.exp;

import java.util.List;

import ner.StanfordNlpWrapper;
import edu.stanford.nlp.util.CoreMap;


public class StanfordNerTest {

	/**
	 * 
	 * @param args The first argument should be the file location of the POS Tagging model, 
	 * while the second argument should be the file location of the NER model.
	 * The default models one could use are available on:
	 * POS Tagger: http://nlp.stanford.edu/software/stanford-postagger-2014-08-27.zip  (under folder models)
	 * NER: http://nlp.stanford.edu/software/stanford-ner-2014-08-27.zip (under folder classifiers)
	 */
	public static void main(String[] args) {
		
		StanfordNlpWrapper nlp = new StanfordNlpWrapper(args[0], args[1]);
		 
		String[] text = {"Barrack Obama wins Vermont!", 
				"The IPA and The Australian are at it again. Trying to discredit climate change science.",
				"I got to admit that Scarlet Johansson is a great actress.",
				"Apple sues Samsung over design patent infringement."};
		
		for (int i = 0; i < text.length; i++){
			System.out.println("Text " + (i+1));
			List<CoreMap> sentences = nlp.getAnnotations(text[i]);
			int count = 0;
		    for(CoreMap sentence: sentences) {
		        List<String> nouns = nlp.getAllNounTokens(sentence);
		        System.out.println(++count + ": " + nouns);
		    }
		}
	}
}
