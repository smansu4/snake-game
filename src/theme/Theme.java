package theme;

import java.util.ArrayList;
import java.util.List;

public class Theme {

    private static Theme theme;

    private List<ColorPalette> themesList;
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
}
