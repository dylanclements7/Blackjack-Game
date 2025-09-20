import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.WindowEvent;

public class Game {
    Deck deck;
    JFrame frame = new JFrame("BlackJack");
    ImageIcon backgroundImage = new ImageIcon("Images/Background.png");
    ImageIcon deckImage = new ImageIcon("Images/Deck.png");
    ImageIcon playAgainBackground = new ImageIcon("Images/PlayAgainBackground.png");
    ImageIcon bruno = new ImageIcon("Images/bruno.png");
    ImageIcon magee = new ImageIcon("Images/magee.png");
    int removalCount = 0;
    int totalAmount;
    int bet;
    ArrayList<Card> proxyDealerHand;

    public Game(int totalAmount, int bet) {
        this.totalAmount = totalAmount;
        this.bet = bet;
    }

    public JLabel printDealerFirstCard() {
        int x = 587;
        int y = 50;
        ImageIcon imgCard = new ImageIcon("Images/BackOfCard.png");
        JLabel cardLabel = new JLabel();
        cardLabel.setIcon(imgCard);
        cardLabel.setBounds(x, y, 101, 141);
        frame.add(cardLabel);
        frame.repaint();
        return cardLabel;
    }

    public void printCard(int id, int numCardsInHand, boolean playerTurn, boolean isFaceDown) {
        int x = 587 + 126 * numCardsInHand;
        int y = 50;
        if (playerTurn) {
            y = 500;
        }
        ImageIcon imgCard;
        if (isFaceDown) {
            imgCard = new ImageIcon("Images/BackOfCard.png");
        } else {
            imgCard = new ImageIcon("Images/" + id + ".png");
        }
        JLabel cardLabel = new JLabel();
        cardLabel.setIcon(imgCard);
        cardLabel.setBounds(x, y, 101, 141);
        frame.add(cardLabel);
        frame.repaint();
    }


    public int AceCount(ArrayList<Card> hand) {
        int AceAmount = 0;
        for (Card i : hand) {
            if (i.getValue() == 11) {
                AceAmount += 1;
            }
        }
        return AceAmount;
    }

    public int HandTotal(ArrayList<Card> hand) {
        int total = 0;
        int AceAmount = AceCount(hand);
        for (Card i : hand) {
            total += i.getValue();
        }
        while (total > 21 && AceAmount > 0) {
            total -= 10;
            AceAmount -= 1;
        }
        return total;
    }

    public void drawCard(ArrayList<Card> hand) {
        hand.add(deck.GetCard(0));
        deck.RemoveCard(0);
    }

    public void playCardFlipAudio() {
        try {
            CardFlipPlayer audioPlayer = new CardFlipPlayer();
            audioPlayer.play();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void playBackgroundMusic() {
        try {
            BackgroundMusicPlayer audioPlayer = new BackgroundMusicPlayer();
            audioPlayer.play();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public boolean DealerHit(ArrayList<Card> DealerHand) {
        if (HandTotal(DealerHand) > 16) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBust(ArrayList<Card> hand) {
        return HandTotal(hand) > 21;
    }

    public void setProxyHand(ArrayList<Card> hand) {
        proxyDealerHand = hand;
    }

    public ArrayList<Card> getProxyDealerHand() {
        return proxyDealerHand;
    }
    //public CheckHand(ArrayList<Card> hand){

    //}
    public void Play() {
        int Databet = this.bet;
        int DatatotalAmount = this.totalAmount;

        frame.setSize(1400, 786);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        frame.setContentPane(backgroundLabel);

        JLabel deckLabel = new JLabel();
        deckLabel.setIcon(deckImage);
        deckLabel.setBounds(407, 34, 117, 157);
        frame.add(deckLabel);

        JButton bStand = new JButton("Stand");
        bStand.setBounds(550, 675, 100, 50);

        JButton bHit = new JButton("Hit");
        bHit.setBounds(650, 675, 100, 50);

        JButton bDouble = new JButton("Double");
        bDouble.setBounds(750, 675, 100, 50);

        JButton bNext = new JButton("Dealer Has Hit. Continue");
        bNext.setBounds(600, 650, 200, 100);

        frame.add(bStand);
        frame.add(bHit);
        frame.add(bDouble);

        frame.repaint();
        frame.setVisible(true);

        deck = new Deck();
        deck.ShuffleDeck();

        ArrayList<Card> DealerHand = new ArrayList<Card>();
        ArrayList<Card> PlayerHand = new ArrayList<Card>();

        drawCard(PlayerHand);
        drawCard(DealerHand);
        drawCard(PlayerHand);
        drawCard(DealerHand);

        printCard(PlayerHand.get(0).getId(), 0, true, false);
        JLabel dealerFirstCard = printDealerFirstCard();
        printCard(PlayerHand.get(1).getId(), 1, true, false);
        printCard(DealerHand.get(1).getId(), 1, false, false);

        bStand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove buttons
                frame.remove(bStand);
                frame.remove(bHit);
                frame.remove(bDouble);
                frame.repaint();

                //audio
                playCardFlipAudio();

                //flip over dealers face down card
                frame.remove(dealerFirstCard);
                printCard(DealerHand.getFirst().getId(), 0, false, false);
                if (DealerHit(DealerHand)) {
                    ArrayList<Card> ProxyList = new ArrayList<Card>();
                    while (DealerHit(DealerHand)) {
                        DealerHand.add(deck.GetCard(0));
                        //setProxyHand(getProxyDealerHand().add(deck.GetCard(0)));
                        ProxyList.add(deck.GetCard(0));
                        deck.RemoveCard(0);
                    }
                    setProxyHand(ProxyList);
                    frame.add(bNext);
                    frame.repaint();
                }
                else {
                    whoWon(DealerHand, PlayerHand);
                }
            }
        });

        bNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (DealerHand.size() == 3 && removalCount == 0) {
                    printCard(DealerHand.get(2).getId(), 2, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 3 && removalCount == 1) {
                    printCard(DealerHand.get(2).getId(), 3, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 3 && removalCount == 2) {
                    printCard(DealerHand.get(2).getId(), 4, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 4 && removalCount == 0) {
                    printCard(DealerHand.get(2).getId(), 2, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 4 && removalCount == 1) {
                    printCard(DealerHand.get(2).getId(), 3, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 4 && removalCount == 2) {
                    printCard(DealerHand.get(2).getId(), 4, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 5 && removalCount == 0) {
                    printCard(DealerHand.get(2).getId(), 2, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 5 && removalCount == 1) {
                    printCard(DealerHand.get(2).getId(), 3, false, false);
                    playCardFlipAudio();
                } else if (DealerHand.size() == 6 && removalCount == 0) {
                    printCard(DealerHand.get(2).getId(), 2, false, false);
                    playCardFlipAudio();
                } else {
                    System.out.println("NO ROOM ON BOARD");
                }
                DealerHand.remove(2);
                removalCount += 1;
                if (DealerHand.size() == 2) {
                    frame.remove(bNext);
                    for (Card i : getProxyDealerHand()) {
                        DealerHand.add(i);
                    }
                    whoWon(DealerHand, PlayerHand);
                    frame.repaint();
                }
            }
        });

        bHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove buttons
                frame.remove(bStand);
                frame.remove(bDouble);
                frame.remove(bHit);
                frame.repaint();

                //give player new card
                PlayerHand.add(deck.GetCard(0));
                printCard(deck.GetCard(0).getId(), PlayerHand.size() - 1, true, false);
                playCardFlipAudio();
                deck.RemoveCard(0);

                //check if player lost
                if (isBust(PlayerHand)) {
                    gameOver(2);
                }
                else if (HandTotal(PlayerHand) == 21) {
                    gameOver(1);
                }
                frame.add(bStand);
                frame.add(bDouble);
                frame.add(bHit);
                frame.repaint();
            }
        });
        bDouble.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if their bet is less than or equal to their total
                if(DatatotalAmount - Databet >= 0) {
                    totalAmount -= bet;
                    bet *= 2;
                    bHit.doClick();
                    if (!isBust(PlayerHand)) {
                        bStand.doClick();
                }
                }
            }
        });
    }



    public void whoWon(ArrayList<Card> dealerHand, ArrayList<Card> playerHand) {
        if (isBust(dealerHand)) {
            gameOver(1);
        }
        else if (HandTotal(playerHand)==21){
            gameOver(1);
        }
        else if (isBust(playerHand)){
            gameOver(2);
        }
        else if (HandTotal(playerHand) > HandTotal(dealerHand)) {
            gameOver(1);
        }
        else if (HandTotal(dealerHand) > HandTotal(playerHand)) {
            gameOver(2);
        }
        else {
            gameOver(0);
        }
    }
    public void gameOver(int result) {
        //remove buttons
        //take who won
        //get the total
        //get the bet
        //do payouts
        //ask if player wants to play again

        if(result == 1) {
            totalAmount += (bet*2);
            JFrame playAgain = new JFrame("You Win!");
            JLabel backgroundLabel = new JLabel();
            backgroundLabel.setIcon(playAgainBackground);
            playAgain.setContentPane(backgroundLabel);
            playAgain.setSize(600, 600);
            playAgain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            playAgain.setLocation(50, 700);
            playAgain.setResizable(false);
            playAgain.setVisible(true);
            JButton bPlayAgain = new JButton("You Win! Play Again");
            bPlayAgain.setBounds(350, 250, 200, 100);
            playAgain.add(bPlayAgain);

            JLabel mageeLabel = new JLabel();
            mageeLabel.setIcon(magee);
            mageeLabel.setBounds(0,131,565,600);
            playAgain.add(mageeLabel);

            bPlayAgain.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent x) {
                    BetMenu newBet = new BetMenu(totalAmount);
                    frame.setVisible(false);
                    frame.dispose();
                    playAgain.setVisible(false);
                    playAgain.dispose();
                }
            });
        }
        else if(result == 0){
            totalAmount += bet;
            JFrame tie = new JFrame("You Tied!");
            JLabel backgroundLabel = new JLabel();
            backgroundLabel.setIcon(playAgainBackground);
            tie.setContentPane(backgroundLabel);
            tie.setSize(600, 600);
            tie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tie.setLocation(50, 700);
            tie.setResizable(false);
            tie.setVisible(true);
            JButton bTie = new JButton("Tie: You get your bet back!");
            bTie.setBounds(350, 250, 200, 100);
            tie.add(bTie);

            JLabel mageeLabel = new JLabel();
            mageeLabel.setIcon(magee);
            mageeLabel.setBounds(0,131,565,600);
            tie.add(mageeLabel);

            bTie.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent x) {
                    BetMenu newBet = new BetMenu(totalAmount);
                    frame.setVisible(false);
                    frame.dispose();
                    tie.setVisible(false);
                    tie.dispose();
                }
            });
        }
        else if (result == 2){
            if(totalAmount < 10){
                JFrame broke = new JFrame("You're Broke :(");
                JLabel backgroundLabel = new JLabel();
                backgroundLabel.setIcon(playAgainBackground);
                broke.setContentPane(backgroundLabel);
                broke.setSize(600, 600);
                broke.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                broke.setLocation(50, 700);
                broke.setResizable(false);
                broke.setVisible(true);
                JButton bBroke = new JButton("You're Broke! Get Out!");
                bBroke.setBounds(350, 250, 200, 100);
                broke.add(bBroke);

                JLabel brunoLabel = new JLabel();
                brunoLabel.setIcon(bruno);
                brunoLabel.setBounds(0,0,352,600);
                broke.add(brunoLabel);

                JLabel brunoText = new JLabel();
                brunoText.setText("At least you're not $50 million down like me!");
                brunoText.setBounds(280,100,290,200);
                brunoText.setOpaque(true);
                brunoText.setBackground(Color.white);
                brunoText.setHorizontalAlignment(JLabel.CENTER);
                broke.add(brunoText);
                broke.repaint();

                bBroke.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent x) {
                        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        MainMenu menu = new MainMenu(false);
                        frame.setVisible(false);
                        frame.dispose();
                        broke.setVisible(false);
                        broke.dispose();
                    }
                });
            }
            else{
                JFrame loser = new JFrame("You Lose :(");
                JLabel backgroundLabel = new JLabel();
                backgroundLabel.setIcon(playAgainBackground);
                loser.setContentPane(backgroundLabel);
                loser.setSize(600, 600);
                loser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loser.setLocation(50, 700);
                loser.setResizable(false);
                loser.setVisible(true);
                JButton bLoser = new JButton("You Lose! Play Again");
                bLoser.setBounds(350, 250, 200, 100);
                loser.add(bLoser);

                JLabel brunoLabel = new JLabel();
                brunoLabel.setIcon(bruno);
                brunoLabel.setBounds(0,0,352,600);
                loser.add(brunoLabel);

                JLabel brunoText = new JLabel();
                brunoText.setText("99% of gamblers quit before their big win!");
                brunoText.setBounds(280,120,290,100);
                brunoText.setOpaque(true);
                brunoText.setBackground(Color.white);
                brunoText.setHorizontalAlignment(JLabel.CENTER);
                loser.add(brunoText);

                bLoser.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent x) {
                        BetMenu newBet = new BetMenu(totalAmount);
                        frame.setVisible(false);
                        frame.dispose();
                        loser.setVisible(false);
                        loser.dispose();
                    }
                });
            }
        }
    }
}