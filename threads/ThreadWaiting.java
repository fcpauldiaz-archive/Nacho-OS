package nachos.threads;
import nachos.machine.*;

public class ThreadWaiting {
	private long time;
	private KThread thread;

	public ThreadWaiting(long time, KThread thread) {
		this.time = time;
		this.thread = thread;
	}

	public KThread getThread() {
		return this.thread;
	}

	public long getTime() {
		return this.time;
	}
}