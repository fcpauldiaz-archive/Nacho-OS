package nachos.threads;
import nachos.machine.*;

public class ListenerTest implements Runnable{
    Communicator com;
    public ListenerTest(Communicator com){
        this.com = com;
    }

    public void run(){
        for (int i=0; i<2; i++) {
            this.com.listen();
            int random = 1000 + (int)(Math.random() * 3000);
            Lib.debug('c', KThread.currentThread().getName()+ " se va a dormir: "+random);
            ThreadedKernel.alarm.waitUntil(random);
        }
    }
}