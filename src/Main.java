
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, SignatureException, NoSuchAlgorithmException {
        String input = "tank.bmp";
        String encrypt = "output.bmp";
        String decrypt = "result.bmp";
        String text = "input.txt";
        String sign = "signature.txt";
        String test = "test.bmp";
        System.out.print("Write password or write -1: ");
        String pswrd = new Scanner(System.in).nextLine();
        Key key = new Key(pswrd);
        while (true){
            System.out.println("Encrypt(1) / decrypt(2) / signature(3) / confirmSignature(4)" +
                    " / exit(0)");
            int mode = new Scanner(System.in).nextInt();
            if (mode == 1){
                RSA.encrypt(input, encrypt, key);
                System.out.println("Successful!\n");
                continue;
            }
            if (mode == 2){
                RSA.decrypt(encrypt, decrypt, key);
                System.out.println("Successful!\n");
                continue;
            }
            if (mode == 3){
                RSA.signature(text, sign, key);
                System.out.println("Successful!\n");
                continue;
            }
            if (mode == 4){
                System.out.println(RSA.confirmSignature(text, sign, key) + "\n");
                continue;
            }
            if (mode == 5){
                RSA.encryptBMP(input, encrypt, key);
                System.out.println("Successful!");
            }
            if (mode == 6){
                RSA.decryptBMP(encrypt, decrypt, key);
            }
            if (mode == 0){
                break;
            }
        }
    }
}
