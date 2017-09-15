package game.entities;

import game.*;

public class Bird extends Entity {
    public float timeOffs;
    public float xd = 0;
    public float zd = 0;
    public float rot = 0;
    public float rotA = (float)(Math.random() + 1.0) * 0.01f;
    
    public Bird(int x, int y, int z, int xRot, int yRot) {
        super(x, y - 40, z, xRot, yRot);
        this.model = new Model("/bird.obj", 6);
        this.model.flipYZ();
        this.model.setRotation(xRot, yRot);
        this.timeOffs = (float)Math.random() * 1239813.0f;
    }
    
    public void tick() {
        float f = 0.0f;
        float f2 = 0.0f;
        this.rot += this.rotA;
        this.rotA = (float) ((double) this.rotA * 0.99);
        this.rotA = (float) ((double) this.rotA + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.07999999821186066);
        this.xd = (float) Math.sin(this.rot) * 2;
        this.zd = (float) Math.cos(this.rot) * 2;
        this.x += this.xd;
        this.z += this.zd;
        this.model.xRot = (int) (Math.sin(System.nanoTime() / 1.75E9 * 1.0f + this.timeOffs) * 140);
    }
}
