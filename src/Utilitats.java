import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Utilitats {

    //11000101011100010101000011010100010011010101000100011101000100010101

    static String FILE_PATH = "/home/47767573t/Gitprojects/M09FirmaDigital/src/Teoria.odt";
    static String ALGORITMO= "MD5"; //Algoritme per conseguir el hash

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        File archivo = new File (FILE_PATH);

        byte[] resultado = digestiona(archivo, ALGORITMO);

        String hashResultado = getHash(resultado);

        System.out.println("Resultado de codigo en "+ALGORITMO+": "+hashResultado);
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
        return md.digest();
    }

    public static String getHash (byte[] resultado){

        String z = "";

        for (int i = 0; i < resultado.length; i++) {
            z += Integer.toHexString((resultado[i] >> 4) & 0xf);
            z += Integer.toHexString(resultado[i] & 0xf);
        }
        return z;
    }


}
