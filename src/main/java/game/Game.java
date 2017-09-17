package game;

import java.io.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Event;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.jar.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.net.*;

import game.entities.*;
import game.phys.AABB;

public class Game extends JPanel implements Runnable {
	boolean drawStats = false;
    boolean darkPalette = false;
    
    public PlayerController pc;
    
    public static ArrayList<Class> entityClasses = new ArrayList<Class>();
    public static ArrayList<Class> shaders = new ArrayList<Class>();
    
    public Renderer renderer;
    
    long time, time2, frames, fps;
    boolean isMIDlet = true;
    
    Font font;
    
    public static final int MENU_CLOSED = 0;
    public static final int MENU_ADDENTITY = 1;
    public static final int MENU_DELENTITY = 2;
    public static final int MENU_OPTIONS = 3;
    public static final int MENU_GAMEMENU = 4;
    public static final int MENU_SAVEWORLD = 5;
    public static final int MENU_LOADWORLD = 6;
    public static final int MENU_SHADERS = 7;
    
    int scroll = 0;
	
	Graphics buffer;
	Graphics buffer2;
	BufferedImage bufferImage;
	BufferedImage bufferImage2;

	Thread t;
    
    long loopStartTime;
	long loopEndTime;
    
    int scr_Width;
    int scr_Height;
    
    //public AABB playerAABB = new AABB();

	public Game() {}
    
    public Game(boolean isMIDlet) {
        this.isMIDlet = isMIDlet;
        if (this.isMIDlet) init();
    }
    
    public void init() {
        this.init(this.getWidth(), this.getHeight());
    }
    
    public void init(int width, int height) {
        try {
            this.renderer = new Renderer(width, height);
            this.pc = new PlayerController();
            
            this.scr_Width = width;
            this.scr_Height = height;
            
            // Set up a double buffer
            bufferImage = new BufferedImage(width, height, 1);
            buffer = bufferImage.getGraphics();
            bufferImage2 = new BufferedImage(width + 64, height + 64, 1);
            buffer2 = bufferImage2.getGraphics();
            buffer2.setColor(CColor.LIGHTBLUE.getColor());
            buffer2.fillRect(0, 0, width + 64, height + 64);
            
            this.renderer.entities.add(new Tree(0, 0, 0, 0, 0));
            this.renderer.entities.add(new Tree(0, 0, 200, 30, 0));
            this.renderer.entities.add(new Tree(300, 0, 0, 100, 0));
            
            InputStream istream = getClass().getResourceAsStream("/C64_Pro_Mono-STYLE.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, istream).deriveFont(8.0f);
            buffer.setFont(font);
            
            ArrayList<Class<?>> classes = getClassesForPackage("game.entities");
            for (Class clazz : classes) {
                entityClasses.add(clazz);
            }
            
            classes = getClassesForPackage("game.shaders");
            for (Class clazz : classes) {
                shaders.add(clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void start() {
		t = new Thread(this);
		t.start();
    }
   
	public void run() {
        time = System.currentTimeMillis();
        frames = 0;
        fps = 0;
   		while (true) {
            time2 = System.currentTimeMillis();
			tick();
            repaint();
            while (System.currentTimeMillis() - time2 < 40);
            if (System.currentTimeMillis() - time > 1000) {
                fps = frames;
                frames = 0;
                time = System.currentTimeMillis();
            }
            frames ++;
		}
	}


	void tick() {
        this.pc.update();
        
		this.renderer.rotateCameraRelative(this.pc.dxRot, this.pc.dyRot);
        
        /*int xd = this.pc.dx;
        int yd = this.pc.dy;
        int zd = this.pc.dz;
        
        ArrayList<AABB> arrayList = Renderer.inst.getEntityAABBs();
        AABB aabb = Renderer.playerAABB.cloneMove(this.renderer.getCamX() + xd, 0);
        int n;
        for (n = 0; n < arrayList.size(); ++n) {
            xd = arrayList.get(n).clipXCollide(Renderer.playerAABB, xd);
        }
        aabb = aabb.cloneMove(0, this.renderer.getCamZ() + zd);
        for (n = 0; n < arrayList.size(); ++n) {
            zd = arrayList.get(n).clipZCollide(Renderer.playerAABB, zd);
        }*/

		this.renderer.moveCameraRelative(this.pc.dx, this.pc.dy, this.pc.dz);
	}


	protected void paintComponent(Graphics g) {
        g.setFont(font);
        
		Graphics originalBuffer = g;
		g = buffer;

		this.renderer.render(g);
        
        if (this.pc.menuState == MENU_ADDENTITY) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Create Entity", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            int i = 0;
            int ypos = 39;
            for (Class clazz : entityClasses) {
                g.drawString(clazz.getName(), i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = entityClasses.size();
                
            if (this.pc.menuSel >= 0) {
                Class clazz = entityClasses.get(this.pc.menuSel);
                this.pc.menuSel = -1;
                this.pc.menuState = MENU_CLOSED;
                try {
                    Class[] types = {java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE};
                    Constructor constructor = clazz.getConstructor(types);
                    Entity instance = (Entity) constructor.newInstance(this.renderer.getCamX(), 0, this.renderer.getCamZ(), this.renderer.getXRot(), 0);
                    this.renderer.entities.add(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (this.pc.menuState == MENU_DELENTITY) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Delete Entity (Pos:" + this.renderer.getCamX() + "," + this.renderer.getCamY() + "," + this.renderer.getCamZ() + ")", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            int i = 0;
            int ypos = 39;
            for (Entity e : this.renderer.entities) {
                if (i < this.scroll) {
                    i ++;
                    continue;
                }
                g.drawString(e.getClass().getName() + "@" + e.x + "," + e.y + "," + e.z, i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
                if (i >= 19 + this.scroll) break;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = this.renderer.entities.size();
            
            if (this.pc.tempMenuSel >= 19 + this.scroll)
                this.scroll ++;
            else if (this.scroll > 0 && this.pc.tempMenuSel < this.scroll)
                this.scroll = 0;
                
            if (this.pc.menuSel >= 0) {
                this.renderer.entities.remove(this.pc.menuSel);
                this.pc.menuSel = -1;
                this.pc.menuState = MENU_CLOSED;
            }
        } else if (this.pc.menuState == MENU_OPTIONS) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Options", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            String[] options = new String[] {
                "Culling: ",
                "Solid polygons: ",
                "Show FPS: ",
                "Colodore palette: ",
                "Shader dithering: ",
                "Select shader"
            };
            
            boolean[] optionvalues = new boolean[] {
                this.renderer.culling,
                this.renderer.filled,
                drawStats,
                darkPalette,
                TEDDither.ditherImage
            };
            
            int i = 0;
            int ypos = 39;
            for (String s : options) {
                if (i >= optionvalues.length)
                    g.drawString(s, i == this.pc.tempMenuSel ? 24 : 16, ypos);
                else
                    g.drawString(s + (optionvalues[i] ? "ON" : "OFF"), i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = options.length;
                
            if (this.pc.menuSel >= 0) {
                if (this.pc.menuSel < optionvalues.length - 1)
                    optionvalues[this.pc.menuSel] = !optionvalues[this.pc.menuSel];
                
                switch (this.pc.menuSel) {
                    case 0:
                        this.renderer.culling = !this.renderer.culling;
                        break;
                    case 1:
                        this.renderer.filled = !this.renderer.filled;
                        break;
                    case 2:
                        drawStats = !drawStats;
                        break;
                    case 3:
                        if (this.renderer.shader.getClass().getName() == "game.shaders.Flat") {
                            darkPalette = !darkPalette;
                            if (darkPalette) {
                                CColor.setPalette1();
                            } else {
                                CColor.setPalette0();
                            }
                            buffer2.setColor(CColor.LIGHTBLUE.getColor());
                            buffer2.fillRect(0, 0, scr_Width + 64, scr_Height + 64);
                        } else {
                            optionvalues[this.pc.menuSel] = !optionvalues[this.pc.menuSel];
                        }
                        break;
                    case 4:
                        TEDDither.ditherImage = !TEDDither.ditherImage;
                        break;
                    case 5:
                        this.pc.menuState = MENU_SHADERS;
                        this.pc.menuSel = -1;
                        this.pc.tempMenuSel = 0;
                        break;
                }
                this.pc.menuSel = -1;
            }
        } else if (this.pc.menuState == MENU_GAMEMENU) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Game Menu", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            String[] options = new String[] {
                "Options",
                "Save World",
                "Load World",
                "Quit Game"
            };
            
            int i = 0;
            int ypos = 39;
            for (String s : options) {
                g.drawString(s, i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = options.length;
                
            if (this.pc.menuSel >= 0) {
                switch (this.pc.menuSel) {
                    case 0:
                        this.pc.menuState = MENU_OPTIONS;
                        this.pc.menuSel = -1;
                        this.pc.tempMenuSel = 0;
                        break;
                    case 1:
                        this.pc.menuState = MENU_SAVEWORLD;
                        this.pc.menuSel = -1;
                        this.pc.tempMenuSel = 0;
                        break;
                    case 2:
                        this.pc.menuState = MENU_LOADWORLD;
                        this.pc.menuSel = -1;
                        this.pc.tempMenuSel = 0;
                        break;
                    case 3:
                        System.exit(0);
                }
                this.pc.menuSel = -1;
            }
        } else if (this.pc.menuState == MENU_SAVEWORLD) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Save World", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            String[] options = new String[] {
                new File("World1.sav").exists() ? "World 1" : "-Empty-",
                new File("World2.sav").exists() ? "World 2" : "-Empty-",
                new File("World3.sav").exists() ? "World 3" : "-Empty-",
                new File("World4.sav").exists() ? "World 4" : "-Empty-",
                new File("World5.sav").exists() ? "World 5" : "-Empty-"
            };
            
            int i = 0;
            int ypos = 39;
            for (String s : options) {
                g.drawString(s, i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = options.length;
                
            if (this.pc.menuSel >= 0) {
                switch (this.pc.menuSel) {
                    case 0:
                        WorldIO.saveWorld(new File("World1.sav"), this.renderer);
                        this.pc.menuState = MENU_CLOSED;
                        this.pc.menuSel = -1;
                        break;
                    case 1:
                        WorldIO.saveWorld(new File("World2.sav"), this.renderer);
                        this.pc.menuState = MENU_CLOSED;
                        this.pc.menuSel = -1;
                        break;
                    case 2:
                        WorldIO.saveWorld(new File("World3.sav"), this.renderer);
                        this.pc.menuState = MENU_CLOSED;
                        this.pc.menuSel = -1;
                        break;
                    case 3:
                        WorldIO.saveWorld(new File("World4.sav"), this.renderer);
                        this.pc.menuState = MENU_CLOSED;
                        this.pc.menuSel = -1;
                        break;
                    case 4:
                        WorldIO.saveWorld(new File("World5.sav"), this.renderer);
                        this.pc.menuState = MENU_CLOSED;
                        this.pc.menuSel = -1;
                        break;
                }
                this.pc.menuSel = -1;
            }
        } else if (this.pc.menuState == MENU_LOADWORLD) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Load World", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            String[] options = new String[] {
                new File("World1.sav").exists() ? "World 1" : "-Empty-",
                new File("World2.sav").exists() ? "World 2" : "-Empty-",
                new File("World3.sav").exists() ? "World 3" : "-Empty-",
                new File("World4.sav").exists() ? "World 4" : "-Empty-",
                new File("World5.sav").exists() ? "World 5" : "-Empty-"
            };
            
            int i = 0;
            int ypos = 39;
            for (String s : options) {
                g.drawString(s, i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = options.length;
            
            File f;
            if (this.pc.menuSel >= 0) {
                switch (this.pc.menuSel) {
                    case 0:
                        f = new File("World1.sav");
                        if (f.exists() && f.isFile()) {
                            WorldIO.loadWorld(f, this.renderer);
                            this.pc.menuState = MENU_CLOSED;
                            this.pc.menuSel = -1;
                        }
                        break;
                    case 1:
                        f = new File("World2.sav");
                        if (f.exists() && f.isFile()) {
                            WorldIO.loadWorld(f, this.renderer);
                            this.pc.menuState = MENU_CLOSED;
                            this.pc.menuSel = -1;
                        }
                        break;
                    case 2:
                        f = new File("World3.sav");
                        if (f.exists() && f.isFile()) {
                            WorldIO.loadWorld(f, this.renderer);
                            this.pc.menuState = MENU_CLOSED;
                            this.pc.menuSel = -1;
                        }
                        break;
                    case 3:
                        f = new File("World4.sav");
                        if (f.exists() && f.isFile()) {
                            WorldIO.loadWorld(f, this.renderer);
                            this.pc.menuState = MENU_CLOSED;
                            this.pc.menuSel = -1;
                        }
                        break;
                    case 4:
                        f = new File("World5.sav");
                        if (f.exists() && f.isFile()) {
                            WorldIO.loadWorld(f, this.renderer);
                            this.pc.menuState = MENU_CLOSED;
                            this.pc.menuSel = -1;
                        }
                        break;
                }
                this.pc.menuSel = -1;
            }
        } else if (this.pc.menuState == MENU_SHADERS) {
            g.setColor(CColor.GREY1.getColor());
            g.fillRect(16, 16, this.scr_Width - 32, this.scr_Height - 32);
            g.setColor(CColor.GREY3.getColor());
            g.drawString("Select shader", 24, 24);
            
            g.setColor(CColor.GREEN.getColor());
            
            int i = 0;
            int ypos = 39;
            for (Class clazz : shaders) {
                if (i < this.scroll) {
                    i ++;
                    continue;
                }
                g.drawString(clazz.getName(), i == this.pc.tempMenuSel ? 24 : 16, ypos);
                ypos += 8;
                i ++;
                if (i >= 19 + this.scroll) break;
            }
            
            if (this.pc.menuState != MENU_CLOSED)
                this.pc.menuItems = shaders.size();
            
            if (this.pc.tempMenuSel >= 19 + this.scroll)
                this.scroll ++;
            else if (this.scroll > 0 && this.pc.tempMenuSel < this.scroll)
                this.scroll = 0;
                
            if (this.pc.menuSel >= 0) {
                Class clazz = shaders.get(this.pc.menuSel);
                this.pc.menuSel = -1;
                this.pc.menuState = MENU_CLOSED;
                try {
                    Constructor constructor = clazz.getConstructor(new Class[] {});
                    this.renderer.shader = (Shader) constructor.newInstance();
                    if (clazz.getName() == "game.shaders.Flat") {
                        darkPalette = !darkPalette;
                        if (darkPalette) {
                            CColor.setPalette1();
                        } else {
                            CColor.setPalette0();
                        }
                        buffer2.setColor(CColor.LIGHTBLUE.getColor());
                        buffer2.fillRect(0, 0, scr_Width + 64, scr_Height + 64);
                    } else {
                        TEDDither.setTEDPalette();
                        buffer2.setColor(CColor.LIGHTBLUE.getColor());
                        buffer2.fillRect(0, 0, scr_Width + 64, scr_Height + 64);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (this.renderer.shader.getClass().getName() != "game.shaders.Flat") {
            buffer2.drawImage(TEDDither.dither(bufferImage), 32, 32, scr_Width, scr_Height, null);
        } else {
            buffer2.drawImage(bufferImage, 32, 32, scr_Width, scr_Height, null);
        }
        
		originalBuffer.drawImage(bufferImage2, 0, 0, this.getWidth(), this.getHeight(), null);
        
        if (drawStats) {
			originalBuffer.setColor(Color.BLACK);
			originalBuffer.drawString(this.renderer.z_Buffer_Index + " triangles, " + this.fps + " fps", 0, 8);
			originalBuffer.drawString(this.renderer.getCamX() + ", " + this.renderer.getCamY() + ", " + this.renderer.getCamZ(), 0, 17);
		}
        
		loopEndTime = System.currentTimeMillis();
	}
    
    private static ArrayList<Class<?>> getClassesForPackage(String pkgname) {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        // Get a File object for the package
        File directory = null;
        String fullPath;
        String relPath = pkgname.replace('.', '/');
        System.out.println("ClassDiscovery: Package: " + pkgname + " becomes Path:" + relPath);
        URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
        System.out.println("ClassDiscovery: Resource = " + resource);
        if (resource == null) {
            throw new RuntimeException("No resource for " + relPath);
        }
        fullPath = resource.getFile();
        System.out.println("ClassDiscovery: FullPath = " + resource);

        try {
            directory = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(pkgname + " (" + resource + ") does not appear to be a valid URL / URI.  Strange, since we got it from the system...", e);
        } catch (IllegalArgumentException e) {
            directory = null;
        }
        System.out.println("ClassDiscovery: Directory = " + directory);

        if (directory != null && directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    String className = pkgname + '.' + files[i].substring(0, files[i].length() - 6);
                    System.out.println("ClassDiscovery: className = " + className);
                    try {
                        classes.add(Class.forName(className));
                    } 
                    catch (ClassNotFoundException e) {
                        throw new RuntimeException("ClassNotFoundException loading " + className);
                    }
                }
            }
        }
        else {
            try {
                String jarPath = fullPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
                JarFile jarFile = new JarFile(jarPath);         
                Enumeration<JarEntry> entries = jarFile.entries();
                while(entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    if(entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
                        System.out.println("ClassDiscovery: JarEntry: " + entryName);
                        String className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
                        System.out.println("ClassDiscovery: className = " + className);
                        try {
                            classes.add(Class.forName(className));
                        } 
                        catch (ClassNotFoundException e) {
                            throw new RuntimeException("ClassNotFoundException loading " + className);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(pkgname + " (" + directory + ") does not appear to be a valid package", e);
            }
        }
        return classes;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game(false);
        game.setPreferredSize(new Dimension(768, 528));
        game.setFocusable(true);
        game.init(320, 200);
        game.addKeyListener(game.pc);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
