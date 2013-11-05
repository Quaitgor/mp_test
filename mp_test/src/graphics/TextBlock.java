package graphics;

public class TextBlock {
	//private HashMap<Integer, String> texts;
	private String name = "default";
	private String text;
	private double textSpeed = 100;
	private double waitTime = 300;
	private String textFragment = null;
	private int step = 0;
	private int realChars = 0;
	private double deltaSinceStart = 0;
	private double nextFragmentDouble = 0;
	private TextWriter tW = TextWriter.getInstance();
	private double posX = 0;
	private double posY = 0;
	private boolean state = false;

	public TextBlock(String name, String text, double textSpeed, double newWait){
		this.name = name;
		this.text = text;
		this.textSpeed = textSpeed;
		this.waitTime = newWait;
		tW.addText(this);
	}
	
	public TextBlock(String name, String text, double textSpeed){
		this.name = name;
		this.text = text;
		this.textSpeed = textSpeed;
		tW.addText(this);
	}
	
	public TextBlock(String name, String text){
		this.name = name;
		this.text = text;
		tW.addText(this);
	}
	
	public void setWaitTime(double newWait){
		this.waitTime = newWait;
	}
	
	public String getName(){
		return name;
	}
	public double getX(){
		return posX;
	}
	public double getY(){
		return posY;
	}
	public void setPos(double x, double y){
		this.posX = x;
		this.posY = y;
	}
	public int getRealChars(){
		return realChars;
	}
	
	public String getText(double delta){
		if(state){
			if(step < text.length()){
				this.deltaSinceStart += delta;
				getTextFragment();
			}
			return textFragment;
		}
		return null;
	}
	
	public boolean getState(){
		return state;
	}
	
	public void setState(boolean newState){
		state = newState;
	}
	
	private void getTextFragment(){
		if (nextFragmentDouble < deltaSinceStart){
			nextFragmentDouble += textSpeed;
			if(step < text.length()){
				String temp = text.substring(step, step+1);
				//text.getChars(step, step+1, temp, 0);
				if (temp.equals("\\")){
					String command = text.substring(step, step+3);
					//wait command
					if(command.equals("\\w8")){
						nextFragmentDouble += 3*textSpeed;
						step += 2;
					}
					//line break command
					if(command.equals("\\lb")){
						textFragment = textFragment + "\\lb";
						step += 2;
					}
				}else{
					char x[] = new char[text.length()];
					text.getChars(step, step+1, x, 0);
					if(textFragment == null){
						textFragment = ""+x[0];
					}else{
						textFragment = textFragment + x[0];
					}
					realChars++;
				}
			}
			step++;
		}
	}
}
