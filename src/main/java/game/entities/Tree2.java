package game.entities;

import game.*;

public class Tree2 extends Entity {
    public Tree2(int x, int y, int z, int xRot, int yRot) {
        super(x, y - 22, z, xRot, yRot);
        this.model = new Model("/tree2.obj", 12);
        this.model.setRotation(xRot, yRot);
    }
}
