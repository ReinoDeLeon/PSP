package stream_threads;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Channel extends Observable {
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
	
}
