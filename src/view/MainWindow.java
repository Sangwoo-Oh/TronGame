package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame {
    private Board panel;
    private PlayerConfig player1Config;
    private PlayerConfig player2Config;
    
    public MainWindow() throws IOException {
        setTitle("Tron Game!");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(
                    MainWindow.this,
                    "Do you really want to quit?",
                    "Really?",
                    JOptionPane.YES_NO_OPTION
                );

                if (n == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
        
        // Player configuration dialog
        showInputDialog(this);
        panel = new Board(player1Config, player2Config);
        add(panel);
        pack();
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu menuRestart = new JMenu("Restart");
        JMenuItem restartWithSamePlayers = new JMenuItem("With same players");
        JMenuItem restartWithNewPlayers = new JMenuItem("With new players");
        JMenuItem menuHighscore = new JMenuItem("Highscore");
        menuRestart.add(restartWithSamePlayers);
        menuRestart.add(restartWithNewPlayers);
        menu.add(menuRestart);
        menu.add(menuHighscore);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        restartWithNewPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int result = JOptionPane.showConfirmDialog(
                   MainWindow.this,
                   "Do you want to restart a game with new players?",
                   "Restart Confirmation",
                   JOptionPane.YES_NO_OPTION
               );
               if (result == JOptionPane.YES_OPTION) {
                   try {
                        remove(panel);
                        showInputDialog(null);
                        panel = new Board(player1Config, player2Config);
                        add(panel);
                        validate();
                        repaint();
                   } catch (IOException exception){}
               }
           } 
        });
        restartWithSamePlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int result = JOptionPane.showConfirmDialog(
                   MainWindow.this,
                   "Do you want to restart a game with same players?",
                   "Restart Confirmation",
                   JOptionPane.YES_NO_OPTION
               );
               if (result == JOptionPane.YES_OPTION) {
                   try {
                        remove(panel);
                        panel = new Board(player1Config, player2Config);
                        add(panel);
                        validate();
                        repaint();
                   } catch (IOException exception){}
               }
           } 
        });
        menuHighscore.addActionListener(new AbstractAction("Highscore table") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultWindow(panel.getResults(), MainWindow.this);
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                int kc = ke.getKeyCode();               
                panel.changeDirection(kc);
                
            }
        });
        
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    public void showInputDialog(Component c) {
        while (true) {
            HashSet<String> colors = new HashSet<>(new ArrayList<>(Arrays.asList("Blue", "Red", "Green", "Yellow")));
            player1Config = NameAndColorInputDialog.showNameAndColorInputDialog(
                c,
                "Enter details for Player 1:",
                "Player 1 Configuration",
                colors.stream().toArray(String[]::new)
            );
            if (player1Config == null) System.exit(0);
            if (player1Config.getName().equals("")) {
                JOptionPane.showMessageDialog(
                        c,
                        "Empty name is not allowed",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }
            if (player1Config.getName().length() > 20) {
                JOptionPane.showMessageDialog(
                        c,
                        "Maximum length of name is 20.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }

            colors.remove(player1Config.getColor());

            player2Config = NameAndColorInputDialog.showNameAndColorInputDialog(
                    c,
                    "Enter details for Player 2:",
                    "Player 2 Configuration",
                    colors.stream().toArray(String[]::new)
            );
            if (player2Config == null) System.exit(0);
            if (player2Config.getName().equals("")) {
                JOptionPane.showMessageDialog(
                        c,
                        "Empty name is not allowed",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }
            if (player2Config.getName().length() > 20) {
                JOptionPane.showMessageDialog(
                        c,
                        "Maximum length of name is 20.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }
            
            if (player1Config.getName().equalsIgnoreCase(player2Config.getName())) {
                JOptionPane.showMessageDialog(
                        c,
                        "Players cannot have the same name. Please enter unique names.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }

            break;
        }
    }
    
    public static void main(String[] args) {
        try {
            JFrame game = new MainWindow();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}