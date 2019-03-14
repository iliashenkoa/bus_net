package com.iliashenko.observer;

public interface NotifierBus {
	public void addObserver(WatchDogBus wd);

	public void removeObserver(WatchDogBus wd);

	public void notifyObserver();
}
