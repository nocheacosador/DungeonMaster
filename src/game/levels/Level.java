package game.levels;

import game.characters.Character;
import java.util.List;

import rendering.ComplexRenderable;
import rendering.RenderLayer;
import rendering.Renderer;
import tiles.TileMap;

public abstract class Level implements ComplexRenderable {
    
    @FunctionalInterface
    public interface LevelCompleteCallback {
        void call(Level l);
    }
    
    protected TileMap tileMap;
    protected Character player;
    protected List<Character> npcs;

    private boolean callLevelCompleteCallback;
    private int levelCompleteCallbackDelay;
    private int levelCompleteCallbackRequestTimepoint;
    protected LevelCompleteCallback levelCompleteCallback; 

    protected abstract void onLoad();
    
    protected abstract void onUnload();
    
    protected abstract void onUpdate(int millis);
    
    protected abstract void onPause();
    
    protected abstract void onResume();

    public void load() {
        onLoad();
        callLevelCompleteCallback = false;
        System.out.println("[" + getName() + "] loaded." );
    }
    
    public void unload() {
        onUnload();
        System.out.println("[" + getName() + "] unloaded." );
    }
    
    public void update(int millis) {
        onUpdate(millis);

        if (callLevelCompleteCallback && millis - levelCompleteCallbackRequestTimepoint > levelCompleteCallbackDelay) {
            if (levelCompleteCallback != null) {
                levelCompleteCallback.call(this);
            }
            System.out.println("[" + getName() + "] Level complete.");
        }
    }
    
    public void pause() {
        onPause();
        System.out.println("[" + getName() + "] paused." );
    }
    
    public void resume() {
        onResume();
        System.out.println("[" + getName() + "] resumed." );
    }
    
    public abstract String getName();

    protected void setCameraTransform(Renderer renderer) {
        for (RenderLayer l : renderer.layers) {
            if (l.name != "GUI") {
                l.setTransform(renderer.getWidth() / 2 - player.getX(), renderer.getHeight() / 2 - player.getY(), 1);
            }
        }
    }

    @Override
    public void complexSubmit(Renderer renderer) {
        setCameraTransform(renderer);
    
        renderer.submit(tileMap);
        
        if (npcs != null) {
            for (Character c : npcs.toArray(new Character[0])) {
                renderer.submit(c);
            }
        }
    }

    public String getTileMapFilePath() {
        return tileMap.getFile().getAbsolutePath();
    }

    public void setPlayerCharacter(Character player) {
        this.player = player;
    }

    public void setLevelCompleteCallback(LevelCompleteCallback callback) {
        this.levelCompleteCallback = callback;
    }

    protected void callLevelCompleteAfter(int millis, int delay) {
        if (!callLevelCompleteCallback) {
            levelCompleteCallbackRequestTimepoint = millis;
            callLevelCompleteCallback = true;
            levelCompleteCallbackDelay = delay;
        }
    }

    public List<Character> getNPCS() {
        return npcs;
    }
}