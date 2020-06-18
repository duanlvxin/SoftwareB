package common.utils.RSA;

import java.awt.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public class testRSA {
    public static void main(String[] args) throws Exception {

        //生成公私钥 openssl genrsa -out rsa_1024_priv.pem 1024&& openssl rsa -pubout -in rsa_1024_priv.pem -out rsa_1024_pub.pem
        //或者http://travistidwell.com/jsencrypt/demo/index.html 在线生成

        //上面生成的是pkcs1,java需要pkcs8，私钥要转为pkcs8格式
        //pkcs1 转pkcs8  openssl pkcs8 -topk8 -inform PEM -in private.key -outform pem -nocrypt -out pkcs8.pem
        //pkcs8 转pkcs1  openssl rsa -in pkcs8.pem -out pri_key.pem

//        String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCLh48Wq5I6atYb0kurC09Q535\n" +
//                "b37dZpJR+UElX5cEfNMiVQwcK3IU/74lwWC6hCC90iFPjIcK+p/+XA2FBSQDgcW1\n" +
//                "24rh+KTMOgesxLyYrjnrfPyUUuBm3MUQnTAytV6IAVMvq7s0N+R/zXKxpYBypMgD\n" +
//                "l5XYbOvUE5SJwTvhIQIDAQAB".replace("\n","");
//        String  privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMKz0Z18guQrLPdC\n" +
//                "JdtPK0vR7SpeBie0LBELf7atQYyKJ3WgX6CNGmrFDlsQWp17X0T/s9KzaHbpc2I2\n" +
//                "PvlZoBz7CHnfDGwi7p/sI9dVNeOdfFa5drsqmK60rGrj6BzveGgPDJJoXPb5Lbfb\n" +
//                "MDN34hG/QGfuDsEkfUbYYB+apC0ZAgMBAAECgYEAuFiPWGBCggyLJ5T+yPXdlY0u\n" +
//                "05VwmHkT3BOaGXlTfeB02f89a4MOBxeKrxf94+ui2W6NcSqi9yu0LsITv/1nBUK4\n" +
//                "oBOjC75garFjYjSDTdGLD96BDOahVzqKMY8eE9XEZQEX4bhj6ZDa5q8nvGnkant0\n" +
//                "o5yZzGeT2ZMK69SMdjECQQDfR2TBcNHhJLp3I8qzuV8iYsZnyUEU+I47DRRv9qKl\n" +
//                "1WWAW3EJ7cyv69OkAQHwJy2BDQDuSFgn6T/lx4N1yCmlAkEA3zxXROmK5T7shE1H\n" +
//                "Ymt3AdTSFnAcR/lKbd/+iuIhaN77MOzEaKvlDxRv5Jh4G+lLkwHFSAaRYRPmkv6q\n" +
//                "mBqTZQJBAN3uA6r2rdaggCrty4wqg/IUxerhMqxahl0Rmi/TsUUuQA5+VXQuBpcR\n" +
//                "y7KnQbrn5iXwu+0cwWsiP93wGq3Wv/UCQCvw0Ky7258sN5oDLB3vUUmG/qN0Bd0U\n" +
//                "8NWX1Z64zCK8YW1L7Y086KWDPFMev+WekkWpf4+h21PkeupMPoAaGxECQGDnx0/U\n" +
//                "A972Y4wZfP+jjCG74UdqSH51+FRrLOzLUuhvQSiKYmSpZXpWzOKa0ZnmAuIfjWIe\n" +
//                "n/VmjuQ6iaLiWR0=".replace("\n","");

//        Map<String,String> keyPair = RSAUtils.createKeys(1024);
//        String privateKey = keyPair.get("privateKey");
//        String publicKey = keyPair.get("publicKey");
//
//        String passwd = "123456";
//        String encodedData = RSAUtils.publicEncrypt(passwd,RSAUtils.getPublicKey(publicKey));
//        System.out.println("加密后:"+encodedData);
//        String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
//        System.out.println("解密后文字: \r\n" + decodedData);

          String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHC23mLCsGlvPorMqU5GkYOPrY_YXlZc_rKORmGUaAi4IaAm_unEgeHFYyUPcxgX9FrxaWaAhgMdaodcl6faz_ecyJHtXXE4K5AJ1cZ1y5tLnLmobbzs_iS_rReZsMXZej--stZSfFe2yq8LaTTL8CdUWie_2D0bm6Obqj01zSQIDAQAB";
          String text = "123456";
          String encodedData = RSAUtils.publicEncrypt(text,RSAUtils.getPublicKey(publicKey));
          System.out.println("加密后:"+encodedData);
    }
}
