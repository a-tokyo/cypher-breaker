package t3;

import java.security.PublicKey;

public class Certificate {
	public static long CERTIFICATE_LIFETIME_MS = (long) 6.307e+10; // 1.99 years
	
	protected String[] attribues;
	protected byte[] ca_signature;
	
	
	public byte[] getCa_signature() {
		return ca_signature;
	}


	public void setCa_signature(byte[] ca_signature) {
		this.ca_signature = ca_signature;
	}


	public Certificate(PublicKey publicKey) {
		long currTSMS = System.currentTimeMillis();
		long expiaryTimeStampMS = currTSMS + CERTIFICATE_LIFETIME_MS;
		this.attribues = new String [3];
		
		this.attribues[0] = publicKey.hashCode() + "";
		this.attribues[1] = Long.toString(currTSMS);
		this.attribues[2] = Long.toString(expiaryTimeStampMS);
	}
	
	public Certificate(String [] attribues, byte[] ca_signature) {
		this.attribues = attribues;
		this.setCa_signature(ca_signature);
	}
	
}
