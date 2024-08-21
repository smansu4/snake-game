import theme.Theme;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private final int boardWidth;
    private final int boardHeight;
    private Theme theme;

    private Snake snake;
    private Snake snake2;

    private final Tile food;
    private final Random random;

    //game logic vars
    private Timer gameLoop;

    private boolean isMultiplayer = true;
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

        snake = new Snake(5,5);

        if(isMultiplayer) {
            snake2 = new Snake(18,5);
        }

        food = new Tile(10,10);
        random = new Random();
        placeTile(food);

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
        g.fill3DRect(food.getX() * Tile.SIZE, food.getY() * Tile.SIZE,Tile.SIZE,Tile.SIZE, true);

        //Snake 1
        g.setColor(theme.getPalette().getSnakeColor());
        g.fill3DRect(snake.head.getX() * Tile.SIZE, snake.head.y * Tile.SIZE, Tile.SIZE, Tile.SIZE, true);

        //snakeBody
        snake.body.forEach(t -> g.fill3DRect(t.getX() * Tile.SIZE, t.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, true));

        //snake2
        if(isMultiplayer) {
            g.setColor(theme.getPalette().getSecondarySnakeColor());
            g.fill3DRect(snake2.head.getX() * Tile.SIZE, snake2.head.y * Tile.SIZE, Tile.SIZE, Tile.SIZE, true);

            snake2.body.forEach(t -> g.fill3DRect(t.getX() * Tile.SIZE, t.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, true));
        }

        // game over and pause screen
        if(gameOver) {
            displayScreen(g, "Game Over!", "Press enter to replay");
        } else if(pausedGame) {
            displayScreen(g, "Paused Game", "Press enter to unpause");
        }

        if(isMultiplayer) {
            //todo: decide how to display score in UI for each player - color, location, label
            g.setColor(theme.getPalette().getScoreColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 16));
            g.drawString("P1 Score: " + snake.body.size(), Tile.SIZE - 16, Tile.SIZE);

            g.setColor(theme.getPalette().getScoreColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 16));
            g.drawString("P2 Score: " + snake2.body.size(), Tile.SIZE - 16, Tile.SIZE + 25);
        } else {
            // display score
            g.setColor(theme.getPalette().getScoreColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 20));
            g.drawString("Score: " + snake.body.size(), Tile.SIZE - 16, Tile.SIZE);
        }
    }

    public void placeTile(Tile tile) {
        tile.setX(random.nextInt(boardWidth / Tile.SIZE));   // 600/25 = 24 so rand num between 0 adn 24;
        tile.setY(random.nextInt(boardHeight / Tile.SIZE));  // 600
    }

    public boolean isCollision(Tile tile1, Tile tile2) {
        return tile1.getX() == tile2.getX() && tile1.getY() == tile2.getY();
    }

    public void didSnakeEatFood(Snake player) {
        if(isCollision(player.head, food)) {
            player.eat(new Tile(food.getX(), food.getY()));
            placeTile(food);
        }
    }

    //Considering for future release
    public void manageBoundaryCollision(Snake player) {
        if(player.head.x < 0) {
            player.head.x = 0;
            player.velocityX = 0;
        } else if (player.head.x == boardWidth/Tile.SIZE) {
            player.head.x = boardWidth/Tile.SIZE - 1;
            player.velocityX = 0;
        } else if(player.head.y < 0) {
            player.head.y = 0;
            player.velocityY = 0;
        } else if (player.head.y == boardWidth/Tile.SIZE) {
            player.head.y = boardWidth/Tile.SIZE - 1;
            player.velocityY = 0;
        }
    }

    public boolean didSnakeCollideWithBody(Snake player) {
        for (Tile snakeSegment : player.body) {
            if (isCollision(player.head, snakeSegment)) {
                return true;
            }
        }
        return false;
    }

    public void checkGameRules () {
        //GameOver conditions
        if(didSnakeCollideWithBody(snake) || isSnakeOutOfBounds(snake)) gameOver = true;

        if(isMultiplayer) {
            if(isSnakeOutOfBounds(snake2) || isSnakeOutOfBounds(snake2)) gameOver = true;
        }
    }

    private boolean isSnakeOutOfBounds(Snake player) {
        return player.head.x * Tile.SIZE < 0 || player.head.x * Tile.SIZE >= boardWidth ||
                player.head.y * Tile.SIZE < 0 || player.head.y * Tile.SIZE >= boardHeight;
    }

    public void restartGameState() {
        //stop snake and clear body
        snake.resetSnake();
        //move pieces
        placeTile(snake.head);

        if(isMultiplayer) {
            snake2.resetSnake();
            placeTile(snake2.head);
        }

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
            didSnakeEatFood(snake);
            snake.move();
            if(isMultiplayer) {
                didSnakeEatFood(snake2);
                snake2.move();
            }
            checkGameRules();
        } else if(restart) {
            restartGameState();
        }

        repaint();
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {

        if(isMultiplayer) {
            multiplayerKeyPressed(e);
        }

        //snake
        if(e.getKeyCode() == KeyEvent.VK_UP && snake.velocityY != 1) {
            snake.velocityX = 0;
            snake.velocityY = -1;
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN && snake.velocityY != -1) {
            snake.velocityX = 0;
            snake.velocityY = 1;
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT && snake.velocityX != 1) {
            snake.velocityX = -1;
            snake.velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && snake.velocityX != -1) {
            snake.velocityX = 1;
            snake.velocityY = 0;
        }

        // color theme
        else if(e.getKeyCode() == KeyEvent.VK_T) {
            theme.toggle();
        }

        // screens
        else if(!gameStarted && e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }
        else if(!gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            pausedGame = !pausedGame;
        }
        else if(gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            restart = true;
        }
    }

    private void multiplayerKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && snake2.velocityY != 1) {
            snake2.velocityX = 0;
            snake2.velocityY = -1;
        } else if(e.getKeyCode() == KeyEvent.VK_S && snake2.velocityY != -1) {
            snake2.velocityX = 0;
            snake2.velocityY = 1;
        } else if(e.getKeyCode() == KeyEvent.VK_A && snake2.velocityX != 1) {
            snake2.velocityX = -1;
            snake2.velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_D && snake2.velocityX != -1) {
            snake2.velocityX = 1;
            snake2.velocityY = 0;
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
