package Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DrawingTexture {

	private TextureData texD;
	private Texture tex;
	private int height;
	private int width;
	private String texturepath;
	
	public DrawingTexture(TextureData texD){
		this.texD = texD;
		getTexD();
		BufferedImage bimg = null;
		try {
			tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(texturepath)), GL11.GL_NEAREST);
			bimg = ImageIO.read(new File(texturepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getTexD(){
		this.height = texD.height;
		this.width = texD.width;
		this.texturepath = texD.graphics;
	}

	
	public void draw(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		double[] tempLocation = texD.owner.getPosition();
		System.out.println(tempLocation[0]);
		GL11.glTranslatef((int)tempLocation[0], (int)tempLocation[1], -122);
		//GL11.glRotated(rotation, 0.0, 0.0, 1.0);
		//int temp = (int)spriteDisplayX;
		//int temp2 = (int)spriteDisplayY;
		//glTranslatef(-temp/2, -temp2/2, -layer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
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
}
