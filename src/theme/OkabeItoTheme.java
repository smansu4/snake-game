package theme;

import java.awt.*;

/**
 * The color palette of this theme was selected from the Okabe and Ito palette.
 * Palette can be found here: https://siegal.bio.nyu.edu/color-palette/
 *
 * There are many color-blind friendly palettes available online.
 * This theme was chosen because it also contains black and I prefer black for
 * the background.
 */
public class OkabeItoTheme implements Theme {
    @Override
    public Color getSnakeColor() {
        return new Color(213, 94, 0);
    }

    @Override
    public Color getFoodColor() {
        return new Color(0, 114, 178);
    }

    @Override
    public Color getTextColor() {
        return new Color(204, 121, 167);
    }

    @Override
    public Color getScoreColor() {
        return new Color(0,158,115);
    }
}
