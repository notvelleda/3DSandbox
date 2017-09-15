package game.entities;

import game.*;

public class SpaceShip extends Entity {
    public SpaceShip(int x, int y, int z, int xRot, int yRot) {
        super(x, y - 15, z, xRot, yRot);
        this.model = new Model(new int[][] {
			{80,0,0,0,30,-20,0,-30,-20,CColor.GREY3.getInt()},
			{80,0,0,0,30,20,0,30,10,CColor.GREEN.getInt()},
			{80,0,0,0,-30,10,0,-30,20,CColor.GREEN.getInt()},
			{80,0,0,10,0,40,0,30,20,CColor.BLUE.getInt()},
			{80,0,0,0,-30,20,10,0,40,CColor.BLUE.getInt()},
			{80,0,0,10,-70,0,0,-30,10,CColor.GREY2.getInt()},
			{80,0,0,0,30,10,10,70,0,CColor.GREY2.getInt()},
			{80,0,0,0,-30,-20,10,-70,0,CColor.WHITE.getInt()},
			{80,0,0,10,70,0,0,30,-20,CColor.WHITE.getInt()},
			{10,-70,0,-50,-70,0,0,-30,10,CColor.GREY2.getInt()},
			{10,-70,0,0,-30,-20,-50,-70,0,CColor.GREY3.getInt()},
			{10,70,0,0,30,10,-50,70,0,CColor.GREY2.getInt()},
			{10,70,0,-50,70,0,0,30,-20,CColor.GREY3.getInt()},
			{-50,-70,0,0,-30,-20,0,-30,10,CColor.GREY3.getInt()},
			{-50,70,0,0,30,10,0,30,-20,CColor.GREY3.getInt()},
			{0,30,-20,-30,0,0,0,-30,-20,CColor.GREY3.getInt()},
			{0,-30,-20,-30,0,0,-30,0,20,CColor.GREY3.getInt()},
			{0,30,-20,-30,0,20,-30,0,0,CColor.GREY3.getInt()},
			{-30,0,20,0,-30,10,0,-30,-20,CColor.GREY2.getInt()},
			{-30,0,20,0,30,-20,0,30,10,CColor.GREY2.getInt()},
			{10,0,40,-30,0,20,0,30,20,CColor.BLUE.getInt()},
			{10,0,40,0,-30,20,-30,0,20,CColor.BLUE.getInt()},
			{-30,0,20,0,-30,20,0,-30,10,CColor.GREEN.getInt()},
			{-30,0,20,0,30,10,0,30,20,CColor.GREEN.getInt()}
		});
        this.model.setRotation(xRot, yRot);
    }
}
