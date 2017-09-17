package game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class TEDDither {
    public static int[][] palette = new int[][] {
        {0, 0, 0},
        {23, 23, 23},
        {70, 7, 10},
        {0, 42, 38},
        {62, 2, 70},
        {0, 51, 0},
        {15, 13, 112},
        {31, 33, 0},
        {62, 14, 0},
        {48, 23, 0},
        {15, 43, 0},
        {70, 3, 38},
        {0, 49, 10},
        {3, 23, 97},
        {31, 7, 112},
        {3, 49, 0},
        {38, 38, 38},
        {89, 20, 23},
        {1, 59, 55},
        {81, 12, 89},
        {5, 69, 1},
        {30, 28, 133},
        {48, 50, 0},
        {81, 28, 1},
        {66, 39, 0},
        {30, 60, 0},
        {89, 14, 55},
        {1, 66, 23},
        {15, 38, 117},
        {48, 19, 133},
        {15, 67, 0},
        {55, 55, 55},
        {109, 35, 39},
        {12, 78, 73},
        {100, 27, 109},
        {18, 88, 12},
        {46, 44, 155},
        {65, 68, 0},
        {100, 44, 12},
        {85, 56, 0},
        {46, 78, 0},
        {109, 29, 73},
        {12, 85, 39},
        {29, 55, 138},
        {65, 34, 155},
        {29, 86, 0},
        {74, 74, 74},
        {129, 51, 56},
        {26, 97, 93},
        {121, 42, 130},
        {32, 108, 26},
        {63, 61, 177},
        {84, 87, 0},
        {121, 61, 26},
        {104, 74, 7},
        {63, 98, 0},
        {129, 45, 93},
        {26, 105, 56},
        {45, 73, 160},
        {84, 51, 177},
        {45, 105, 7},
        {123, 123, 123},
        {184, 98, 103},
        {68, 150, 144},
        {175, 88, 185},
        {76, 161, 68},
        {112, 109, 235},
        {135, 138, 31},
        {175, 110, 68},
        {157, 124, 43},
        {112, 150, 31},
        {184, 90, 144},
        {68, 158, 103},
        {91, 123, 217},
        {135, 98, 235},
        {91, 158, 43},
        {155, 155, 155},
        {219, 129, 134},
        {97, 183, 177},
        {209, 118, 220},
        {105, 195, 96},
        {143, 140, 255},
        {168, 171, 56},
        {209, 141, 96},
        {191, 156, 69},
        {143, 183, 56},
        {219, 121, 177},
        {97, 192, 134},
        {121, 155, 253},
        {168, 128, 255},
        {121, 192, 69},
        {224, 224, 224},
        {255, 195, 201},
        {160, 254, 248},
        {255, 183, 255},
        {169, 255, 159},
        {211, 208, 255},
        {237, 241, 113},
        {255, 209, 159},
        {255, 224, 129},
        {211, 254, 113},
        {255, 186, 248},
        {160, 255, 201},
        {187, 224, 255},
        {237, 195, 255},
        {187, 255, 129},
        {255, 255, 255},
        {253, 255, 255},
        {255, 255, 253},
        {255, 255, 201},
        {255, 255, 219}
    };
    
    public static CColor BLACK = new CColor("000000", 0);
    public static CColor WHITE = new CColor("ffffff", 1);
    public static CColor RED = new CColor("6d2327", 2);
    public static CColor CYAN = new CColor("449690", 3);
    public static CColor VIOLET = new CColor("792a82", 4);
    public static CColor GREEN = new CColor("4ca144", 5);
    public static CColor BLUE = new CColor("0f0d70", 6);
    public static CColor YELLOW = new CColor("edf171", 7);
    public static CColor ORANGE = new CColor("793d1a", 8);
    public static CColor BROWN = new CColor("301700", 9);
    public static CColor LIGHTRED = new CColor("b86267", 10);
    public static CColor GREY1 = new CColor("373737", 11);
    public static CColor GREY2 = new CColor("7b7b7b", 12);
    public static CColor LIGHTGREEN = new CColor("a9ff9f", 13);
    public static CColor LIGHTBLUE = new CColor("706deb", 14);
    public static CColor GREY3 = new CColor("9b9b9b", 15);
    
    public static void setTEDPalette() {
        // TED (Plus-4, C16)
        CColor.BLACK = new CColor("000000", 0);
        CColor.WHITE = new CColor("ffffff", 1);
        CColor.RED = new CColor("6d2327", 2);
        CColor.CYAN = new CColor("449690", 3);
        CColor.VIOLET = new CColor("792a82", 4);
        CColor.GREEN = new CColor("4ca144", 5);
        CColor.BLUE = new CColor("0f0d70", 6);
        CColor.YELLOW = new CColor("edf171", 7);
        CColor.ORANGE = new CColor("793d1a", 8);
        CColor.BROWN = new CColor("301700", 9);
        CColor.LIGHTRED = new CColor("b86267", 10);
        CColor.GREY1 = new CColor("373737", 11);
        CColor.GREY2 = new CColor("7b7b7b", 12);
        CColor.LIGHTGREEN = new CColor("a9ff9f", 13);
        CColor.LIGHTBLUE = new CColor("706deb", 14);
        CColor.GREY3 = new CColor("9b9b9b", 15);
        
        CColor.colors = new CColor[] {
            CColor.BLACK,CColor.WHITE,CColor.RED,CColor.CYAN,
            CColor.VIOLET,CColor.GREEN,CColor.BLUE,CColor.YELLOW,
            CColor.ORANGE,CColor.BROWN,CColor.LIGHTRED,CColor.GREY1,
            CColor.GREY2,CColor.LIGHTGREEN,CColor.LIGHTBLUE,CColor.GREY3};
    }
    
    public static boolean ditherImage = false;
    
    public static IndexColorModel icm;
    
    static {
        byte[] r = new byte[palette.length];
        byte[] g = new byte[palette.length];
        byte[] b = new byte[palette.length];
        
        int i = 0;
        for (int[] j : palette) {
            r[i] = (byte) j[0];
            g[i] = (byte) j[1];
            b[i] = (byte) j[2];
            i ++;
        }
        
        icm = new IndexColorModel(
                7,  // 7 bits = 128 colors
                palette.length,            
                r, g, b
        );
    }
    
    public static BufferedImage dither(BufferedImage src) {
        BufferedImage img = new BufferedImage(
                src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED,
                icm);
        
        if (ditherImage) {
            Graphics2D g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
            g2d.drawImage(src, 0, 0, null);
            g2d.dispose();
        } else {
            for (int x = 0; x < src.getWidth(); x++) {
                for (int y = 0; y < src.getHeight(); y++) {
                    img.setRGB(x, y, src.getRGB(x, y));
                }
            }
        }
            
        return img;
    }
}
