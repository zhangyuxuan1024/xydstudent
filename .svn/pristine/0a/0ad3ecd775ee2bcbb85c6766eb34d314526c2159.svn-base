package com.xyd.student.xydexamanalysis.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.json.JSONObject;

public class SignUtils {

	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	public static final int RESULT_INVALID_PARAM = 0;
	public static final int RESULT_CHECK_SIGN_FAILED = 1;
	public static final int RESULT_CHECK_SIGN_SUCCEED = 2;

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));
			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static JSONObject string2JSON(String str, String split) {
		JSONObject json = new JSONObject();
		try {
			String[] arrStr = str.split(split);
			for (int i = 0; i < arrStr.length; i++) {
				String[] arrKeyValue = arrStr[i].split("=");
				json.put(arrKeyValue[0],
						arrStr[i].substring(arrKeyValue[0].length() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static int checkSign(String checkstring, String publickkey) {
		int retVal = RESULT_CHECK_SIGN_SUCCEED;
		try {
			JSONObject objContent = string2JSON(checkstring, ";");
			String result = objContent.getString("result");
			result = result.substring(1, result.length() - 1);
			// 获取待签名数据
			int iSignContentEnd = result.indexOf("&sign_type=");
			String signContent = result.substring(0, iSignContentEnd);
			// 获取签名
			JSONObject objResult = string2JSON(result, "&");
			String signType = objResult.getString("sign_type");
			signType = signType.replace("\"", " ");
			String sign = objResult.getString("sign");
			sign = sign.replace("\"", "");
			// 进行验签 返回验签结果
			if (signType.equalsIgnoreCase("RSA")) {
				if (!doCheck(signContent, sign, publickkey))
					retVal = RESULT_CHECK_SIGN_FAILED;
			}
		} catch (Exception e) {
			retVal = RESULT_INVALID_PARAM;
			e.printStackTrace();
		}
		return retVal;
	}

}
