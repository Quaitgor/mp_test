package Graphics;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Conroller.Controller;
import Network.Client;

public class GraphicSync{
    public static boolean isRunning = true;
	public static final int FRAMEWIDTH = 1280;
	public static final int FRAMEHEIGHT = 768;
	private static Controller con;
	private static Thread conThread;

	public GraphicSync(){
		System.out.println("starting Controller");
		con = new Controller();
		conThread = new Thread(con);
		conThread.start();
		System.out.println("starting Graphics");
		initDisplay();
	}
	private void initDisplay(){
				initGL(FRAMEWIDTH,FRAMEHEIGHT);
				initGame();
				while (isRunning) {
					if (Display.isCloseRequested()) {
						isRunning = false;
					}
					render();
					//checkCollision();
					//updateInfo();
					Display.update();
					Display.sync(60);
					//checkReset();
				}
				Display.destroy();
				System.exit(0);
	}
	private void render(){
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    	
		{
			int x = 100;
			int y = 100;
			int width = 100;
			int height = 100;
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, -100);
			//GL11.glRotated(rotation, 0.0, 0.0, 1.0);
			//int temp = (int)spriteDisplayX;
			//int temp2 = (int)spriteDisplayY;
			//glTranslatef(-temp/2, -temp2/2, -layer);
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
			GL11.glColor4f(1f, 1f, 1f, 1f);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(0, height);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(width, height);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(width, 0);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		}

		{
			int x = 150;
			int y = 150;
			int width = 100;
			int height = 100;
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glTranslatef(x, y, -122);
			//GL11.glRotated(rotation, 0.0, 0.0, 1.0);
			//int temp = (int)spriteDisplayX;
			//int temp2 = (int)spriteDisplayY;
			//glTranslatef(-temp/2, -temp2/2, -layer);
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
			GL11.glColor4f(0.5f, 0.5f, 0.5f, 1f);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(0, height);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(width, height);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(width, 0);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		}
	}
	
	private void initGame(){
		
		
		//test
		/*
		*/
		
	}
	private void initGL(int width, int height){
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.setFullscreen(false);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearDepth(1.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glViewport(0, 0, width, height);
		GL11.glOrtho(0, width, height, 0, 0, 128);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glLoadIdentity();
	}
}
