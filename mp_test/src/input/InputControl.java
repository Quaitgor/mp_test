package input;

import java.util.HashMap;
import observer.DeltaUpdater;
import observer.Observer;
import org.lwjgl.input.Keyboard;

public class InputControl implements Observer {
    private HashMap<Integer, InputInterface> registredInterfaces;

    private static InputControl ic = null;

    private InputControl() {
	registredInterfaces = new HashMap<Integer, InputInterface>();
	DeltaUpdater.registerDelta(this);
    }

    public static InputControl getInstance() {
	if (ic == null) {
	    ic = new InputControl();
	}
	return ic;
    }

    public void registerKey(String key, InputInterface ci) {
	int intKey = Keyboard.getKeyIndex(key.trim().toUpperCase());
	registredInterfaces.put(intKey, ci);
    }

    public void unregisterKey(String key) {
	int intKey = Keyboard.getKeyIndex(key.trim().toUpperCase());
	registredInterfaces.remove(intKey);
    }

    private void keyboardState() {
	while (Keyboard.next()) {
	    if (Keyboard.getEventKeyState()) {
		// Fire event once when pressed
		InputInterface ci = registredInterfaces.get(Keyboard.getEventKey());
		if (ci != null) {
		    ci.keyDown(Keyboard.getKeyName(Keyboard.getEventKey()));
		}
	    } else {
		// Fire event once when released
		InputInterface ci = registredInterfaces.get(Keyboard.getEventKey());
		if (ci != null) {
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
