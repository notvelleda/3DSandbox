package game.entities;

import game.*;

public class Weapon extends Entity {
    public Weapon(int x, int y, int z, int xRot, int yRot) {
        super(x, y, z, xRot, yRot);
        this.model = new Model("/scarh.obj", 10);
        this.model.setRotation(xRot, yRot);
        this.model.invertZ();
        this.model.addZ(70);
    }
    
    public void tick() {
        Renderer r = Renderer.inst;
        this.x = r.getCamX() - 2;
        this.y = r.getCamY() - 3;
        this.z = r.getCamZ() - 2;
        this.model.setRotation((-r.getXRot()) + 192, r.getYRot());
    }
}
