package game.entities;

import game.*;

public class Weapon extends Entity {
    long time = System.currentTimeMillis();
    
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
    
    public boolean onTrigger() {
        if (System.currentTimeMillis() - time > 250) {
            Renderer r = Renderer.inst;
            Entity e = (Entity) new Projectile(r.getCamX(), r.getCamY(), r.getCamZ(), r.getXRot(), r.getYRot());
            r.entities.add(e);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
