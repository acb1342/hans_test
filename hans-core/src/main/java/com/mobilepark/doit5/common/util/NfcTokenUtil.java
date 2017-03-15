package com.mobilepark.doit5.common.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.sun.jndi.toolkit.chars.BASE64Decoder;
import com.sun.jndi.toolkit.chars.BASE64Encoder;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

public class NfcTokenUtil {
	
	public static synchronized String getNfcCertToken(String deviceId, Long usid) {
		
		deviceId = StringUtils.leftPad(deviceId, 16, "0");
		
		String hexTimestamp = Long.toHexString(Instant.now().getEpochSecond());
		String hexCustNo = StringUtils.leftPad(Long.toHexString(usid), 7, "0");
		
		String dummy1 = RandomStringUtils.randomAlphanumeric(1);
		String dummy2 = hexCustNo.substring(0, 1);
		String dummy3 = RandomStringUtils.randomAlphanumeric(1);
		String dummy4 = RandomStringUtils.randomAlphanumeric(1);
		
		String deviceId1 = deviceId.substring(0, 4);
		String deviceId2 = deviceId.substring(4, 8);
		String deviceId3 = deviceId.substring(8, 12);
		String deviceId4 = deviceId.substring(12, 16);
		
		String hexCustNo1 = hexCustNo.substring(1, 4);
		String hexCustNo2 = hexCustNo.substring(4, 7);
		
		String hexTimestamp1 = hexTimestamp.substring(0, 4);
		String hexTimestamp2 = hexTimestamp.substring(4, 8);
		
		
		Map<String, String> pieceMap = new HashMap<String, String>();
		
		pieceMap.put("D1", deviceId1);
		pieceMap.put("D2", deviceId2);
		pieceMap.put("D3", deviceId3);
		pieceMap.put("D4", deviceId4);
		pieceMap.put("X1", dummy1);
		pieceMap.put("X2", dummy2);
		pieceMap.put("C1", hexCustNo1);
		pieceMap.put("C2", hexCustNo2);
		pieceMap.put("X3", dummy3);
		pieceMap.put("X4", dummy4);
		pieceMap.put("T1", hexTimestamp1);
		pieceMap.put("T2", hexTimestamp2);
		
		return shuffle(pieceMap);
	}
	
	private static String shuffle(Map<String, String> pieceMap) {
		String keyOrderStr = Env.get("nfc.token.keyorder");
		String[] keyOrder = keyOrderStr.split(",");
		
		StringBuffer result = new StringBuffer();
		
		for (String key : keyOrder) {
			result.append(pieceMap.get(key));
		}
		
		return result.toString();
	}
	
	public static Long getUsidFromNDEF(String ndef) throws Exception {
		
		String unwrappedNdef = NfcTokenUtil.unwrapNDEFShuffle(ndef);
		
		String encStr = unwrappedNdef.substring(0, 12);
		String encKey = unwrappedNdef.substring(12);
		
		String decStr = decrypt3DES(encStr, encKey);
		Long usid = Long.parseLong(decStr, 16);
		
		return usid;
	}
	
	private static String unwrapNDEFShuffle(String str) {
		
		StringBuffer result = new StringBuffer();
		
		String keyOrderStr = Env.get("nfc.ndef.keyorder");
		String[] keyOrder = keyOrderStr.split(",");
		
		Map<String, String> ndefMap = new HashMap<String, String>();
		
		for (String key : keyOrder) {
			int size = 0;
			if (StringUtils.startsWith(key, "X")) {
				continue;
			} else if (StringUtils.startsWith(key, "E")) {
				size = 3;
			} else if (StringUtils.startsWith(key, "D") || StringUtils.startsWith(key, "T")) {
				size = 4;
			}
			
			int startIdx = 0;
			for (String key2 : keyOrder) {
				if (StringUtils.equals(key2, key)) {
					break;
				}
				
				if (StringUtils.startsWith(key2, "X")) {
					startIdx += 1;
				} else if (StringUtils.startsWith(key2, "E")) {
					startIdx += 3;
				} else if (StringUtils.startsWith(key2, "D") || StringUtils.startsWith(key2, "T")) {
					startIdx += 4;
				}
				
			}
			
			ndefMap.put(key, str.substring(startIdx, startIdx + size));
		}
		
		result.append(ndefMap.get("E1")).append(ndefMap.get("E2")).append(ndefMap.get("E3")).append(ndefMap.get("E4"))
			.append(ndefMap.get("D1")).append(ndefMap.get("D2")).append(ndefMap.get("D3")).append(ndefMap.get("D4"))
			.append(ndefMap.get("T1")).append(ndefMap.get("T2"));
		
		return result.toString();
	}
	
	@SuppressWarnings("unused")
	private static String encrypt3DES(String str, String keyValue) throws Exception {
        
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(keyValue));
        
        byte[] inputBytes1 = str.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        
        BASE64Encoder encoder = new BASE64Encoder();
        String outputStr1 = encoder.encode(outputBytes1);
        
        return outputStr1;
	}
	
	private static String decrypt3DES(String encStr, String keyValue) throws Exception {
		
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey(keyValue));
        BASE64Decoder decoder = new BASE64Decoder();

        byte[] inputBytes1 = decoder.decodeBuffer(encStr);
        byte[] outputBytes2 = cipher.doFinal(inputBytes1);

        return new String(outputBytes2);
	}
	
	private static Key getKey(String keyValue) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        
        return keyFactory.generateSecret(desKeySpec);
	}
	
	public static void main(String args[]) throws Exception {
		/*String deviceId = "abcdefghijklmnop";
		
		String hexTimestamp = Long.toHexString(Instant.now().getEpochSecond());
		
		String hexCustNo = StringUtils.leftPad(Integer.toHexString(9999999), 6, "0");
		
		String dummy1 = RandomStringUtils.randomAlphanumeric(1);
		String dummy2 = "0";
		String dummy3 = RandomStringUtils.randomAlphanumeric(1);
		String dummy4 = RandomStringUtils.randomAlphanumeric(1);
		
		String deviceId1 = deviceId.substring(0, 4);
		String deviceId2 = deviceId.substring(4, 8);
		String deviceId3 = deviceId.substring(8, 12);
		String deviceId4 = deviceId.substring(12, 16);
		
		String hexCustNo1 = hexCustNo.substring(0, 3);
		String hexCustNo2 = hexCustNo.substring(3, 6);
		
		String hexTimestamp1 = hexTimestamp.substring(0, 4);
		String hexTimestamp2 = hexTimestamp.substring(4, 8);
		
		System.out.println("hexTimestamp : " + hexTimestamp);
		System.out.println("hexCustNo : " + hexCustNo);
		
		System.out.println("deviceId1 : " + deviceId1);
		System.out.println("deviceId2 : " + deviceId2);
		System.out.println("deviceId3 : " + deviceId3);
		System.out.println("deviceId4 : " + deviceId4);
		
		System.out.println("hexCustNo1 : " + hexCustNo1);
		System.out.println("hexCustNo2 : " + hexCustNo2);
		
		System.out.println("hexTimestamp1 : " + hexTimestamp1);
		System.out.println("hexTimestamp2 : " + hexTimestamp2);
		
		System.out.println("hexTimeStamp : " + hexTimestamp);
		System.out.println("hexCustNo : " + hexCustNo);
		System.out.println("dummy1 : " + dummy1);
		System.out.println("dummy2 : " + dummy2);
		System.out.println("dummy3 : " + dummy3);
		System.out.println("dummy4 : " + dummy4);
		
		Map<String, String> pieceMap = new HashMap<String, String>();
		
		pieceMap.put("D1", deviceId1);
		pieceMap.put("D2", deviceId2);
		pieceMap.put("D3", deviceId3);
		pieceMap.put("D4", deviceId4);
		pieceMap.put("X1", dummy1);
		pieceMap.put("X2", dummy2);
		pieceMap.put("C1", hexCustNo1);
		pieceMap.put("C2", hexCustNo2);
		pieceMap.put("X3", dummy3);
		pieceMap.put("X4", dummy4);
		pieceMap.put("T1", hexTimestamp1);
		pieceMap.put("T2", hexTimestamp2);
		
		String shuffledString = shuffle(pieceMap);
		
		System.out.println(shuffledString);*/
		
		/*String hexCustNo = StringUtils.leftPad(Long.toHexString(1000001L), 7, "0");
		
		System.out.println(hexCustNo);
		
		String enc = encrypt3DES(hexCustNo, "abcdefghijklmnop!@#$%^&*");
		
		System.out.println("" + enc);*/
		// System.out.println(decrypt3DES(enc));
		
		// String ndef = unwrapNDEFShuffle("0iCfijkl0mnop%^&*M/Z0efgh/U=0!@#$ZjGabcd");
		String ndef = unwrapNDEFShuffle("0iCfijkl0mnop%^&*M/Z0efgh/U=0!@#$ZjGabcd");
		
		System.out.println("unwrapped ndef string : " + ndef);
		
		String encStr = ndef.substring(0, 12);
		String encKey = ndef.substring(12);
		
		System.out.println("encoded body : " + encStr);
		System.out.println("encode, decode key : " + encKey);
		
		String decStr = decrypt3DES(encStr, encKey);
		
		System.out.println("decoded hex : " + decStr);
		
		Long usid = Long.parseLong(decStr, 16);
		
		System.out.println("decoded usid : " + usid);
		
		System.out.println(decrypt3DES("wcQTYvjD4nQ=", "0123456789012345584FE4E2"));
	}
}
