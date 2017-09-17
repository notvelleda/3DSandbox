package game;

import java.awt.Color;

public class CColor {
    // C=64 color palette :)
    public static CColor BLACK = new CColor("000000", 0);
    public static CColor WHITE = new CColor("FFFFFF", 1);
    public static CColor RED = new CColor("880000", 2);
    public static CColor CYAN = new CColor("AAFFEE", 3);
    public static CColor VIOLET = new CColor("CC44CC", 4);
    public static CColor GREEN = new CColor("00CC55", 5);
    public static CColor BLUE = new CColor("0000AA", 6);
    public static CColor YELLOW = new CColor("EEEE77", 7);
    public static CColor ORANGE = new CColor("DD8855", 8);
    public static CColor BROWN = new CColor("664400", 9);
    public static CColor LIGHTRED = new CColor("FF7777", 10);
    public static CColor GREY1 = new CColor("333333", 11);
    public static CColor GREY2 = new CColor("777777", 12);
    public static CColor LIGHTGREEN = new CColor("AAFF66", 13);
    public static CColor LIGHTBLUE = new CColor("0088FF", 14);
    public static CColor GREY3 = new CColor("BBBBBB", 15);
    
    public static CColor[] colors = new CColor[] {
        CColor.BLACK,       // 0
        CColor.WHITE,       // 1
        CColor.RED,         // 2
        CColor.CYAN,        // 3
        CColor.VIOLET,      // 4
        CColor.GREEN,       // 5
        CColor.BLUE,        // 6
        CColor.YELLOW,      // 7
        CColor.ORANGE,      // 8
        CColor.BROWN,       // 9
        CColor.LIGHTRED,    // 10
        CColor.GREY1,       // 11
        CColor.GREY2,       // 12
        CColor.LIGHTGREEN,  // 13
        CColor.LIGHTBLUE,   // 14
        CColor.GREY3        // 15
    };
    
    private String hex;
    private Color color;
    private int colorInt;
    private int index;
    
    CColor(String str, int index) {
        this.hex = str;
        this.colorInt = Integer.parseInt(str, 16);
        this.color = new Color(this.colorInt);
        this.index = index;
    }
    
    public String getHex() {
        return this.hex;
    }
    
    public int getInt() {
        return this.index;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public String toString() {
        return "Color(#" + this.hex + ")";
    }
    
    public byte getRed() {
        return (byte) this.color.getRed();
    }
    
    public byte getGreen() {
        return (byte) this.color.getGreen();
    }
    
    public byte getBlue() {
        return (byte) this.color.getBlue();
    }
    
    public static void setPalette0() {
        // Default palette
        CColor.BLACK = new CColor("000000", 0);
        CColor.WHITE = new CColor("FFFFFF", 1);
        CColor.RED = new CColor("880000", 2);
        CColor.CYAN = new CColor("AAFFEE", 3);
        CColor.VIOLET = new CColor("CC44CC", 4);
        CColor.GREEN = new CColor("00CC55", 5);
        CColor.BLUE = new CColor("0000AA", 6);
        CColor.YELLOW = new CColor("EEEE77", 7);
        CColor.ORANGE = new CColor("DD8855", 8);
        CColor.BROWN = new CColor("664400", 9);
        CColor.LIGHTRED = new CColor("FF7777", 10);
        CColor.GREY1 = new CColor("333333", 11);
        CColor.GREY2 = new CColor("777777", 12);
        CColor.LIGHTGREEN = new CColor("AAFF66", 13);
        CColor.LIGHTBLUE = new CColor("0088FF", 14);
        CColor.GREY3 = new CColor("BBBBBB", 15);
        
        colors = new CColor[] {
            CColor.BLACK,CColor.WHITE,CColor.RED,CColor.CYAN,
            CColor.VIOLET,CColor.GREEN,CColor.BLUE,CColor.YELLOW,
            CColor.ORANGE,CColor.BROWN,CColor.LIGHTRED,CColor.GREY1,
            CColor.GREY2,CColor.LIGHTGREEN,CColor.LIGHTBLUE,CColor.GREY3};
    }
    
    public static void setPalette1() {
        // Alternate palette from http://www.pepto.de/projects/colorvic/2001/
        /*CColor.BLACK = new CColor("000000", 0);
        CColor.WHITE = new CColor("FFFFFF", 1);
        CColor.RED = new CColor("68372B", 2);
        CColor.CYAN = new CColor("70A4B2", 3);
        CColor.VIOLET = new CColor("6F3D86", 4);
        CColor.GREEN = new CColor("588D43", 5);
        CColor.BLUE = new CColor("352879", 6);
        CColor.YELLOW = new CColor("B8C76F", 7);
        CColor.ORANGE = new CColor("6F4F25", 8);
        CColor.BROWN = new CColor("433900", 9);
        CColor.LIGHTRED = new CColor("9A6759", 10);
        CColor.GREY1 = new CColor("444444", 11);
        CColor.GREY2 = new CColor("6C6C6C", 12);
        CColor.LIGHTGREEN = new CColor("9AD284", 13);
        CColor.LIGHTBLUE = new CColor("6C5EB5", 14);
        CColor.GREY3 = new CColor("959595", 15);*/
        // Alternate palette from http://www.colodore.com/
        CColor.BLACK = new CColor("000000", 0);
        CColor.WHITE = new CColor("FFFFFF", 1);
        CColor.RED = new CColor("813338", 2);
        CColor.CYAN = new CColor("75cec8", 3);
        CColor.VIOLET = new CColor("8e3c97", 4);
        CColor.GREEN = new CColor("56ac4d", 5);
        CColor.BLUE = new CColor("2e2c9b", 6);
        CColor.YELLOW = new CColor("edf171", 7);
        CColor.ORANGE = new CColor("8e5029", 8);
        CColor.BROWN = new CColor("553800", 9);
        CColor.LIGHTRED = new CColor("c46c71", 10);
        CColor.GREY1 = new CColor("4a4a4a", 11);
        CColor.GREY2 = new CColor("7b7b7b", 12);
        CColor.LIGHTGREEN = new CColor("a9ff9f", 13);
        CColor.LIGHTBLUE = new CColor("706deb", 14);
        CColor.GREY3 = new CColor("b2b2b2", 15);
        
        colors = new CColor[] {
            CColor.BLACK,CColor.WHITE,CColor.RED,CColor.CYAN,
            CColor.VIOLET,CColor.GREEN,CColor.BLUE,CColor.YELLOW,
            CColor.ORANGE,CColor.BROWN,CColor.LIGHTRED,CColor.GREY1,
            CColor.GREY2,CColor.LIGHTGREEN,CColor.LIGHTBLUE,CColor.GREY3};
    }
}
