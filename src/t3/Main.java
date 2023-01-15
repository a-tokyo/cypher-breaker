package t3;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class Main {
	static CertificateAuthority ca = new CertificateAuthority();
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		User userA = new User(ca);
		User userB = new User(ca);
		
		userA.requestACertificate();
		userB.requestACertificate();
		
		
		// If true, then should communicate else don't
		System.out.println(userA.verifyACertificate(userB.publicKey.hashCode() + ""));
	}
}
