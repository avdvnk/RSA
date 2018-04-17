import java.io.*;
import java.math.BigInteger;

class Utils {
    public static BigInteger modPow(BigInteger value, BigInteger exponent, BigInteger m) {
        if (exponent.equals(BigInteger.ZERO))
            return BigInteger.ONE;

        BigInteger z = modPow(value, exponent.shiftRight(1), m);
        BigInteger zPowTwo = z.pow(2).mod(m);
        if (exponent.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return zPowTwo;
        return zPowTwo.multiply(value).mod(m);
    }

    public static String bytesToStringList(byte[] bytes) {
        BufferedReader r;
        try {
            r = new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(bytes),
                            "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            try {
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    return line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void writeTestFile () throws IOException {
        File output = new File("test.bmp");
        FileOutputStream fos = new FileOutputStream(output);
        byte[] data = {11, 43, 127, -44, 31};
        fos.write(data);
    }

    public static byte[] toByteArray (int[] array){
        byte[] result = new byte[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte)array[i];
        }
        return result;
    }


}
