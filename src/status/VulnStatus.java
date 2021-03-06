package status;

public interface VulnStatus {

	//Status array
	public StatusTimers conditions = new StatusTimers();
	
	//Abstract method that provides implementation of condition effects.
	public void processEffects(float delta);
	
}
