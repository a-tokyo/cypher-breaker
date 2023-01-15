package t1;

import java.util.ArrayList;

public class VigenereCipherBreaker implements CipherBreaker {
	private String cipherText = "";

	public static final double IC_AVERAGE = 0.053;
	public static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static final int MIN_KEY_LENGTH = 2;
	public static final int MAX_KEY_LENGTH = 22;

	public int computeKeyLength() {
		ArrayList<Integer> candidateKeyLengths = new  ArrayList<Integer>();
		for (int i = MIN_KEY_LENGTH; i < MAX_KEY_LENGTH; i++) {
			String [] newPiles = cipherToPiles(cipherText, i);
			int [] frequencies = getFrequencies(newPiles);
			float ic = getIC(frequencies, newPiles);
			if(ic > IC_AVERAGE) {
				System.out.println("Candidate: " + i);
				candidateKeyLengths.add(i);
			}
		}
		return getMostMultiplesKey(candidateKeyLengths);
	}

	// Pure function
	public String [] cipherToPiles(String cipherText, int keyLength) {
		String [] newPiles = new String [keyLength];
		int curr = 0;
		for (int i = 0; i < cipherText.length(); i++) {
			newPiles[curr] = newPiles[curr] == null ? "" + cipherText.charAt(i) : newPiles[curr] + cipherText.charAt(i);			
			curr += 1;
			if(curr == newPiles.length){
				curr = 0;
			}
		}
		return newPiles;
	}

	// Pure function
	public int [] getFrequencies(String [] piles) {
		int [] freqs = new int [piles.length];
		int result = 0;
		for (int i = 0; i < ALPHABET.length; i++) {
			char currChar = ALPHABET[i];
			for (int j = 0; j < piles.length; j++) {
				result = 0;
				for (int k = 0; k < piles[j].length(); k++) {
					if(piles[j].charAt(k) == currChar){
						result++;	
					}
				}
				freqs[j] += result*(result-1);
			}
		}
		return freqs;
	}

	// Pure function
	public float getIC(int [] frequencies, String [] piles){
		float ic = 0;
		for (int i = 0; i < frequencies.length; i++) {
			ic += (1.0/((piles[i].length()*(piles[i].length()-1)))) * frequencies[i];
		}	
		return ic/piles.length;
	}

	// Pure function
	public int getMostMultiplesKey(ArrayList<Integer> candidateKeys){
		// Factorize to get key
		int maxSoFar = 0;
		int bestKey = 0;
		for (int i = 0; i < candidateKeys.size(); i++) {
			int curr = 0;
			for (int j = 0; j < candidateKeys.size(); j++) {
				if(candidateKeys.get(j)%candidateKeys.get(i) == 0){
					curr +=1;
				}
			}
			if(curr > maxSoFar){
				bestKey = candidateKeys.get(i);
				maxSoFar=curr;
			}
		}
		return bestKey;
	}

	// Needed for tests
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
}
