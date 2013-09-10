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
	private int imageHeight;
	private int imageWidth;
	private int layer;
	private String texturepath;
	private Unit owner;
	private int nrOfSpritesX = 1;
	private int nrOfSpritesY = 1;
	private int[] hitboxSize = {0,0};
	private int[] hitboxOffset = {0,0};
    private float[] texCords;
    private int[][] anims;
	public float texX = 0.0f;
    public float texY = 0.0f;
    public float texXp = 1.0f;
    public float texYp = 1.0f;
	private boolean hasAnimation = false;
	private int animationStep = 0;
	private int selectedSpriteX = 0;
	private int selectedSpriteY= 0;
	private double lastDelta = 0;
    
	
	public DrawingTexture(TextureData texD, Unit owner){
		this.owner = owner;
		getTexD(texD);
		try {
			tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(texturepath)), GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		calculateSprite();
	}
	
	private void getTexD(TextureData texD){
		this.imageWidth = texD.width;
		this.imageHeight = texD.height;
		this.layer = texD.layer;
		this.texturepath = texD.graphics;
		this.hitboxSize = texD.hitboxSize;
		this.hitboxOffset= texD.hitboxOffset;
		this.nrOfSpritesX = texD.nrOfSpritesX;
		this.nrOfSpritesY = texD.nrOfSpritesY;
		this.anims = texD.anim;
		if(anims.length > 1){
			hasAnimation = true;
			this.lastDelta = owner.animationTiming;
		}
	}
	
	public void resetLastDelta(){
		this.lastDelta = 0;
		this.lastDelta = owner.animationTiming;
		animationStep = 0;
		
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

	private void checkSprite(double animationTiming){
		if(anims[animationStep][2] <= animationTiming - lastDelta){
			lastDelta = animationTiming;
			if(animationStep+1 == anims.length){
				animationStep = 0;
			}else{
				animationStep++;
			}
			selectedSpriteX = anims[animationStep][0];
			selectedSpriteY = anims[animationStep][1];
			calculateSprite();
		}
	}
	
	private void calculateSprite(){
		int spriteWidth = imageHeight/nrOfSpritesX;
		int spriteHeight = imageWidth/nrOfSpritesY;
		texX =  (float)((double)(selectedSpriteX * imageHeight/nrOfSpritesX) /  imageHeight);
		texY =  (float)((double)(selectedSpriteY * imageWidth/nrOfSpritesY) /  imageWidth);
		texXp = (float)((double)(selectedSpriteX * imageHeight/nrOfSpritesX+spriteWidth) /  imageHeight);
		texYp = (float)((double)(selectedSpriteY * imageWidth/nrOfSpritesY+spriteHeight) /  imageWidth);
		texCords = new float[] {texX,texY,texXp,texYp};
	}
	
	private void drawHitbox(){
		double[] position = owner.getPosition();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1f, 0.5f, 0.5f, 1f);
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], -127);
		GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
		GL11.glTranslatef(-imageWidth/2, -imageHeight/2, -0);
		float[] texc = new float[]{0,0,1,1};
		float[] vertex = {0,0,imageWidth,imageHeight};
		drawBegin(texc, vertex);
		GL11.glPopMatrix();
		//draw smaller Hitbox
		if(hitboxSize != null){
			float[] vertex2 = {-hitboxSize[0]/2,-hitboxSize[1]/2,hitboxSize[0]/2, hitboxSize[1]/2};
			GL11.glColor4f(0.5f, 0.5f, 0.5f, 1f);
			GL11.glPushMatrix();
			GL11.glTranslatef((int)position[0]+hitboxOffset[0], (int)position[1]+hitboxOffset[1], -126);
			GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
			drawBegin(texc, vertex2);
			GL11.glPopMatrix();
		}
	}

	
	public void draw(double animationTiming){
		if(hasAnimation) checkSprite(animationTiming);
		float[] vertex = {0,0,imageWidth,imageHeight};
		
		if (Controller.showHitbox) drawHitbox();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		double[] position = owner.getPosition();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], layer);
		GL11.glRotated(owner.rotation, 0.0, 0.0, 1.0);
		GL11.glTranslatef(-imageWidth/2, -imageHeight/2, 0);
		drawBegin(texCords, vertex);
		GL11.glPopMatrix();
	}
}
