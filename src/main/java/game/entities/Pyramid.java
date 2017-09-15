package game.entities;

import game.*;

public class Pyramid extends Entity {
    public Pyramid(int x, int y, int z, int xRot, int yRot) {
        super(x, y, z, xRot, yRot);
        this.model = new Model("/pyramid.obj");
        this.model.setRotation(xRot, yRot);
    }
}
