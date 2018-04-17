import java.math.BigInteger;
import java.util.Random;

public class Key {
    private BigInteger n;
    private BigInteger fe;
    private BigInteger d;
    private BigInteger e;
    public Key(String password){
        BigInteger p;
        BigInteger q;
        Random random = new Random(new BigInteger(password.getBytes()).longValue());
        if (password.equals("-1")){
            p = BigInteger.probablePrime(64, new Random());
            q = BigInteger.probablePrime(64, new Random());
            e = BigInteger.probablePrime(64, new Random());
        }
        else {
            p = BigInteger.probablePrime(64, random);
            q = BigInteger.probablePrime(64, random);
            e = BigInteger.probablePrime(64, random);
        }
        n = p.multiply(q);
        fe = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        d = e.modInverse(fe);
    }
    public BigInteger getE(){
        return e;
    }
    public BigInteger getD(){
        return d;
    }
    public BigInteger getN(){
        return n;
    }
    public BigInteger getFe(){
        return fe;
    }
}
