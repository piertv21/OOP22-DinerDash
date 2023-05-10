package it.unibo.dinerdash.view.impl;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.view.api.GamePanel;
import it.unibo.dinerdash.view.api.GameView;
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

/**
 * {@inheritDoc}
 *
 * Implementation of the View interface.
 */
public class ViewImpl extends JFrame implements View {

    private static final long serialVersionUID = -6298731756889190906L;

    private static final String ROOT = "it" + Constants.SEP + "unibo" + Constants.SEP + "dinerdash" + Constants.SEP;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private final Optional<Controller> controller;
    private GamePanel currentView;
    private final ImageReaderWithCache imageCacher;
    private boolean gameStarted;

    private double widthRatio;
    private double heightRatio;

    /**
     * Class constructor.
     * 
     * @param controller
     */
    public ViewImpl(final Controller controller) {
        super(Constants.GAME_NAME);
        this.controller = Optional.of(controller);
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

        final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screenSize.getWidth();
        final int screenHeight = (int) screenSize.getHeight();

        this.setSize(screenWidth / 2, screenHeight / 2);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final var screenSize = e.getComponent().getSize();
                widthRatio = screenSize.getWidth() / controller.getRestaurantWidth();
                heightRatio = screenSize.getHeight() / controller.getRestaurantHeight();
            }
        });

        this.loadResources();
        this.showStartView();
        this.setVisible(true);
    }

    private void showStartView() {
        this.currentView = new StartView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameView() {
        this.currentView = new GameViewImpl(this);
        this.controller.get().start((GameView) this.currentView);
        this.gameStarted = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOverView() {
        this.currentView = new GameOverView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showExitDialog() {
        if (this.gameStarted) {
            this.controller.get().pause();
        }

        final int result = JOptionPane.showConfirmDialog(this, "Do you really want to exit?",
            "Exit", JOptionPane.YES_NO_OPTION);

        if (this.gameStarted) {
            if (result == JOptionPane.YES_OPTION) {
                controller.get().quit();
            } else {
                this.controller.get().resume();
            }
        } else {
            if (result == JOptionPane.YES_OPTION) {
                controller.get().quitWithoutPlaying();
            }
        }
    }

    @Override
    public void refreshView() {
        this.setContentPane(this.currentView);
        this.validate();
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quit() {
        this.imageCacher.clearCache();
        this.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return this.controller.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playAgain() {
        this.showGameView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeightRatio() {
        return this.heightRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWidthRatio() {
        return this.widthRatio;
    }

    private void loadResources() {
        final var path = "src" + Constants.SEP + "main" + Constants.SEP + "resources" + Constants.SEP + ROOT;
        final var assetsPath = this.searchAssets(path);
        assetsPath.forEach(this.imageCacher::readImage);
    }

    private List<String> searchAssets(final String path) {
        return Optional.ofNullable(new File(path).listFiles())
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .flatMap(file -> getFilesRecursively(file, path))
            .filter(file -> file.getName().matches("(?i).+\\.(jpg|jpeg|png|gif)$"))
            .map(File::getPath)
            .collect(Collectors.toList());
    }

    private Stream<File> getFilesRecursively(final File file, final String basePath) {
        final String filePath = file.getPath();
        final String relativePath = filePath.substring(basePath.length());
        return file.isDirectory() ? Optional.ofNullable(file.listFiles())
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .flatMap(f -> getFilesRecursively(f, basePath))
            : Stream.of(new File(relativePath));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This function must provide a reference to the ImageCacher"
        + "in order to load assets in advance")
    public ImageReaderWithCache getImageCacher() {
        return this.imageCacher;
    }

}
