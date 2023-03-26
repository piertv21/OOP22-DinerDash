package it.unibo.dinerdash.view.api;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.impl.TableViewable;
import it.unibo.dinerdash.view.impl.View;

/*
 * Main Game View Panel
 */
public class GameView extends FramePanel {

    private static final String TIME = "Time";
    private static final String COINS = "Coins";
    private static final String QUIT = "Quit";
    private static final String RESTART = "Restart";

    private JLabel timeLabel;
    private JLabel coinLabel;
    private JButton exitButton;
    private JButton restartButton;
    private JButton powerupButton1;
    private JButton powerupButton2;
    private JButton powerupButton3;
    private JButton powerupButton4;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private Image backgroundImage;

    private GameEntityViewable waitress;
    private LinkedList<TableViewable>tableList = new LinkedList<>();

    public GameView(View mainFrame) {
        super(mainFrame);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(0, 30));

        timeLabel = new JLabel(TIME + ": " + this.getMainFrame().getController().getRemainingTime());
        timeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        coinLabel = new JLabel(COINS + ": " + this.getMainFrame().getController().getCoins());
        coinLabel.setFont(new Font("Arial", Font.BOLD, 15));
        coinLabel.setForeground(Color.BLACK);
        coinLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(coinLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 30));

        exitButton = new JButton(QUIT);
        exitButton.addActionListener((e) -> this.getMainFrame().getController().quit());
        bottomPanel.add(exitButton, BorderLayout.EAST);
        
        restartButton = new JButton(RESTART);
        restartButton.addActionListener((e) -> this.getMainFrame().getController().restart());
        bottomPanel.add(restartButton);
        add(bottomPanel, BorderLayout.SOUTH);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        var c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets.top = 6;
        c.insets.right = 8;

        powerupButton1 = new JButton("1");
        powerupButton1.setActionCommand("boost_velocità");  //aumenta velocità cameriera
        rightPanel.add(powerupButton1, c);

        c.gridy = 1;
        powerupButton2 = new JButton("2");
        powerupButton2.setActionCommand("boost_cuoco"); //aumenta velocità di preparazione dei piatti
        rightPanel.add(powerupButton2, c);

        c.gridy = 2;
        powerupButton3 = new JButton("3");
        powerupButton3.setActionCommand("boost_guadagno");  //aumenta il guadagno
        rightPanel.add(powerupButton3, c);

        c.gridy = 3;
        powerupButton4 = new JButton("4");
        powerupButton4.setActionCommand("boost_consumazione");  //aumenta la velocità di consumazione dei clienti
        rightPanel.add(powerupButton4, c);
        
        add(rightPanel, BorderLayout.EAST);

        this.initViewableEntities();
    }

    private void initViewableEntities() {
        var waitressPosition = new Pair<>(40, 120);        
        this.waitress = new GameEntityViewable(waitressPosition, backgroundImage);
        try {
            backgroundImage = loadIcon("background.png").getImage();
            this.waitress.setIcon(
                loadIcon("waitress.png").getImage()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int a=0; a<4 ; a++){
            try {
                tableList.add(new TableViewable( new Pair<>(100, 200),loadIcon("emptyTable.png").getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

    private void addCustomerInLine() {
        this.getMainFrame().getController().addCustomerInLine();
    }

    // https://stackoverflow.com/questions/49871233/using-imageicon-to-access-a-picture-cant-access-it-how-to-fix
    private ImageIcon loadIcon(String iconName) throws IOException {
        final URL imgURL = ClassLoader.getSystemResource(iconName);
        return new ImageIcon(imgURL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight(), this);
        g.drawImage(waitress.getIcon(), waitress.getPosition().getX(), waitress.getPosition().getY(), 120, 180, this);
    }

}
