package input;

public interface InputInterface {

	public void keyUp(String key);
	public void keyDown(String key);
	public void registerControl();
	public void unregisterControl();
	public void keyAction(String key);
}