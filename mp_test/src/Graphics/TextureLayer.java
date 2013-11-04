package Graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSource;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Conroller.Controller;
import Production.Basic;
import Production.GraphicalElement;
import Reader.JavaAndXML;

public class TextureLayer {

	private Texture tex;
	private int imageHeight;
	private int imageWidth;
	private int layer;
	private String texturepath;
	private Basic owner;
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
	private int spriteDisplayX = 0;
	private int spriteDisplayY = 0;
	private boolean repeat = true;
	private boolean playOnSpawn = true;
	private JavaAndXML jxml = JavaAndXML.getInstance();
    
	
	public TextureLayer(String texD, Basic owner){
		this.owner = owner;
		getTexD(texD);
		CodeSource src = getClass().getProtectionDomain().getCodeSource();
		try {
			InputStream textureFile = null;
			if (src.getLocation().toString().endsWith(".jar")) {
				String newTexturepath = texturepath.substring(4, texturepath.length());
				textureFile = this.getClass().getResourceAsStream("/"+newTexturepath);
				if(textureFile == null){
					//if no png found use file in res folder next to the jar
					textureFile = new FileInputStream(new File(texturepath));
				}
			}else{
				textureFile = new FileInputStream(new File(texturepath));
			}
			tex = TextureLoader.getTexture("PNG", textureFile, GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		calculateSprite();
	}
	

	private void getTexD(String graphic){
		TextureData texD = (TextureData) jxml.XMLtoJava(Controller.graphics.get(graphic), TextureData.class);
		this.imageWidth = texD.width;
		this.imageHeight = texD.height;
		this.layer = texD.layer;
		this.texturepath = texD.graphics;
		this.hitboxSize = texD.hitboxSize;
		this.hitboxOffset= texD.hitboxOffset;
		this.nrOfSpritesX = texD.nrOfSpritesX;
		this.nrOfSpritesY = texD.nrOfSpritesY;
		this.spriteDisplayX = imageWidth/nrOfSpritesX;
		this.spriteDisplayY = imageHeight/nrOfSpritesY;
		this.repeat = texD.repeat;
		this.playOnSpawn = texD.playOnSpawn;
		this.anims = texD.anim;
		if(!playOnSpawn){
			animationStep = anims.length-1;
			changeSprite(new int[]{anims[animationStep][0],anims[animationStep][1]});
		}
		if(anims.length > 1){
			hasAnimation = true;
			this.lastDelta = ((GraphicalElement)owner).animationTiming;
		}
	}
	
	public void resetLastDelta(double remove){
		this.lastDelta -= remove;
		
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
	
	public void playAnimation(double animationTiming){
		animationStep = 0;
		lastDelta = animationTiming;
		hasAnimation = true;
		changeSprite(new int[]{anims[animationStep][0],anims[animationStep][1]});
	}
	
	private void checkSprite(double animationTiming){
		if(anims[animationStep][2] <= animationTiming - lastDelta){
			lastDelta = animationTiming;
			if(animationStep+1 == anims.length){
				if(!repeat){
					hasAnimation = false;
				}else{
					animationStep = 0;
				}
			}else{
				animationStep++;
			}
			changeSprite(new int[]{anims[animationStep][0],anims[animationStep][1]});
		}
	}
	
	public void changeSprite(int[] spriteCords){
		selectedSpriteX = spriteCords[0];
		selectedSpriteY = spriteCords[1];
		calculateSprite();
	}
	
	private void calculateSprite(){
		texCords = new float[]{0,0,0,0};
		if(selectedSpriteX != -1){
			int spriteWidth = imageHeight/nrOfSpritesX;
			int spriteHeight = imageWidth/nrOfSpritesY;
			texX =  (float)((double)(selectedSpriteX * imageHeight/nrOfSpritesX) /  imageHeight);
			texY =  (float)((double)(selectedSpriteY * imageWidth/nrOfSpritesY) /  imageWidth);
			texXp = (float)((double)(selectedSpriteX * imageHeight/nrOfSpritesX+spriteWidth) /  imageHeight);
			texYp = (float)((double)(selectedSpriteY * imageWidth/nrOfSpritesY+spriteHeight) /  imageWidth);
			texCords = new float[] {texX,texY,texXp,texYp};
		}
	}
	
	private void drawHitbox(){
		double[] position = owner.getPosition();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1f, 0.5f, 0.5f, 0.7f);
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], -127);
		GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
		GL11.glTranslatef(-spriteDisplayX/2, -spriteDisplayY/2, -0);
		float[] texc = new float[]{0,0,1,1};
		float[] vertex = {0,0,spriteDisplayX,spriteDisplayY};
		drawBegin(texc, vertex);
		GL11.glPopMatrix();
		//draw smaller Hitbox
		if(hitboxSize != null){
			float[] vertex2 = {-hitboxSize[0]/2,-hitboxSize[1]/2,hitboxSize[0]/2, hitboxSize[1]/2};
			GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.7f);
			GL11.glPushMatrix();
			GL11.glTranslatef((int)position[0]+hitboxOffset[0], (int)position[1]+hitboxOffset[1], -126);
			GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
			drawBegin(texc, vertex2);
			GL11.glPopMatrix();
		}
	}

	public void draw(){
		draw(0);
	}
	public void draw(double animationTiming){
		//ToDo fix sprited image position
		if(hasAnimation) checkSprite(animationTiming);
		float[] vertex = {0,0,spriteDisplayX,spriteDisplayY};
		
		if (Controller.showHitbox) drawHitbox();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		double[] position = owner.getPosition();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		GL11.glPushMatrix();
		GL11.glTranslatef((int)position[0], (int)position[1], layer);
		GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
		GL11.glTranslatef(-spriteDisplayX/2, -spriteDisplayY/2, 0);
		drawBegin(texCords, vertex);
		GL11.glPopMatrix();
	}
}
