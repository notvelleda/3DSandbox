package game.phys;

public class AABB {
    public int x0;
    public int z0;
    public int x1;
    public int z1;

    public AABB(int f, int f2, int f3, int f4) {
        this.x0 = f;
        this.z0 = f2;
        this.x1 = f3;
        this.z1 = f4;
    }

    public AABB expand(int f, int f3) {
        int f4 = this.x0;
        int f6 = this.z0;
        int f7 = this.x1;
        int f9 = this.z1;
        if (f < 0) {
            f4 += f;
        }
        if (f > 0) {
            f7 += f;
        }
        if (f3 < 0) {
            f6 += f3;
        }
        if (f3 > 0) {
            f9 += f3;
        }
        return new AABB(f4, f6, f7, f9);
    }

    public AABB grow(int f, int f3) {
        int f4 = this.x0 - f;
        int f6 = this.z0 - f3;
        int f7 = this.x1 + f;
        int f9 = this.z1 + f3;
        return new AABB(f4, f6, f7, f9);
    }

    public AABB cloneMove(int f, int f3) {
        return new AABB(this.x0 + f, this.z0 + f3, this.x1 + f, this.z1 + f3);
    }

    public int clipXCollide(AABB aabb, int f) {
        int f2;
        if (aabb.z1 <= this.z0 || aabb.z0 >= this.z1) {
            return f;
        }
        if (f > 0 && aabb.x1 <= this.x0 && (f2 = this.x0 - aabb.x1) < f) {
            f = f2;
        }
        if (f < 0 && aabb.x0 >= this.x1 && (f2 = this.x1 - aabb.x0) > f) {
            f = f2;
        }
        return f;
    }

    public int clipZCollide(AABB aabb, int f) {
        int f2;
        if (aabb.x1 <= this.x0 || aabb.x0 >= this.x1) {
            return f;
        }
        if (f > 0 && aabb.z1 <= this.z0 && (f2 = this.z0 - aabb.z1) < f) {
            f = f2;
        }
        if (f < 0 && aabb.z0 >= this.z1 && (f2 = this.z1 - aabb.z0) > f) {
            f = f2;
        }
        return f;
    }

    public boolean intersects(AABB aabb) {
        if (aabb.x1 <= this.x0 || aabb.x0 >= this.x1) {
            return false;
        }
        if (aabb.z1 <= this.z0 || aabb.z0 >= this.z1) {
            return false;
        }
        return true;
    }
    
    public boolean _intersects(AABB aabb) {
        return aabb.x1 > this.x0 && aabb.x0 < this.x1 && aabb.z1 > this.z0 && aabb.z0 > this.z1;
    }

    public void move(int f, int f3) {
        this.x0 += f;
        this.z0 += f3;
        this.x1 += f;
        this.z1 += f3;
    }
    
    public String toString() {
        return "AABB(" + this.x0 + ", " + this.z0 + ", " + this.x1 + ", " + this.z1 + ")";
    }
}

