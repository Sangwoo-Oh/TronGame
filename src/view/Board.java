package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Direction;
import model.Game;
import model.Player;
import model.Position;
import persistence.Result;
import res.ResourceLoader;

public  class Board extends JPanel {
    private Game game;
    private int winner;
    private Image crash, motorcycle;
    private PlayerConfig player1Config;
    private PlayerConfig player2Config;

    private Random rand = new Random();
    public Board(PlayerConfig player1Config, PlayerConfig player2Config) throws IOException {
        setPreferredSize(new Dimension(600, 620));
        setBackground(Color.BLACK);
        
        crash = ResourceLoader.loadImage("res/crash.png");
        motorcycle = ResourceLoader.loadImage("res/motorcycle.png");
        
        this.player1Config = player1Config;
        this.player2Config = player2Config;
        Position player1InitPos = new Position(generateInt(200, 400), generateInt(200, 400));
        Position player2InitPos = new Position(generateInt(200, 500), generateInt(200, 400));
        System.out.println(player1InitPos.toString() + " " + player2InitPos.toString());
        Player player1 = new Player(player1Config.getName(), player1InitPos);        
        Player player2 = new Player(player2Config.getName(), player2InitPos);
        this.game = new Game(player1, player2);
        runGameLoop();
    }
    
    private int generateInt(int min, int max) {
        return rand.nextInt((max-min) + 1) + min;
    }
    
    private void runGameLoop() {
        winner = 0;
        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                winner = game.move();
                repaint();
                if (winner == 1) {
                    ((Timer) e.getSource()).stop();
                    showGameOverDialog(player1Config);
                } else if (winner == 2) {
                    ((Timer) e.getSource()).stop();
                    showGameOverDialog(player2Config);
                }
            }
        });
        timer.start();
    }
    
    // dialog when game is over
    private void showGameOverDialog(PlayerConfig winner) {
        int result = JOptionPane.showConfirmDialog(
            this,
            winner.getName() + " (" + winner.getColor() + ") " + " Won!\nRestart game?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            restartGame();
        }
    }
    
    private void restartGame() {
        Position player1InitPos = new Position(generateInt(200, 400), generateInt(200, 400));
        Position player2InitPos = new Position(generateInt(200, 500), generateInt(200, 400));
        
        this.game = new Game(new Player(player1Config.getName(), player1InitPos),
                             new Player(player2Config.getName(), player2InitPos));
        System.out.println(player1InitPos.toString() + " " + player2InitPos.toString());
        repaint();
        runGameLoop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       
        if (game.isPlayer1Staying() || game.isPlayer2Staying()) {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            String text;
            // Player1 
            g.setColor(getColorFromName(player1Config.getColor()));
            if (game.isPlayer1Staying())
                text = player1Config.getName() + " (" + player1Config.getColor() + "): Press any Key (WASD)!";
            else
                text = player1Config.getName() + " (" + player1Config.getColor() + "): Key pressed";
            
            g.drawString(text, 10, 25);   
            
            // Player2
            g.setColor(getColorFromName(player2Config.getColor()));
            if (game.isPlayer2Staying())
                text = player2Config.getName() + "(" + player2Config.getColor() + "): Press any Key (↑←↓→)!";
            else
                text = player2Config.getName() + "(" + player2Config.getColor() + "): Key pressed";
            
            g.drawString(text, 10, 50);   
            

        }
        for (int y=0; y<600; y++) {
            for (int x=0; x<600; x++) {
                if (game.field[y][x] == 1) {
                    g.setColor(getColorFromName(player1Config.getColor()));
                    g.fillRect(x, y, 1, 1);
                } else if (game.field[y][x] == 2) {
                    g.setColor(getColorFromName(player2Config.getColor()));
                    g.fillRect(x, y, 1, 1);                
                }
            }
        }
        
        int[] player1Pos = game.getPlayer1Pos();
        int[] player2Pos = game.getPlayer2Pos();
        
        // draw player1
        g.setColor(getColorFromName(player1Config.getColor()));
        g.fillOval(player1Pos[0] - 5, player1Pos[1] - 5, 10, 10);
        g.drawImage(motorcycle,game.getPlayer1Pos()[0] - 5, game.getPlayer1Pos()[1] - 5, 10, 10, null);
        
        // draw player2
        g.setColor(getColorFromName(player2Config.getColor()));
        g.fillOval(player2Pos[0] - 5 , player2Pos[1] - 5, 10, 10);
        g.drawImage(motorcycle,game.getPlayer2Pos()[0] - 5, game.getPlayer2Pos()[1] - 5, 10, 10, null);
        
//        System.out.println(winner);
        if (winner == 1) {
            g.drawImage(crash,game.getPlayer2Pos()[0] - 10, game.getPlayer2Pos()[1] - 10, 20, 20, null);
        } else if (winner == 2) {
            g.drawImage(crash,game.getPlayer1Pos()[0] - 10, game.getPlayer1Pos()[1] - 10, 20, 20, null);
        }
//        g.drawImage(crash, 20, 20, 20, 20, null);
    }
    
    public void changeDirection(int keyCode) {
        Direction d = null;
        int player = 0;
        switch (keyCode){
            case KeyEvent.VK_A:  player = 1; d = Direction.LEFT; break;
            case KeyEvent.VK_D: player = 1; d = Direction.RIGHT; break;
            case KeyEvent.VK_W:    player = 1; d = Direction.UP; break;
            case KeyEvent.VK_S:  player = 1; d = Direction.DOWN; break;
            case KeyEvent.VK_LEFT:  player = 2; d = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: player = 2; d = Direction.RIGHT; break;
            case KeyEvent.VK_UP:    player = 2; d = Direction.UP; break;
            case KeyEvent.VK_DOWN:  player = 2; d = Direction.DOWN; break;
        }
        if (d == null) return;
        game.changeDirection(player, d);
    }
    
    private Color getColorFromName(String colorName) {
        return switch (colorName) {
            case "Blue" -> Color.CYAN;
            case "Red" -> Color.RED;
            case "Green" -> Color.GREEN;
            case "Yellow" -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }

    public ArrayList<Result> getResults() {
        return game.getHighScores();
    }
}
