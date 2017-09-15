package game;

public class Entity {
    public Model model;
    public int x;
    public int y;
    public int z;
    
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
    
    public void tick() {}
}
