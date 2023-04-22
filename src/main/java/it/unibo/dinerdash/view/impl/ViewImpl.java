package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.View;

import java.awt.*;
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

    public static final String FRAME_NAME = "Diner Dash";
    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 600;

    private Controller controller;
    private GamePanel currentView;
    private ImageReaderWithCache imageCacher;

    private double widthRatio;
    private double heightRatio;

    public ViewImpl(Controller controller) {
        super(FRAME_NAME);
        this.controller = controller;
        this.imageCacher = new ImageReaderWithCache(ROOT);
        
        this.widthRatio = 0;
        this.heightRatio = 0;
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                controller.quitWithoutPlaying();
            }
        });
        this.setLocationByPlatform(true);
        this.setResizable(true);
        
        this.setSize(1280, 720); //TODO DEBUG 720p
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                var screenSize = e.getComponent().getSize(getSize());
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
    
    private List<String> searchAssets(String path) {
        return Optional.ofNullable(new File(path).listFiles())
                .map(Arrays::stream)
                .orElse(Stream.empty())
                .flatMap(file -> getFilesRecursively(file, path))
                .filter(file -> file.getName().toLowerCase().matches(".+\\.(jpg|jpeg|png|gif)$"))
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    private Stream<File> getFilesRecursively(File file, String basePath) {
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

}
