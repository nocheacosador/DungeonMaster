package sound;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.sound.Sound;
import processing.sound.SoundFile;

public class SoundsManager {
    private class SoundFileInfo {
        String name;
        String path;
        boolean cache;

        SoundFileInfo(String name, String path, boolean cache) {
            this.name = name;
            this.path = path;
            this.cache = cache;
        }
    }

    public final SoundFileInfo[] SOUND_FILES = {
        new SoundFileInfo( "hit", "assets/sounds/small-hit.mp3", true ),
        new SoundFileInfo( "blood", "assets/sounds/blood.mp3", true ),
        new SoundFileInfo( "theme song", "assets/sounds/Visigoth - Dungeon Master.mp3", true ),
        new SoundFileInfo( "boss song", "assets/sounds/Pentakill - Deathfire Grasp.mp3", true )
    };

    private Map<String, SoundFile> sounds;
    private Sound sound;

    private static SoundsManager instance;
    
    private SoundsManager() {}

    public static SoundsManager getInstance() {
        if (instance == null) {
            instance = new SoundsManager();
        }

        return instance;
    }

    public void load(PApplet applet) {
        sounds = new HashMap<String, SoundFile>();
        sound = new Sound(applet);
        
        for (SoundFileInfo si : SOUND_FILES) {
            SoundFile sf = new SoundFile(applet, si.path, si.cache);
            sounds.put(si.name, sf);
        }
    }

    public SoundFile get(String key) {
        return sounds.get(key);
    }

    public void setVolume(float volume) {
        sound.volume(volume);
    }
}
