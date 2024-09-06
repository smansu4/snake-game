# snake-game
The classic snake game written in Java and Swing.

The objective of this project is to get familiar with game development and Java GUI development using Java Swing. 
The skills learned in this project will be used in making new projects in the future. This project is
based on a tutorial linked in the resources section below. 

Additional features (not in the tutorial) have been added:  

- Restart feature allowing users to replay without exiting game window
- Support for color-blind friendly color palette
- Pause feature allowing players to stop game without losing progress 
- Multiplayer option for competitive players
- Main menu screen with game options
- High score with persistence between game launches
- Game over menu 

Resources: 
- This project is based on the tutorial: https://www.youtube.com/watch?v=Y62MJny9LHg
- Java Swing documentation: https://docs.oracle.com/javase/tutorial/uiswing/index.html 
- Okabe and Ito color-blind friendly palette: https://siegal.bio.nyu.edu/color-palette/


## How to play

- Press space bar to pause, unpause, and restart game
- Use the arrow keys on the keyboard to move snake
- To exit, close game window

#### Single Player:

  - Player controls snake movement with arrow keys

#### Multiplayer: 
See gameplay notes below. Two users can play against each other. 
- Player 1 controls the snake with the arrow keys
- Player 2 controls the snake with the WASD keys
- Normal game rules apply:
  - Neither snake can cross game border
  - Snakes cannot collide into their own body (However, they can collide with each other)


Game play notes:

- Player score is displayed on the top left hand (and right if multiplayer) corner of window

Development Notes: 
- Replaced the painted options menu to a JPanel with UI components for cleaner look. The buttons allow the user to more easily select game settings vs having to read instructions in small text and toggle keys.
- Decided not to toggle color of main menu as swing does not allow user to change button color easily. The background setting changes the screen color behind the button. Updating opacity replaced the button look and feel with a rectangular box. The original silver buttons did not look good against the dark theme black background.
- The color-blind friendly theme was changed into a light mode. There wasn't a nice way to preview the color changes in the menu for the user without having to recreate the JPanel (to pick up new theme with each key press). Both options would have displayed a black game background with seemingly little difference in colors. Renaming the two themes as light and dark and set background accordingly was more intuitive for users.
  - The game text/snake/food in light mode uses color-blind friendly colors against the lighter background.
- Made the design choice to make the `Theme` class a singleton. Originally the theme was changed via toggling the `T` key. I didn't want to instantiate new instances of the themes each key press while user was deciding on a selection. The singleton pattern was used to ensure only once instance of each color palette would exist.
  - There is now a menu for theme selection and user does not see the UI change in real time with the selection as with the previous drawn menu. For the future, the theme logic can be changed to only instantiate a palette when the user clicks play. The `Theme.java` file can then be completely removed after moving the inner classes to their own file.
- Can also consider using redis to read/write high scores instead of using file. Currently, using file for simplicity.

### Images: 
![Screenshot of game menu](src/main/resources/imgs/options-menu-screen.png?raw=true "Option Menu seen upon app start up")
![Screenshot of single player game in dark mode](src/main/resources/imgs/single-player-dark-mode.png?raw=true "Single player dark mode")
![Screenshot of multi plauer game in light mode](src/main/resources/imgs/multi-player-light-mode.png?raw=true "Multi player dark mode")
![Screenshot of game over screen](src/main/resources/imgs/game-over-screen.png?raw=true "Game over screen")


### TODOs:
5. Resolve the below issue. Memory leak issue with listeners in `SnakeGame.java`. The menu screen doesn't show the error when running for long periods of time. Thinking it's the game loop causing this?
````
CodeCache: size=131072Kb used=4129Kb max_used=4131Kb free=126943Kb
 bounds [0x0000000102bfc000, 0x000000010300c000, 0x000000010abfc000]
 total_blobs=1784 nmethods=1255 adapters=446
 compilation: disabled (not enough contiguous free space left)
OpenJDK 64-Bit Server VM warning: CodeCache is full. Compiler has been disabled.
OpenJDK 64-Bit Server VM warning: Try increasing the code cache size using -XX:ReservedCodeCacheSize=
````