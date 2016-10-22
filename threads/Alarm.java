package nachos.threads;

import nachos.machine.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public ArrayList waitingThreads = new ArrayList();

    public Alarm() {

    	Machine.timer().setInterruptHandler(new Runnable() {
    		public void run() { timerInterrupt(); }
    	    });
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {
	      //Tarea 2.2
        boolean intStatus = Machine.interrupt().disable();
        long time = Machine.timer().getTime();
        if (waitingThreads.isEmpty()) {
            return; 
        }

        if (((ThreadWaiting)waitingThreads.get(0)).getTime() > time) {
            return;
        }
          System.out.println("Alarm interrupt time = " + time);
      
        Iterator<ThreadWaiting> it = waitingThreads.iterator();
        while (it.hasNext()) {
            ThreadWaiting next = it.next();
            next.getThread().ready();
           
            it.remove();
            Lib.assertTrue(next.getTime() <= time);
            System.out.println("Thread name alarm "   + next.getThread().getName() + " saliendo " + next.getTime());
        }
        Machine.interrupt().restore(intStatus);
    
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
      //Tarea 2.1
      boolean intStatus = Machine.interrupt().disable();
    	// for now, cheat just to get something working (busy waiting is bad)
    	long wakeTime = Machine.timer().getTime() + x;
       
      ThreadWaiting alarmTo = new ThreadWaiting(wakeTime, KThread.currentThread());
      waitingThreads.add(alarmTo);
    	 
      KThread.sleep();

      Machine.interrupt().restore(intStatus);

    }

}
