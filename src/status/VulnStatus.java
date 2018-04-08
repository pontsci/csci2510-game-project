package status;

public interface VulnStatus {

	//Status array
	public StatusTimers conditions = new StatusTimers();
	
	public abstract void processEffects(float delta);
	
	//Abstract methods that provide implementation of condition effects.
	public abstract void healthEffect();
	public abstract void fireRateEffect();
	public abstract void dmgEffect();
	public abstract void shieldEffect();
	public abstract void taserEffect();
	public abstract void dmgOverTime();
}
