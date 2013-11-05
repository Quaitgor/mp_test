package production;

public class Basic extends DataCollector {

	protected double x = -100;
	protected double y = -100;
	protected int rotation = 0;
	
	public double[] getPosition(){
		return new double[] {x,y};
	}
	
	public void setPosition(int nx, int ny){
		this.x = nx;
		this.y = ny;
	}
	public int getRotation(){
		return rotation;
	}
}
