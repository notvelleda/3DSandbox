package game.shaders;

import java.awt.Graphics;
import java.awt.Color;
import game.*;

public class Flat implements Shader {
    public void drawBackground(Graphics g, CColor skyColor, CColor groundColor,
                                int skyHeight, int scrHeight, int scrWidth) {
        g.setColor(skyColor.getColor());
        g.fillRect(0,0,scrWidth, skyHeight);

        g.setColor(groundColor.getColor());
        g.fillRect(0,skyHeight,scrWidth, scrHeight-skyHeight);
    }
    
    public Color getPolygonColor(int x1, int y1, int z1, int x2, int y2, int z2,
                                    int distance, CColor color) {
        return color.getColor();
    }
}
