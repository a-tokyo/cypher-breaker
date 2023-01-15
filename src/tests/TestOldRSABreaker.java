package tests;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import t2.OldRSABreaker;

public class TestOldRSABreaker {

	public TestOldRSABreaker() {
		// TODO Auto-generated constructor stub
	}
		/**
		 * Test Case Number 1
		 */
		@Test
		public void testCase1() {
			OldRSABreaker rsa = new OldRSABreaker();
			rsa.setE(BigInteger.valueOf(5524331));
			rsa.setN(1177662719);
			rsa.setEncryptedMessage(798624420);
			assertEquals(2771, rsa.getD().intValue());
			assertEquals(BigInteger.valueOf(828365), rsa.decryptMessage());
		}
		
		
		/**
		 * Test Case Number 2
		 */
		@Test
		public void testCase2() {
			OldRSABreaker rsa = new OldRSABreaker();
			rsa.setE(BigInteger.valueOf(94981));
			rsa.setN(352794499);
			rsa.setEncryptedMessage(188885141);
			assertEquals(85381, rsa.getD().intValue());
			assertEquals(BigInteger.valueOf(8310199), rsa.decryptMessage());

		}
		
		/**
		 * Test Case Number 3
		 */
		@Test
		public void testCase3() {
			OldRSABreaker rsa = new OldRSABreaker();
			rsa.setE(BigInteger.valueOf(73009));
			rsa.setN(1356367693);
			rsa.setEncryptedMessage(650959675);
			assertEquals(241489, rsa.getD().intValue());
			assertEquals(BigInteger.valueOf(778371), rsa.decryptMessage());

		}

		/**
		 * Test Case Number 4
		 */
		@Test
		public void testCase4() {
			OldRSABreaker rsa = new OldRSABreaker();
			rsa.setE(BigInteger.valueOf(2324949943L));
			rsa.setN(1356367693);
			rsa.setEncryptedMessage(660349543);
			assertEquals(7, rsa.getD().intValue());
			assertEquals(BigInteger.valueOf(778371), rsa.decryptMessage());
		}
}
		