package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    
    private int listener = 0;             
    private int speaker  = 0;             
    private int transferWord = 0;                 
    private boolean wordReady;  

    private Lock lock;                    
    private Condition2 condSpeaker;       
    private Condition2 condListener;      


    /**
     * Allocate a new communicator.
     */
    public Communicator() {
        this.lock = new Lock();
        this.condSpeaker = new Condition2(lock);
        this.condListener = new Condition2(lock);
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * solo puede hablar una persona a la vez.
     * Si alguien más habla espera.
     * No puede hablar alguien si no hay nadie escuchando
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
        //Tarea 3.1
        lock.acquire();
        //hay un nuevo speaker en lock
        speaker++;

        //si no hay nadie escuchando o está listo para transferir
        while (wordReady || listener == 0) { 
            condSpeaker.sleep(); 
        }
        lock.release();
        Lib.debug('c', "Speak word " + KThread.currentThread().getName()+ " : " + word);
        lock.acquire();
        //speaker says the word
        this.transferWord = word;
        //listo para trasferir word
        this.wordReady = true;
        //Wake all listeners
        condListener.wakeAll(); 
        //deja de hablar
        speaker--;

        lock.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     * El listener procesa la palabra y le tiene que avisar al siguiente que ya termino
     *
     * @return	the integer transferred.
     */    
    public int listen() {
        //Tarea 3.2
        lock.acquire();
        //hay alguien escuchando
        listener++;
        //Mientras no haya nadie escuchando
        while (wordReady == false) {  
            condSpeaker.wakeAll();
            condListener.sleep();  
        }
        int word = this.transferWord;
        lock.release();
        Lib.debug('c', "Listen word " + KThread.currentThread().getName() + ": " + word);
        lock.acquire();
        //ya se transfirio la palabra
        this.wordReady = false;

        //deja de escuchar
        listener--;

        lock.release();

        return word;
    }
}
