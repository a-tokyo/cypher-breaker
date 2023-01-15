package t2;

import java.math.BigInteger;
import java.util.Arrays;

public class OldRSABreaker {

	private int n;
	private BigInteger e;
	private int encryptedMessage;

	private static boolean[] primes;
	private static BigInteger x, y, d;

	// MAX_VALUE for test cases is actually 1e6
	private static int MAX_VALUE = (int)1e7;

	public OldRSABreaker() {
		primes = new boolean[MAX_VALUE + 1];
		Arrays.fill(primes, true);
		primes[1] = false;
		for(long i = 2 ; i < MAX_VALUE ; i++) {
			if(primes[(int) i]) {
				for(long j = i * i ; j < MAX_VALUE ; j += i)
					primes[(int) j] = false;
			}
		}
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getPhi() {
		Pair pair = sieve();
		int phi = (pair.getP()-1)*(pair.getQ()-1);
		return BigInteger.valueOf(phi);
	}

	public void setEncryptedMessage(int encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public BigInteger getD() { 
		extendedEuclid(getPhi(), e);
		return y;
	}

	public BigInteger decryptMessage() {
		BigInteger encryptedBI = new BigInteger(encryptedMessage+"");
		return encryptedBI.modPow(getD(), BigInteger.valueOf(n));
	}

	public Pair sieve() {
		for(long i=2;i*i<=n; i++) {
			if(n%i==0 && primes[(int)i] && primes[(int)(n/i)]) {
				return new Pair((int)i,(int)(n/i));
			}
		}
		return null;
	}

	private void extendedEuclid(BigInteger a, BigInteger b)
	{
		if(b.equals(BigInteger.ZERO)) { x = BigInteger.ONE; y = BigInteger.ZERO; d = a; return; }
		extendedEuclid(b, a.mod(b));
		BigInteger x1 = y;
		BigInteger y1 = x.subtract(a.divide(b).multiply(y));
		x = x1; y = y1;
	}
}