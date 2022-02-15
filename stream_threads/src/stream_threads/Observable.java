package stream_threads;

import stream_threads.*;

import java.util.Vector;

public class Observable{
	private boolean changed = false;
    private Vector<Observer> obs;

    public Observable() {
        obs = new Vector<>();
    }
    
	public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }
	
	public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }
    
    public void notifySpecificObserver(Object arg, String objective) {
    	setChanged();
    	/*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }
        PeerConnection client;
        for (int i = arrLocal.length-1; i>=0; i--) {
        	client = (PeerConnection) arrLocal[i];
            if (client.clientNickname.equals(objective)) {
            	client.update(this, arg);
            }
        }
    }
    
    public void notifyObservers(Object arg) {
    	setChanged();
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((PeerConnection)arrLocal[i]).update(this, arg);
    }
    
    protected synchronized void clearChanged() {
        changed = false;
    }
    protected synchronized void setChanged() {
        changed = true;
    }
}
