package model;

public class Player {
    String name;
    Position position;
    Direction direction;
    
    public Player(String name, Position position) {
        this.name = name;
        this.position = position;
        this.direction = Direction.STAY;
    }
    
    public void move() {
        int prev_x = position.x;
        int prev_y = position.y;
        position.x += direction.x;
        position.y += direction.y;
    }
    
    public void changeDirection(Direction direction) {
        if (this.direction == Direction.DOWN && direction == Direction.UP
          || this.direction == Direction.UP && direction == Direction.DOWN
          || this.direction == Direction.RIGHT && direction == Direction.LEFT
          || this.direction == Direction.LEFT && direction == Direction.RIGHT) {
            return;
        }
        this.direction = direction;
    }
}
