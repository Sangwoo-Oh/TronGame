package model;

public enum Direction {
    STAY(0, 0), DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);
    
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final int x, y;
}
