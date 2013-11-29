package graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import controller.Controller;
import production.*;

@XmlRootElement
public class TextureLayer extends DataInit {
    @XmlElement
    protected String graphics = "default";
    @XmlElement
    protected int height = 100;
    @XmlElement
    protected int width = 100;
    @XmlElement
    protected int layer = -75;
    @XmlElement
    protected int[] hitboxSize = { 0, 0 };
    @XmlElement
    protected int[] hitboxOffset = { 0, 0 };
    @XmlElement
    protected int nrOfSpritesX = 1;
    @XmlElement
    protected int nrOfSpritesY = 1;
    @XmlElement
    protected int anim[][] = { { 0, 0, 0 } };
    @XmlElement
    protected boolean repeat = true;
    @XmlElement
    protected boolean playOnSpawn = true;

    private Texture tex;
    private float[] texCords;
    public float texX = 0.0f;
    public float texY = 0.0f;
    public float texXp = 1.0f;
    public float texYp = 1.0f;
    private boolean hasAnimation = false;
    private int animationStep = 0;
    private int selectedSpriteX = 0;
    private int selectedSpriteY = 0;
    private double lastDelta = 0;
    private int spriteDisplayX = 0;
    private int spriteDisplayY = 0;
    protected GraphicalElement owner;

    public void init(GraphicalElement owner) {
	this.owner = owner;
	try {
	    InputStream textureStream = null;
	    if (Controller.isJarFile) {
		String newTexturepath = graphics.substring(Controller.config.getString("source").length(), graphics.length());
		textureStream = this.getClass().getResourceAsStream("/" + newTexturepath);
		if (textureStream == null) {
		    // if no png is found => try res folder
		    textureStream = new FileInputStream(new File(graphics));
		}
	    } else {
		textureStream = new FileInputStream(new File(graphics));
	    }
	    tex = TextureLoader.getTexture("PNG", textureStream, GL11.GL_NEAREST);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// prepare variables
	this.spriteDisplayX = width / nrOfSpritesX;
	this.spriteDisplayY = height / nrOfSpritesY;
	if (!playOnSpawn) {
	    animationStep = -1;
	    changeSprite(new int[] { anim[anim.length - 1][0], anim[anim.length - 1][1] });
	}
	if (anim.length > 1) {
	    hasAnimation = true;
	    this.lastDelta = ((GraphicalElement) owner).animationTiming;
	}
	calculateSprite();
    }

    public void resetLastDelta(double remove) {
	this.lastDelta -= remove;
    }

    private void drawBegin(float[] texCords, float[] vertex) {
	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glTexCoord2f(texCords[0], texCords[1]);
	    GL11.glVertex2f(vertex[0], vertex[1]);
	    GL11.glTexCoord2f(texCords[0], texCords[3]);
	    GL11.glVertex2f(vertex[0], vertex[3]);
	    GL11.glTexCoord2f(texCords[2], texCords[3]);
	    GL11.glVertex2f(vertex[2], vertex[3]);
	    GL11.glTexCoord2f(texCords[2], texCords[1]);
	    GL11.glVertex2f(vertex[2], vertex[1]);
	}
	GL11.glEnd();
    }

    public void playAnimation() {
	animationStep = 0;
	lastDelta = 0;
	hasAnimation = true;
    }

    public void turnOnAnimation() {
	if (!repeat) {
	    playAnimation();
	    repeat = true;
	}
    }

    public void stopAnimation() {
	repeat = false;
    }

    private void checkSprite(double animationTiming) {
	if (animationStep != -1 && anim[animationStep][2] <= animationTiming - lastDelta) {
	    lastDelta = animationTiming;
	    if (animationStep + 1 == anim.length) {
		if (!repeat) {
		    hasAnimation = false;
		    animationStep = anim.length - 1;
		} else {
		    animationStep = 0;
		}
	    } else {
		animationStep++;
	    }
	    changeSprite(new int[] { anim[animationStep][0], anim[animationStep][1] });
	}
    }

    public void changeSprite(int[] spriteCords) {
	selectedSpriteX = spriteCords[0];
	selectedSpriteY = spriteCords[1];
	calculateSprite();
    }

    private void calculateSprite() {
	texCords = new float[] { 0, 0, 0, 0 };
	if (selectedSpriteX != -1) {
	    int spriteWidth = height / nrOfSpritesX;
	    int spriteHeight = width / nrOfSpritesY;
	    texX = (float) ((double) (selectedSpriteX * height / nrOfSpritesX) / height);
	    texY = (float) ((double) (selectedSpriteY * width / nrOfSpritesY) / width);
	    texXp = (float) ((double) (selectedSpriteX * height / nrOfSpritesX + spriteWidth) / height);
	    texYp = (float) ((double) (selectedSpriteY * width / nrOfSpritesY + spriteHeight) / width);
	    texCords = new float[] { texX, texY, texXp, texYp };
	}
    }

    private void drawHitbox() {
	// draw graphics size
	double[] position = owner.getPosition();
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glColor4f(1f, 0.5f, 0.5f, 0.7f);
	GL11.glPushMatrix();
	GL11.glTranslatef((int) position[0], (int) position[1], -127);
	GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
	GL11.glTranslatef(-spriteDisplayX / 2, -spriteDisplayY / 2, -0);
	float[] texc = new float[] { 0, 0, 1, 1 };
	float[] vertex = { 0, 0, spriteDisplayX, spriteDisplayY };
	drawBegin(texc, vertex);
	GL11.glPopMatrix();
	if (hitboxSize != null) {
	    // draw Hitbox
	    float[] vertex2 = { -hitboxSize[0] / 2, -hitboxSize[1] / 2, hitboxSize[0] / 2, hitboxSize[1] / 2 };
	    GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.7f);
	    GL11.glPushMatrix();
	    GL11.glTranslatef((int) position[0] + hitboxOffset[0], (int) position[1] + hitboxOffset[1], -126);
	    GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
	    drawBegin(texc, vertex2);
	    GL11.glPopMatrix();
	}
    }

    public void draw() {
	draw(0);
    }

    public void draw(double animationTiming) {
	if (hasAnimation)
	    checkSprite(animationTiming);
	float[] vertex = { 0, 0, spriteDisplayX, spriteDisplayY };
	if (Controller.showHitbox)
	    drawHitbox();
	GL11.glColor4f(1f, 1f, 1f, 1f);
	double[] position = owner.getPosition();
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
	GL11.glPushMatrix();
	GL11.glTranslatef((int) position[0], (int) position[1], layer);
	GL11.glRotated(owner.getRotation(), 0.0, 0.0, 1.0);
	GL11.glTranslatef(-spriteDisplayX / 2, -spriteDisplayY / 2, 0);
	drawBegin(texCords, vertex);
	GL11.glPopMatrix();
    }

    public void destroyObject() {
	owner = null;
    }
}
