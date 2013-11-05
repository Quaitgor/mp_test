package observer;

import java.util.Vector;

/**
 * DeltaUpdater is the Observer for the delta, objects registering with this object get updated delta values,
 * needed to keep this game in sync. The observer pattern allows easy access to get the delta and send it out to all
 * objects that need it, and at the same time allows each object registered to the observer to unregister itself, removing
 * */
public class DeltaUpdater{
	
	private static Vector<Observer> observers;
	private double delta;
	
	private static DeltaUpdater DU = new DeltaUpdater();
	
	private DeltaUpdater(){
		observers = new Vector<Observer>();
	}
	
	public static DeltaUpdater getInstance(){
		return DU;
	}
	

	/**
	 * register() adds the object to the vector and the objects will get updated delta values
	 * */
	public static void register(Observer newObserver) {
		observers.add(newObserver);
	}

	/**
	 * unregister() removes the object from the list and stopping the delta update for that object
	 * */
	public void unregister(Observer deleteObserver) {
		int observerIndex = observers.indexOf(deleteObserver);
		if(observerIndex >= 0)observers.remove(observerIndex);
	}

	/**
	 * notifyObserver() sends updated delta values to each registered observer in the vector
	 * */
	public void notifyObserver() {
		for(int i=0;i<observers.size();i++){
			observers.elementAt(i).update(delta);
		}
	}

	/**
	 * setDelta updates the delta and sends the new value to each observer with the notifyObserver() method
	 * */
	public void setDelta(double delta){
		this.delta = delta;
		notifyObserver();
	}
	/**
	 * getObserverNumber returns the number of observers that are registered to the DeltaUpdater,
	 * used for statistics only
	 * */
	public int getObserverNumber(){
		return observers.size();
	}
	
	/**
	 * this Method clears the Observers Vector, used as a reset-method
	 * */
	public void clearObserver(){
		observers.clear();
	}
}