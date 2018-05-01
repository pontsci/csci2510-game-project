package sound;

import util.ResourceLoader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SteamSound
{

    private byte[] musicBytes;
    private byte[] playerWeaponBytes;
    private byte[] enemyWeaponBytes;

    public void initialize(){
        InputStream in = ResourceLoader.load(SteamSound.class,
                "src/resources/sound/levelmusic.wav", "asdf");
        musicBytes = readBytes(in);
        in = ResourceLoader.load(SteamSound.class, "src/resources/sound/WeaponFire.wav", "asdf");
        playerWeaponBytes = readBytes(in);
        enemyWeaponBytes = readBytes(in);
    }

    public static void playerWeapon(){

    }

    private byte[] readBytes(InputStream in) {
        try {
            BufferedInputStream buf = new BufferedInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read;
            while ((read = buf.read()) != -1) {
                out.write(read);
            }
            in.close();
            return out.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
