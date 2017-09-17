package game.entities;

import game.*;

public class Projectile extends Entity {
    int ox, oy, oz;
    
    public Projectile(int x, int y, int z, int xRot, int yRot) {
        super(x, y, z, xRot, yRot);
        this.model = new Model("/projectile.obj", 2);
        this.model.setRotation(xRot, yRot);
        this.ox = x;
        this.oy = y;
        this.oz = z;
        this.aabb = this.model.calcAABB();
    }
    
    public void tick() {
        this.moveRelative(0, 0, 10);
        this.moveNoclip();
        if (this.x - this.ox > 3000 || this.z - this.oz > 3000 || this.x - this.ox < -3000 || this.z - this.oz < -3000)
            this.despawn();
    }
    
    public void onCollision(Entity e) {
        this.despawn();
        if (e.health > 0) {
            e.health -= Math.random() * 5;
            if (e.health <= 0) e.despawn();
        }
    }
    
    public boolean canCollide() {
        return true;
    }
}
