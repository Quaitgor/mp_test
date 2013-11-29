package production;

public class Basic extends DataInit {

    protected double x = -100;
    protected double y = -100;
    protected int rotation = 0;

    public double[] getPosition() {
	return new double[] { x, y };
    }

    public void setPosition(double x2, double y2) {
	this.x = x2;
	this.y = y2;
    }

    public int getRotation() {
	return rotation;
    }
}
