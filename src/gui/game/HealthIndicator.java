package gui.game;

import game.characters.Character;
import processing.core.PConstants;
import processing.core.PGraphics;
import rendering.Renderable;
import rendering.Renderer;
import rendering.Texture;
import rendering.TextureManager;

public class HealthIndicator extends Renderable {
    private Character character;
    
    public float x = 0;
    public float y = 0;

    public float scale = 1;

    Texture full;
    Texture half;
    Texture empty;

    public HealthIndicator(Character c) {
        character = c;
        layer = Renderer.GUI;

        TextureManager tm = TextureManager.getInstance();
        full = tm.getTexture("ui_heart_full");
        half = tm.getTexture("ui_heart_half");
        empty = tm.getTexture("ui_heart_empty");
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(PGraphics g) {
        int h = character.health;
        float heartSize = 16 * scale;
        float totalWidth = character.maxHealth / 2 * heartSize;

        g.noStroke();
        g.noTint();
        g.textureMode(PConstants.IMAGE);
        g.beginShape(PConstants.QUADS);

        for (int i = 0; i < character.maxHealth / 2; i++) {
            Texture t = empty;
            if (h >= 2) {
                t = full;
            } 
            else if (h == 1) {
                t = half;
            }
            float X = x - totalWidth / 2 + i * heartSize;
            g.texture(t.texture);
            g.vertex(X , y, t.left, t.bottom);
            g.vertex(X + heartSize, y, t.right, t.bottom);
            g.vertex(X + heartSize, y - heartSize, t.right, t.top);
            g.vertex(X , y - heartSize, t.left, t.top);

            h -= 2;
        }
        g.endShape();
    }

    @Override
    public float getY() {
        return y;
    }
    
}
