package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KmerTokenizer implements Tokenizer {

	
	public KmerTokenizer(int k){
		this.k = k;
	}
	protected final int k;
	
	
	public List<String> getTokens(String text) {
		
		Set<String> kmers = new HashSet<String>();
		for (int i = 0; i < text.length()-k+1; i++){
			kmers.add(text.substring(i, i+k));
		}
		return new ArrayList<String>(kmers);
	}

}
