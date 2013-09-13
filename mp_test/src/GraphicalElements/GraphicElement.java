package GraphicalElements;

public class GraphicElement {

	public int x = 0;
	public int y = 0;
	public int rotation = 0;

	public void init(){}
	
	public double[] getPosition(){
		return new double[] {x,y};
	}
	public void setPosition(int nx, int ny){
		this.x = nx;
		this.y = ny;
	}
}
