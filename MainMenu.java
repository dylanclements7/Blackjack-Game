import java.awt.event.*;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class MainMenu {
    JFrame MenuFrame = new JFrame("Main Menu");
    ImageIcon backgroundImage = new ImageIcon("Images/MenuBackground.png");
    ImageIcon menuLogo = new ImageIcon("Images/MenuLogo.png");
    ImageIcon chipsImage = new ImageIcon("Images/Chips.png");
    ImageIcon chipsFlippedImage = new ImageIcon("Images/ChipsFlipped.png");
    BetMenu bet;

    public MainMenu(boolean Music) {
        if(Music){
            playBackgroundMusic();
        }

        MenuFrame.setSize(1400, 786);
        MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuFrame.setLocationRelativeTo(null);
        MenuFrame.setResizable(false);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        MenuFrame.setContentPane(backgroundLabel);

        JLabel menuLogoLabel = new JLabel();
        menuLogoLabel.setIcon(menuLogo);
        menuLogoLabel.setBounds(10,10,300,300);
        MenuFrame.add(menuLogoLabel);

        JLabel menu2LogoLabel = new JLabel();
        menu2LogoLabel.setIcon(menuLogo);
        menu2LogoLabel.setBounds(1090,10,300,300);
        MenuFrame.add(menu2LogoLabel);

        JLabel chipsLabel = new JLabel();
        chipsLabel.setIcon(chipsImage);
        chipsLabel.setBounds(400,600,199,141);
        MenuFrame.add(chipsLabel);

        JLabel chipsFlippedLabel = new JLabel();
        chipsFlippedLabel.setIcon(chipsFlippedImage);
        chipsFlippedLabel.setBounds(800,600,199,141);
        MenuFrame.add(chipsFlippedLabel);

        MenuFrame.repaint();

        JButton bPlay = new JButton("PLAY");
        bPlay.setBounds(550,575, 300, 150);
        bPlay.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));

        MenuFrame.add(bPlay);
        MenuFrame.setVisible(true);
        bPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Test.Start();
                BetMenu betMenu = new BetMenu();
                //THIS IS RIGHT NO VALUE
                MenuFrame.setVisible(false);
                MenuFrame.dispose();
            }
        });
    }
    public void playBackgroundMusic(){
        try {
            BackgroundMusicPlayer audioPlayer = new BackgroundMusicPlayer();
            audioPlayer.play();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {throw new RuntimeException(ex);}
    }
}