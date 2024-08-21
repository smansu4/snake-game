package theme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Theme {

    private static Theme theme;

    private final List<ColorPalette> themesList;
    private boolean useDefaultTheme = true;

    private Theme() {
        themesList = new ArrayList<>();
        themesList.add(new ClassicTheme());
        themesList.add(new OkabeItoTheme());
    }

    public static Theme getInstance() {
        if(theme == null) {
            theme = new Theme();
        }

        return theme;
    }

    public ColorPalette toggle() {
        useDefaultTheme = !useDefaultTheme;
        return getPalette();
    }

    public ColorPalette getPalette() {
        if(useDefaultTheme){
            return themesList.get(0);
        }
        return themesList.get(1);
    }

    private static final class ClassicTheme implements ColorPalette {
        @Override
        public Color getSnakeColor() {
            return Color.GREEN;
        }

        @Override
        public Color getSecondarySnakeColor() {
            return Color.MAGENTA;
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

    /**
     * The color palette of this theme was selected from the Okabe and Ito palette.
     * Palette can be found here: <a href="https://siegal.bio.nyu.edu/color-palette/">...</a>
     * <p>
     * There are many color-blind friendly palettes available online.
     * This theme was chosen because it also contains black and I prefer black for
     * the background.
     */
    private static final class OkabeItoTheme implements ColorPalette {
        @Override
        public Color getSnakeColor() {
            return new Color(240, 228, 66);
        }

        @Override
        public Color getSecondarySnakeColor() {
            return new Color(0,158,115);
        }

        @Override
        public Color getFoodColor() {
            return new Color(0, 114, 178);
        }

        @Override
        public Color getTextColor() {
            return new Color(213, 94, 0);
        }

        @Override
        public Color getScoreColor() {
            return new Color(230,159,0);
        }
    }
}
