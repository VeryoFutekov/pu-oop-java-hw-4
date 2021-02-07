package game.fields;

import game.Constants;

import java.awt.*;

public abstract class BasicField {
    protected int y;
    protected int x;
    protected Color color;

    public BasicField(int y, int x, Color color) {
        this.y = y * Constants.BASIC_FIELD_SIZE;
        this.x = x * Constants.BASIC_FIELD_SIZE;
        this.color = color;
    }

    /**
     * render the game
     * @param g
     */
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, Constants.BASIC_FIELD_SIZE, Constants.BASIC_FIELD_SIZE);
        g.setColor(Color.black);
        g.drawRect(x, y, Constants.BASIC_FIELD_SIZE, Constants.BASIC_FIELD_SIZE);
    }


}
