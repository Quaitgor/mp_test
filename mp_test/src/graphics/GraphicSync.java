package graphics;


import observer.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import controller.Controller;


public class GraphicSync{
    public static boolean isRunning = true;
    static long lastFrame;
	public static DeltaUpdater deltaUpdater;
	public static double delta = 0;
	public static final int FRAMEWIDTH = 1280;
	public static final int FRAMEHEIGHT = 768;
	private static GraphicSync gs = null;
	
	private GraphicSync(){
		System.out.println("starting Graphics");
		deltaUpdater = DeltaUpdater.getInstance();
		getDelta();
		initData();
	}
	public static GraphicSync getInstance(){
		if(gs == null){
			gs = new GraphicSync();
		}
		return gs;
	}
	
	public void initDisplay(){
		initGL(FRAMEWIDTH,FRAMEHEIGHT);
		Controller.getInstance().start();
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
	
	
	private void initData(){
		System.out.println("starting Controller");
		Controller.getInstance();
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

	
	private void render(){
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        getDelta();
        deltaUpdater.setDelta(delta);
	}
	
    private static double getDelta() {
        long currentTime = getTime();
        double delta1 = (double) (currentTime - lastFrame);
        lastFrame = getTime();
        delta = delta1;
        return delta;
    }
    
    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
