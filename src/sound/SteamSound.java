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

    /**
     * initialize all of the sound resources
     */
    public static void initialize(){
        InputStream in = ResourceLoader.load(SteamSound.class,
                "src/resources/sound/levelmusic.wav", "asdf");
        musicBytes = readBytes(in);
        in = ResourceLoader.load(SteamSound.class, "src/resources/sound/PlayerFire.wav", "asdf");
        playerWeaponBytes = readBytes(in);
        in = ResourceLoader.load(SteamSound.class, "src/resources/sound/WeaponFire.wav", "asdf");
        enemyWeaponBytes = readBytes(in);
        loadMusic(musicBytes);
        loadPlayerWeapon(playerWeaponBytes);
        loadEnemyWeapon(enemyWeaponBytes);
    }

    /**
     * load the music file
     * @param rawData data for the music file
     */
    private static void loadMusic(byte[] rawData){
        musicLoopClip = new LoopEvent(new BlockingClip(rawData));
        musicLoopClip.initialize();
    }

    /**
     * load the player weapon file
     * @param rawData data for the weapon file
     */
    private static void loadPlayerWeapon(byte[] rawData){
        playerWeaponOneShotClip = new RestartEvent((new BlockingClip(rawData)));
        playerWeaponOneShotClip.initialize();
    }

    /**
     * load the enemy weapon file
     * @param rawData data for the enemy weapon file
     */
    private static void loadEnemyWeapon(byte[] rawData){
        enemyWeaponOneShotClip = new RestartEvent(new BlockingClip(rawData));
        enemyWeaponOneShotClip.initialize();
    }

    /**
     * play the playerWeapon clip
     */
    public static void playerWeaponFire(){
        playerWeaponOneShotClip.fire();
    }

    /**
     * play the enemyWeapon clip
     */
    public static void enemyWeaponFire(){
        enemyWeaponOneShotClip.fire();
    }

    /**
     * play the music clip
     */
    public static void musicLoopFire(){
        musicLoopClip.fire();
    }

    /**
     * read and store bytes from stream for easy access
     * @param in the file stream
     * @return the stream in bytes
     */
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
