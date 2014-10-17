package util;

import java.util.ArrayList;
import java.util.List;


/**
 * Attention: This is copied from a class in socialsensor.eu geo-util project!
 * Factor out whenever possible.
 * @author papadop
 *
 */
public class SimpleTokenizer implements Tokenizer {

	/**
	 * Utility method for splitting a piece of text to tokens
	 * @param text
	 * @return List of tokens
	 */
	public List<String> getTokens(String text){
		String[] tokens = text.toLowerCase().split("[\\s\\p{Punct}]");
		List<String> tokenList = new ArrayList<String>(tokens.length);
		for (int i = 0; i < tokens.length; i++){
			if (tokens[i].trim().length() < 1){
				continue;
			}
			tokenList.add(tokens[i].trim());
		}
		return tokenList;
	}
	
}
