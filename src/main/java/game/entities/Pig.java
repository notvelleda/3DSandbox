package game.entities;

import game.*;

public class Pig extends Entity {
    public float timeOffs;
    public float xd = 0;
    public float zd = 0;
    public float rot = 0;
    public float rotA = (float)(Math.random() + 1.0) * 0.01f;
    
    public Pig(int x, int y, int z, int xRot, int yRot) {
        super(x, y + 6, z, xRot, yRot);
        this.model = new Model("/piggy.obj");
        this.model.flipYZ();
        this.model.setRotation(xRot, yRot);
        this.aabb = this.model.calcAABB();
        this.health = 10f;
        this.timeOffs = (float)Math.random() * 1239813.0f;
    }
    
    public void tick() {
        float f = 0.0f;
        float f2 = 0.0f;
        this.rot += this.rotA;
        this.rotA = (float) ((double) this.rotA * 0.99);
        this.rotA = (float) ((double) this.rotA + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.07999999821186066);
        this.moveRelative((int) (Math.cos(this.rot) * 3), 0, (int) (Math.sin(this.rot) * 3));
        this.move();
        this.model.xRot = (int) (Math.sin(System.nanoTime() / 1.75E9 * 1.0f + this.timeOffs) * 140);
    }
}
