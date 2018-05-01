package sound;

import util.ResourceLoader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SteamSound
{

    private static byte[] musicBytes;
    private static byte[] playerWeaponBytes;
    private static byte[] enemyWeaponBytes;
    private static LoopEvent musicLoopClip;
    private static RestartEvent playerWeaponOneShotClip;
    private static RestartEvent enemyWeaponOneShotClip;

    public static void initialize(){
        InputStream in = ResourceLoader.load(SteamSound.class,
                "src/resources/sound/levelmusic.wav", "asdf");
        musicBytes = readBytes(in);
        in = ResourceLoader.load(SteamSound.class, "src/resources/sound/PlayerFire.wav", "asdf");
        playerWeaponBytes = readBytes(in);
        in = ResourceLoader.load(SteamSound.class, "src/resources/sound/WeaponFire.wav", "asdf");
        enemyWeaponBytes = readBytes(in);
        loadMusic(musicBytes);
        loadWeapons(playerWeaponBytes);
    }

    private static void loadMusic(byte[] rawData){
        musicLoopClip = new LoopEvent(new BlockingClip(rawData));
        musicLoopClip.initialize();
    }

    private static void loadWeapons(byte[] rawData){
        playerWeaponOneShotClip = new RestartEvent((new BlockingClip(rawData)));
        playerWeaponOneShotClip.initialize();

        enemyWeaponOneShotClip = new RestartEvent(new BlockingClip(rawData));
        enemyWeaponOneShotClip.initialize();

    }

    public static void playerWeaponFire(){
        playerWeaponOneShotClip.fire();
    }

    public static void enemyWeaponFire(){
        enemyWeaponOneShotClip.fire();
    }

    public static void musicLoopFire(){
        musicLoopClip.fire();
    }

    private static byte[] readBytes(InputStream in) {
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
