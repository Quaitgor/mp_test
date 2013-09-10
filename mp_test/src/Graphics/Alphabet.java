package Graphics;

import org.lwjgl.opengl.GL11;

public class Alphabet {
	/*
	private int columns;
	private int rows;
	private int which_column;
	private int which_row;
	private int wide;
	private int tall;
	

	public void setSpriteSheetPosition(int columns, int rows, int which_column, int which_row, int wide, int tall){
		this.columns = columns; //Number of columns the sprite sheet has
		this.rows = rows;  //Number of rows the sprite sheet has.
		this.which_column = which_column; //Which column and row I want to display on the Sprite
		this.which_row = which_row;
		this.wide = wide; //How many cells wide I want the sprite to be composed of
		this.tall = tall; //How many cells tall I want the sprite to be composed of
	}
	public void draw(){
		GL11.glPushMatrix();
		//imageData.getTexture().bind();
		int tx = (int)location.x;
		int ty = (int)location.y;
		glTranslatef(tx, ty, location.layer);
		
		float height = imageData.getTexture().getHeight();
		float width = imageData.getTexture().getWidth();
		
		float texture_X = ((float)which_column/(float)columns)*width;
		float texture_Y = ((float)which_row/(float)rows)*height;
		float texture_XplusWidth = ((float)(which_column+wide)/(float)columns)*width;
		float texture_YplusHeight = ((float)(which_row+tall)/(float)rows)*height;	
        
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(texture_X, texture_Y);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(texture_X, texture_YplusHeight);
			GL11.glVertex2f(0, getHeight());
			
			GL11.glTexCoord2f(texture_XplusWidth, texture_YplusHeight);
			GL11.glVertex2f(getWidth(), getHeight());

			GL11.glTexCoord2f(texture_XplusWidth, texture_Y);
			GL11.glVertex2f(getWidth(), 0);
		}
		GL11.glEnd();
		GL11.glPopMatrix();
    }
    */
}
