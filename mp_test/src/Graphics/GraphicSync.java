package Graphics;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Network.Client;

public class GraphicSync{
    public static boolean isRunning = true;
	public static final int FRAMEWIDTH = 1280;
	public static final int FRAMEHEIGHT = 768;

	public GraphicSync(){
		System.out.println("started Graphics");
		initDisplay();
	}
	private void initDisplay(){
		Thread gameDisplay = new Thread(){
			public void run(){
				initGL(FRAMEWIDTH,FRAMEHEIGHT);
				while (isRunning) {
					if (Display.isCloseRequested()) {
						isRunning = false;
					}
					//render();
					//checkCollision();
					//updateInfo();
					Display.update();
					Display.sync(60);
					//checkReset();
				}
				Display.destroy();
				System.exit(0);
			}
		};
		gameDisplay.start();
	}
	
	private void initGL(int width, int height){
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.setFullscreen(true);
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearDepth(1.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glViewport(0,0,width,height);
		GL11.glOrtho(0,width,height,0,0,128);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glCullFace(GL11.GL_BACK);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		GL11.glLoadIdentity();
	}
}
