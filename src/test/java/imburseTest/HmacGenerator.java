package imburseTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class HmacGenerator {
    MessageDigest digest;
    String accID;
    String tenantID;
    String publicKey;
    String privateKey;
    Mac sha256_Hmac;

    public HmacGenerator() throws NoSuchAlgorithmException {
        accID = "782f1b71-7ca4-4465-917f-68d58ffbec8b ";
        tenantID = "6423ae63-59b6-4986-a949-c910ac622471";
        publicKey = "7934d5e6-260c-46d5-9309-e72a59cb90cd";
        privateKey = "aWRpTN9tRsf2EyM8rcvz7bohO/fAg6IF+daZ1JYE9AM=";
        digest = MessageDigest.getInstance("SHA-256");
        sha256_Hmac = Mac.getInstance("HmacSHA256");
    }

    String createHashbody(String msg, MessageDigest digest) {
        if (msg.isEmpty()) {
            return msg;
        }
        byte[] bodyBytes;
        try {
            bodyBytes = msg.getBytes("utf-8");
            byte[] hashedMsg = digest.digest(bodyBytes);
            return Base64.getEncoder().encodeToString(hashedMsg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    String createSigniture(String privatekey, String toBeSigned, Mac sha256_Hmac) throws UnsupportedEncodingException {

        try {
            byte[] keyByte = Base64.getDecoder().decode(privatekey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte, "HmacSHA256");
            sha256_Hmac.init(secretKeySpec);
            byte[] message = toBeSigned.getBytes("utf-8");
            byte[] hashmsg = sha256_Hmac.doFinal(message);
            String signedSigniture = Base64.getEncoder().encodeToString(hashmsg);

           
            return signedSigniture;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String createToken(String body) throws UnsupportedEncodingException {
        long seconds = System.currentTimeMillis() / 1000l;
        String nonce = (String.valueOf(seconds));
        String hashedBody = createHashbody(body,digest);
        String toBeSigned = String.format("%s:%s:%s:%s", publicKey,nonce,seconds,hashedBody);
        String signedSigniture = createSigniture(privateKey, toBeSigned, sha256_Hmac);
        String token = String.format("hmac %s:%s:%s:%s", publicKey, nonce, seconds, signedSigniture);
        System.out.println("Token: " + token);
        return token;
    }
}

