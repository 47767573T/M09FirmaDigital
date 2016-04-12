
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.security.*;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Main {

    //TODO: definir la private.key
    public static final String FICHERO_LLAVE_PRIVADA = "C:\\Users\\Moises\\Desktop\\Git\\M09FirmaDigital\\src\\LlavePrivada.odt";
    //TODO fin

    public static final String FICHERO_PLANO = "C:\\Users\\Moises\\Desktop\\Git\\M09FirmaDigital\\src\\Teoria.odt";
    public static final String FICHERO_FIRMADO = "C:\\Users\\Moises\\Desktop\\Git\\M09FirmaDigital\\src\\TeoriaFirmada.odt";
    public static PublicKey publicKey = null;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException
            , InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, SignatureException {

        KeyPair keyPair = null;
        PrivateKey priK = null;

        File f = new File(FICHERO_PLANO);

        if(!Utilitats.areKeysPresent()){
            keyPair = Utilitats.generateKey(FICHERO_LLAVE_PRIVADA);
            priK = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }else{
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(FICHERO_LLAVE_PRIVADA));
            priK = (PrivateKey) ois.readObject();
        }

        byte[] bufferDigestion = Utilitats.digestiona(f,"MD5");
        byte[] encryptdigestionat = Utilitats.signar(bufferDigestion,priK);
        System.out.println("Longitud del fitxer: "+f.length());
        System.out.println("Longitud de la firma: "+encryptdigestionat.length);
        Utilitats.read(f);

        Utilitats.write(FICHERO_FIRMADO, Utilitats.concatenateByteArrays(Utilitats.read(f), encryptdigestionat));

        byte[] FirmaParaVerificar = Utilitats.extraerFirma(Paths.get(FICHERO_FIRMADO));
        boolean verificacion = Utilitats.verificarFirma(publicKey, FirmaParaVerificar);

        System.out.println("Resultado de verificacion: "+ verificacion);
    }



}