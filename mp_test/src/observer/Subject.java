package observer;

/**
 * Subject is interface for an Observer-Object.
 * New Observers (like DeltaUpdater class) can be built when other
 * Objects implement this interface.
 * */
public interface Subject {
	//Basic Methods for every Observer
	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObserver();
}