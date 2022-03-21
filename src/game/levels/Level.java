package game.levels;

import old_tiles.TileMap;

public abstract class Level {
    protected String name;
    protected TileMap map;

    public abstract void onUpdate();
    public abstract void onLoad();
    public abstract void onUnload();
    public void onPause() { }
    public void onResume() { }
    public String getName() {
        return name;
    }
}