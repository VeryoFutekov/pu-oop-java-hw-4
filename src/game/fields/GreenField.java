package game.fields;

import java.awt.*;

public class GreenField extends BasicField {
    private boolean isEnding;

    public GreenField(int y, int x, Color color) {
        super(y, x, color);

    }

    public GreenField setEnding(boolean ending) {
        isEnding = ending;
        return this;
    }

    public boolean isEnding() {
        return isEnding;
    }
}
