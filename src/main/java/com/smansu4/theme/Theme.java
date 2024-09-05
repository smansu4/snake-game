package com.smansu4.theme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Theme {
    public enum ThemeEnum {
        LIGHT_THEME,
        DARK_THEME
    }

    private static Theme theme;
    private final ColorPalette light;
    private final ColorPalette dark;
    private ThemeEnum selection;

    private Theme() {
        this.light = new Light();
        this.dark = new Dark();
        selection = ThemeEnum.DARK_THEME;
    }

    public static Theme getInstance() {
        if(theme == null) theme = new Theme();

        return theme;
    }

    public void setTheme(ThemeEnum themeEnum) {
        selection = themeEnum;
    }

    public ColorPalette getPalette() {
        if(selection == ThemeEnum.DARK_THEME) return dark;

        return light;
    }

    private static final class Dark implements ColorPalette {
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
            return Color.WHITE;
        }

        @Override
        public Color getBackgroundColor() {
            return Color.BLACK;
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
    private static final class Light implements ColorPalette {
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
        public Color getBackgroundColor() {
            return new Color(245,245,245);
        }
    }
}
