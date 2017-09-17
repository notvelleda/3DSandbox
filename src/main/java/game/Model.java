package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;

import game.phys.AABB;

public class Model {
    public int xRot;
    public int yRot;
    public int[][] triangles;
    public String name = "";
    public static HashMap<String, int[][]> models = new HashMap<String, int[][]>();
    
    public Model(int[][] triangles) {
        this.xRot = 0;
        this.yRot = 0;
        this.triangles = triangles;
    }
    
    public Model(String filename) {
        this(filename, false);
    }
    
    public Model(String filename, float scale) {
        this(filename, false, scale);
    }
    
    public Model(String filename, boolean external) {
        this(filename, external, 4);
    }
    
    public Model(String filename, boolean external, float scale) {
        this.xRot = 0;
        this.yRot = 0;
        this.triangles = triangles;
        try {
            if (external) {
                this.loadObj(filename, new BufferedReader(new FileReader(new File(filename))), scale);
            } else {
                this.loadObj("jar:/" + filename, new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename))), scale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int[][] createBox(int x, int y, int z, int l, int w, int h) {
        int[][] triangles = new int[12][10];
        l += x;
        w += z;
        h += y;
        int[][] vertices = new int[][] {
            {x, z, h}, {x, z, y}, {x, w, h}, {x, w, y},
            {l, z, h}, {l, z, y}, {l, w, h}, {l, w, y}
        };
        int[] vloc = new int[] {1,2,4,1,4,3,5,6,8,5,8,7,6,2,1,6,1,5,8,4,3,8,3,7,4,2,6,4,6,8,3,1,5,3,5,7};
        int j = 0;
        for (int i = 0; i < 12; i ++) {
            for (int k = 0; k < 3; k ++) {
                triangles[i][k * 3] = vertices[vloc[j] - 1][0];
                triangles[i][k * 3 + 1] = vertices[vloc[j] - 1][1];
                triangles[i][k * 3 + 2] = vertices[vloc[j] - 1][2];
                j ++;
            }
            triangles[i][9] = CColor.BLACK.getInt();
        }
        
        return triangles;
    }
    
    public AABB calcAABB() {
        int[] verticesX = new int[this.triangles.length * 3];
        int[] verticesZ = new int[this.triangles.length * 3];
        
        for (int i = 0; i < this.triangles.length; i ++) {
            verticesX[i * 3] = this.triangles[i][0];
            verticesX[i * 3 + 1] = this.triangles[i][3];
            verticesX[i * 3 + 2] = this.triangles[i][6];
            verticesZ[i * 3] = this.triangles[i][1];
            verticesZ[i * 3 + 1] = this.triangles[i][4];
            verticesZ[i * 3 + 2] = this.triangles[i][7];
        }
        
        Arrays.sort(verticesX);
        Arrays.sort(verticesZ);
        
        AABB aabb = new AABB(verticesX[0], verticesZ[0], verticesX[verticesX.length - 1], verticesZ[verticesZ.length - 1]);
        //System.out.println(aabb);
        
        return aabb;
    }
    
    public void setRotation(int xRot, int yRot) {
        this.xRot = xRot;
        this.yRot = yRot;
    }
    
    public void invertZ() {
        for (int i = 0; i < this.triangles.length; i ++) {
            this.triangles[i][1] = -this.triangles[i][1];
            this.triangles[i][4] = -this.triangles[i][4];
            this.triangles[i][7] = -this.triangles[i][7];
        }
    }
    
    public void addZ(int z) {
        for (int i = 0; i < this.triangles.length; i ++) {
            this.triangles[i][1] += z;
            this.triangles[i][4] += z;
            this.triangles[i][7] += z;
        }
    }
    
    public void flipYZ() { // Flip Y and Z axes
        int[][] vertices = new int[this.triangles.length * 3][3];
        int[] colors = new int[this.triangles.length];
        for (int i = 0; i < this.triangles.length; i ++) {
            vertices[i * 3] = new int[] {this.triangles[i][0], this.triangles[i][1], this.triangles[i][2]};
            vertices[i * 3 + 1] = new int[] {this.triangles[i][3], this.triangles[i][4], this.triangles[i][5]};
            vertices[i * 3 + 2] = new int[] {this.triangles[i][6], this.triangles[i][7], this.triangles[i][8]};
            colors[i] = this.triangles[i][9];
        }
        for (int i = 0; i < this.triangles.length; i ++) {
            int[] vert0 = vertices[i * 3];
            int[] vert1 = vertices[i * 3 + 1];
            int[] vert2 = vertices[i * 3 + 2];
            this.triangles[i] = new int[] {
                vert0[0], vert0[2], vert0[1],
                vert1[0], vert1[2], vert1[1],
                vert2[0], vert2[2], vert2[1], 
                colors[i]};
        }
    }
    
    public void loadObj(String filename, BufferedReader bufferedReader, float scale) throws FileNotFoundException, IOException {
        if (models.containsKey(filename)) {
            this.triangles = models.get(filename).clone();
        } else {
            int lineCount = 0;

            String line = null;
            
            ArrayList<int[]> vertices = new ArrayList<int[]>();
            int c = CColor.WHITE.getInt();
            
            ArrayList<int[]> triangles = new ArrayList<int[]>();

            while (true) {
                line = bufferedReader.readLine();
                if (null == line) {
                    break;
                }

                line = line.trim().replaceAll("/[0-9]*+/?[0-9]*+", ""); // Trim line and remove unnecessary chars

                if (line.length() == 0) {
                    continue;
                }

                if (line.startsWith("#")) { // comment
                    continue;
                } else if (line.startsWith("mtllib")) {
                } else if (line.startsWith("usemtl")) {
                } else if (line.startsWith("o")) {
                    this.name = line.substring(1).trim();
                } else if (line.startsWith("s")) {
                } else if (line.startsWith("vn")) {
                } else if (line.startsWith("vt")) {
                } else if (line.startsWith("v")) {
                    float[] values = StringUtils.parseFloatList(3, line, 1);
                    vertices.add(new int[] {(int) (values[0] * scale), (int) (values[2] * scale), (int) (values[1] * scale)});
                } else if (line.startsWith("f")) {
                    int[] values = StringUtils.parseIntList(line, 1);
                    int[] vert0 = vertices.get(values[0] - 1);
                    int[] vert1 = vertices.get(values[1] - 1);
                    int[] vert2 = vertices.get(values[2] - 1);
                    triangles.add(new int[] {vert0[0], vert0[1], vert0[2], vert1[0], vert1[1], vert1[2], vert2[0], vert2[1], vert2[2], c});
                } else if (line.startsWith("c")) {
                    c = CColor.colors[Integer.parseInt(line.substring(1).trim())].getInt();
                } else {
                   System.err.println("unknown " + line);
                }
                lineCount++;
            }
            bufferedReader.close();

            System.out.println("Loaded model \"" + this.name + "\" (" + filename + "): " + lineCount + " lines, " + triangles.size() + " tris");
            
            int[][] model = (int[][]) triangles.toArray(new int[triangles.size()][10]);
            int[][] model2 = new int[model.length][10];
            int[][] model3 = new int[model.length][10];
            
            // Copy model twice
            int i = 0;
            for (int[] k : model) {
                int j = 0;
                for (int l : k) {
                    model2[i][j] = l;
                    model3[i][j] = l;
                    j ++;
                }
                i ++;
            }
            
            this.triangles = model2;
            models.put(filename, model3);
        }
    }
}

// This code was written by myself, Sean R. Owens, sean at guild dot net,
// and is released to the public domain. Share and enjoy. Since some
// people argue that it is impossible to release software to the public
// domain, you are also free to use this code under any version of the
// GPL, LPGL, Apache, or BSD licenses, or contact me for use of another
// license.  (I generally don't care so I'll almost certainly say yes.)
// In addition this code may also be used under the "unlicense" described
// at http://unlicense.org/ .  See the file UNLICENSE in the repo.

class StringUtils {
    // ----------------------------------------------------------------------
    // String parsing stuff
    // ----------------------------------------------------------------------
    public static void printErrMsg(String methodName, String errorMsg, int mCount, char message[]) {
        //log.log(SEVERE, methodName + ": " + errorMsg);
        System.err.println(methodName + ": " + errorMsg);
        String msg1 = "ERROR: " + methodName + ": msg=\\";
        String msg2 = "ERROR: " + methodName + ":      ";
        for (int loopi = 0; loopi < message.length; loopi++) {
            msg1 = msg1 + message[loopi];
            msg2 = msg2 + " ";
        }
        msg1 = msg1 + "\\";
        msg2 = msg2 + "^";
        /*log.log(SEVERE, msg1);
        log.log(SEVERE, msg1);*/
        System.err.println(msg1);
        System.err.println(msg2);
    }

    // if errMsg != null, then we test if we've run past end of message
    // and if so, printErrMsg(errMsg), and return -1.  If no error then
    // we return the mCount indexing the next non-whitespace char.
    public static int skipWhiteSpace(int mCount, char messageChars[], String errMsg) {
        //Skip whitespace
        while (mCount < messageChars.length) {
            if (messageChars[mCount] == ' ' || messageChars[mCount] == '\n' || messageChars[mCount] == '\t') {
                mCount++;
            } else {
                break;
            }
        }
        if (errMsg != null) {
            if (mCount >= messageChars.length) {
                printErrMsg("RString.skipWhiteSpace", errMsg, mCount, messageChars);
                return -1;
            }
        }
        return mCount;
    }

    public static float[] parseFloatList(int numFloats, String list, int startIndex) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        float[] returnArray = new float[numFloats];
        int returnArrayCount = 0;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);
        int listLength = listChars.length;

        int count = startIndex;
        int itemStart = startIndex;
        int itemEnd = 0;
        int itemLength = 0;

        while (count < listLength) {
            // Skip any leading whitespace
            itemEnd = skipWhiteSpace(count, listChars, null);
            count = itemEnd;
            if (count >= listLength) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listLength) {
                if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            itemLength = itemEnd - itemStart;
            returnArray[returnArrayCount++] = Float.parseFloat(new String(listChars, itemStart, itemLength));
            if (returnArrayCount >= numFloats) {
                break;
            }

            count = itemEnd;
        }
        return returnArray;
    }

    public static int[] parseIntList(String list, int startIndex) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        ArrayList<Integer> returnList = new ArrayList<Integer>();

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);
        int listLength = listChars.length;

        int count = startIndex;
        int itemStart = startIndex;
        int itemEnd = 0;
        int itemLength = 0;

        while (count < listLength) {
            // Skip any leading whitespace
            itemEnd = skipWhiteSpace(count, listChars, null);
            count = itemEnd;
            if (count >= listLength) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listLength) {
                if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            itemLength = itemEnd - itemStart;
            returnList.add(Integer.parseInt(new String(listChars, itemStart, itemLength)));

            count = itemEnd;
        }

        int returnArray[] = new int[returnList.size()];
        for (int loopi = 0; loopi < returnList.size(); loopi++) {
            returnArray[loopi] = returnList.get(loopi);
        }
        return returnArray;
    }

    // Note, 'face' lines are like so;
    //
    // f 11 12 13
    // f 24 25 26 27
    //
    // where the numbers are indexes of vertex (v) lines.  I'm
    // guessing they'll be mostly triangles or quads, but I think the
    // spec allows for any number of points in a face, as long as
    // there are at least three.
    //
    // Faces may also look like;
    //
    // f 11/4/1 12/5/2 13/6/3
    // f 21/14/11 22/15/12 23/16/13 24/17/14
    //
    // where the second number in each group is the index of a texture
    // (vt) line, and the third number is the index of verte normal
    // (vn) line; These lines can also leave the middle element blank
    // (but include both slashes) like so;
    //
    // f 11//1 12//2 13//3
    // f 21//11 22//12 23//13 24//14
    //
    // And lastly, I haven't seen it just yet but I've only begun
    // looking, but I'm guessing you can also leave off the last
    // element, so you'd get either;
    //
    // f 11/4 12/5 13/6
    // f 21/14 22/15 23/16 24/17
    //
    // or;
    //
    // f 11/4/ 12/5/ 13/6/
    // f 21/14/ 22/15/ 23/16/ 24/17/
    //
    // Note that in this case whatever builds the object should
    // probably compute the normal for each vertex.  How this is done
    // differs from what I've read but usually you compute the normal
    // for each face using vertices that make up the face (this is
    // _fairly_ straightforward) and then for a specific vertex you
    // average the vertices of every face that uses that vertex to get
    // the vertex normal.  (I've also seen variations that do the
    // averaging but weight it by the area of each face.)
    //
    // The spec states there is no space between number and slash (/)
    // but I'm not sure how strictly that is followed by code that
    // writes .obj files.  But I think it must be strict because if it
    // isn't, then things get way too unclear.  In fact I think you
    // couldn't parse face lines if that were the case.  Anyway, at
    // least my first version of this will assume that there are no
    // spaces between slashes and numbers.
    public static int[] parseListVerticeNTuples(String list, int expectedValuesPerTuple) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        //	log.log(INFO, "list=|"+list+"|");

        String[] vertexStrings = parseWhitespaceList(list);

        //	log.log(INFO, "found "+vertexStrings.length+" strings in parseWhitespaceList");

        ArrayList<Integer> returnList = new ArrayList<Integer>();
        Integer emptyMarker = new Integer(Integer.MIN_VALUE);

        for (int loopi = 0; loopi < vertexStrings.length; loopi++) {
            //	    log.log(INFO, "parsing vertexStrings["+loopi+"]=|"+vertexStrings[loopi]+"|");
            parseVerticeNTuple(vertexStrings[loopi], returnList, emptyMarker, expectedValuesPerTuple);
        }

        int returnArray[] = new int[returnList.size()];
        for (int loopi = 0; loopi < returnList.size(); loopi++) {
            returnArray[loopi] = returnList.get(loopi);
        }
        return returnArray;
    }

    private static void parseVerticeNTuple(String list, ArrayList<Integer> returnList, Integer emptyMarker, int expectedValueCount) {

//        	log.log(INFO, "list=|"+list+"|");

        String[] numbers = parseList('/', list);
//        	log.log(INFO, "found "+numbers.length+" strings in parselist with delim /");
        int foundCount = 0;

        int index = 0;
        while (index < numbers.length) {
//            	    log.log(INFO, "examining numbers["+index+"]=|"+numbers[index]+"|");
            if (numbers[index].trim().equals("")) {
//                log.log(INFO, "numbers["+index+"] is empty, adding emptymarker to list");
                returnList.add(emptyMarker);
            } else {
//                                log.log(INFO, "numbers["+index+"] is NOT empty, adding parsed int "+Integer.parseInt(numbers[index])+" to list.");
                returnList.add(Integer.parseInt(numbers[index]));
            }
            foundCount++;
            index++;
        }
        while (foundCount < expectedValueCount) {
            returnList.add(emptyMarker);
            foundCount++;
        }
    }

    public static String[] parseList(char delim, String list) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        ArrayList<String> returnVec = new ArrayList<String>();
        String[] returnArray = null;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);

        int count = 0;
        int itemStart = 0;
        int itemEnd = 0;
        String newItem = null;

        while (count < listChars.length) {
            count = itemEnd;
            if (count >= listChars.length) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listChars.length) {
                if (delim != listChars[itemEnd]) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            newItem = new String(listChars, itemStart, itemEnd - itemStart);
            itemEnd++;
            count = itemEnd;
            returnVec.add(newItem);
        }
        // Convert from vector to array, and return it.
        returnArray = new String[1];
        returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
        return returnArray;
    }

    public static String[] parseWhitespaceList(String list) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        ArrayList<String> returnVec = new ArrayList<String>();
        String[] returnArray = null;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);

        int count = 0;
        int itemStart = 0;
        int itemEnd = 0;
        String newItem = null;

        while (count < listChars.length) {
            // Skip any leading whitespace
            itemEnd = skipWhiteSpace(count, listChars, null);
            count = itemEnd;
            if (count >= listChars.length) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listChars.length) {
                if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            newItem = new String(listChars, itemStart, itemEnd - itemStart);
            itemEnd++;
            count = itemEnd;
            returnVec.add(newItem);
        }
        // Convert from vector to array, and return it.
        returnArray = new String[1];
        returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
        return returnArray;
    }
}
