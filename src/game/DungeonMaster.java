package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import game.characters.Character;
import game.characters.*;
import gui.game.HealthIndicator;
import gui.util.FontManager;
import gui.widgets.Label;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.Event;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;
import processing.sound.SoundFile;
import rendering.RenderLayer;
import rendering.Renderer;
import rendering.TextureManager;
import sound.SoundsManager;
import tiles.TileFactory;
import tiles.TileMap;

public class DungeonMaster extends PApplet {
    TileMap tileMap;
    Renderer renderer;
    PGraphics graphics;  

    final String playerCharacter = "Knight";

    BlockingQueue<Event> eventQueue;

    int drawCount = 0;

    // start menu objects
    final float[] TITLE_COLOR = new float[] { 255, 208, 0, 255 };
    final float[] BUTTON_COLOR = new float[] { 55, 204, 172, 255 };
    final float[] BUTTON_COLOR_ACTIVE = new float[] { 0, 255, 157, 255 };

    Label titleLabel;
    Label level1Button;
    Label level2Button;
    Label level3Button;

    Character leftCharacter;
    Character rightCharacter;

    SoundFile themeSong;
    SoundFile bossSong;
    float bossSongAmp = 0;

    // game objects
    Character player;
    List<Character> npcs;
    HealthIndicator healthIndicator;

    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;
    boolean wPressed;
    boolean sPressed;
    boolean aPressed;
    boolean dPressed;

    // game over objects
    Label gameOverLabel;
    Label restartButton;
    Label startMenuButton;

    // pause objects
    Label pauseLabel;
    Label continueButton;
    Label quitButton;

    // level complete objects
    Label levelCompleteLabel;
    Label nextLevelButton;

    enum State {
        StartMenu, GameRunning, GamePaused, GameOver, LevelComplete
    };

    enum Level {
        One, Two, Three, None;

        public Level next() {
            switch (this) {
                case One:   return Two;
                case Two:   return Three;
                default:    return None;
            }
        }
    }

    State gameState = State.StartMenu;
    Level level = Level.One;

    public void settings() {
        size(1200, 800, P2D);
    }

    public void setup() {
        background(0);
        textMode(CENTER);
        text("loading...", width / 2, height / 2);
    }

    public void load() {
        TextureManager.getInstance().load(this);
        FontManager.getInstance().load(this);
        SoundsManager.getInstance().load(this);         // takes ages
        TileFactory.getInstance().load();
        
        graphics = createGraphics(width, height, P2D);
        renderer = new Renderer(graphics);

        ((PGraphicsOpenGL)graphics).textureSampling(2); // set opengl texture sampling to NEAREST

        eventQueue = new LinkedBlockingQueue<Event>();

        createStartMenu();
        createGameOverMenu();
        createPauseMenu();
        createLevelCompleteMenu();
    }
  
    public void draw() {
        if (drawCount == 0) {
            background(0);
            textMode(CENTER);
            text("loading...", width / 2, height / 2);
            drawCount++;
            return;
        }
        else if (drawCount == 1) {
            load();
            drawCount++;
            return;
        }

        // handle events
        while (!eventQueue.isEmpty()) {
            try {
                Event event = eventQueue.take();

                switch (event.getFlavor()) {
                    case Event.MOUSE:
                        switch (event.getAction()) {
                            case MouseEvent.CLICK:
                                onMouseClicked((MouseEvent)event);
                                break;
                            default: 
                                break;
                        }
                        break;
                    case Event.KEY:
                        switch (event.getAction()) {
                            case KeyEvent.PRESS:
                                onKeyPressed((KeyEvent)event);
                                break;
                            case KeyEvent.RELEASE:
                                onKeyReleased((KeyEvent)event);
                                break;
                            default:  
                                break;
                        }
                        break;
                    default: 
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        background(0);
        
        switch (gameState) {
            case GameOver:
                gameOver();
                break;
            case GamePaused:
                gamePaused();
                break;
            case GameRunning:
                gameRunning();
                break;
            case StartMenu:
                startMenu();
                break;
            case LevelComplete:
                levelComplete();
                break;
        } 
        
        renderer.render();
        image(graphics, 0, 0);
    }

    private void createStartMenu() {
        titleLabel = new Label("Dungeon Master", width / 2, 200, 80, "Consolas Bold");
        titleLabel.align = CENTER;
        titleLabel.color = TITLE_COLOR;

        level1Button = new Label("Level 1", width / 2, 360, 50, "Consolas Bold");
        level1Button.align = CENTER;
        level1Button.color = BUTTON_COLOR;

        level2Button = new Label("Level 2", width / 2, 420, 50, "Consolas Bold");
        level2Button.align = CENTER;
        level2Button.color = BUTTON_COLOR;

        level3Button = new Label("Level 3", width / 2, 480, 50, "Consolas Bold");
        level3Button.align = CENTER;
        level3Button.color = BUTTON_COLOR;

        Random rand = new Random();
        
        String hcName = CharacterFactory.HEROES[rand.nextInt(CharacterFactory.HEROES.length)];
        String mcName = CharacterFactory.MONSTERS[rand.nextInt(CharacterFactory.MONSTERS.length)];

        rightCharacter = CharacterFactory.createCharacter(mcName);
        leftCharacter = CharacterFactory.createCharacter(hcName);

        rightCharacter.setPosition(width / 4, height / 3 * 2);
        leftCharacter.setPosition(width / 4 * 3, height / 3 * 2);

        rightCharacter.setLayer(Renderer.GUI);
        rightCharacter.scale = 6;
        leftCharacter.setLayer(Renderer.GUI);
        leftCharacter.scale = 6;

        themeSong = SoundsManager.getInstance().get("theme song");

        if (themeSong != null) themeSong.play();
    }

    private void createGameOverMenu() {
        gameOverLabel = new Label("See Ya on the Day of the Dead", width / 2, 200, 70, "Consolas Bold");
        gameOverLabel.align = CENTER;

        restartButton = new Label("No! I'll try again", width / 2, 420, 50, "Consolas Bold");
        restartButton.align = CENTER;
        restartButton.color = BUTTON_COLOR;

        startMenuButton = new Label("Ok...", width / 2, 480, 50, "Consolas Bold");
        startMenuButton.align = CENTER;
        startMenuButton.color = BUTTON_COLOR;
    }

    private void createPauseMenu() {
        pauseLabel = new Label("Tired?", width / 2, 200, 70, "Consolas Bold");
        pauseLabel.align = CENTER;

        continueButton = new Label("I'm never tired!", width / 2, 420, 50, "Consolas Bold");
        continueButton.align = CENTER;
        continueButton.color = BUTTON_COLOR;

        quitButton = new Label("Yeah... Get me out of here...", width / 2, 480, 50, "Consolas Bold");
        quitButton.align = CENTER;
        quitButton.color = BUTTON_COLOR;
    }

    private void createLevelCompleteMenu() {
        levelCompleteLabel = new Label("YAY! Level cleared of monsters", width / 2, 200, 70, "Consolas Bold");
        levelCompleteLabel.align = CENTER;
        
        nextLevelButton = new Label("Let's get some more!", width / 2, 420, 50, "Consolas Bold");
        nextLevelButton.align = CENTER;
        nextLevelButton.color = BUTTON_COLOR;
    }
    
    private void startMenu() {
        if (level1Button.contains(mouseX, mouseY)) level1Button.color = BUTTON_COLOR_ACTIVE;
        else                                       level1Button.color = BUTTON_COLOR;

        if (level2Button.contains(mouseX, mouseY)) level2Button.color = BUTTON_COLOR_ACTIVE;
        else                                       level2Button.color = BUTTON_COLOR;

        if (level3Button.contains(mouseX, mouseY)) level3Button.color = BUTTON_COLOR_ACTIVE;
        else                                       level3Button.color = BUTTON_COLOR;

        leftCharacter.update(millis(), null, null);
        rightCharacter.update(millis(), null, null);

        renderer.submit(titleLabel);
        renderer.submit(level1Button);
        renderer.submit(level2Button);
        renderer.submit(level3Button);

        renderer.submit(leftCharacter);
        renderer.submit(rightCharacter);
    }
        
    private void gameRunning() {
        // camera folowing player1
        
        //if (npcs.isEmpty()) {
            //gameState = State.LevelComplete;
        //}

        if (wPressed || upPressed)          player.setVerticalMove(Character.UP);
        else if (sPressed || downPressed)   player.setVerticalMove(Character.DOWN);
        else                                player.setVerticalMove(Character.STILL);

        if (aPressed || leftPressed)        player.setHorizontalMove(Character.LEFT);
        else if (dPressed || rightPressed)  player.setHorizontalMove(Character.RIGHT);
        else                                player.setHorizontalMove(Character.STILL);

        tileMap.update(millis());
        player.update(millis(), tileMap, null);
        
        float scale = 1.f;

        if (level == Level.Three) {
            // -60 - scale 0.5
            // 190 - scale 1
            
            if (player.getY() < -60.f) {
                scale = 0.5f;
            }
            else if (player.getY() > 190.f) {
                scale = 1.f;
            }
            else {
                scale = remap(player.getY(), -60.f, 190.f, 0.5f, 1.f);
                if (bossSong != null && !bossSong.isPlaying()) {
                    bossSong.play();
                }
            }

            if (bossSong != null && bossSong.isPlaying() && bossSongAmp < 1.f) {
                bossSongAmp += 0.001f;
                if (bossSongAmp > 1.f) bossSongAmp = 1.f;
                bossSong.amp(bossSongAmp);
            }
        }

        for (RenderLayer l : renderer.layers) {
            if (l.name != "GUI") {
                l.setTransform(width / 2 - player.getX() * scale, height / 2 - player.getY() * scale, scale);
            }
        }

        //npcs.forEach( (c) -> { 
        for ( Character c : npcs.toArray(new Character[0])) {
            c.update(millis(), tileMap, player); 
        }

        renderer.submit(tileMap);
        renderer.submit(player);
        renderer.submit(healthIndicator);
        //npcs.forEach( (c) -> {
        for ( Character c : npcs.toArray(new Character[0])) {
            renderer.submit(c);
        }
    }

    private void gamePaused() {
        if (continueButton.contains(mouseX, mouseY)) continueButton.color = BUTTON_COLOR_ACTIVE;
        else                                         continueButton.color = BUTTON_COLOR;

        if (quitButton.contains(mouseX, mouseY)) quitButton.color = BUTTON_COLOR_ACTIVE;
        else                                     quitButton.color = BUTTON_COLOR;

        renderer.submit(pauseLabel);
        renderer.submit(continueButton);
        renderer.submit(quitButton);
    }

    private void gameOver() {
        if (restartButton.contains(mouseX, mouseY)) restartButton.color = BUTTON_COLOR_ACTIVE;
        else                                        restartButton.color = BUTTON_COLOR;

        if (startMenuButton.contains(mouseX, mouseY)) startMenuButton.color = BUTTON_COLOR_ACTIVE;
        else                                          startMenuButton.color = BUTTON_COLOR;

        renderer.submit(gameOverLabel);
        renderer.submit(restartButton);
        renderer.submit(startMenuButton);
    }

    private void levelComplete() {
        if (nextLevelButton.contains(mouseX, mouseY)) nextLevelButton.color = BUTTON_COLOR_ACTIVE;
        else                                          nextLevelButton.color = BUTTON_COLOR;

        renderer.submit(levelCompleteLabel);
        renderer.submit(nextLevelButton);
    }

    private void loadLevel(Level level) {
        switch (level) {
            case One:
                loadLevel1();
                break;
            case Two:
                loadLevel2();
                break;
            case Three:
                loadLevel3();
                break;
            case None:
                throw new RuntimeException("level was None");
        }

        this.level = level;
    }
    
    private void loadLevel1() {
        tileMap = new TileMap("assets/maps/castle_dungeon.map");
        player = CharacterFactory.createCharacter(playerCharacter);

        healthIndicator = new HealthIndicator(player);
        healthIndicator.setPosition(width / 2, height - 10);
        healthIndicator.scale = 4;

        Character npc1 = CharacterFactory.createCharacter("Big Zombie");
        Character npc2 = CharacterFactory.createCharacter("Tiny Zombie");

        player.setSpawnSlot(tileMap.getSpawn(0));
        
        player.setDeathHandler(new Character.DeathHandler() {
            @Override
            public void onDeath() {
                System.out.println("You dead, nigga");
                gameState = State.GameOver;
            }
        });
        
        npc1.setSpawnSlot(tileMap.getSpawn(1));
        npc2.setSpawnSlot(tileMap.getSpawn(2));

        npcs = new LinkedList<Character>(Arrays.asList(npc1, npc2));
        
        for (Character c : npcs.toArray(new Character[0])) { 
            c.setDeathHandler(new Character.DeathHandler() {
                @Override
                public void onDeath() {
                    npcs.remove(c);
                }
            });
        }
    }

    private void loadLevel2() {
        tileMap = new TileMap("assets/maps/square_hall.map");
        player = CharacterFactory.createCharacter(playerCharacter);

        healthIndicator = new HealthIndicator(player);
        healthIndicator.setPosition(width / 2, height - 10);
        healthIndicator.scale = 4;

        Character npc1 = CharacterFactory.createCharacter("Big Zombie");
        Character npc2 = CharacterFactory.createCharacter("Tiny Zombie");
        Character npc3 = CharacterFactory.createCharacter("Tiny Zombie");
        Character npc4 = CharacterFactory.createCharacter("Skelet");
        Character npc5 = CharacterFactory.createCharacter("Skelet");

        player.setSpawnSlot(tileMap.getSpawn(0));
        
        player.setDeathHandler(new Character.DeathHandler() {
            @Override
            public void onDeath() {
                System.out.println("You dead, nigga");
                gameState = State.GameOver;
            }
        });
        
        npc1.setSpawnSlot(tileMap.getSpawn(1));
        npc2.setSpawnSlot(tileMap.getSpawn(3));
        npc3.setSpawnSlot(tileMap.getSpawn(4));
        npc4.setSpawnSlot(tileMap.getSpawn(5));
        npc5.setSpawnSlot(tileMap.getSpawn(6));

        npcs = new LinkedList<Character>(Arrays.asList(npc1, npc2, npc3, npc4, npc5));
        
        for (Character c : npcs.toArray(new Character[0])) { 
            c.setDeathHandler(new Character.DeathHandler() {
                @Override
                public void onDeath() {
                    npcs.remove(c);
                }
            });
        }
    }

    private void loadLevel3() {
        tileMap = new TileMap("assets/maps/GilgameshDungeon.map");
        player = CharacterFactory.createCharacter(playerCharacter);

        healthIndicator = new HealthIndicator(player);
        healthIndicator.setPosition(width / 2, height - 10);
        healthIndicator.scale = 4;

        Character npc1 = CharacterFactory.createCharacter("Demon");
        //Character npc2 = CharacterFactory.createCharacter("Tiny Zombie");

        player.setSpawnSlot(tileMap.getSpawn(0));
        
        player.setDeathHandler(new Character.DeathHandler() {
            @Override
            public void onDeath() {
                System.out.println("You dead, nigga");
                gameState = State.GameOver;
            }
        });
        
        bossSong = SoundsManager.getInstance().get("boss song");
        bossSongAmp = 0;
        bossSong.stop();
        npc1.setSpawnSlot(tileMap.getSpawn(1));
        //npc2.setSpawnSlot(tileMap.getSpawn(2));

        npcs = new LinkedList<Character>(Arrays.asList(npc1));
        
        for (Character c : npcs.toArray(new Character[0])) { 
            c.setDeathHandler(new Character.DeathHandler() {
                @Override
                public void onDeath() {
                    npcs.remove(c);
                }
            });
        }
    }

    private void onMouseClicked(MouseEvent e) {
        switch (gameState) {
            case GameRunning:
                if (e.getButton() == LEFT) {
                    float mouseX = (e.getX() - width / 2 + player.getX());
                    float mouseY = (e.getY() - height / 2 + player.getY());
                    player.attack(mouseX, mouseY, npcs);
                }
                break;
            
            case StartMenu:
                if (level1Button.contains(mouseX, mouseY)) {
                    loadLevel(Level.One);
                    gameState = State.GameRunning;
                }
                else if (level2Button.contains(mouseX, mouseY)) {
                    loadLevel(Level.Two);
                    gameState = State.GameRunning;
                }
                else if (level3Button.contains(mouseX, mouseY)) {
                    loadLevel(Level.Three);
                    gameState = State.GameRunning;
                }

                if (gameState != State.StartMenu) {
                    themeSong.stop();
                }
                break;

            case GameOver:
                if (restartButton.contains(mouseX, mouseY)) {
                    loadLevel(level);
                    gameState = State.GameRunning;
                }
                else if (startMenuButton.contains(mouseX, mouseY)) {
                    gameState = State.StartMenu;
                    createStartMenu();
                }
                break;

            case GamePaused:
                if (continueButton.contains(mouseX, mouseY)) {
                    gameState = State.GameRunning;
                }
                else if (quitButton.contains(mouseX, mouseY)) {
                    gameState = State.StartMenu;
                    createStartMenu();
                }
                break;
            
            case LevelComplete:
                if (nextLevelButton.contains(mouseX, mouseY)) {
                    level = level.next();
                    if (level == Level.None) {
                        gameState = State.StartMenu;
                        createStartMenu();
                    }
                    else {
                        loadLevel(level);
                        gameState = State.GameRunning;
                    }
                }
                break;
        }
        
    }
    
    private void onKeyPressed(KeyEvent e) {
        if (gameState != State.GameRunning) return;
        
        switch (e.getKeyCode()) {
            case 'W':
                wPressed = true;
                break;
            case 'S':
                sPressed = true;
                break;
            case 'A':
                aPressed = true;
                break;
            case 'D':
                dPressed = true;
                break;
            case UP:
                upPressed = true;
                break;
            case DOWN:
                downPressed = true;
                break;
            case LEFT:
                leftPressed = true;
                break;
            case RIGHT:
                rightPressed = true;
                break;
            case 'E':
                if ((e.getModifiers() & KeyEvent.CTRL) != 0) {
                    pause();
                    launchEditor();
                    resume();

                    tileMap.reload();
                }
                break;
            case 'P':
                gameState = State.GamePaused;
                break;
        }
    }

    private void onKeyReleased(KeyEvent e) {
        if (gameState != State.GameRunning) return;

        switch (e.getKeyCode()) {
            case 'W':
                wPressed = false;
                break;
            case 'S':
                sPressed = false;
                break;
            case 'A':
                aPressed = false;
                break;
            case 'D':
                dPressed = false;
                break;
            case UP:
                upPressed = false;
                break;
            case DOWN:
                downPressed = false;
                break;
            case LEFT:
                leftPressed = false;
                break;
            case RIGHT:
                rightPressed = false;
                break;
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        eventQueue.add(e);
    }

    public void keyPressed(KeyEvent e) {
        eventQueue.add(e);

        if (key == ESC) key = 0;
    }

    public void keyReleased(KeyEvent e) {
        eventQueue.add(e);
    }

    private void launchEditor() {
        if (gameState != State.GameRunning) return;
        
        Process theProcess = null;
        BufferedReader outStream = null;
        BufferedReader errStream = null;
    
        System.out.println("Tile Editor invoked");
        
        try
        {
            theProcess = Runtime.getRuntime().exec("java --enable-preview -jar TileEditor.jar \"" + tileMap.getFile().getAbsolutePath() + "\"");
        }
        catch(IOException e)
        {
            System.err.println("Error on exec() method");
            e.printStackTrace();
        }
            
        // read from the called program's standard output stream
        try
        {
            outStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ));  
            errStream = new BufferedReader(new InputStreamReader( theProcess.getErrorStream() ));

            while (theProcess.isAlive()) {
                while (!outStream.ready() && !errStream.ready() && theProcess.isAlive()) { }
                if (outStream.ready()) System.out.println("[TileEditor] [stdout] " + outStream.readLine());
                if (errStream.ready()) System.out.println("[TileEditor] [stderr] " + errStream.readLine());
            }
        }
        catch(IOException e)
        {
            System.err.println("Error on BufferedReader.readLine()");
            e.printStackTrace();  
        }

        System.out.println("Tile Editor finnished");
    }

    public static float remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    public static void main(String[] args) {
        PApplet.main(DungeonMaster.class);
    }
}