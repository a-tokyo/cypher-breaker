package t3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Base64;

public class User {
PublicKey publicKey;
PrivateKey privateKey;

CertificateAuthority ca;

String line = null;

	public User(CertificateAuthority ca) {
		this.ca = ca;
		generateKeysDSA();
	}
	
	private void generateKeysDSA(){
		try {
			KeyPairGenerator kg;
			kg = KeyPairGenerator.getInstance("DSA");
			KeyPair keyPair = kg.genKeyPair();
			this.privateKey = keyPair.getPrivate();
			this.publicKey = keyPair.getPublic();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	
	public void requestACertificate() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Certificate certificate = ca.generateCertificate(this.publicKey);
		ca.writeFile(this.publicKey.hashCode() + "", certificate);
	}
	
	public boolean verifyACertificate(String otherPubKey) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		ArrayList<String> readLines = readCertificate(otherPubKey);
		String [] attrs = new String[3];
		attrs[0] = readLines.get(0);
		attrs[1] = readLines.get(1);
		attrs[2] = readLines.get(2);
		System.out.println(readLines);
		byte [] csSigB64 = Base64.getDecoder().decode(readLines.get(3));
		Certificate certificate = new Certificate(attrs, csSigB64);
		if(Long.parseLong(certificate.attribues[2]) - System.currentTimeMillis() <= 0){
			return false;
		}
		return ca.verifyCertificate(certificate);
	}
	
	
	ArrayList<String> readCertificate(String otherCert) {
		ArrayList<String> readLines = new ArrayList<String> ();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(otherCert);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                readLines.add(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                otherCert + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + otherCert + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		return readLines;
	}
	
	
}
