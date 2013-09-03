package Observer;

/**
 * Implementing classes receive updates on the delta value.
 * 
 * required to fulfill the Observer-pattern
 * */
public interface Observer {

	/**
	 * update() is the method that every registered Object needs to recieve the updated delta.
	 * With this method the DeltaUpdater can send to each object the new value of delta
	 * */
	public void update(double delta);
}