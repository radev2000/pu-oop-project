package gameComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class FinalFrame extends JFrame{

    private final int greenPlayerPoints = GameFrame.playerOnePoints;
    private final int redPlayerPoints   = GameFrame.playerTwoPoints;

    private final String winner         = getWinner();

    JPanel upPanel                      = new JPanel();
    JPanel downPanel                    = new JPanel();

    JLabel greenPointsJL                = new JLabel("Player Green points:");
    JLabel greenPiecesJL                = new JLabel("Pieces lost:");
    JLabel redPointsJL                  = new JLabel("Player Red points::");
    JLabel redPiecesJL                  = new JLabel("Pieces lost:");

    JLabel greenPointsResultJL          = new JLabel("" + greenPlayerPoints);
    JLabel greenPiecesResultJL          = new JLabel("" + GameFrame.greenPiecesQueue);
    JLabel redPointsResultJL            = new JLabel("" + redPlayerPoints);
    JLabel redPiecesResultJL            = new JLabel("" + GameFrame.redPiecesQueue);

    JLabel winnerJL                     = new JLabel("Winner: " + winner);
    JLabel roundsJL                     = new JLabel("Rounds: " + GameFrame.rounds);

    JButton emptyButton                 = new JButton("");
    JButton emptyButton2                = new JButton("");

    JButton exitButton                  = new JButton("Exit");
    JButton newGameButton               = new JButton("New Game");


    public FinalFrame( ) {

        addComponents();

        buttonsActionListener();

        this.setTitle("RESULTS : ");
        this.setSize(500, 250);
        this.setLayout(new GridLayout(2,1));
        this.emptyButton.setVisible(false);
        this.emptyButton2.setVisible(false);
        this.add(upPanel);
        this.add(downPanel);
        this.setVisible(true);

    }

    private void addComponents(){

        upPanel.setLayout(new GridLayout(5,2));
        downPanel.setLayout(new FlowLayout());

        upPanel.add(emptyButton);
        upPanel.add(emptyButton2);

        upPanel.add(greenPointsJL);
        upPanel.add(greenPointsResultJL);

        upPanel.add(greenPiecesJL);
        upPanel.add(greenPiecesResultJL);

        upPanel.add(redPointsJL);
        upPanel.add(redPointsResultJL);

        upPanel.add(redPiecesJL);
        upPanel.add(redPiecesResultJL);



        downPanel.add(exitButton);
        downPanel.add(newGameButton);
    }


    private String getWinner(){

        if(this.greenPlayerPoints > this.redPlayerPoints){
            return "Green";
        }
        return "Red";
    }


    private void buttonsActionListener(){

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameButtonActionPerformed(e);
            }
        });
    }


    private void exitButtonActionPerformed(ActionEvent evt) {

        this.dispose();
    }


    public void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {

        this.dispose();

        GameFrame.greenPiecesLeft = 6;
        GameFrame.redPiecesLeft = 6;
        GameFrame.greenPiecesQueue.clear();
        GameFrame.redPiecesQueue.clear();
        GameFrame.playerOnePoints = 0;
        GameFrame.playerTwoPoints = 0;
        GameFrame.isGreenPlayerTurn = true;
        GameFrame gameFrame = new GameFrame();

    }
}
