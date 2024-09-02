package com.smansu4.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public final Tile head;
    public final List<Tile> body;

    int velocityX;
    int velocityY;

    public Snake(int x, int y) {
        head = new Tile(x,y);
        body = new ArrayList<>();

        velocityX = 0;
        velocityY = 0;
    }

    public void move() {
        // The snake body does not know where to go,
        // as it is following the lead of the head.
        // The body tiles need to catch up to the tile
        // before it;
        for(int i = body.size() - 1; i >= 0; i--) {
            Tile snakeSegment = body.get(i);

            //This is the first body part right before head
            if(i == 0) {
                snakeSegment.setX(head.getX());
                snakeSegment.setY(head.getY());
            } else {
                Tile prevSnakeSegment = body.get(i - 1);
                snakeSegment.setX(prevSnakeSegment.getX());
                snakeSegment.setY(prevSnakeSegment.getY());
            }
        }

        //Snake Head
        head.x += velocityX;
        head.y += velocityY;
    }

    public void eat(Tile tile) {
        body.add(tile);
    }

    public void shedBody() {
        body.clear();
    }

    public void resetSnake() {
        shedBody();

        velocityX = 0;
        velocityY = 0;
    }
}
