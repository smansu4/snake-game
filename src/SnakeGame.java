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

    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize = 25;
    private Theme theme;

    private final Tile snakeHead;
    private final ArrayList<Tile> snakeBody;
    private final Tile food;
    private final Random random;

    //game logic vars
    private Timer gameLoop;
    private int velocityX;
    private int velocityY;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean restart = false;
    private boolean pausedGame = false;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.theme = Theme.getInstance();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(theme.getPalette().getBackgroundColor());
        addKeyListener(this);
        setFocusable(true);         //snake game to listen to event

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<>();
        food = new Tile(10,10);
        random = new Random();
        placeTile(food);

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

        if(!gameStarted) {
            displayMainMenuScreen(g);
            return;
        }

        //Food
        g.setColor(theme.getPalette().getFoodColor());
        g.fill3DRect(food.x * tileSize,food.y * tileSize,tileSize,tileSize, true);

        //Snake
        g.setColor(theme.getPalette().getSnakeColor());
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //snakeBody
        snakeBody.forEach(t -> g.fill3DRect(t.x * tileSize, t.y * tileSize, tileSize, tileSize, true));

        // game over and pause screen
        if(gameOver) {
            displayScreen(g, "Game Over!", "Press enter to replay");
        } else if(pausedGame) {
            displayScreen(g, "Paused Game", "Press enter to unpause");
        }

        // display score
        g.setColor(theme.getPalette().getScoreColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 20));
        g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
    }

    public void placeTile(Tile tile) {
        tile.x = random.nextInt(boardWidth/tileSize);   //600 /25 = 24 so rand num between 0 adn 24;
        tile.y = random.nextInt(boardHeight/tileSize);  //600
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat food
        if(collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeTile(food);
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
        placeTile(snakeHead);
        placeTile(food);

        //reset state
        restart = false;
        gameOver = false;
    }

    private void displayScreen(Graphics g, String title, String subtitle) {
        g.setColor(theme.getPalette().getTextColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 48));
        g.drawString(title, boardWidth/4, boardHeight/3);

        g.setColor(theme.getPalette().getTextColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 24));
        g.drawString(subtitle, 185, 330);
    }

    private void displayMainMenuScreen(Graphics g) {
        displayScreen(g, "SNAKE GAME", "Press enter to play");

        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        g.setColor(theme.getPalette().getTextColor());
        g.drawString("Press enter to pause game", 210, 400);
        g.drawString("Press 'T' to change theme", 215, 425);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameStarted && !gameOver && !pausedGame) {
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
        } //theme
        else if(e.getKeyCode() == KeyEvent.VK_T) {
            theme.toggle();
        } else if(!gameStarted && e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }
        else if(!gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            pausedGame = !pausedGame;
        }
        else if(gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
