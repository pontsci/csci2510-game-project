package status;

import java.awt.Graphics;
import java.util.ArrayList;

import util.Matrix3x3f;
import util.Vector2f;

public class StatusTimers {
	private ArrayList<Status> statusList;
	private ArrayList<Float> timers;
	private StatusArchive archive = new StatusArchive();
	
	// Default Constructor pulls array from StatusArchive
	public StatusTimers() {
		statusList = new ArrayList<Status>();
		timers = new ArrayList<Float>();
		
		ArrayList<Status> list = archive.returnStatuses();
		
		for (int i = 0; i < list.size(); i++) {
			statusList.add(list.get(i));
			timers.add(new Float(0));
		}
	}

	// Constructor when provided a Status ArrayList
	public StatusTimers(ArrayList<Status> list) {
		statusList = new ArrayList<Status>();
		timers = new ArrayList<Float>();

		for (int i = 0; i < list.size(); i++) {
			statusList.add(list.get(i));
			timers.add(new Float(0));
		}
	}

	// Adds a status object to the array
	public void addStatus(int id, String name, float timer) {
		statusList.add(new Status(id, name, timer, new Vector2f(1,1), null));
		timers.add(new Float(0));
	}

	// Decrements the timers of any active status effects
	public void updateObjects(float delta, Matrix3x3f viewport) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).active) {
				timers.set(i, new Float(timers.get(i).floatValue() - delta));

				if (timers.get(i).floatValue() <= 0) {
					timers.set(i, new Float(0));
					statusList.get(i).active = false;
				}
			}
			
			statusList.get(i).icon.update(delta, viewport);
		}
	}

	// Renders Active Status's FXs
	public void render(Graphics g) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).active)
				statusList.get(i).render(g);
		}
	}

	// Activates a status's effects with the default timer value
	public void activateStatus(int id) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).id == id) {
				statusList.get(i).active = true;
				timers.set(i, new Float(statusList.get(i).duration));
				break;
			}
		}
	}

	// Activate a status's effects with the default timer value
	public void activateStatus(String name) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).name.equals(name)) {
				statusList.get(i).active = true;
				timers.set(i, new Float(statusList.get(i).duration));
				break;
			}
			
		}
	}

	// Returns requested status object
	public Status getStatus(int id) {
		Status ret = null;

		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).id == id) {
				ret = statusList.get(i);
			}
		}

		return ret;
	}

	// Returns requested status object
	public Status getStatus(String name) {
		Status ret = null;

		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).name.equals(name)) {
				ret = statusList.get(i);
			}
		}

		return ret;
	}

	// Removes requested status object
	public void removeStatus(int id) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).id == id) {
				statusList.remove(i);
				timers.remove(i);
			}
		}
	}

	// Removes requested status object
	public void removeStatus(String name) {
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).name.equals(name)) {
				statusList.remove(i);
				timers.remove(i);
			}

		}
	}

	// Return timer for a status
	public float getTimer(int id) {
		float ret = 0;

		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).id == id) {
				ret = timers.get(i);
				break;
			}
		}

		return ret;
	}

	// Return timer for a status
	public float getTimer(String name) {
		float ret = 0;

		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).name.equals(name)) {
				ret = timers.get(i);
				break;
			}
		}

		return ret;
	}
	
	//Determines if any status effect is active
	public boolean anyStatusActive() {
		boolean ret = false;
		
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).active) {
				ret = true;
				break;
			}
		}
		
		return ret;
	}
}