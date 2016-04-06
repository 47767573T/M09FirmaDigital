
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Main {

    //TODO: definir la private.key
    public static final String FICHERO_LLAVE_PRIVADA = "private.key";
    //TODO fin

    public static final String FICHERO_PLANO = "/home/47767573t/Gitprojects/M09FirmaDigital/src/Teoria.odt";
    public static final String FICHERO_FIRMADO = "/home/47767573t/Gitprojects/M09FirmaDigital/src/TeoriaFirmada.odt";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException
            , InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        KeyPair keyPair = null;
        PrivateKey priK = null;

        File f = new File(FICHERO_PLANO);

/*      keyPair = Utilitats.generateKey();
        priK = keyPair.getPrivate();
*/

        if(!Utilitats.areKeysPresent()){
            keyPair = Utilitats.generateKey();
            priK = keyPair.getPrivate();
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
        /*write(file o string, bytes) file <-- bytes*/

        Utilitats.write(
                FICHERO_FIRMADO
                ,Utilitats.concatenateByteArrays(
                        Utilitats.read(f)
                        ,encryptdigestionat
                )
        );
    }



}