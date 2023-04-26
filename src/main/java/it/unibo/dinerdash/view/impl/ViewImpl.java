package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Main View.
 */
public class ViewImpl extends JFrame implements View {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "dinerdash" + SEP;

    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 600;

    private Controller controller;
    private GamePanel currentView;
    private ImageReaderWithCache imageCacher;
    private boolean gameStarted;

    private double widthRatio;
    private double heightRatio;

    public ViewImpl(Controller controller) {
        super(Constants.GAME_NAME);
        this.controller = controller;
        this.imageCacher = new ImageReaderWithCache(ROOT);
        
        this.widthRatio = 0;
        this.heightRatio = 0;
        this.gameStarted = false;
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                showExitDialog();
            }
        });
        this.setLocationByPlatform(true);
        this.setResizable(true);
        
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        
        this.setSize(screenWidth / 2, screenHeight / 2);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                var screenSize = e.getComponent().getSize();
                widthRatio = screenSize.getWidth() / controller.getRestaurantWidth();
                heightRatio = screenSize.getHeight() / controller.getRestaurantHeight();     
            }
        });

        this.loadResources();
        this.showStartView();
        this.setVisible(true);
    }

    @Override
    public void showStartView() {
        this.currentView = new StartView(this);
        this.refreshView();
    }

    @Override
    public void showGameView() {
        this.currentView = new GameViewImpl(this);
        this.refreshView();
    }

    @Override
    public void showGameOverView() {
        this.currentView = new GameOverView(this);
        this.refreshView();
    }

    @Override
    public void showExitDialog() {
        if(this.gameStarted) {
            this.controller.pause();
        }

        final int result = JOptionPane.showConfirmDialog(this, "Do you really want to exit?",
            "Exit", JOptionPane.YES_NO_OPTION);

        if(this.gameStarted) {
            if (result == JOptionPane.YES_OPTION) {
                controller.quit();
            } else {
                this.controller.resume();
            }
        } else {
            if (result == JOptionPane.YES_OPTION) {
                controller.quitWithoutPlaying();
            }
        }
    }

    private void refreshView() {
        this.setContentPane(this.currentView);
        this.validate();
        this.repaint();
    }

    @Override
    public void quit() {
        this.imageCacher.clearCache();
        this.dispose();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void playAgain() {
        this.showGameView();
    }
    
    @Override
    public double getHeightRatio() {
        return this.heightRatio;
    }

    @Override
    public double getWidthRatio(){
        return this.widthRatio;
    }

    private void loadResources() {
        var path = "src" + SEP + "main" + SEP + "resources" + SEP + ROOT;
        var assetsPath = this.searchAssets(path);
        assetsPath.forEach(this.imageCacher::readImage);
    }
    
    private List<String> searchAssets(final String path) {
        return Optional.ofNullable(new File(path).listFiles())
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .flatMap(file -> getFilesRecursively(file, path))
            .filter(file -> file.getName().toLowerCase().matches(".+\\.(jpg|jpeg|png|gif)$"))
            .map(File::getPath)
            .collect(Collectors.toList());
    }

    private Stream<File> getFilesRecursively(final File file, final String basePath) {
        String filePath = file.getPath();
        String relativePath = filePath.substring(basePath.length());
        return file.isDirectory() ? Optional.ofNullable(file.listFiles())
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .flatMap(f -> getFilesRecursively(f, basePath))
            : Stream.of(new File(relativePath));
    }

    @Override
    public ImageReaderWithCache getImageCacher() {
        return this.imageCacher;
    }

    @Override
    public void setGameStarted(final boolean started) {
        this.gameStarted = started;
    }

}
