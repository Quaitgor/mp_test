package testing;

public interface InputInterface {

	public void keyUp(String key);
	public void keyDown(String key);
	public void register();
	public void unregister();
	public void keyAction(String key);
}