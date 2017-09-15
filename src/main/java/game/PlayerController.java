package game;

import net.java.games.input.*;
import java.awt.event.*;

public class PlayerController implements KeyListener {
    public int dx, dy, dz;
    public int dx2, dy2, dz2;
    public int dxRot, dyRot;
    public int dxRot2, dyRot2;
    
    public int menuState = 0;
    public int tempMenuSel;
    public int menuSel = 0;
    public int menuItems = 0;
    
    private int xbs = 0;
    private int abs = 0;
    private boolean grounded = true;
    private int ypos = -10;
    private int oldypos = -10;
    private float yvel = 0;
    
    // Key constants:
    public static final int UP = 0;
    public static final int DOWN= 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int FIRE= 4;
    public static final int GAME_A = 5;
    public static final int GAME_B = 6;
    public static final int GAME_C = 7;
    public static final int GAME_D = 8;
    
    public Controller controller;
    
    public PlayerController() {
        this.dx = 0;
        this.dy = 0;
        this.dz = 0;
        this.dxRot = 0;
        this.dyRot = 0;
        
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        /*for(int i = 0; i < ca.length; i ++) {
            System.out.println(ca[i].getName());
            System.out.println("Type: "+ca[i].getType().toString());
            Component[] components = ca[i].getComponents();
            System.out.println("Component Count: "+components.length);
            for(int j=0;j<components.length;j++){
                System.out.println("Component "+j+": "+components[j].getName());
                System.out.println("    Identifier: "+ components[j].getIdentifier().getName());
                System.out.print("    ComponentType: ");
                if (components[j].isRelative()) {
                    System.out.print("Relative");
                } else {
                    System.out.print("Absolute");
                }
                if (components[j].isAnalog()) {
                    System.out.print(" Analog");
                } else {
                    System.out.print(" Digital");
                }
                System.out.println("");
            }
        }*/
        
        if (ca.length > 0) {
            this.controller = ca[0];
        }
    }
    
    public void update() {
        if (this.controller != null) {
            this.controller.poll();
            Component[] components = this.controller.getComponents();
            for(int i = 0; i < components.length; i++) {
                Component c = components[i];
                
                if (this.menuState == 0) {
                    if (c.getName().equals("x")) {
                        this.dx = this.dx2 + (int) -(c.getPollData() * 3);
                    }
                    if (c.getName().equals("y")) {
                        this.dz = this.dz2 + (int) -(c.getPollData() * 5);
                    }
                    if (c.getName().equals("rx")) {
                        this.dxRot = this.dxRot2 + (int) -(c.getPollData() * 2);
                    }
                    if (c.getName().equals("ry")) {
                        this.dyRot = this.dyRot2 + (int) -(c.getPollData() * 2);
                    }
                }
                
                if (c.getPollData() == 1.0) {
                    if (this.menuState == 0) {
                        if (c.getName().equals("Y")) {
                            this.menuState = Game.MENU_ADDENTITY;
                            this.menuSel = -1;
                            this.tempMenuSel = 0;
                            this.xbs = 0;
                        }
                        if (c.getName().equals("X")) {
                            this.menuState = Game.MENU_DELENTITY;
                            this.menuSel = -1;
                            this.tempMenuSel = 0;
                            this.xbs = 0;
                        }
                        if (i == 7) {
                            this.menuState = Game.MENU_GAMEMENU;
                            this.menuSel = -1;
                            this.tempMenuSel = 0;
                            this.xbs = 0;
                        }
                    } else if (this.menuState > 0) {
                        if (c.getName().equals("X")) {
                            if (c.getPollData() == 1 && this.xbs == 0) {
                                this.xbs = 1;
                                this.tempMenuSel ++;
                                if (this.tempMenuSel >= this.menuItems)
                                    this.tempMenuSel = 0;
                            }
                        }
                        if (c.getName().equals("A")) {
                            if (c.getPollData() == 1 && this.abs == 0) {
                                this.abs = 1;
                                this.menuSel = this.tempMenuSel;
                            }
                        }
                        if (c.getName().equals("B")) {
                            this.menuState = 0;
                        }
                    }
                }
                
                if (c.getName().equals("X") && c.getPollData() == 0 && this.xbs == 1)
                    this.xbs = 0;
                if (c.getName().equals("A") && c.getPollData() == 0 && this.abs == 1)
                    this.abs = 0;
            }
        } else {
            this.dx = this.dx2;
            this.dy = this.dy2;
            this.dz = this.dz2;
            this.dxRot = this.dxRot2;
            this.dyRot = this.dyRot2;
        }
    }
    
    protected void _keyPressed(int keyCode) {
		int keyAction = getGameAction(keyCode);

		if (keyAction == GAME_A) {
			this.dyRot2=1;
		} else if (keyAction == GAME_B) {
			this.dyRot2=-1;
		} else if (keyAction == LEFT && this.menuState == Game.MENU_CLOSED) {
			this.dxRot2=1;
		} else if (keyAction == RIGHT && this.menuState == Game.MENU_CLOSED) {
			this.dxRot2=-1;
		} else if (keyAction == UP && this.menuState == Game.MENU_CLOSED) {
			this.dz2 = 5;
		} else if (keyAction == DOWN && this.menuState == Game.MENU_CLOSED) {
			this.dz2 = -5;
		} else if (keyAction == GAME_C) {
            if (this.menuState == Game.MENU_CLOSED) {
                this.menuState = Game.MENU_ADDENTITY;
                this.menuSel = -1;
                this.tempMenuSel = 0;
                this.xbs = 0;
            }
		} else if (keyAction == GAME_D) {
            if (this.menuState == Game.MENU_CLOSED) {
                this.menuState = Game.MENU_DELENTITY;
                this.menuSel = -1;
                this.tempMenuSel = 0;
                this.xbs = 0;
            }
        } else if (keyAction == FIRE) {
            this.tempMenuSel ++;
            if (this.tempMenuSel >= this.menuItems)
                this.tempMenuSel = 0;
        } else if (keyAction == 10) {
            this.menuSel = this.tempMenuSel;
        } else if (keyAction == 27) {
            if (this.menuState > 0) {
                this.menuState = 0;
            } else {
                this.menuState = Game.MENU_GAMEMENU;
                this.menuSel = -1;
                this.tempMenuSel = 0;
                this.xbs = 0;
            }
		}

	}

	protected void _keyReleased(int keyCode) {
		int keyAction = getGameAction(keyCode);
		if (keyAction == UP || keyAction == DOWN) {
			this.dz2 = 0;
		} else if (keyAction == LEFT || keyAction == RIGHT) {
			this.dxRot2 = 0;
		} else if (keyAction == GAME_A || keyAction == GAME_B) {
			this.dyRot2 = 0;
		}
	}
    
    protected int getGameAction(int keyCode) {
        switch(keyCode) {
            case 38:
                return UP;
            case 40:
                return DOWN;
            case 37:
                return LEFT;
            case 39:
                return RIGHT;
            case 32:
                return FIRE;
            case 49:
                return GAME_A;
            case 50:
                return GAME_B;
            case 51:
                return GAME_C;
            case 52:
                return GAME_D;
        }
        return keyCode;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        this._keyPressed(e.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        this._keyReleased(e.getKeyCode());
    }
}
