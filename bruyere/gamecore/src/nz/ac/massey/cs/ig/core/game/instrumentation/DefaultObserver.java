package nz.ac.massey.cs.ig.core.game.instrumentation;

import java.util.ArrayList;
import java.util.List;

public class DefaultObserver implements Observer {
	
	private List<Observer> observers;
	
	private Object observable;
	
	public DefaultObserver() {
		observers = new ArrayList<Observer>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void invoke() {
		for(Observer observer : observers) {
			observer.invoke();
		}
	}

	@Override
	public void invoke(int lineNumber, int opcode, String owner, String name, String desc) {
		for(Observer observer : observers) {
			observer.invoke(lineNumber, opcode, owner, name, desc);
		}
	}

	@Override
	public void setObservable(Object observable) {
		this.observable = observable;
		for(Observer observer : observers) {
			observer.setObservable(observable);
		}
	}

	public void addObserver(Observer obs) {
		if(observers.contains(obs) || obs == this) {
			return;
		}
		observers.add(obs);
		obs.setObservable(observable);
	}
	
	public void removeObserver(Observer obs) {
		observers.remove(obs);
	}
}
