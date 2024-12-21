package model;

import java.util.ArrayList;
import persistence.Database;
import persistence.Result;

public class Game {
    public Player player1;
    public Player player2;
    public int[][] field;
    public Database database;
    
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        field = new int[600][600];
        database = new Database();
    }
    
    public boolean isPlayer1Staying() { return player1.direction == Direction.STAY; }
    public boolean isPlayer2Staying() { return player2.direction == Direction.STAY; }
    
    public int[] getPlayer1Pos() {
        return new int[]{player1.position.x, player1.position.y};
    }
    
    public int[] getPlayer2Pos() {
        return new int[]{player2.position.x, player2.position.y};
    }
    
    public int move() {
        if (player1.direction == Direction.STAY || player2.direction == Direction.STAY) return 0;
        
       
        player1.move();
        player2.move();
       
        int res = isOutOfBound();
        if (res == 1) {
            database.storeResult(player1.name);
            return 1;
        } else if (res == 2) {
            database.storeResult(player2.name);
            return 2;
        }
        
        res = mark();
        if (res == 1) {
            database.storeResult(player1.name);
            return 1;
        } else if (res == 2) {
            database.storeResult(player2.name);
            return 2;
        }
        
        return 0;
    }
    
    public int mark() {
        if (player1.direction == Direction.STAY || player2.direction == Direction.STAY) return 0;
        if (field[player1.position.y][player1.position.x] != 0)
            return 2;
        else
            field[player1.position.y][player1.position.x] = 1;
        
        if (field[player2.position.y][player2.position.x] != 0)
            return 1;
        else
            field[player2.position.y][player2.position.x] = 2;
        
        return 0;
    }
    
    public void changeDirection(int player, Direction direction) {
        if (player == 1) player1.changeDirection(direction);
        else if (player == 2) player2.changeDirection(direction);
    }
    
    public int isOutOfBound() {
        if (player1.position.y < 0 || player1.position.y >= 600 || player1.position.x < 0 || player1.position.x >= 600)
            return 2;
        
        if (player2.position.y < 0 || player2.position.y >= 600 || player2.position.x < 0 || player2.position.x >= 600)
            return 1;
        
        return 0;
    }

    public ArrayList<Result> getHighScores() {
        return database.getHighScores();
    }
}
