
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;

public class RSA {
    public static void encrypt (String inputFilename, String outputFilename, Key key) throws IOException {
        BigInteger e = key.getE();
        BigInteger n = key.getN();
        File input = new File(inputFilename);
        File output = new File(outputFilename);
        long fileSize = input.length();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        StringBuilder result = new StringBuilder();
        ArrayList<BigInteger> arrayList = new ArrayList<>();
        for (int i = 0; i < fileSize; i++) {
            int m = fis.read();
            BigInteger value = new BigInteger(String.valueOf(m));
            arrayList.add(Utils.modPow(value, e, n));
        }
        result.append(Arrays.toString(arrayList.toArray()));
        result.deleteCharAt(0);
        result.deleteCharAt(result.length() - 1);
        result.append("\n");
        byte[] data = result.toString().getBytes();
        fos.write(data);
    }
    public static void decrypt (String inputFilename, String outputFilename, Key key) throws IOException {
        BigInteger d = key.getD();
        BigInteger n = key .getN();
        File input = new File(inputFilename);
        File output = new File(outputFilename);
        long fileSize = input.length();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        byte[] test = new byte[(int)fileSize];
        for (int i = 0; i < fileSize; i++) {
            test[i] = (byte)fis.read();
        }
        String[] arrayList = Utils.bytesToStringList(test)
                .split("\\s*,\\s*");
        int[] data = new int[arrayList.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = new BigInteger(arrayList[i]).modPow(d, n).intValue();
        }
        fos.write(Utils.toByteArray(data));
    }

    public static void signature (String messageFile, String signatureFile, Key key) throws IOException, NoSuchAlgorithmException, SignatureException {
        BigInteger d = key.getD();
        BigInteger n = key.getN();
        File input = new File(messageFile);
        File output = new File(signatureFile);
        long fileSize = input.length();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        StringBuilder result = new StringBuilder();
        ArrayList<BigInteger> arrayList = new ArrayList<>();
        byte[] test = new byte[(int)fileSize];
        for (int i = 0; i < fileSize; i++) {
            test[i] = (byte)fis.read();
        }
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(test);
        byte[] data = crypt.digest();
        for (int i = 0; i < data.length; i++) {
            int m = data[i] & 0xff;
            BigInteger value = new BigInteger(String.valueOf(m));
            arrayList.add(Utils.modPow(value, d, n));
        }
        result.append(Arrays.toString(arrayList.toArray()));
        result.deleteCharAt(0);
        result.deleteCharAt(result.length() - 1);
        result.append("\n");
        fos.write(result.toString().getBytes());
    }

    public static boolean confirmSignature (String messageFile, String signatureFile, Key key) throws IOException, NoSuchAlgorithmException {
        BigInteger e = key.getE();
        BigInteger n = key.getN();
        File input = new File(signatureFile);
        File message = new File(messageFile);
        long fileSize = input.length();
        long messageSize = message.length();
        FileInputStream fis = new FileInputStream(input);
        FileInputStream fism = new FileInputStream(message);
        byte[] data = new byte[(int) fileSize];
        for (int i = 0; i < (int) fileSize; i++) {
            data[i] = (byte)fis.read();
        }
        String[] arrayList = Utils.bytesToStringList(data)
                .split("\\s*,\\s*");
        data = new byte[arrayList.length];
        for (int i = 0; i < data.length; i++) {
            BigInteger value = new BigInteger(arrayList[i]);
            data[i] = (byte)Utils.modPow(value, e, n).intValue();
        }
        int[] dataMessage = new int[(int)messageSize];
        for (int i = 0; i < dataMessage.length; i++) {
            dataMessage[i] = fism.read();
        }
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(Utils.toByteArray(dataMessage));
        return new BigInteger(1, md.digest()).toString(16).equals(new BigInteger(1, data).toString(16));
    }

    public static void encryptBMP (String inputFilename, String outputFilename, Key key) throws IOException {
        BigInteger e = key.getE();
        BigInteger n = key.getN();
        File input = new File(inputFilename);
        File output = new File(outputFilename);
        long fileSize = input.length();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        StringBuilder result = new StringBuilder();
        ArrayList<BigInteger> arrayList = new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            BigInteger value = BigInteger.valueOf(fis.read());
            //arrayList.add(value);
            fos.write(value.intValue());
        }
        for (int i = 54; i < fileSize; i++) {
            int m = fis.read();
            BigInteger value = new BigInteger(String.valueOf(m));
            Color color = new Color(value.intValue());
            value = BigInteger.valueOf(color.getRGB()).modPow(e, n);
            fos.write(value.intValue());
        }
        /*result.append(Arrays.toString(arrayList.toArray()));
        result.deleteCharAt(0);
        result.deleteCharAt(result.length() - 1);
        result.append("\n");
        byte[] data = result.toString().getBytes();
        fos.write(data);*/
    }

    public static void decryptBMP (String inputFilename, String outputFilename, Key key) throws IOException {
        BigInteger d = key.getD();
        BigInteger n = key .getN();
        File input = new File(inputFilename);
        File output = new File(outputFilename);
        long fileSize = input.length();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        byte[] test = new byte[(int)fileSize];
        for (int i = 0; i < fileSize; i++) {
            test[i] = (byte)fis.read();
        }
        String[] arrayList = Utils.bytesToStringList(test)
                .split("\\s*,\\s*");
        int[] data = new int[arrayList.length];
        for (int i = 0; i < 54; i++) {
            data[i] = new BigInteger(arrayList[i]).intValue();
        }
        for (int i = 54; i < data.length; i++) {
            data[i] = new BigInteger(arrayList[i]).modPow(d, n).intValue();
        }
        fos.write(Utils.toByteArray(data));
    }
}
