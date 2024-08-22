# snake-game
The classic snake game written in Java and Swing.

The objective of this project is to get familiar with game development and Java GUI development using Java Swing. 
The skills learned in this project will be used in making new projects in the future. This project is
based on a tutorial linked in the resources section below. 

Additional features (not in the tutorial) have been added:  

- Restart feature allowing users to replay without exiting game window
- Support for color-blind friendly color palette (toggleable in game)
- Pause feature allowing players to stop game without losing progress 
- Main menu screen

Resources: 
- This project is based on the tutorial: https://www.youtube.com/watch?v=Y62MJny9LHg
- Java Swing documentation: https://docs.oracle.com/javase/tutorial/uiswing/index.html 
- Okabe and Ito color-blind friendly palette: https://siegal.bio.nyu.edu/color-palette/


## How to play

- Press 'Enter' to start, pause, and restart game
- Use the arrow keys on the keyboard to move snake
- Press 'T' to change color theme 
- To exit, close game window

#### Single Player:

  - Player controls snake movement with arrow keys

#### Multiplayer: 
See gameplay notes below. When enabled, two users can play against each other. 
- Player 1 controls the snake with the arrow keys
- Player 2 controls the snake with the WASD keys
- Normal game rules apply:
  - Neither snake can cross game border
  - Snakes cannot collide into their own body (However, they can collide with each other)


Game play notes:

- There is a multi-player feature available in the gameplay. Feature is currently disabled in gameplay. To enable, update the `isMultiPlayer` flag in the `SnakeGame.java` file to `true`.
- Player score is displayed on the top left (and right if multiplayer) hand corner of window
- The color theme can be changed during game play 
