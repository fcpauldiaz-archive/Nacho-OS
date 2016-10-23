package nachos.threads;
import nachos.machine.*;

public class SpeakerTest implements Runnable {
    Communicator com;
    public SpeakerTest(Communicator com){
        this.com = com;
    }

    public void run(){
        int random = 2000;
        for (int i=0; i<2; i++) {
            this.com.speak(++random);
            Lib.debug('c', KThread.currentThread().getName()+ " dormirá ticks: "+random); 
            ThreadedKernel.alarm.waitUntil(random + (int)(Math.random() * 3000));
        }
    }
}