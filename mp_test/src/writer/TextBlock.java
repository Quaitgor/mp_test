package writer;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import production.DataInit;

@XmlRootElement
public class TextBlock extends DataInit {

	@XmlElement
	private String text;
	@XmlElement
	private String font;
	@XmlElement
	private double textSpeed = 100;
	@XmlElement
	private double waitTime = 300;
	@XmlElement
	private int limit = 60;
	@XmlElement
	private double posX = 0;
	@XmlElement
	private double posY = 0;
	@XmlElement
	private double lineBreakTime = 1;
	
	private boolean state = false;
	private int step = 0;
	private int realChars = 0;
	private double deltaSinceStart = 0;
	private double nextFragmentDouble = 0;
	private HashMap<Integer, String> textLines;
	private int lineNr = 0;

	public void init(){
		TextWriter.getInstance().addText(this);
		textLines = new HashMap<Integer, String>();
		this.state = true;
	}
	
	public void setWaitTime(double newWait){
		this.waitTime = newWait;
	}
	public int getLimit(){
		return limit;
	}
	public String getFont(){
		return font;
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
	
	public HashMap<Integer, String> getText(double delta){
		if(state){
			if(step < text.length()){
				this.deltaSinceStart += delta;
				createTextFragment();
			}
			return textLines;
		}
		return null;
	}
	
	public boolean getState(){
		return state;
	}
	
	public void setState(boolean newState){
		state = newState;
	}
	
	private void createTextFragment(){
		if (nextFragmentDouble < deltaSinceStart){
			nextFragmentDouble += textSpeed;
			if(step < text.length()){
				String temp = text.substring(step, step+1);
				if(temp.equals("\\")){
					String command = text.substring(step, step+3);
					//wait command
					if(command.equals("\\w8")){
						nextFragmentDouble += waitTime;
						step += 2;
					}
					//line break command
					if(command.equals("\\lb")){
						lineNr++;
						nextFragmentDouble += waitTime*lineBreakTime;
						step += 2;
					}
				}else{
					char x[] = new char[text.length()];
					text.getChars(step, step+1, x, 0);
					String newText = textLines.get(lineNr);
					if(newText == null){
						newText = ""+x[0];
					}else{
						newText = newText + x[0];
					}
					textLines.put(lineNr, newText);
					realChars++;
				}
			}
			step++;
		}
	}
}
