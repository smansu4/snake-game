package com.smansu4.snake;

import com.smansu4.enums.GameStateAction;
import com.smansu4.theme.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static com.smansu4.panels.ScoreEnums.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final HighScoreReader highScoreReader;
    private final int boardWidth;
    private final int boardHeight;
    private int highScore;
    private final Theme theme;

    private final Snake snake;
    private final Snake snake2;

    private final Tile food;
    private final Random random;

    //game logic vars
    private Timer gameLoop;

    private final boolean multiplayerEnabled;
    private boolean gameOver = false;
    private boolean pausedGame = false;

    public SnakeGame(int boardWidth, int boardHeight, boolean isMultiplayer) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.theme = Theme.getInstance();
        this.multiplayerEnabled = isMultiplayer;
        highScoreReader = new HighScoreReader();
        highScoreReader.readHighScore();

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(theme.getPalette().getBackgroundColor());
        addKeyListener(this);
        setFocusable(true);         //snake game to listen to event

        snake = new Snake(18,5);
        snake2 = new Snake(5,5);

        food = new Tile(10,10);
        random = new Random();
        placeTile(food);

        //100ms = 1/10 sec
        gameLoop = new Timer(100, this);
    }

    public void startGame() {
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Food
        g.setColor(theme.getPalette().getFoodColor());
        g.fillRoundRect(food.getX() * Tile.SIZE, food.getY() * Tile.SIZE,Tile.SIZE,Tile.SIZE, 25,25);

        //Snake 1
        g.setColor(theme.getPalette().getSnakeColor());
        g.fill3DRect(snake.head.getX() * Tile.SIZE, snake.head.y * Tile.SIZE, Tile.SIZE, Tile.SIZE, true);

        //snakeBody
        snake.body.forEach(t -> g.fill3DRect(t.getX() * Tile.SIZE, t.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, true));

        //snake2
        if(multiplayerEnabled) {
            g.setColor(theme.getPalette().getSecondarySnakeColor());
            g.fill3DRect(snake2.head.getX() * Tile.SIZE, snake2.head.y * Tile.SIZE, Tile.SIZE, Tile.SIZE, true);

            snake2.body.forEach(t -> g.fill3DRect(t.getX() * Tile.SIZE, t.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, true));
        }

        // game over and pause screen
        if(pausedGame) {
            displayScreen(g, "Paused Game", "Press space to unpause");
        }
        if(multiplayerEnabled) {
            g.setColor(theme.getPalette().getSecondarySnakeColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 16));
            g.drawString("Score: " + snake2.body.size(), Tile.SIZE - 16, Tile.SIZE);

            g.setColor(theme.getPalette().getSnakeColor());
            g.setFont(new Font("Ariel", Font.PLAIN, 16));
            g.drawString("Score: " + snake.body.size(), Tile.SIZE * 19, Tile.SIZE);
        } else {
            // display score
            g.setColor(theme.getPalette().getTextColor());
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

    public void checkForHighScore() {
        if(snake.body.size() > highScore || snake2.body.size() > highScore) {
            highScore = Math.max(snake.body.size(), snake2.body.size());
            highScoreReader.updateHighScore(highScore);
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

        if(multiplayerEnabled) {
            if(isSnakeOutOfBounds(snake2) || isSnakeOutOfBounds(snake2)) gameOver = true;
        }
    }

    private boolean isSnakeOutOfBounds(Snake player) {
        return player.head.x * Tile.SIZE < 0 || player.head.x * Tile.SIZE >= boardWidth ||
                player.head.y * Tile.SIZE < 0 || player.head.y * Tile.SIZE >= boardHeight;
    }

    private void displayScreen(Graphics g, String title, String subtitle) {
        g.setColor(theme.getPalette().getTextColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 48));
        g.drawString(title, boardWidth/4, boardHeight/3);

        g.setColor(theme.getPalette().getTextColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 24));
        g.drawString(subtitle, 175, 330);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver && !pausedGame) {
            didSnakeEatFood(snake);
            snake.move();
            if(multiplayerEnabled) {
                didSnakeEatFood(snake2);
                snake2.move();
            }
            checkGameRules();
        }

        repaint();

        if(gameOver) {
            int defaultOldValue = -1;

            gameLoop.stop();
            checkForHighScore();
            this.firePropertyChange(HIGH_SCORE.toString(), defaultOldValue, highScore);
            this.firePropertyChange(P1_SCORE.toString(), defaultOldValue, snake.body.size());
            if(multiplayerEnabled) {
                this.firePropertyChange(P2_SCORE.toString(), defaultOldValue, snake2.body.size());
            }
            this.firePropertyChange(GameStateAction.GAME_OVER.toString(), false, true);
        }
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {

        if(multiplayerEnabled) {
            multiplayerKeyPressed(e);
        }

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
        } else if(!gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            pausedGame = !pausedGame;
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

    private class HighScoreReader {
        private final String FILE_PATH = "src/main/resources/highScore.txt";

        private void readHighScore()  {
            try {
                File file = new File(FILE_PATH);
                Scanner scanner = new Scanner(file);
                highScore = scanner.nextInt();
                scanner.close();
            } catch (IOException e) {
                System.out.println("Error reading highScore.txt");
                highScore = 0;
            }
        }

        private void updateHighScore(int currentScore) {
            try {
                FileWriter writer = new FileWriter(FILE_PATH);
                writer.write(String.valueOf(currentScore));
                writer.close();
            } catch(IOException e) {
                System.out.println("highScore.txt not found");
            }
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
