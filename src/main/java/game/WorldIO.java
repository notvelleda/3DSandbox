package game;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Constructor;

public class WorldIO {
    public static void saveWorld(File file, Renderer renderer) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("p " + renderer.getCamX() + " " + renderer.getCamY() +
                " " + renderer.getCamZ() + " " + renderer.getXRot() + " " + renderer.getYRot() + "\n");
            for (Entity e : renderer.entities) {
                writer.write("e " + e.getClass().getName() + " " + e.x + " " + e.y + " " + e.z + " " + e.model.xRot + " " + e.model.yRot + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadWorld(File file, Renderer renderer) {
        try {
            int lineCount = 0;
            String line = null;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            renderer.entities.clear();

            while (true) {
                line = reader.readLine();
                if (null == line) {
                    break;
                }

                line = line.trim().replaceAll("/[0-9]*+/?[0-9]*+", ""); // Trim line and remove unnecessary chars

                if (line.length() == 0) {
                    continue;
                }
                
                if (line.startsWith("#")) { // comment
                    continue;
                } else if (line.startsWith("p")) {
                    int[] values = StringUtils.parseIntList(line, 1);
                    renderer.setCamPos(values[0], values[1], values[2], values[3], values[4]);
                } else if (line.startsWith("e")) {
                    String classname = line.split("\\s+")[1];
                    int[] values = StringUtils.parseIntList(line, classname.length() + 3);
                    Class clazz = Class.forName(classname);
                    try {
                        Class[] types = {java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE};
                        Constructor constructor = clazz.getConstructor(types);
                        Entity instance = (Entity) constructor.newInstance(renderer.getCamX(), 0, renderer.getCamY(), renderer.getXRot(), 0);
                        instance.x = values[0];
                        instance.y = values[1];
                        instance.z = values[2];
                        instance.model.xRot = values[3];
                        instance.model.yRot = values[4];
                        renderer.entities.add(instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                   System.err.println("unknown " + line);
                }
                lineCount++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
