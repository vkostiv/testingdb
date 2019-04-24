package kostiv.testingdb.util;

import java.security.SecureRandom;

import org.springframework.util.Base64Utils;

/**
 * Utility class to provide necessary tokens manipulation methods 
 * 
 * @author Vasyl.Kostiv
 *
 */
public class TokenUtil {

	/**
	 * Source of random sequences
	 */
	private static SecureRandom sRandom = new SecureRandom();
	
	/**
	 * Generates 64-bytes length array and encode it using Base64 for proper exchanging 
	 * through the HTTP connection 
	 * 
	 * @return token generated
	 */
	public static String generateRandomToken() {
		String token = null;
		byte[] arrToken = new byte[64];
		synchronized(sRandom) {
			sRandom.nextBytes(arrToken);
			token = Base64Utils.encodeToString(arrToken);
		}
	    return token;
    }
	
	public static int randomIndex(int size) {
		synchronized (sRandom) {
			return sRandom.nextInt(size);
		}
	}
}
