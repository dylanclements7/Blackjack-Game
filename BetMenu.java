import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BetMenu implements ActionListener{
    public int bet;
    public int totalMoney;

    JLabel betLabel = new JLabel();
    JFrame betFrame = new JFrame();
    JButton submit = new JButton("Submit Bet");

    //buttons: 10 50 100 500 1,000, 10,000 which add to bet which we display,
    //submit button to finalize which removes buttons, happens before round starts but in Play()
    //hand gameOver function their total and their bet, it does payout
    public BetMenu() {
        setBet(0);
        setTotalMoney(1000);
        JFrame betFrame = new JFrame("Bet Window");
        ImageIcon backgroundImage = new ImageIcon("Images/BetMenuBackground.jpg");
        betFrame.setSize(675, 360);
        betFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        betFrame.setLocationRelativeTo(null);
        betFrame.setResizable(false);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        betFrame.setContentPane(backgroundLabel);

        JLabel explain = new JLabel();
        explain.setText("Select Bet Amount:");
        explain.setBounds(58, 5, 220, 50);
        explain.setOpaque(true);
        explain.setBackground(Color.white);
        explain.setHorizontalAlignment(JLabel.CENTER);

        JLabel totalMoneyText = new JLabel();
        totalMoneyText.setText("Your Balance: " + String.valueOf(getTotalMoney()));
        totalMoneyText.setBounds(58,275,220,50);
        totalMoneyText.setOpaque(true);
        totalMoneyText.setBackground(Color.white);
        totalMoneyText.setHorizontalAlignment(JLabel.CENTER);

        JButton b10 = new JButton("10");
        b10.setBounds(58, 65, 100, 50);

        JButton b50 = new JButton("50");
        b50.setBounds(178, 65, 100, 50);

        JButton b100 = new JButton("100");
        b100.setBounds(58, 140, 100, 50);

        JButton b500 = new JButton("500");
        b500.setBounds(178, 140, 100, 50);

        JButton b1000 = new JButton("1000");
        b1000.setBounds(58, 215, 100, 50);

        JButton b10000 = new JButton("10000");
        b10000.setBounds(178, 215, 100, 50);

        submit.setBounds(430, 190, 150, 75);

        betLabel.setText(String.valueOf(bet));
        betLabel.setBounds(430, 95, 150, 75);
        betLabel.setOpaque(true);
        betLabel.setBackground(Color.white);
        betLabel.setHorizontalAlignment(JLabel.CENTER);

        betLabel.setText(String.valueOf(bet));
        betFrame.add(b10);
        betFrame.add(b50);
        betFrame.add(b100);
        betFrame.add(b500);
        betFrame.add(b1000);
        betFrame.add(b10000);
        betFrame.add(betLabel);
        betFrame.add(submit);
        betFrame.add(explain);
        betFrame.add(totalMoneyText);

        b10.addActionListener(this);
        b50.addActionListener(this);
        b100.addActionListener(this);
        b500.addActionListener(this);
        b1000.addActionListener(this);
        b10000.addActionListener(this);

        betFrame.setVisible(true);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent x) {
                betFrame.setVisible(false);
                betFrame.dispose();
                Game game = new Game(totalMoney - bet, bet);
                game.Play();
            }
        });
    }

    public BetMenu(int totalMoney) {
        setBet(0);
        setTotalMoney(totalMoney);
        JFrame betFrame = new JFrame("Bet Window");
        ImageIcon backgroundImage = new ImageIcon("Images/BetMenuBackground.jpg");
        betFrame.setSize(675, 360);
        betFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        betFrame.setLocationRelativeTo(null);
        betFrame.setResizable(false);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        betFrame.setContentPane(backgroundLabel);

        JLabel explain = new JLabel();
        explain.setText("Select Bet Amount:");
        explain.setBounds(58, 5, 220, 50);
        explain.setOpaque(true);
        explain.setBackground(Color.white);
        explain.setHorizontalAlignment(JLabel.CENTER);

        JLabel totalMoneyText = new JLabel();
        totalMoneyText.setText("Your Balance: " + String.valueOf(getTotalMoney()));
        totalMoneyText.setBounds(58,275,220,50);
        totalMoneyText.setOpaque(true);
        totalMoneyText.setBackground(Color.white);
        totalMoneyText.setHorizontalAlignment(JLabel.CENTER);

        JButton b10 = new JButton("10");
        b10.setBounds(58, 65, 100, 50);

        JButton b50 = new JButton("50");
        b50.setBounds(178, 65, 100, 50);

        JButton b100 = new JButton("100");
        b100.setBounds(58, 140, 100, 50);

        JButton b500 = new JButton("500");
        b500.setBounds(178, 140, 100, 50);

        JButton b1000 = new JButton("1000");
        b1000.setBounds(58, 215, 100, 50);

        JButton b10000 = new JButton("10000");
        b10000.setBounds(178, 215, 100, 50);

        submit.setBounds(430, 190, 150, 75);

        betLabel.setText(String.valueOf(bet));
        betLabel.setBounds(430, 95, 150, 75);
        betLabel.setOpaque(true);
        betLabel.setBackground(Color.white);
        betLabel.setHorizontalAlignment(JLabel.CENTER);

        betLabel.setText(String.valueOf(bet));
        betFrame.add(b10);
        betFrame.add(b50);
        betFrame.add(b100);
        betFrame.add(b500);
        betFrame.add(b1000);
        betFrame.add(b10000);
        betFrame.add(betLabel);
        betFrame.add(submit);
        betFrame.add(explain);
        betFrame.add(totalMoneyText);

        b10.addActionListener(this);
        b50.addActionListener(this);
        b100.addActionListener(this);
        b500.addActionListener(this);
        b1000.addActionListener(this);
        b10000.addActionListener(this);

        betFrame.setVisible(true);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent x) {
                betFrame.setVisible(false);
                betFrame.dispose();
                Game newGame = new Game(getTotalMoney()-getBet(), getBet());
                newGame.Play();
            }
        });
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void actionPerformed(ActionEvent e) {
        JButton actionSource = (JButton) e.getSource();
        String fullButtonName = actionSource.getText();
        int buttonValue = Integer.parseInt(fullButtonName);
        bet += buttonValue;
        if((getTotalMoney()-bet)<0){
            bet -= buttonValue;
        }
        setBet(bet);
        betLabel.setText(String.valueOf(bet));
        betFrame.repaint();
    }
}
