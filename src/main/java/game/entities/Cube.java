package game.entities;

import game.*;

public class Cube extends Entity {
    public Cube(int x, int y, int z, int xRot, int yRot) {
        super(x, y, z, xRot, yRot);
        this.model = new Model("/cube.obj", 1.75f);
        this.model.setRotation(xRot, yRot);
        this.aabb = this.model.calcAABB();
    }
}
