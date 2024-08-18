package theme;

import java.awt.*;

public interface Theme {

    Color getSnakeColor();
    Color getFoodColor();
    Color getTextColor();
    Color getScoreColor();

    default Color getBackgroundColor() {return Color.BLACK;};
}
