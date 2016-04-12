import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Scanner;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Utilitats {

    static String FILE_PATH = "/home/47767573t/Gitprojects/M09FirmaDigital/src/Teoria.odt";
    static String ALGORITMO_HASH = "MD5";            //Algoritme per conseguir el hash
    static String ALGORITMO_ENCRYPTAR = "RSA";
    static int MEDIDA_CLAVE = 1024;

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

        byte[] resultadoDigestion = md.digest();

        return resultadoDigestion;
    }

    public static byte[] signar(byte[] digestionado, PrivateKey priK) throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] bufferCifrado = null;

        Cipher cifrador = Cipher.getInstance("RSA");
        cifrador.init(Cipher.ENCRYPT_MODE, priK);

        bufferCifrado = cifrador.doFinal(digestionado);
        System.out.println("texto cifrado");
        //mostrarBuffer(bufferCifrado);

        return bufferCifrado;
    }

    public static byte[] desencriptar(byte[] digestionado, PublicKey pubK ) throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] bufferDescifrado = null;

        Cipher descifrador = Cipher.getInstance("RSA");
        descifrador.init(Cipher.DECRYPT_MODE, pubK);

        bufferDescifrado = descifrador.doFinal(digestionado);
        System.out.println("texto descifrado");
        mostrarBuffer(bufferDescifrado);

        return bufferDescifrado;
    }

    public static KeyPair generateKey() throws NoSuchAlgorithmException {

        KeyPairGenerator claveGenerada = KeyPairGenerator.getInstance(ALGORITMO_ENCRYPTAR);
        claveGenerada.initialize(MEDIDA_CLAVE);

        KeyPair claveRSA = claveGenerada.generateKeyPair();
        System.out.println("Clave generada");

        return claveRSA;
    }

    public static boolean areKeysPresent(){

        boolean hayKey = false;
        File fichero = new File(Main.FICHERO_LLAVE_PRIVADA);

        if (fichero.exists()) {
            hayKey = true;
            System.out.println("Clave privada existe");
        }

        return hayKey;
    }

    public static void mostrarBuffer(byte[] buffer){

        System.out.write(buffer, 0, buffer.length);
    }

    public static byte[] read (File fichero) throws IOException {

        return Files.readAllBytes(Paths.get(fichero.getAbsolutePath()));
    }

    public static byte[] concatenateByteArrays(byte[] buffers1, byte[] buffers2) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream( );
        bos.write (buffers1);
        bos.write (buffers2);
        return bos.toByteArray();
    }

    public static void write(String fichero, byte[] arrayConcatenada) throws IOException {

        File f = new File(fichero);

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(arrayConcatenada);
        fos.close();
    }

    public static byte[] extraerFirma (Path fichero, int longitudFirma) throws IOException {

        byte[] ficheroEnBytes = Files.readAllBytes(fichero);
        int longituFichero = ficheroEnBytes.length;

        byte[] firmaEnBytes =


        return firmaEnBytes;

    }

}
