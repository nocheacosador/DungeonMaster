package game.levels;

import java.util.Arrays;
import java.util.LinkedList;

import game.characters.Character;
import game.characters.CharacterFactory;
import processing.sound.SoundFile;
import rendering.RenderLayer;
import rendering.Renderer;
import sound.SoundsManager;
import tiles.TileMap;

public class ThirdLevel extends Level {
    private boolean bossFight;
    private SoundFile bossSong;
    private float bossSongAmp;
    private float scale;

    @Override
    protected void onLoad() {
        tileMap = new TileMap("assets/maps/GilgameshDungeon.map");

        Character boss = CharacterFactory.createCharacter("Demon");
        Character npc1 = CharacterFactory.createCharacter("Tiny Zombie");
        Character npc2 = CharacterFactory.createCharacter("Tiny Zombie");
        Character npc3 = CharacterFactory.createCharacter("Skelet");
        Character npc4 = CharacterFactory.createCharacter("Skelet");

        npc1.setSpawnSlot(tileMap.getSpawn(1));
        npc2.setSpawnSlot(tileMap.getSpawn(2));
        npc3.setSpawnSlot(tileMap.getSpawn(3));
        npc4.setSpawnSlot(tileMap.getSpawn(4));
        boss.setSpawnSlot(tileMap.getSpawn(9));

        player.setSpawnSlot(tileMap.getSpawn(0));
        player.health = player.maxHealth;

        npcs = new LinkedList<Character>(Arrays.asList(npc1, npc2, npc3, npc4, boss));

        for (Character c : npcs.toArray(new Character[0])) { 
            c.setDeathHandler(() -> {
                npcs.remove(c);
            });
        }

        bossSong = SoundsManager.getInstance().get("boss song");
        bossSongAmp = 0;
        bossFight = false;
        scale = 1.f;
    }

    @Override
    protected void onUnload() {
        if (bossSong != null && bossFight)
            bossSong.stop();
    }

    @Override
    protected void onUpdate(int millis) {
        if (npcs.isEmpty()) {
            callLevelCompleteAfter(millis, 2000);
        }
        
        tileMap.update(millis);
        player.update(millis, tileMap, null);
        for (Character c : npcs.toArray(new Character[0])) {
            c.update(millis, tileMap, player); 
        }
        
        if (player.getY() < -60.f) {
            scale = 0.5f;
        }
        else if (player.getY() > 190.f) {
            scale = 1.f;
        }
        else {
            scale = remap(player.getY(), -60.f, 190.f, 0.5f, 1.f);
            if (bossSong != null && !bossSong.isPlaying()) {
                bossFight = true;
                bossSong.loop();
            }
        }

        if (bossSong != null && bossSong.isPlaying() && bossSongAmp < 1.f) {
            bossSongAmp += 0.001f;
            if (bossSongAmp > 1.f) bossSongAmp = 1.f;
            bossSong.amp(bossSongAmp);
        }
    }

    @Override
    protected void onPause() {
        if (bossSong != null && bossFight)
            bossSong.pause();
    }

    @Override
    protected void onResume() {
        if (bossSong != null && bossFight)
            bossSong.loop();
    }

    @Override
    public String getName() {
        return "Level 3";
    }

    @Override
    protected void setCameraTransform(Renderer renderer) {
        for (RenderLayer l : renderer.layers) {
            if (l.name != "GUI") {
                l.setTransform(renderer.getWidth() / 2 - player.getX() * scale, renderer.getHeight() / 2 - player.getY() * scale, scale);
            }
        }
    }

    private static float remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }
}
