import java.io.*;
import java.security.*;
import java.util.Scanner;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Utilitats {

    //11000101011100010101000011010100010011010101000100011101000100010101

    static String FILE_PATH = "/home/47767573t/Gitprojects/M09FirmaDigital/src/Teoria.odt";
    static String ALGORITMO_HASH = "MD5";            //Algoritme per conseguir el hash
    static String ALGORITMO_ENCRYPTAR = "RSA";
    static int MEDIDA_CLAVE = 256;

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        File archivo = new File (FILE_PATH);

        byte[] resultado = digestiona(archivo, ALGORITMO_HASH);

        String hashResultado = getHash(resultado);

        System.out.println("Resultado de codigo en "+ALGORITMO_HASH+": "+hashResultado);
    }

    public static byte[] digestiona(File f, String algoritme) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algoritme);

        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[1];
        int caracter;

        caracter = is.read(buffer);

        while( caracter != -1 ) {
            md.update(buffer);
            caracter = is.read(buffer);
        }

        byte[] res = md.digest();
        String resStr = getHash(res);

        return md.digest();

        System.out.println(resStr);
    }

    public static String getHash (byte[] resultado){

        String z = "";

        for (int i = 0; i < resultado.length; i++) {
            z += Integer.toHexString((resultado[i] >> 4) & 0xf);
            z += Integer.toHexString(resultado[i] & 0xf);
        }
        return z;
    }

    public static KeyPair generateKey() throws NoSuchAlgorithmException {

        KeyPairGenerator claveGenerada = KeyPairGenerator.getInstance(ALGORITMO_ENCRYPTAR);
        claveGenerada .initialize(MEDIDA_CLAVE);

        KeyPair claveRSA = claveGenerada.generateKeyPair();

        return claveRSA;
    }


}
