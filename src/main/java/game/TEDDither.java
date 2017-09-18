package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class TEDDither {
    static class C3 {
        int r, g, b;

        public C3(int c) {
            Color color = new Color(c);
            r = color.getRed();
            g = color.getGreen();
            b = color.getBlue();
        }

        public C3(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public C3 add(C3 o) {
            return new C3(r + o.r, g + o.g, b + o.b);
        }

        public int clamp(int c) {
            return Math.max(0, Math.min(255, c));
        }

        public int diff(C3 o) {
            int Rdiff = o.r - r;
            int Gdiff = o.g - g;
            int Bdiff = o.b - b;
            int distanceSquared = Rdiff * Rdiff + Gdiff * Gdiff + Bdiff * Bdiff;
            return distanceSquared;
        }

        public C3 mul(double d) {
            return new C3((int) (d * r), (int) (d * g), (int) (d * b));
        }

        public C3 sub(C3 o) {
            return new C3(r - o.r, g - o.g, b - o.b);
        }

        public Color toColor() {
            return new Color(clamp(r), clamp(g), clamp(b));
        }

        public int toRGB() {
            return toColor().getRGB();
        }
    }
    
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

    private static C3 findClosestPaletteColor(C3 c, C3[] palette) {
        C3 closest = palette[0];

        for (C3 n : palette) {
            if (n.diff(c) < closest.diff(c)) {
                closest = n;
            }
        }

        return closest;
    }
    
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
    public static boolean floydSteinbergDithering = false;
    
    public static IndexColorModel icm;
    
    public static C3[] c3palette;
    
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
        
        c3palette = new C3[palette.length];
        i = 0;
        for (int[] j : palette)
            c3palette[i ++] = new C3(j[0], j[1], j[2]);
    }
    
    public static BufferedImage floydSteinbergDither(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        C3[][] d = new C3[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                d[y][x] = new C3(img.getRGB(x, y));
            }
        }

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                C3 oldColor = d[y][x];
                C3 newColor = findClosestPaletteColor(oldColor, c3palette);
                img.setRGB(x, y, newColor.toColor().getRGB());

                C3 err = oldColor.sub(newColor);

                if (x + 1 < w) {
                    d[y][x + 1] = d[y][x + 1].add(err.mul(7. / 16));
                }
                
                if (x - 1 >= 0 && y + 1 < h) {
                    d[y + 1][x - 1] = d[y + 1][x - 1].add(err.mul(3. / 16));
                }
                
                if (y + 1 < h) {
                    d[y + 1][x] = d[y + 1][x].add(err.mul(5. / 16));
                }
                
                if (x + 1 < w && y + 1 < h) {
                    d[y + 1][x + 1] = d[y + 1][x + 1].add(err.mul(1. / 16));
                }
            }
        }

        return img;
    }
    
    public static BufferedImage colorModelDither(BufferedImage src) {
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
    
    public static BufferedImage dither(BufferedImage src) {
        if (ditherImage) {
            if (floydSteinbergDithering) {
                return floydSteinbergDither(src);
            } else {
                return colorModelDither(src);
            }
        } else {
            BufferedImage img = new BufferedImage(
                src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED,
                icm);
            for (int x = 0; x < src.getWidth(); x++) {
                for (int y = 0; y < src.getHeight(); y++) {
                    img.setRGB(x, y, src.getRGB(x, y));
                }
            }
            return img;
        }
    }
}
