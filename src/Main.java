
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * Created by 47767573t on 30/03/16.
 */
public class Main {

        public static final String PRIVATE_KEY_FILE = "private.key";

        public static final String FICHERO_PLANO = "Teoria.odt";
        public static final String FICHERO_FIRMADO = "TeoriaFirmada.odt";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {

        KeyPair keyPair = null;
        PrivateKey prik = null;

        File f = new File(FICHERO_PLANO);


            keyPair = Utils.generateKey();
            prik = keyPair.getPrivate();

            ObjectInputStream inputStream = null;
            inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            prik = (PrivateKey) inputStream.readObject();


        byte[] digestionat = Utilitats.digestiona(f,"MD5");
        byte[] encryptdigestionat = Utils.signar(digestionat,prik);
        System.out.println("Longitud del fitxer: "+f.length());
        System.out.println("Longitud de la firma: "+encryptdigestionat.length);
        Utils.write(FITXER_SIGNAT,Utils.concatenateByteArrays(Utils.read(f),encryptdigestionat));
    }

}