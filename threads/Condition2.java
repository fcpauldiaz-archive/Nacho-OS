package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see nachos.threads.Condition
 */
public class Condition2 {

    private Lock conditionLock;
    private ThreadQueue waiter;

    /**
     * Allocate a new condition variable.
     *
     * @param   conditionLock   the lock associated with this condition
     *              variable. The current thread must hold this
     *              lock whenever it uses <tt>sleep()</tt>,
     *              <tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
       this.conditionLock = conditionLock;
       //el false le dice que no da prioridad
       waiter = ThreadedKernel.scheduler.newThreadQueue(false);
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());
        boolean intStatus = Machine.interrupt().disable();

        conditionLock.release();

        this.waiter.waitForAccess(KThread.currentThread());

        KThread.currentThread().sleep();

        conditionLock.acquire();
        Machine.interrupt().restore(intStatus);
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        boolean intStatus = Machine.interrupt().disable();
        KThread nextKThread = waiter.nextThread();
        //cambiar estado del next kThread
        if (nextKThread != null) {
            nextKThread.ready();
        }

        Machine.interrupt().restore(intStatus);
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        boolean intStatus = Machine.interrupt().disable();
        KThread nextKThread = waiter.nextThread();

        while(nextKThread != null) {
            nextKThread.ready();
            nextKThread = waiter.nextThread();
        }

        Machine.interrupt().restore(intStatus);
    }
}