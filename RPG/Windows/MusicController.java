package Windows;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicController {
    private static Clip mainClip;
    private static Clip childClip;

    public static void playMain(String filepath) {
        mainClip = loadAndPlay(filepath);
    }
    // 停止並關閉主音樂
    public static void stopMain() {
        if (mainClip != null && mainClip.isRunning()) mainClip.stop();
    }
    // 暫停主音樂
    public static void resumeMain() {
        if (mainClip != null && !mainClip.isRunning()) mainClip.start();
    }
    // 停止並關閉主音樂
    public static void stopAndCloseMain() {
        if (mainClip != null) {
            mainClip.stop();
            mainClip.close();
        }
    }
    // 播放子音樂
    public static void playChild2(String filepath) {
        childClip = loadAndPlay(filepath);
    }
    // 停止並關閉子音樂
    public static void stopChild2() {
        if (childClip != null && childClip.isRunning()) childClip.stop();
    }
    // 暫停子音樂
    public static void stopAndCloseChild2() {
        if (childClip != null) {
            childClip.stop();
            childClip.close();
        }
    }
    public static void playChild3(String filepath) {
        childClip = loadAndPlay(filepath);
    }

    public static void stopChild3() {
        if (childClip != null && childClip.isRunning()) childClip.stop();
    }

    public static void stopAndCloseChild3() {
        if (childClip != null) {
            childClip.stop();
            childClip.close();
        }
    }
    public static void playChild1(String filepath) {
        childClip = loadAndPlay(filepath);
    }

    public static void stopChild1() {
        if (childClip != null && childClip.isRunning()) childClip.stop();
    }

    public static void stopAndCloseChild1() {
        if (childClip != null) {
            childClip.stop();
            childClip.close();
        }
    }
    // 載入音樂並播放
    private static Clip loadAndPlay(String filepath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}
