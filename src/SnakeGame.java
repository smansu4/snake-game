import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;

    private Tile snakeHead;
    private ArrayList<Tile> snakeBody;
    private Tile food;
    private Random random;

    //game logic vars
    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);         //snake game to listen to event

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<>();
        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        //100ms = 1/10 sec
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid
//        for (int i = 0; i < boardWidth/tileSize; i++) {
//            g.setColor(Color.DARK_GRAY);
//
//            //(x1, y1, x2, y2) start and stop coordinates (x1,y1)(x2,y2)
//            g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
//            g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
//        }

        //Food
        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize,food.y * tileSize,tileSize,tileSize, true);

        //Snake
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //snakeBody
        snakeBody.forEach(t -> g.fill3DRect(t.x * tileSize, t.y * tileSize, tileSize, tileSize, true));

        //Score
        if(gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Ariel", Font.PLAIN, 48));
            g.drawString("Game Over!", boardWidth/4, boardHeight/3);

            //TODO: replay button
//            g.setColor(Color.GRAY);
//            g.setFont(new Font("Ariel", Font.PLAIN, 24));
//            g.fill3DRect(boardWidth/3, boardHeight/2, 200, 50, true);
//
//            g.setColor(Color.BLACK);
//            g.drawString("Click to Replay", boardWidth/3, boardHeight/4);
        }

        //Always show score on screen
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ariel", Font.PLAIN, 16));
        g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize);   //600 /25 = 24 so rand num between 0 adn 24;
        food.y = random.nextInt(boardHeight/tileSize);  //600
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat food
        if(collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // The snake body does not know where to go,
        // as it is following the lead of the head.
        // The body tiles need to catch up to the tile
        // before it;
        for(int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakeSegment = snakeBody.get(i);

            //This is the first body part right before head
            if(i == 0) {
                snakeSegment.x = snakeHead.x;
                snakeSegment.y = snakeHead.y;
            } else {
                Tile prevSnakeSegment = snakeBody.get(i - 1);
                snakeSegment.x = prevSnakeSegment.x;
                snakeSegment.y = prevSnakeSegment.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //GameOver conditions
        for(int i = 0; i < snakeBody.size(); i++) {
            Tile snakeSegment = snakeBody.get(i);
            if(collision(snakeHead, snakeSegment)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight ) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
            gameLoop.stop();
        }
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    //do not need
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //do not need
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
