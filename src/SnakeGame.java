import theme.Theme;

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
    private Theme colorPalette;

    private Tile snakeHead;
    private ArrayList<Tile> snakeBody;
    private Tile food;
    private Random random;

    //game logic vars
    private Timer gameLoop;
    private int velocityX;
    private int velocityY;

    private boolean gameOver = false;
    private boolean restart = false;
    private boolean pausedGame = false;

    public SnakeGame(int boardWidth, int boardHeight, Theme colorPalette) {
        this.colorPalette = colorPalette;

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(colorPalette.getBackgroundColor());
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

        //Food
        g.setColor(colorPalette.getFoodColor());
        g.fill3DRect(food.x * tileSize,food.y * tileSize,tileSize,tileSize, true);

        //Snake
        g.setColor(colorPalette.getSnakeColor());
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //snakeBody
        snakeBody.forEach(t -> g.fill3DRect(t.x * tileSize, t.y * tileSize, tileSize, tileSize, true));

        //Score
        if(gameOver) {
            g.setColor(colorPalette.getTextColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 48));
            g.drawString("Game Over!", boardWidth/4, boardHeight/3);

            g.setColor(colorPalette.getTextColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 24));
            g.drawString("Click space to replay", 175, 330);
        } else if(pausedGame) {
            g.setColor(colorPalette.getTextColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 48));
            g.drawString("Paused Game", boardWidth/4, boardHeight/3);

            g.setColor(colorPalette.getTextColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 24));
            g.drawString("Click space to unpause", 175, 330);
        }

        //Always show score on screen
        g.setColor(colorPalette.getScoreColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 20));
        g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize);   //600 /25 = 24 so rand num between 0 adn 24;
        food.y = random.nextInt(boardHeight/tileSize);  //600
    }

    private void placeSnake() {
        snakeHead.x = random.nextInt(boardWidth/tileSize);
        snakeHead.y = random.nextInt(boardHeight/tileSize);
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
        for (Tile snakeSegment : snakeBody) {
            if (collision(snakeHead, snakeSegment)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize >= boardHeight ) {
            gameOver = true;
        }
    }

    public void restartGameState() {

        //stop snake from moving;
        velocityX = 0;
        velocityY = 0;

        //clear the body
        snakeBody.clear();

        //move pieces
        placeSnake();
        placeFood();

        //reset state
        restart = false;
        gameOver = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver && !pausedGame) {
            move();
        } else if(restart) {
            restartGameState();
        }

        repaint();
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
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        } else if(!gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            pausedGame = !pausedGame;
        }
        else if(gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart = true;
        }
    }


    //do not need methods below
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
