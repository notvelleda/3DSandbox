/* THREEDEE by BENJAMIN RYVES
 * e: benryves@benryves.com
 * w: www.benryves.com
 * You use this source at your own risk!
 * If you use any of it in your own projects, give credit where it's due...
 * but I can't enforce that, and frankly I don't care.
 */

package game;

import java.awt.Graphics;
import java.util.ArrayList;

import game.entities.*;
import game.shaders.*;
import game.phys.AABB;

public class Renderer {
    public static final int MAX_POLYGONS = 32768;
    public static Renderer inst;
    public static AABB playerAABB;
    
    // Engine settings:
	int near_Clipping_Plane = 10;		// Objects closer than this value are culled.
	boolean filled = true;				// If true: use solid mode. If false: use wireframe mode.
	boolean drawWires = false;			// If true: draw black borders around shapes.
	boolean culling = false;			// If true: cull all back-facing triangles.
    
    // Screen variables:
	int scr_Height;
	int scr_Width;
	int scr_xCentre;
	int scr_yCentre;
    
    // Engine variables:
	int speed = 0;

	public static int[] trig_Table;
	int[] l_Buf_1;
	int[] l_Buf_2;
	int[] l_Buf_3;
	int [][] z_Buffer;

	int cam_X = -1000;
	int cam_Y = 0;
	int cam_Z = -10;

	int xRot = 0;
	int yRot = 0;
	int r_xRot = xRot;
	int r_yRot = yRot;

	int dzCentre = 0;
	int z_Buffer_Index;

	int world_Scale;

	int xCentre;
	int yCentre;
	int zCentre;

	int rotation_Anim = 0;
    
    // 3D Models:
	/*int[][] ball_Shadow =
		{
			{30,30,0,-30,-30,0,-30,+30,0,CColor.GREY1.getInt()},
			{30,30,0,+30,-30,0,-30,-30,0,CColor.GREY1.getInt()},
		};

	int[][] ball = 
		{{0,0,-30,18,11,-21,0,21,-21,11837122},
		{0,0,-30,18,-11,-21,18,11,-21,8949369},
		{0,0,-30,0,-21,-21,18,-11,-21,9722708},
		{0,0,-30,-18,-11,-21,0,-21,-21,4858051},
		{0,0,-30,-18,11,-21,-18,-11,-21,5065846},
		{0,0,-30,0,21,-21,-18,11,-21,12997981},
		{0,21,-21,18,11,-21,0,30,0,235176},
		{18,11,-21,26,15,0,0,30,0,12762823},
		{18,11,-21,18,-11,-21,26,15,0,13664874},
		{18,-11,-21,26,-15,0,26,15,0,11895681},
		{18,-11,-21,0,-21,-21,26,-15,0,760892},
		{0,-21,-21,0,-30,0,26,-15,0,6946315},
		{0,-21,-21,-18,-11,-21,0,-30,0,14472350},
		{-18,-11,-21,-26,-15,0,0,-30,0,13262053},
		{-18,-11,-21,-18,11,-21,-26,-15,0,6266896},
		{-18,11,-21,-26,15,0,-26,-15,0,16138895},
		{-18,11,-21,0,21,-21,-26,15,0,14620434},
		{0,21,-21,0,30,0,-26,15,0,943497},
		{0,30,0,26,15,0,0,21,21,15930916},
		{26,15,0,18,11,21,0,21,21,6107219},
		{26,15,0,26,-15,0,18,11,21,8805830},
		{26,-15,0,18,-11,21,18,11,21,12869997},
		{26,-15,0,0,-30,0,18,-11,21,897656},
		{0,-30,0,0,-21,21,18,-11,21,9939799},
		{0,-30,0,-26,-15,0,0,-21,21,7863482},
		{-26,-15,0,-18,-11,21,0,-21,21,5002385},
		{-26,-15,0,-26,15,0,-18,-11,21,10447116},
		{-26,15,0,-18,11,21,-18,-11,21,10868635},
		{-26,15,0,0,30,0,-18,11,21,4425710},
		{0,30,0,0,21,21,-18,11,21,4686581},
		{0,21,21,18,11,21,0,0,30,13921760},
		{18,11,21,18,-11,21,0,0,30,13834527},
		{18,-11,21,0,-21,21,0,0,30,9884514},
		{0,-21,21,-18,-11,21,0,0,30,16543897},
		{-18,-11,21,-18,11,21,0,0,30,15283444},
		{-18,11,21,0,21,21,0,0,30,3806179}};*/
    
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    
    public Shader shader = new Flat();
    
    public Renderer(int width, int height) {
        z_Buffer = new int[MAX_POLYGONS][8];

        scr_Width = width;
        scr_Height = height;
        scr_xCentre = scr_Width/2;
        scr_yCentre = scr_Height/2;

        world_Scale = 256 / scr_Height;

        trig_Table = new int[256];
        
        l_Buf_1 = new int[scr_Height+1];
        l_Buf_2 = new int[scr_Height+1];
        l_Buf_3 = new int[scr_Height+1];
        generate_LUT();
        
        entities.clear();
        
        playerAABB = new AABB(-5, -5, 5, 5);
        
        inst = this;
    }
    
    private void generate_LUT() {
			trig_Table[0]=0;
			trig_Table[1]=6;
			trig_Table[2]=12;
			trig_Table[3]=18;
			trig_Table[4]=25;
			trig_Table[5]=31;
			trig_Table[6]=37;
			trig_Table[7]=43;
			trig_Table[8]=49;
			trig_Table[9]=56;
			trig_Table[10]=62;
			trig_Table[11]=68;
			trig_Table[12]=74;
			trig_Table[13]=80;
			trig_Table[14]=86;
			trig_Table[15]=92;
			trig_Table[16]=97;
			trig_Table[17]=103;
			trig_Table[18]=109;
			trig_Table[19]=115;
			trig_Table[20]=120;
			trig_Table[21]=126;
			trig_Table[22]=131;
			trig_Table[23]=136;
			trig_Table[24]=142;
			trig_Table[25]=147;
			trig_Table[26]=152;
			trig_Table[27]=157;
			trig_Table[28]=162;
			trig_Table[29]=167;
			trig_Table[30]=171;
			trig_Table[31]=176;
			trig_Table[32]=181;
			trig_Table[33]=185;
			trig_Table[34]=189;
			trig_Table[35]=193;
			trig_Table[36]=197;
			trig_Table[37]=201;
			trig_Table[38]=205;
			trig_Table[39]=209;
			trig_Table[40]=212;
			trig_Table[41]=216;
			trig_Table[42]=219;
			trig_Table[43]=222;
			trig_Table[44]=225;
			trig_Table[45]=228;
			trig_Table[46]=231;
			trig_Table[47]=234;
			trig_Table[48]=236;
			trig_Table[49]=238;
			trig_Table[50]=241;
			trig_Table[51]=243;
			trig_Table[52]=244;
			trig_Table[53]=246;
			trig_Table[54]=248;
			trig_Table[55]=249;
			trig_Table[56]=251;
			trig_Table[57]=252;
			trig_Table[58]=253;
			trig_Table[59]=254;
			trig_Table[60]=254;
			trig_Table[61]=255;
			trig_Table[62]=255;
			trig_Table[63]=255;
			trig_Table[64]=256;
			trig_Table[65]=255;
			trig_Table[66]=255;
			trig_Table[67]=255;
			trig_Table[68]=254;
			trig_Table[69]=254;
			trig_Table[70]=253;
			trig_Table[71]=252;
			trig_Table[72]=251;
			trig_Table[73]=249;
			trig_Table[74]=248;
			trig_Table[75]=246;
			trig_Table[76]=244;
			trig_Table[77]=243;
			trig_Table[78]=241;
			trig_Table[79]=238;
			trig_Table[80]=236;
			trig_Table[81]=234;
			trig_Table[82]=231;
			trig_Table[83]=228;
			trig_Table[84]=225;
			trig_Table[85]=222;
			trig_Table[86]=219;
			trig_Table[87]=216;
			trig_Table[88]=212;
			trig_Table[89]=209;
			trig_Table[90]=205;
			trig_Table[91]=201;
			trig_Table[92]=197;
			trig_Table[93]=193;
			trig_Table[94]=189;
			trig_Table[95]=185;
			trig_Table[96]=181;
			trig_Table[97]=176;
			trig_Table[98]=171;
			trig_Table[99]=167;
			trig_Table[100]=162;
			trig_Table[101]=157;
			trig_Table[102]=152;
			trig_Table[103]=147;
			trig_Table[104]=142;
			trig_Table[105]=136;
			trig_Table[106]=131;
			trig_Table[107]=126;
			trig_Table[108]=120;
			trig_Table[109]=115;
			trig_Table[110]=109;
			trig_Table[111]=103;
			trig_Table[112]=97;
			trig_Table[113]=92;
			trig_Table[114]=86;
			trig_Table[115]=80;
			trig_Table[116]=74;
			trig_Table[117]=68;
			trig_Table[118]=62;
			trig_Table[119]=56;
			trig_Table[120]=49;
			trig_Table[121]=43;
			trig_Table[122]=37;
			trig_Table[123]=31;
			trig_Table[124]=25;
			trig_Table[125]=18;
			trig_Table[126]=12;
			trig_Table[127]=6;
			trig_Table[128]=0;
			trig_Table[129]=-7;
			trig_Table[130]=-13;
			trig_Table[131]=-19;
			trig_Table[132]=-26;
			trig_Table[133]=-32;
			trig_Table[134]=-38;
			trig_Table[135]=-44;
			trig_Table[136]=-50;
			trig_Table[137]=-57;
			trig_Table[138]=-63;
			trig_Table[139]=-69;
			trig_Table[140]=-75;
			trig_Table[141]=-81;
			trig_Table[142]=-87;
			trig_Table[143]=-93;
			trig_Table[144]=-98;
			trig_Table[145]=-104;
			trig_Table[146]=-110;
			trig_Table[147]=-116;
			trig_Table[148]=-121;
			trig_Table[149]=-127;
			trig_Table[150]=-132;
			trig_Table[151]=-137;
			trig_Table[152]=-143;
			trig_Table[153]=-148;
			trig_Table[154]=-153;
			trig_Table[155]=-158;
			trig_Table[156]=-163;
			trig_Table[157]=-168;
			trig_Table[158]=-172;
			trig_Table[159]=-177;
			trig_Table[160]=-182;
			trig_Table[161]=-186;
			trig_Table[162]=-190;
			trig_Table[163]=-194;
			trig_Table[164]=-198;
			trig_Table[165]=-202;
			trig_Table[166]=-206;
			trig_Table[167]=-210;
			trig_Table[168]=-213;
			trig_Table[169]=-217;
			trig_Table[170]=-220;
			trig_Table[171]=-223;
			trig_Table[172]=-226;
			trig_Table[173]=-229;
			trig_Table[174]=-232;
			trig_Table[175]=-235;
			trig_Table[176]=-237;
			trig_Table[177]=-239;
			trig_Table[178]=-242;
			trig_Table[179]=-244;
			trig_Table[180]=-245;
			trig_Table[181]=-247;
			trig_Table[182]=-249;
			trig_Table[183]=-250;
			trig_Table[184]=-252;
			trig_Table[185]=-253;
			trig_Table[186]=-254;
			trig_Table[187]=-255;
			trig_Table[188]=-255;
			trig_Table[189]=-256;
			trig_Table[190]=-256;
			trig_Table[191]=-256;
			trig_Table[192]=-256;
			trig_Table[193]=-256;
			trig_Table[194]=-256;
			trig_Table[195]=-256;
			trig_Table[196]=-255;
			trig_Table[197]=-255;
			trig_Table[198]=-254;
			trig_Table[199]=-253;
			trig_Table[200]=-252;
			trig_Table[201]=-250;
			trig_Table[202]=-249;
			trig_Table[203]=-247;
			trig_Table[204]=-245;
			trig_Table[205]=-244;
			trig_Table[206]=-242;
			trig_Table[207]=-239;
			trig_Table[208]=-237;
			trig_Table[209]=-235;
			trig_Table[210]=-232;
			trig_Table[211]=-229;
			trig_Table[212]=-226;
			trig_Table[213]=-223;
			trig_Table[214]=-220;
			trig_Table[215]=-217;
			trig_Table[216]=-213;
			trig_Table[217]=-210;
			trig_Table[218]=-206;
			trig_Table[219]=-202;
			trig_Table[220]=-198;
			trig_Table[221]=-194;
			trig_Table[222]=-190;
			trig_Table[223]=-186;
			trig_Table[224]=-182;
			trig_Table[225]=-177;
			trig_Table[226]=-172;
			trig_Table[227]=-168;
			trig_Table[228]=-163;
			trig_Table[229]=-158;
			trig_Table[230]=-153;
			trig_Table[231]=-148;
			trig_Table[232]=-143;
			trig_Table[233]=-137;
			trig_Table[234]=-132;
			trig_Table[235]=-127;
			trig_Table[236]=-121;
			trig_Table[237]=-116;
			trig_Table[238]=-110;
			trig_Table[239]=-104;
			trig_Table[240]=-98;
			trig_Table[241]=-93;
			trig_Table[242]=-87;
			trig_Table[243]=-81;
			trig_Table[244]=-75;
			trig_Table[245]=-69;
			trig_Table[246]=-63;
			trig_Table[247]=-57;
			trig_Table[248]=-50;
			trig_Table[249]=-44;
			trig_Table[250]=-38;
			trig_Table[251]=-32;
			trig_Table[252]=-26;
			trig_Table[253]=-19;
			trig_Table[254]=-13;
			trig_Table[255]=-7;
	}
    
    public static int get_Sin(int angle) {
		angle = angle & 0xFF;
		return trig_Table[angle];
	}

	public static int get_Cos(int angle) {
		angle = (angle+64) & 0xFF;
		return trig_Table[angle];
	}

	public void draw_3D_Model(int[][] model, int x_Pos, int y_Pos, int z_Pos, int r_X, int r_Y) {
		int obj_X_Pos = cam_X-x_Pos;
		int obj_Y_Pos = cam_Y-y_Pos;
		int obj_Z_Pos = cam_Z-z_Pos;

		r_xRot = xRot;
		r_yRot = yRot;

		xCentre = get_Rotated_X(obj_X_Pos,obj_Y_Pos,obj_Z_Pos);
		yCentre = get_Rotated_Y(obj_X_Pos,obj_Y_Pos,obj_Z_Pos);
		zCentre = get_Rotated_Z(obj_X_Pos,obj_Y_Pos,obj_Z_Pos);
	
		r_xRot = r_X + xRot;
		r_yRot = r_Y + yRot;

		for (int i=0; i<model.length; i++) {
			draw_3D_Triangle(
				model[i][0],model[i][1],model[i][2],
				model[i][3],model[i][4],model[i][5],
				model[i][6],model[i][7],model[i][8],
				model[i][9]);
		}
	}

	public void draw_3D_Triangle(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int colour) {
		int rotX1 = get_Rotated_X(x1,y1,z1);
		int rotY1 = get_Rotated_Y(x1,y1,z1);
		int rotZ1 = get_Rotated_Z(x1,y1,z1);
		int rotX2 = get_Rotated_X(x2,y2,z2);
		int rotY2 = get_Rotated_Y(x2,y2,z2);
		int rotZ2 = get_Rotated_Z(x2,y2,z2);
		int rotX3 = get_Rotated_X(x3,y3,z3);
		int rotY3 = get_Rotated_Y(x3,y3,z3);
		int rotZ3 = get_Rotated_Z(x3,y3,z3);

		int real_Near_Clipping_Plane = near_Clipping_Plane-zCentre;

		if (rotZ1>real_Near_Clipping_Plane && rotZ2>real_Near_Clipping_Plane && rotZ3>real_Near_Clipping_Plane) {
			int u0x = rotX2-rotX1;
			int u0y = rotY2-rotY1;
			int u0z = rotZ2-rotZ1;
			int u1x = rotX3-rotX1;
			int u1y = rotY3-rotY1;
			int u1z = rotZ3-rotZ1;
	
			//int resX = u0y*u1z - u1y*u0z;
			//int resY = u1x*u0z - u0x*u1z;
			int resZ = u0x*u1y - u1x*u0y;	

			if (resZ>=0 || !culling) {
				int scrX1 = (256 * (rotX1+xCentre)) / (rotZ1+zCentre) + scr_xCentre;
				int scrY1 = (256 * (rotY1+yCentre)) / (rotZ1+zCentre) + scr_yCentre;
				int scrX2 = (256 * (rotX2+xCentre)) / (rotZ2+zCentre) + scr_xCentre;
				int scrY2 = (256 * (rotY2+yCentre)) / (rotZ2+zCentre) + scr_yCentre;
				int scrX3 = (256 * (rotX3+xCentre)) / (rotZ3+zCentre) + scr_xCentre;
				int scrY3 = (256 * (rotY3+yCentre)) / (rotZ3+zCentre) + scr_yCentre;

				if (
					(scrX1>0 || scrX2>0 || scrX3>0) &&
					(scrX1<scr_Width || scrX2<scr_Width || scrX3<scr_Width) &&
					(scrY1>0 || scrY2>0 || scrY3>0) &&
					(scrY1<scr_Height || scrY2<scr_Height || scrY3<scr_Height)
				) {
                    int distance = rotZ1+rotZ2+rotZ3+zCentre*3;
					z_Buffer[z_Buffer_Index][0] = scrX1;
					z_Buffer[z_Buffer_Index][1] = scrY1;
					z_Buffer[z_Buffer_Index][2] = scrX2;
					z_Buffer[z_Buffer_Index][3] = scrY2;
					z_Buffer[z_Buffer_Index][4] = scrX3;
					z_Buffer[z_Buffer_Index][5] = scrY3;
					z_Buffer[z_Buffer_Index][6] = colour;
					z_Buffer[z_Buffer_Index][7] = distance;
					z_Buffer_Index++;
				}
			}
		}
		
	}

	private void draw_Triangle(Graphics g, int x0, int y0, int x1, int y1, int x2, int y2) {
		if (filled) {
			int y0s; 
			int y1s;
			int y2s;

			int swapVar;
			if (y0>y1) {
				swapVar = y0; y0 = y1; y1 = swapVar;
				swapVar = x0; x0 = x1; x1 = swapVar;
			}

			if (y0>y2) {
				swapVar = y0; y0 = y2; y2 = swapVar;
				swapVar = x0; x0 = x2; x2 = swapVar;
			}

			if (y1>y2) {
				swapVar = y1; y1 = y2; y2 = swapVar;
				swapVar = x1; x1 = x2; x2 = swapVar;
			}


			get_Line_Bounds(x0,y0,x1,y1,l_Buf_1);
			get_Line_Bounds(x1,y1,x2,y2,l_Buf_2);
			get_Line_Bounds(x2,y2,x0,y0,l_Buf_3);

			if (y0<0) { y0s=0; } else { y0s = y0; }
			if (y1<0) { y1s=0; } else { y1s = y1; }
			if (y2<0) { y2s=0; } else { y2s = y2; }
			if (y0s>=scr_Height) y0s=scr_Height-1;
			if (y1s>=scr_Height) y1s=scr_Height-1;
			if (y2s>=scr_Height) y2s=scr_Height-1;

			for (int y=y0s; y<=y1s; y++) {
				g.drawLine(l_Buf_1[y],y,l_Buf_3[y],y);
			}

			for (int y=y1s; y<=y2s; y++) {
				g.drawLine(l_Buf_3[y],y,l_Buf_2[y],y);
			}
	
			g.setColor(CColor.BLACK.getColor());
		}

		if (!filled || drawWires) {
			g.drawLine(x0,y0,x1,y1);
			g.drawLine(x1,y1,x2,y2);
			g.drawLine(x2,y2,x0,y0);
		}

	}

	private int get_Rotated_X(int x, int y, int z) {
		return (((-x * get_Sin(r_xRot)) + (y * get_Cos(r_xRot)))>>8)/world_Scale;
	}

	private int get_Rotated_Y(int x, int y, int z) {
		return (((-x * get_Cos(r_xRot) * get_Sin(r_yRot)) - (y * get_Sin(r_xRot) * get_Sin(r_yRot)))/65536 - (z * get_Cos(r_yRot))/256)/world_Scale;
	}

	private int get_Rotated_Z(int x, int y, int z) {
		return (((-x * get_Cos(r_xRot) * get_Cos(r_yRot)) - (y * get_Sin(r_xRot) * get_Cos(r_yRot)))/65536 + (z * get_Sin(r_yRot))/256)/world_Scale;
	}

	private void get_Line_Bounds(int x1, int y1, int x2, int y2, int[] lineBuffer) {
		int deltax = Math.abs(x2 - x1);
		int deltay = Math.abs(y2 - y1);
		int x = x1;
		int y = y1;
		int xinc1;
		int xinc2;
		int yinc1;
		int yinc2;

		int den;
		int num;
		int numadd;
		int numpixels;

		if (x2>=x1) {
			xinc1 = 1;
			xinc2 = 1;
		} else {
			xinc1 = -1;
			xinc2 = -1;
		}

		if (y2>=y1) {
			yinc1 = 1;
			yinc2 = 1;
		} else {
			yinc1 = -1;
			yinc2 = -1;
		}

		if (deltax>=deltay) {
			xinc1 = 0;
			yinc2 = 0;
			den = deltax;
			num = deltax / 2;
			numadd = deltay;
			numpixels = deltax;
		} else {
			xinc2 = 0;
			yinc1 = 0;
			den = deltay;
			num = deltay / 2;
			numadd = deltax;
			numpixels = deltay;
		}

		for (int curpixel=0; curpixel<=numpixels; curpixel++) {
			num+=numadd;

			if (y>0 && y<=scr_Height) {
				if (x<0) {
					lineBuffer[y]=0;
				} else if (x>scr_Width-1) {
					lineBuffer[y]=scr_Width-1;
				} else {
					lineBuffer[y]=x;
				}
			}
  
			if (num>=den) {
				num-=den;
				x+=xinc1;
    				y+=yinc1;
			}
  
			x+=xinc2;
			y+=yinc2;
		}
	}

	void quicksort(int[][] a, int lo, int hi) {
		int i=lo, j=hi, h;
		int x=a[(lo+hi)/2][7];
		do {
			while (a[i][7]<x) i++; 
			while (a[j][7]>x) j--;
			if (i<=j) {
				for (int s=0; s<=7; s++) {
					h=a[i][s]; a[i][s]=a[j][s]; a[j][s]=h;
				}
				i++; j--;
			}
		} while (i<=j);

		if (lo<j) quicksort(a, lo, j);
		if (i<hi) quicksort(a, i, hi);
	}
    
    public void render(Graphics g) {
        // Horizon:
		r_xRot = xRot;
		r_yRot = yRot;

		int sky_Y = get_Rotated_Y(5000, 5000, 0);
		int sky_Z = get_Rotated_Z(5000, 5000, 0);
			
		if (sky_Z==0) sky_Z = 1;

		int sky_Height = ((256 * sky_Y) / sky_Z) + scr_yCentre;
		
		if (sky_Height > scr_Height) {
			sky_Height = scr_Height;
		}

		if (sky_Height < 0) {
			sky_Height = 0;
		}

        this.shader.drawBackground(g, CColor.LIGHTBLUE, CColor.GREEN, sky_Height, scr_Height, scr_Width);

		z_Buffer_Index = 0;
        
        ArrayList<Entity> despawned = null;
        
        for (Entity entity : entities) {
            entity.tick();
            try {
                draw_3D_Model(entity.model.triangles, entity.x, entity.z, -entity.y, entity.model.xRot, entity.model.yRot); // Y axis inconsistencies (Y should = up)
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (entity._despawn) {
                if (despawned == null) despawned = new ArrayList<Entity>();
                despawned.add(entity);
            }
            /*if (entity.aabb != null) {
                if (entity.aabb.cloneMove(entity.x, entity.z).intersects(playerAABB.cloneMove(this.getCamX(), this.getCamZ()))) {
                    System.out.println("Foo");
                }
            }*/
            if (entity.aabb != null && entity.canCollide()) {
                for (Entity entity2 : entities) {
                    if (entity != entity2 && entity2.aabb != null) {
                        if (entity.aabb.cloneMove(entity.x, entity.z)._intersects(entity2.aabb.cloneMove(entity2.x, entity2.z))) {
                            entity.onCollision(entity2);
                        }
                    }
                }
            }
        }
        
        if (despawned != null) {
            for (Entity entity : despawned) {
                entities.remove(entity);
            }
            despawned.clear();
        }

		if (z_Buffer_Index>0) {
			quicksort(z_Buffer,0,z_Buffer_Index-1);
		}

		for (int i=z_Buffer_Index-1; i>=0; i--) {
            g.setColor(this.shader.getPolygonColor(z_Buffer[i][0],z_Buffer[i][1],
                    z_Buffer[i][2],z_Buffer[i][3],z_Buffer[i][4],z_Buffer[i][5],
                    z_Buffer[i][7], CColor.colors[z_Buffer[i][6]]));
			draw_Triangle(g,
				z_Buffer[i][0],z_Buffer[i][1],
				z_Buffer[i][2],z_Buffer[i][3],
				z_Buffer[i][4],z_Buffer[i][5]);
		}
    }
    
    public ArrayList<AABB> getEntityAABBs() {
        ArrayList<AABB> ret = new ArrayList<AABB>();
        for (Entity e : entities)
            if (e.aabb != null)
                ret.add(e.aabb.cloneMove(e.x, e.z));
        return ret;
    }
    
    public void moveCamera(int dx, int dy, int dz) {
        cam_X += dx;
		cam_Y += dz;
        cam_Z -= dy;
    }
    
    public void moveCameraRelative(int dx, int dy, int dz) {
        cam_Y += (get_Sin(xRot) * dz) / 256;
		cam_X += (get_Cos(xRot) * dz) / 256;
        
        cam_Y += (get_Sin(xRot + 70) * dx) / 256;
		cam_X += (get_Cos(xRot + 70) * dx) / 256;
        
        cam_Z -= dy;
    }
    
    public void rotateCamera(int xRot, int yRot) {
        this.xRot = xRot;
        this.yRot = yRot;
    }
    
    public void rotateCameraRelative(int dxRot, int dyRot) {
        this.xRot += dxRot;
        this.yRot += dyRot;
    }
    
    public void setCamPos(int x, int y, int z, int xRot, int yRot) {
        this.cam_X = x;
        this.cam_Z = -y;
        this.cam_Y = z;
        this.xRot = xRot;
        this.yRot = yRot;
    }
    
    public int getCamX() {
        return cam_X;
    }
    
    public int getCamY() {
        return -cam_Z;
    }
    
    public int getCamZ() {
        return cam_Y;
    }
    
    public int getXRot() {
        return xRot;
    }
    
    public int getYRot() {
        return yRot;
    }
}
