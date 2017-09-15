package game;

import java.awt.Graphics;
import java.awt.Color;

public interface Shader {
    public void drawBackground(Graphics g, CColor skyColor, CColor groundColor,
                                int skyHeight, int scrHeight, int scrWidth);
    
    public Color getPolygonColor(int x1, int y1, int z1, int x2, int y2, int z2,
                                    int distance, CColor color);
}
