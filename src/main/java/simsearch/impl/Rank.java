package simsearch.impl;

public class Rank implements Comparable<Rank> {

	protected final int index;
	protected final double similarity;
	
	public Rank(int index, double similarity){
		this.index = index;
		this.similarity = similarity;
	}
	
	
	public int getIndex() {
		return index;
	}

	public double getSimilarity() {
		return similarity;
	}



	public int compareTo(Rank o) {
		if (this.getSimilarity() > o.getSimilarity()) return 1;
		if (this.getSimilarity() < o.getSimilarity()) return -1;
		return 0;
	}

}
