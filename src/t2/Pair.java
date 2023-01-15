package t2;

public class Pair {
	private int p;
	private int q;
	public Pair(int p, int q) {
		this.p = p;
		this.q = q;
	}
	public int getP() {
		return p;
	}
	public int getQ() {
		return q;
	}
	
	public String toString() {
		return "(" + p + "," + q + ")";
		
	}
	
}
