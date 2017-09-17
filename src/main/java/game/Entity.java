package game;

import java.util.ArrayList;
import game.phys.AABB;

public class Entity {
    public Model model;
    public AABB aabb = null;
    public int x;
    public int y;
    public int z;
    public int xd;
    public int yd;
    public int zd;
    public boolean _despawn = false;
    public float health = 0f;
    
    public Entity(int x, int y, int z, int xRot, int yRot) {
        this(new Model(new int[][] {}), x, y, z, xRot, yRot);
    }
    
    public Entity(Model model, int x, int y, int z) {
        this(model, x, y, z, 0, 0);
    }
    
    public Entity(Model model, int x, int y, int z, int xRot, int yRot) {
        this.model = model;
        this.x = x;
        this.y = y;
        this.z = z;
        this.model.xRot = xRot;
        this.model.yRot = yRot;
    }
    
    public void moveRelative(int dx, int dy, int dz) {
        zd += (Renderer.get_Sin(this.model.xRot) * dz) / 256;
		xd += (Renderer.get_Cos(this.model.xRot) * dz) / 256;
        
        zd += (Renderer.get_Sin(this.model.xRot + 70) * dx) / 256;
		xd += (Renderer.get_Cos(this.model.xRot + 70) * dx) / 256;
        
        yd += dy;
    }
    
    public void move() {
        ArrayList<AABB> arrayList = Renderer.inst.getEntityAABBs();
        int n;
        AABB aabb = this.aabb.cloneMove(this.x, this.z);
        for (n = 0; n < arrayList.size(); ++n) {
            this.xd = arrayList.get(n).clipXCollide(aabb, this.xd);
        }
        aabb.move(xd, 0);
        for (n = 0; n < arrayList.size(); ++n) {
            this.zd = arrayList.get(n).clipZCollide(aabb, this.zd);
        }
        aabb.move(0, zd);
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }
    
    public void moveNoclip() {
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }
    
    public void tick() {}
    
    public boolean onTrigger() {
        return false;
    }
    
    public boolean canCollide() {
        return false;
    }
    
    public void onCollision(Entity e) {}
    
    public void despawn() {
        this._despawn = true;
    }
}
