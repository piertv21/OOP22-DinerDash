package it.unibo.dinerdash.view.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;
import it.unibo.dinerdash.view.api.View;

/**
 * {@inheritDoc}
 *
 * Implementation of the View interface.
 */
public class ViewImpl implements View {

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private static final int TWO = 2;
    private static final int FOUR = 4;
    private static final int SIX = 6;

    private final JFrame mainFrame;
    private final Optional<Controller> controller;
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
        this.mainFrame = new JFrame(Constants.GAME_NAME);

        this.controller = Optional.of(controller);
        this.imageCacher = new ImageReaderWithCache(Constants.ROOT);
        this.widthRatio = 0;
        this.heightRatio = 0;
        this.gameStarted = false;
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                showExitDialog();
            }
        });
        this.mainFrame.setLocationByPlatform(true);
        this.mainFrame.setResizable(true);

        final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screenSize.getWidth();
        final int screenHeight = (int) screenSize.getHeight();

        this.mainFrame.setSize(screenWidth / 2, screenHeight / 2);
        this.mainFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        this.mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final var screenSize = e.getComponent().getSize();
                widthRatio = screenSize.getWidth() / controller.getRestaurantWidth();
                heightRatio = screenSize.getHeight() / controller.getRestaurantHeight();
            }
        });

        final var assets = allAssetsRelativePath();
        assets.forEach(this.imageCacher::readImage);

        this.showStartView();
        this.mainFrame.setVisible(true);
    }

    private void showStartView() {
        final var startPanel = new StartView(this);
        this.mainFrame.setContentPane(startPanel.getComponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameView() {
        final var gamePanel = new GameViewImpl(this);
        this.mainFrame.setContentPane(gamePanel.getComponent());
        this.controller.get().start(gamePanel);
        this.gameStarted = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOverView() {
        final var gameOverPanel = new GameOverView(this);
        this.mainFrame.setContentPane(gameOverPanel.getComponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showExitDialog() {
        if (this.gameStarted) {
            this.controller.get().pause();
        }

        final int result = JOptionPane.showConfirmDialog(this.mainFrame, "Do you really want to exit?",
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshView() {
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quit() {
        this.imageCacher.clearCache();
        this.mainFrame.dispose();
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

    private List<String> generateAssetPaths(final String pattern, final int starting, final int ending) {
        return IntStream.rangeClosed(starting, ending)
                .mapToObj(i -> String.format(pattern, i))
                .collect(Collectors.toList());
    }

    private List<String> allAssetsRelativePath() {
        final List<String> assets = new ArrayList<>();

        assets.addAll(
            generateAssetPaths("customers/customer%d.png", 1, FOUR));
        assets.addAll(
            generateAssetPaths("dishes/dish%d.png", 1, FOUR));
        assets.addAll(
            generateAssetPaths("hearts/heart%d.png", 0, SIX));
        assets.addAll(
            generateAssetPaths("powerups/powerUp%d.png", 1, FOUR));
        assets.addAll(
            generateAssetPaths("tables/table%d.png", 0, FOUR));
        assets.addAll(
            generateAssetPaths("tables/withDish/tableWithDish%d.png", 1, FOUR));
        assets.addAll(
            generateAssetPaths("waitress/waitress%d.png", 0, TWO));
        assets.add("tables/tableStates/wantToOrder.png");
        assets.add("tables/tableStates/wantToPay.png");
        assets.add("cursors/defaultCursor.png");
        assets.add("cursors/handCursor.png");
        assets.add("background.jpg");
        assets.add("chef.gif");

        return assets;
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
