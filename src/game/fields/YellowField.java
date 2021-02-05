package game.fields;

import java.awt.*;

public class YellowField extends BasicField {
    private String symbol;

    public YellowField(int y, int x, Color color,String symbol) {
        super(y, x, color);
        this.symbol=symbol;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawString(symbol,this.x+50,this.y+50);
    }
}
