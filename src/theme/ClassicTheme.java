package theme;

import java.awt.*;

public final class ClassicTheme implements ColorPalette {
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
        return Color.WHITE;
    }
}
