/*
Lukas Zajončkovskis
*/

package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import game.characters.Character;
import game.levels.FirstLevel;
import game.levels.Level;
import game.levels.SecondLevel;
import game.levels.ThirdLevel;
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
import rendering.Renderer;
import rendering.TextureManager;
import sound.SoundsManager;
import tiles.TileFactory;
import tiles.TileMap;

public class DungeonMaster extends PApplet {
    TileMap tileMap;
    Renderer renderer;
    PGraphics graphics;  

    String playerCharacter = "Knight";

    BlockingQueue<Event> eventQueue;

    int drawCount = 0;

    // start menu objects
    final float[] TITLE_COLOR = new float[] { 255, 208, 0, 255 };
    final float[] BUTTON_COLOR = new float[] { 55, 204, 172, 255 };
    final float[] BUTTON_COLOR_ACTIVE = new float[] { 0, 255, 157, 255 };

    Label titleLabel;
    List<Label> levelButtons;

    Character leftCharacter;
    Character rightCharacter;

    SoundFile themeSong;

    // game objects
    Character player;
    HealthIndicator healthIndicator;
    
    int levelIndex = 0;
    Level[] levels = {
        new FirstLevel(),
        new SecondLevel(),
        new ThirdLevel()
    };

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

    State gameState = State.StartMenu;

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
        
        SoundsManager.getInstance().setVolume(0.5f);

        graphics = createGraphics(width, height, P2D);
        renderer = new Renderer(graphics);

        ((PGraphicsOpenGL)graphics).textureSampling(2); // set opengl texture sampling to NEAREST

        eventQueue = new LinkedBlockingQueue<Event>();

        createStartMenu();
        createGameOverMenu();
        createPauseMenu();
        createLevelCompleteMenu();

        for (Level level : levels) {
            level.setLevelCompleteCallback( (l) -> {
                l.unload();
                gameState = State.LevelComplete;
            });
        }

        player = CharacterFactory.createCharacter(playerCharacter);
        player.setDeathHandler( () -> {
            System.out.println("You dead, nigga");
            gameState = State.GameOver;
        });

        healthIndicator = new HealthIndicator(player);
        healthIndicator.setPosition(width / 2, height - 10);
        healthIndicator.scale = 4;
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
            Event event = eventQueue.poll();

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

        levelButtons = new LinkedList<Label>();
        int levelButtonY = 360;
        for (Level level : levels) {
            Label levelButton = new Label(level.getName(), width / 2, levelButtonY, 50, "Consolas Bold");
            levelButton.align = CENTER;
            levelButton.color = BUTTON_COLOR;
            levelButtons.add(levelButton);
            levelButtonY += 60;
        }

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
        for (Label levelButton : levelButtons) {
            if (levelButton.contains(mouseX, mouseY)) levelButton.color = BUTTON_COLOR_ACTIVE;
            else                                      levelButton.color = BUTTON_COLOR;
        }

        leftCharacter.update(millis(), null, null);
        rightCharacter.update(millis(), null, null);

        renderer.submit(titleLabel);
        for (Label levelButton : levelButtons) {
            renderer.submit(levelButton);
        }
        renderer.submit(leftCharacter);
        renderer.submit(rightCharacter);
    }
        
    private void gameRunning() {
        if (wPressed || upPressed)          player.setVerticalMove(Character.UP);
        else if (sPressed || downPressed)   player.setVerticalMove(Character.DOWN);
        else                                player.setVerticalMove(Character.STILL);

        if (aPressed || leftPressed)        player.setHorizontalMove(Character.LEFT);
        else if (dPressed || rightPressed)  player.setHorizontalMove(Character.RIGHT);
        else                                player.setHorizontalMove(Character.STILL);
        
        levels[levelIndex].update(millis());
        
        renderer.submit(levels[levelIndex]);
        renderer.submit(player);
        renderer.submit(healthIndicator);
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

    private void loadLevel(int index) {
        if (index < 0 || index >= levels.length) 
            throw new RuntimeException("Level index was out of range");
        
        levelIndex = index;
        loadLevel(levels[index]);

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        wPressed = false;
        sPressed = false;
        aPressed = false;
        dPressed = false;
    }

    private void loadLevel(String levelName) {
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].getName() == levelName) {
                levelIndex = i;
                loadLevel(levels[i]);
                return;
            }
        }
        throw new RuntimeException("Level was not found");
    }

    private void loadLevel(Level level) {
        level.setPlayerCharacter(player);
        level.load();
    }
    
    private void onMouseClicked(MouseEvent e) {
        switch (gameState) {
            case GameRunning:
                if (e.getButton() == LEFT) {
                    float mouseX = (e.getX() - width / 2 + player.getX());
                    float mouseY = (e.getY() - height / 2 + player.getY());
                    player.attack(mouseX, mouseY, levels[levelIndex].getNPCS());
                }
                break;
            
            case StartMenu:
                for (Label levelButton : levelButtons) {
                    if (levelButton.contains(mouseX, mouseY)) {
                        playerCharacter = leftCharacter.name;
                        loadLevel(levelButton.text);
                        gameState = State.GameRunning;
                        break;
                    }
                }

                if (leftCharacter.contains(mouseX, mouseY)) {
                    Random rand = new Random();
                    String hcName;
                    do {
                        hcName = CharacterFactory.HEROES[rand.nextInt(CharacterFactory.HEROES.length)];
                    } while (hcName == leftCharacter.name && CharacterFactory.HEROES.length > 1);
                    leftCharacter = CharacterFactory.createCharacter(hcName);
                    leftCharacter.setPosition(width / 4 * 3, height / 3 * 2);
                    leftCharacter.setLayer(Renderer.GUI);
                    leftCharacter.scale = 6;
                }
                else if (rightCharacter.contains(mouseX, mouseY)) {
                    Random rand = new Random();
                    String mcName;
                    do {
                        mcName = CharacterFactory.MONSTERS[rand.nextInt(CharacterFactory.MONSTERS.length)];
                    } while (mcName == leftCharacter.name && CharacterFactory.MONSTERS.length > 1);
                    rightCharacter = CharacterFactory.createCharacter(mcName);
                    rightCharacter.setPosition(width / 4, height / 3 * 2);
                    rightCharacter.setLayer(Renderer.GUI);
                    rightCharacter.scale = 6;
                }

                if (gameState != State.StartMenu) {
                    themeSong.stop();
                }
                break;

            case GameOver:
                if (restartButton.contains(mouseX, mouseY)) {
                    loadLevel(levelIndex);
                    gameState = State.GameRunning;
                }
                else if (startMenuButton.contains(mouseX, mouseY)) {
                    gameState = State.StartMenu;
                    levels[levelIndex].unload();
                    createStartMenu();
                }
                break;

            case GamePaused:
                if (continueButton.contains(mouseX, mouseY)) {
                    gameState = State.GameRunning;
                    levels[levelIndex].resume();
                }
                else if (quitButton.contains(mouseX, mouseY)) {
                    gameState = State.StartMenu;
                    levels[levelIndex].unload();
                    createStartMenu();
                }
                break;
            
            case LevelComplete:
                if (nextLevelButton.contains(mouseX, mouseY)) {
                    levelIndex++;
                    if (levelIndex == levels.length) {
                        gameState = State.StartMenu;
                        createStartMenu();
                    }
                    else {
                        loadLevel(levelIndex);
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
                    levels[levelIndex].pause();

                    pause();
                    launchEditor("\"" + levels[levelIndex].getTileMapFilePath() + "\"");
                    resume();

                    loadLevel(levelIndex);
                }
                break;
            case 'P':
                gameState = State.GamePaused;
                levels[levelIndex].pause();
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

    private void launchEditor(String file) {
        if (gameState != State.GameRunning) return;
        
        Process theProcess = null;
        BufferedReader outStream = null;
        BufferedReader errStream = null;
    
        System.out.println("Tile Editor invoked");
        
        try
        {
            theProcess = Runtime.getRuntime().exec("java --enable-preview -jar TileEditor.jar " + file);
        }
        catch(IOException e)
        {
            System.err.println("Error on exec() method");
            e.printStackTrace();
        }
            
        // read from the called program's stdout and stderr streams
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

    public static void main(String[] args) {
        PApplet.main(DungeonMaster.class);
    }
}