package sound;

public class QuickRestart extends SoundEvent {
	
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_WAITING = "waiting";
	public static final String STATE_RUNNING = "running";
	public static final String EVENT_FIRE = "fire";
	public static final String EVENT_DONE = "done";
	public static final String EVENT_OPEN = "open";
	public static final String EVENT_CLOSE = "close";
	private String currentState;

	public QuickRestart(AudioStream stream) {
		super(stream);
		currentState = STATE_CLOSED;
	}

	public void open() {
		put(EVENT_OPEN);
	}

	public void close() {
		put(EVENT_CLOSE);
	}

	public void fire() {
		put(EVENT_FIRE);
	}

	protected void processEvent(String event) throws InterruptedException {
		//System.out.println("Quick Restart Got: " + event);
		//System.out.println("Current State: " + currentState);
		if (currentState.equals(STATE_CLOSED)) {
			if (event.equals(EVENT_OPEN)) {
				audio.open();
				currentState = STATE_WAITING;
			}
		} else if (currentState.equals(STATE_WAITING)) {
			if (event.equals(EVENT_CLOSE)) {
				audio.close();
				currentState = STATE_CLOSED;
			}
			if (event.equals(EVENT_FIRE)) {
				audio.start();
				currentState = STATE_RUNNING;
			}
		} else if (currentState.equals(STATE_RUNNING)) {
			if (event.equals(EVENT_FIRE)) {
				audio.restart();
			}
			if (event.equals(EVENT_CLOSE)) {
				audio.stop();
				audio.close();
				currentState = STATE_CLOSED;
			}
			if (event.equals(EVENT_DONE)) {
				currentState = STATE_WAITING;
			}
		}
		//System.out.println("New State: " + currentState);
	}

	@Override
	protected void onAudioFinished() {
		put(EVENT_DONE);
	}
}