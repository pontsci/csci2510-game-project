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
        return new Status(0, "HealthUp", 1.5f, null);
	}
	
	//Fire Rate Up Status effect
	public static Status getFireRateStatus() {
        return new Status(1, "FireRateUp", 5.0f, null);
	}
	
	//Damage Up Status effect
	public static Status getDmgStatus() {
        return new Status(2, "DmgUp", 5.0f, null);
	}
	
	//Shield Effect
	public static Status getShieldStatus() {
        return new Status(3, "ShieldUp", 5.0f, null);
	}
	
	//Taser Effect
	public static Status getTaserStatus() {
        return new Status(4, "TaserUp", 5.0f, null);
	}
	
	//DoT Effect
	public static Status getDoTStatus() {
        return new Status(5, "DoT", 4.0f, null);
	}
}
