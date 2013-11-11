package input;

import java.util.HashMap;
import observer.DeltaUpdater;
import observer.Observer;
import org.lwjgl.input.Keyboard;



public class InputControl implements Observer{
	private HashMap<Integer, InputInterface> registredInterfaces;
	
	private static InputControl controller = new InputControl();
	
	private InputControl(){
		registredInterfaces = new HashMap<Integer, InputInterface>();
		DeltaUpdater.registerDelta(this);
	}

	public static InputControl getInstance(){
		return controller;
	}
	
	
	public void registerKey(String key, InputInterface ci){
		int intKey = Keyboard.getKeyIndex(key.trim().toUpperCase());
		registredInterfaces.put(intKey, ci);
	}
	
	public void unregisterKey(String key){
		int intKey = Keyboard.getKeyIndex(key.trim().toUpperCase());
		registredInterfaces.remove(intKey);
	}
	
	private void keyboardState(){
        while (Keyboard.next()) {
    		InputInterface ci = null;
	    	if (Keyboard.getEventKeyState()) {
	    		//Fire event once when pressed
	    		ci = registredInterfaces.get(Keyboard.getEventKey());
	    		if(ci != null){
	    			ci.keyDown(Keyboard.getKeyName(Keyboard.getEventKey()));
	    		}
	    	}else{
	    		//Fire event once when released
	    		ci = registredInterfaces.get(Keyboard.getEventKey());
	    		if(ci != null){
	    			ci.keyUp(Keyboard.getKeyName(Keyboard.getEventKey()));
	    		}
	    	}
        }
	}

	@Override
	public void update(double delta) {
		keyboardState();
	}
}
