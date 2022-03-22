package game.characters;

import java.util.List;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.sound.SoundFile;
import rendering.Renderable;
import rendering.Renderer;
import rendering.Texture;
import sound.SoundsManager;
import tiles.TileMap;
import tiles.TileMap.Slot;

public abstract class Character extends Renderable {
    public static final int STILL = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    
    public int scale = TileMap.TILE_SCALE;

    protected Texture[] idleTextures;
    protected Texture[] runTextures;
    protected Texture hitTexture;
    protected Texture currentTexture;

    protected SoundFile hitSound;
    protected SoundFile bloodSound;

    protected int updatePeriod = 125;
    
    private enum State {
        Idle, Running, Hit
    }

    @FunctionalInterface
    public interface DeathHandler {
        void onDeath();
    }

    protected float x = 0;
    protected float y = 0;

    protected State state = State.Idle;
    protected int horizontalDirection = STILL;
    protected int verticalDirection = STILL;
    protected int lookTo = STILL;
    protected int timeHit = -1;
    protected DeathHandler deathHandler;

    private int lastUpdate = 0;

    public float speed = 100.f;
    public float attackRadius = TileMap.TILE_SIZE * 1.5f;
    public int attackStrength = 4;
    public int attackPeriod = 1000;
    public int maxHealth = 10;
    public int health = 10;

    protected float boundsLeft = TileMap.TILE_SIZE * -0.45f;     // relative to origin
    protected float boundsTop = TileMap.TILE_SIZE * -0.4f;      // relative to origin
    protected float boundsRight = TileMap.TILE_SIZE * 0.45f;    // relative to origin
    protected float boundsBottom = TileMap.TILE_SIZE * 0.2f;    // relative to origin

    public Character() {
        layer = Renderer.MID;

        SoundsManager sm = SoundsManager.getInstance();
        hitSound = sm.get("hit");
        bloodSound = sm.get("blood");

        init();

        health = maxHealth;
    }

    public abstract void init();
    
    public float[] getPosition() {
        return new float[] { x, y };
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // returns array of 4 floats - left, top, right, bottom
    public float[] getBounds() {
        return new float[] { x + boundsLeft, y + boundsTop, x + boundsRight, y + boundsBottom };
    }

    public void setDeathHandler(DeathHandler deathHandler) {
        this.deathHandler = deathHandler;
    }

    public boolean contains(float x, float y) {
        float w = currentTexture.getWidth() * scale / 2.f;
        float h = currentTexture.getHeight() * scale;
        
        if (this.x - w < x && x < this.x + w && this.y - h < y && y < this.y) {
            return true;
        }
        return false;
    }

    public void attack(float x, float y, List<Character> characters) {
        float dx = this.x - x;
        float dy = this.y - y;
        
        if (dx * dx + dy * dy > this.attackRadius * this.attackRadius) {
            return;
        }

        for (Character c : characters.toArray(new Character[0])) {
            if (c == this) continue;
            if (c.contains(x, y)) {
                c.getDamage(attackStrength);
            }
        }
    }

    public void getDamage(int damage) {
        health -= damage;
        state = State.Hit;
        if (hitSound != null) hitSound.play();
        System.out.println(this.getClass().getName() + " got hit.");
    }

    public void update(int millis, TileMap map, Character player) {
        switch (state) {
            case Hit:
                currentTexture = hitTexture;
                if (timeHit == -1) timeHit = millis;
                if (millis - timeHit >= updatePeriod) {
                    state = State.Idle;
                    if (health <= 0) {
                        System.out.println(this.getClass().getName() + " has died.");
                        if (bloodSound != null) bloodSound.play();
                        if (deathHandler != null) deathHandler.onDeath();
                    }
                    timeHit = -1;
                }
                break;
            case Idle:
                currentTexture = idleTextures[millis / updatePeriod % idleTextures.length];
                break;
            case Running:
                currentTexture = runTextures[millis / updatePeriod % runTextures.length];

                int dt = millis - lastUpdate;
                if (dt > 250) dt = 0;   // if not updated for more than 250 milliseconds 
                
                float moveAmount = (float)(dt) / 1000 * speed;

                if (verticalDirection == UP) {
                    float newY = y - moveAmount;
                    boolean slotLeft = map.checkIfWalkable(x + boundsLeft, newY + boundsTop);
                    boolean slotRight = map.checkIfWalkable(x + boundsRight, newY + boundsTop);
                    
                    if (slotLeft && slotRight) {
                        y = newY;
                    }
                }
                else if (verticalDirection == DOWN) {
                    float newY = y + moveAmount;
                    boolean slotLeft = map.checkIfWalkable(x + boundsLeft, newY + boundsBottom);
                    boolean slotRight = map.checkIfWalkable(x + boundsRight, newY + boundsBottom);
                    
                    if (slotLeft && slotRight) {
                        y = newY;
                    }
                }
                
                if (horizontalDirection == LEFT) {
                    float newX = x - moveAmount;
                    boolean slotTop = map.checkIfWalkable(newX + boundsLeft, y + boundsTop);
                    boolean slotBottom = map.checkIfWalkable(newX + boundsLeft, y + boundsBottom);
                    
                    if (slotTop && slotBottom) {
                        x = newX;
                    }
                }
                else if (horizontalDirection == RIGHT) {
                    float newX = x + moveAmount;
                    boolean slotTop = map.checkIfWalkable(newX + boundsRight, y + boundsTop);
                    boolean slotBottom = map.checkIfWalkable(newX + boundsRight, y + boundsBottom);
                    
                    if (slotTop && slotBottom) {
                        x = newX;
                    }
                }

                break;
            default:
                break;
        }

        lastUpdate = millis;
    }
    
    public void setMove(int horizontal, int vertical) {
        if (state == State.Hit) return;

        if (horizontal == STILL && vertical == STILL) state = State.Idle;
        else                                          state = State.Running;

        this.horizontalDirection = horizontal;
        this.verticalDirection = vertical;

        if (horizontal != STILL) this.lookTo = horizontal;
    }

    public void setVerticalMove(int vertical) {
        setMove(horizontalDirection, vertical);
    }

    public void setHorizontalMove(int horizontal) {
        setMove(horizontal, verticalDirection);
    }
    
    public void render(PGraphics g) {
        g.noStroke();
        g.noTint();
        g.textureMode(PConstants.IMAGE);
        g.beginShape(PConstants.QUAD);

        float w = currentTexture.getWidth() * scale / 2.f;
        float h = currentTexture.getHeight() * scale;

        g.texture(currentTexture.texture);

        if (lookTo == RIGHT) {
            g.vertex(x - w, y, currentTexture.left, currentTexture.bottom);
            g.vertex(x + w, y, currentTexture.right, currentTexture.bottom);
            g.vertex(x + w, y - h, currentTexture.right, currentTexture.top);
            g.vertex(x - w, y - h, currentTexture.left, currentTexture.top);
        }
        else {
            g.vertex(x - w, y, currentTexture.right, currentTexture.bottom);
            g.vertex(x + w, y, currentTexture.left, currentTexture.bottom);
            g.vertex(x + w, y - h, currentTexture.left, currentTexture.top);
            g.vertex(x - w, y - h, currentTexture.right, currentTexture.top);
        }
        g.endShape();
    }

    public void setSpawnSlot(Slot slot) {
        x = slot.row * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2; 
        y = slot.column * TileMap.TILE_SIZE - TileMap.TILE_SIZE / 2; 
    }
}
