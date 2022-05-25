package game.levels;

import java.util.Arrays;
import java.util.LinkedList;

import game.characters.Character;
import game.characters.CharacterFactory;
import tiles.TileMap;

public class FirstLevel extends Level {
    @Override
    protected void onLoad() {
        tileMap = new TileMap("assets/maps/castle_dungeon.map");
        
        Character npc1 = CharacterFactory.createCharacter("Big Zombie");
        Character npc2 = CharacterFactory.createCharacter("Tiny Zombie");

        player.setSpawnSlot(tileMap.getSpawn(0));
        
        npc1.setSpawnSlot(tileMap.getSpawn(1));
        npc2.setSpawnSlot(tileMap.getSpawn(2));

        npcs = new LinkedList<Character>(Arrays.asList(npc1, npc2));
        
        for (Character c : npcs.toArray(new Character[0])) { 
            c.setDeathHandler(() -> {
                npcs.remove(c);
            });
        }
    }

    @Override
    protected void onUnload() { }

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
    }

    @Override
    protected void onPause() { }

    @Override
    protected void onResume() { }

    @Override
    public String getName() {
        return "Level 1";
    }
}
