package game.entities;

import game.*;

public class Tree extends Entity {
    public Tree(int x, int y, int z, int xRot, int yRot) {
        super(x, y, z, xRot, yRot);
        this.model = new Model(new int[][] {
            {5,5,0,0,0,50,-5,5,0,CColor.ORANGE.getInt()},
            {-5,-5,0,0,0,50,5,-5,0,CColor.BROWN.getInt()},
            {5,-5,0,0,0,50,5,5,0,CColor.BROWN.getInt()},
            {-5,5,0,0,0,50,-5,-5,0,CColor.ORANGE.getInt()},
            {0,0,50,-10,-10,30,-10,10,30,CColor.GREEN.getInt()},
            {0,0,50,10,10,30,10,-10,30,CColor.LIGHTGREEN.getInt()},
            {0,0,50,-10,10,30,10,10,30,CColor.LIGHTGREEN.getInt()},
            {0,0,50,10,-10,30,-10,-10,30,CColor.GREEN.getInt()},
            {0,0,30,0,20,20,20,0,20,CColor.GREEN.getInt()},
            {0,0,30,20,0,20,0,-20,20,CColor.GREEN.getInt()},
            {0,0,30,-20,0,20,0,20,20,CColor.LIGHTGREEN.getInt()},
            {0,0,30,0,-20,20,-20,0,20,CColor.LIGHTGREEN.getInt()}
        });
        this.model.setRotation(xRot, yRot);
        this.aabb = this.model.calcAABB();
    }
}
