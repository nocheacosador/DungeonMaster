package editor;

import java.io.File;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.util.FontManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import rendering.TextureManager;
import tiles.TileFactory;
import tiles.TileMap;

public class TileEditor extends PApplet {
    static String filepath;

    TileMapWindow tileMapWindow;
    TileSelectorWindow tileSelectorWindow;
    ControlWindow controlWindow;

    private JFileChooser fileChooser = new JFileChooser(new File("assets/maps")) {
        @Override
        public void approveSelection() {
            File f = getSelectedFile();
            if(f.exists() && getDialogType() == SAVE_DIALOG){
                int result = JOptionPane.showConfirmDialog(this, 
                    "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                switch(result){
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                    default:
                        return;
                }
            }
            super.approveSelection();
        }

        @Override
        protected JDialog createDialog(Component parent) throws HeadlessException {
            JDialog dialog = super.createDialog(parent);
            dialog.requestFocus();
            dialog.setLocation(new Point(500, 500));
            return dialog;
        }
    };

    public void settings() {
        size(1200, 800, P2D);
    }

    public void setup()
    {
        TextureManager.getInstance().load(this);
        TileFactory.getInstance().load();
        FontManager.getInstance().load(this);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map files", "map", "xml");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        tileMapWindow = new TileMapWindow(this);
        tileSelectorWindow = new TileSelectorWindow(this);
        controlWindow = new ControlWindow(this);

        if (filepath != null) {
            tileMapWindow.getTileMap().load(new File(filepath));
        }
    }

    public void draw() {
        tileMapWindow.render();
        tileSelectorWindow.render();
        controlWindow.render();

        // render to screen
        background(20);
        tileMapWindow.draw();
        tileSelectorWindow.draw();
        controlWindow.draw();
    }

    public void mouseClicked(MouseEvent event) {
        if (tileSelectorWindow.contains(mouseX, mouseY)) tileSelectorWindow.onMouseClicked(event);
        if (controlWindow.contains(mouseX, mouseY)) controlWindow.onMouseClicked(event);
    }

    public void mousePressed(MouseEvent event) {
        if (tileMapWindow.contains(mouseX, mouseY)) tileMapWindow.onMousePressed(event);
    }
      
    public void mouseDragged(MouseEvent event) {
        if (tileMapWindow.contains(mouseX, mouseY)) tileMapWindow.onMouseDragged(event);
    }
      
    public void mouseReleased(MouseEvent event) {
        if (tileMapWindow.contains(mouseX, mouseY)) tileMapWindow.onMouseReleased(event);
    }

    public void mouseWheel(MouseEvent event) {
        if (tileSelectorWindow.contains(mouseX, mouseY)) tileSelectorWindow.onMouseWheel(event);
        if (tileMapWindow.contains(mouseX, mouseY)) tileMapWindow.onMouseWheel(event);
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case 'T':
                tileMapWindow.setMode(TileMapWindow.Mode.EditTiles);
                break;
            case 'W':
                tileMapWindow.setMode(TileMapWindow.Mode.EditWalkable);
                break;
            case ESC:
                key = 0;
                tileMapWindow.setMode(TileMapWindow.Mode.Inspect);
                break;
            case 'S':
                if ((event.getModifiers() & KeyEvent.CTRL) != 0) {
                    File file = tileMapWindow.getTileMap().getFile();
                    if (file == null) {
//                        int returnVal = fileChooser.showSaveDialog(null);
//                        if (returnVal == JFileChooser.APPROVE_OPTION) {
//                            file = fileChooser.getSelectedFile();
//                            file = new File(file.getAbsolutePath() + ".map");
//
//                            System.out.println("User selected " + file.getAbsolutePath());
//                            tileMapWindow.getTileMap().setFile(file);
//                            tileMapWindow.getTileMap().save();
//                        }
//                        else {
//                            System.out.println("Tile map not saved. No user input.");
//                        }
                        
                        selectOutput("Open file", "saveTileMap");
                    }
                    else {
                        tileMapWindow.getTileMap().save();
                    }
                }
                else {
                    tileMapWindow.setMode(TileMapWindow.Mode.EditSpawn);
                }
                break;
            case 'O':
                if ((event.getModifiers() & KeyEvent.CTRL) != 0) {
                    selectInput("Save file", "openTileMap");
//                    tileMapWindow.getTileMap().save();
//                    int returnVal = fileChooser.showOpenDialog(null);
//                    if (returnVal == JFileChooser.APPROVE_OPTION) {
//                        File file = fileChooser.getSelectedFile();
//                        System.out.println("User selected " + file.getAbsolutePath());
//                        tileMapWindow.getTileMap().load(file);
//                    }
                }
                break;
            case 'N':
                if ((event.getModifiers() & KeyEvent.CTRL) != 0) {
                    tileMapWindow.getTileMap().save();
                    tileMapWindow.setTileMap(new TileMap());
                }
        }
    }

    public void saveTileMap(File selection) {
        if (selection == null) {
            System.out.println("Tile map not saved. No user input.");
            return;
        }

        tileMapWindow.getTileMap().setFile(selection);
        tileMapWindow.getTileMap().save();
    }

    public void openTileMap(File selection) {
        if (selection == null)
            return;
            
        tileMapWindow.getTileMap().save();
        tileMapWindow.getTileMap().load(selection);
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            TileEditor.filepath = args[0];
            System.out.println(args[0]);
        }

        PApplet.main(TileEditor.class);
    }
}