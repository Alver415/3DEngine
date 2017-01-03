package events;

import java.util.ArrayList;


public class EventManager {
	private EventManager(){}

	public static ArrayList<Event> current = new ArrayList<Event>();
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static void add(Event e){
		events.add(e);
	}
	public static ArrayList<Event> garbage = new ArrayList<Event>();
	public static void remove(Event e){
		garbage.add(e);
	}
	
	static {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					current.clear();
					current.addAll(events);
					for (Event e : current){
						if (e.isReady()){
							e.run();
						}
					}
					for (Event e : current){
						if (e.isConsumed()){
							remove(e);
						}
					}
					events.removeAll(garbage);
				}
			}
		}).start();
		
	}

}