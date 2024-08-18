package theme;

import java.awt.*;

public class ClassicTheme implements Theme {
    @Override
    public Color getSnakeColor() {
        return Color.GREEN;
    }

    @Override
    public Color getFoodColor() {
        return Color.RED;
    }

    @Override
    public Color getTextColor() {
        return Color.RED;
    }

    @Override
    public Color getScoreColor() {
        return Color.GREEN;
    }
}
