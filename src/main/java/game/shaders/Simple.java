package game.shaders;

import java.awt.Graphics;
import java.awt.Color;
import game.*;

public class Simple implements Shader {
    public void drawBackground(Graphics g, CColor skyColor, CColor groundColor,
                                int skyHeight, int scrHeight, int scrWidth) {
        g.setColor(skyColor.getColor());
        g.fillRect(0,0,scrWidth, skyHeight);
            
        Color c = groundColor.getColor();
        int j = 0;
        int k = scrHeight - skyHeight - 1;
        if (k < 1) k = 1;
        int l = (k / 75);
        for (int i = scrHeight; i > skyHeight - 1; i --) {
            g.setColor(c);
            g.drawLine(0, i, scrWidth, i);
            if ((l > 0 && j % l == 0) || (l == 0)) {
                int cr = c.getRed() - 1;
                int cg = c.getGreen() - 1;
                int cb = c.getBlue() - 1;
                if (cr > 255) cr = 255;
                if (cg > 255) cg = 255;
                if (cb > 255) cb = 255;
                if (cr < 0) cr = 0;
                if (cg < 0) cg = 0;
                if (cb < 0) cb = 0;
                c = new Color(cr, cg, cb);
            }
            j ++;
        }
    }
    
    public Color getPolygonColor(int x1, int y1, int z1, int x2, int y2, int z2,
                                    int distance, CColor color) {
        Color c2 = color.getColor();
        int newdistance = (int) (distance / 1.25f);
        int distsub = 64;
        int cr = c2.getRed() + (distsub - newdistance);
        int cg = c2.getGreen() + (distsub - newdistance);
        int cb = c2.getBlue() + (distsub - newdistance);
        int maxr = c2.getRed() + 16;
        int maxg = c2.getGreen() + 16;
        int maxb = c2.getBlue() + 16;
        int minr = (int) (c2.getRed() / 2.5f);
        int ming = (int) (c2.getGreen() / 2.5f);
        int minb = (int) (c2.getBlue() / 2.5f);
        if (maxr > 255) maxr = 255;
        if (maxg > 255) maxg = 255;
        if (maxb > 255) maxb = 255;
        if (minr < 0) minr = 0;
        if (ming < 0) ming = 0;
        if (minb < 0) minb = 0;
        if (cr > maxr) cr = maxr;
        if (cg > maxg) cg = maxg;
        if (cb > maxb) cb = maxb;
        if (cr < minr) cr = minr;
        if (cg < ming) cg = ming;
        if (cb < minb) cb = minb;
        return new Color(cr, cg, cb);
    }
}
