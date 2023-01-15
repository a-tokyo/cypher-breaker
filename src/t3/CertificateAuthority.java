package t3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class CertificateAuthority {

	PublicKey publicKey;
	PrivateKey privateKey;
	public CertificateAuthority() {
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

	public Certificate generateCertificate(PublicKey userKey) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException { 
		Certificate certificate = new Certificate(userKey);
		signCertificate(certificate);
		return certificate;
	}
	
	private void signCertificate(Certificate certificate) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
	    Signature sign = Signature.getInstance("SHA256withDSA");
	    sign.initSign(this.privateKey);
	    sign.update(reduceArrayToString(certificate.attribues).getBytes());
	    certificate.setCa_signature(sign.sign());
	}
	
	public boolean verifyCertificate(Certificate cetrificate) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException{
		Signature sign = Signature.getInstance("SHA256withDSA");
		sign.initVerify(this.publicKey);
		sign.update(reduceArrayToString(cetrificate.attribues).getBytes());
		return sign.verify(cetrificate.getCa_signature());
	}
	
	private String reduceArrayToString (String[] array){
		return String.join("_SEP_", array);
	}
	
	void writeFile(String fileName, Certificate certificate) { 

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(int i = 0; i < certificate.attribues.length; i++)
            	bufferedWriter.write(certificate.attribues[i] +"\n");

            byte [] csSigB64 = Base64.getEncoder().encode(certificate.getCa_signature());
            System.out.println("Signature: " + new String(csSigB64));
            bufferedWriter.write(new String(csSigB64) +"\n");
            
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
}
}
