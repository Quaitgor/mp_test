package Graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Conroller.Controller;
import Models.Unit;

public class DrawingTexture {

	private Texture tex;
	private int height;
	private int width;
	private int layer;
	private String texturepath;
	private Unit owner;
	private int sizeX;
	private int sizeY;
	private int[] hitbox = null;
	
	public DrawingTexture(TextureData texD, Unit owner){
		this.owner = owner;
		getTexD(texD);
		//BufferedImage bimg = null;
		try {
			tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(texturepath)), GL11.GL_NEAREST);
			//bimg = ImageIO.read(new File(texturepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getTexD(TextureData texD){
		this.height = texD.height;
		this.width = texD.width;
		this.layer = texD.layer;
		this.texturepath = texD.graphics;
		String string = texD.hitbox;
		if(string != null){
			String[] p = string.split(",");
			if (p.length == 4){
				hitbox = new int[]{Integer.parseInt(p[0]),Integer.parseInt(p[1]),Integer.parseInt(p[2]),Integer.parseInt(p[3])};
			}
		}
		sizeX = (int) (this.width*owner.size);
		sizeY = (int) (this.height*owner.size);
	}
	
	
	private void drawBegin(float[] texCords, float[] vertex){
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(	texCords[0],	texCords[1]);
			GL11.glVertex2f(	vertex[0],		vertex[1]);
			GL11.glTexCoord2f(	texCords[0],	texCords[3]);
			GL11.glVertex2f(	vertex[0],		vertex[3]);
			GL11.glTexCoord2f(	texCords[2],	texCords[3]);
			GL11.glVertex2f(	vertex[2],		vertex[3]);
			GL11.glTexCoord2f(	texCords[2],	texCords[1]);
			GL11.glVertex2f(	vertex[2],		vertex[1]);
		}
		GL11.glEnd();
	}
	
	
	private void drawHitbox(float[] texCoord, float[] vertex){
		double[] position = owner.getPosition();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1f, 0.5f, 0.5f, 1f);
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], -127);
		GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
		GL11.glTranslatef(-sizeX/2, -sizeY/2, -0);
		drawBegin(texCoord, vertex);
		GL11.glPopMatrix();
		//draw smaller Hitbox
		if(hitbox != null){
			float[] vertex2 = {-hitbox[0]/2,-hitbox[1]/2,hitbox[0]/2, hitbox[1]/2};
			GL11.glColor4f(0.5f, 0.5f, 0.5f, 1f);
			GL11.glPushMatrix();
			GL11.glTranslatef((int)position[0]+hitbox[2], (int)position[1]+hitbox[3], -126);
			GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
			drawBegin(texCoord, vertex2);
			GL11.glPopMatrix();
		}
	}
	
	public void draw(){
		float[] texCoord = {0,0,1,1};
		float[] vertex = {0,0,sizeX,sizeY};
		if (Controller.showHitbox) drawHitbox(texCoord,vertex);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		double[] position = owner.getPosition();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], layer);
		GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
		GL11.glTranslatef(-sizeX/2, -sizeY/2, 0);
		drawBegin(texCoord, vertex);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
}
