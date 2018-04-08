package status;

import java.util.ArrayList;

public class StatusArchive {

	public static ArrayList<Status> returnStatuses() {
		ArrayList<Status> list = new ArrayList<Status>();
		
		list.add(getHealthStatus());
		list.add(getFireRateStatus());
		list.add(getDmgStatus());
		list.add(getShieldStatus());
		list.add(getTaserStatus());
		list.add(getDoTStatus());
		
		return list;
	}
	
	//Health Status effect
	public static Status getHealthStatus() {
		Status ret = new Status(0, "HealthUp", 1.5f, null);
		return ret;
	}
	
	//Fire Rate Up Status effect
	public static Status getFireRateStatus() {
		Status ret = new Status(1, "FireRateUp", 5.0f, null);
		return ret;
	}
	
	//Damage Up Status effect
	public static Status getDmgStatus() {
		Status ret = new Status(2, "DmgUp", 5.0f, null);
		return ret;
	}
	
	//Shield Effect
	public static Status getShieldStatus() {
		Status ret = new Status(3, "ShieldUp", 5.0f, null);
		return ret;
	}
	
	//Taser Effect
	public static Status getTaserStatus() {
		Status ret = new Status(4, "TaserUp", 5.0f, null);
		return ret;
	}
	
	//DoT Effect
	public static Status getDoTStatus() {
		Status ret = new Status(5, "DoT", 4.0f, null);
		return ret;
	}
}
