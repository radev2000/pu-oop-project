package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Modal extends JDialog {

    public Modal(JFrame parent, String title, String message) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);

        String buttonText = "Continue.";
        JButton restartButton = new JButton(buttonText);
        panel.add(restartButton);
        getContentPane().add(panel);

        restartButton.addActionListener((ActionEvent event) -> {

            //GamePanel.isGameOn = true;
            this.dispose();
        });
        this.add(panel);
        this.setResizable(false);
        this.setSize(new Dimension(600, 600));
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}