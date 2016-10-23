package nachos.threads;
import nachos.ag.BoatGrader;

public class Boat
{
    static BoatGrader bg;
  
    //cantidad de pasajeros en el bote actual
    //la cantidad maxima de pasajeros es de 2
    public static int cantidadPasajeros = 0;
    public static Condition boatCondition;
    //false Ohau
    //true Molokai
    public static Lock boatLock;
    public static Isla oahu;
    public static Isla molokai;
    public static Communicator com; 

    public Boat() {
      this.boatLock = new Lock();
      this.boatCondition = new Condition(boatLock);
      this.com = new Communicator();
      this.oahu = new Isla("Oahu", boatLock);
      this.molokai = new Isla("Monokai", boatLock);
      selfTest();
    }

    public static void selfTest()
    {
    	BoatGrader b = new BoatGrader();
    	
    	System.out.println("\n ***Testing Boats with only 2 children***");
    	begin(1, 0, b);

      //	System.out.println("\n ***Testing Boats with 2 children, 1 adult***");
      //  	begin(1, 2, b);

      //  	System.out.println("\n ***Testing Boats with 3 children, 3 adults***");
      //  	begin(3, 3, b);
    }

    public static void begin( int adults, int children, BoatGrader b )
    {
    	// Store the externally generated autograder in a class
    	// variable to be accessible by children.
    	bg = b;
      oahu.setPeople(adults, children);
    	// Instantiate global variables here
    	
    	// Create threads here. See section 3.4 of the Nachos for Java
    	// Walkthrough linked from the projects page.
      // Instantiate global variables here
      
      // Create threads here. See section 3.4 of the Nachos for Java
      // Walkthrough linked from the projects page.
      Runnable runnable_ninos = new Runnable() {

          public void run() {
            // local varialbe, indicate where person is
              ChildItinerary(false);
          };
      };

      Runnable runnable_adultos = new Runnable() {

          public void run() {
              //thread local varialbe, indicate where person is
              AdultItinerary(false);
          };
      };
     
      for (int i = 0; i < children; i++) {
          KThread cThread = new KThread(runnable_ninos);
          cThread.setName("Niño- #" + (i+1));
          cThread.fork();
      }
       
      for (int i = 0; i < adults; i++) {
          KThread cThread = new KThread(runnable_adultos);
          cThread.setName("Adulto - #" + (i+1));
          cThread.fork();
      }
        
      while(true)  {
          int r = com.listen();

          System.out.println("***** Receive " + r);
          //recibe la cantidad total de threads
          if (r == children + adults)
          {
              break;
          }
      }

    }

    static void AdultItinerary(boolean islaActual)
    {
    	/* This is where you should put your solutions. Make calls
    	   to the BoatGrader to show that it is synchronized. For
    	   example:
    	       bg.AdultRowToMolokai();
    	   indicates that an adult has rowed the boat across to Molokai
    	*/
      boatLock.acquire();
      while (true) {
        //si el thread adulto está en oahu
        if (islaActual == false) {
          //si no hay esapcio
          if (cantidadPasajeros >= 1) {
            
            oahu.sleep();
          }
          else {
            cantidadPasajeros = 2;
            bg.AdultRowToMolokai();
            //sale de la isla
            oahu.decreaseAdult();
            //cambia de isla
            islaActual = true;
            molokai.addAdult();
            com.speak(molokai.getAllPeople());
            molokai.sleep();
            molokai.wakeAll();
          }
        }
        else if (islaActual == true) {
          molokai.sleep();
        }
        else {
          break;
        }
      }
      boatLock.release();

    }

    static void ChildItinerary(boolean islaActual)
    {
      boatLock.acquire();
      while (true) {
        //isla al inicar en oahu
        if (islaActual == false) {
          //si la canoa esta llena, dormir
          //o si solo queda un niño (cuando se va a traer al adulto faltante)
          if(cantidadPasajeros >= 2 
             || 
            (oahu.getAdults() == 0 && oahu.getChildren() == 1)
             ) {
            oahu.sleep();
          }
          oahu.wakeAll();

          if (oahu.getChildren() > 1) {
            cantidadPasajeros++;
            if (cantidadPasajeros == 1) {
              
              boatCondition.sleep();
              
              oahu.decreaseChildren();

              bg.ChildRowToMolokai();
              //cambiar a molokai
              islaActual = true;
              molokai.addChildren();
              //notificar a otro pasajero
              boatCondition.wake();

              molokai.sleep();
            }
            else if (cantidadPasajeros == 2) {
              boatCondition.wake();
              boatCondition.sleep();
              oahu.decreaseChildren();
              bg.ChildRideToMolokai();
            // all the children get off boat, decrease passenger number
              cantidadPasajeros = cantidadPasajeros - 2;

              // note, now boat arrives on Molokai
              islaActual = true;
              molokai.addChildren();

              com.speak(molokai.getAllPeople());

              // two children arrive in Molokai, wake up one child in Molokai
              molokai.wakeAll();

              // current child is sleeping
              molokai.sleep();
            }
          }//childred > 1
          else if (oahu.getChildren() == 1) {
            oahu.decreaseChildren();
            bg.ChildRowToMolokai();
            //isla molokai
            islaActual = true;
            molokai.addChildren();
            //en realidad deberia quedar cero porque solo 
            //se tranposrto a un child
            cantidadPasajeros--;
            com.speak(molokai.getAllPeople());
            molokai.sleep();
          }
        }
        //molokai
        else if (islaActual == true){
          System.out.println("Child in monokai " + molokai.getChildren());
        }
        else {
          break;
        }
      }

      boatLock.release();

    }

    static void SampleItinerary()
    {
  	  // Please note that this isn't a valid solution (you can't fit
    	// all of them on the boat). Please also note that you may not
    	// have a single thread calculate a solution and then just play
    	// it back at the autograder -- you will be caught.
    	System.out.println("\n ***Everyone piles on the boat and goes to Molokai***");
    	bg.AdultRowToMolokai();
    	bg.ChildRideToMolokai();
    	bg.AdultRideToMolokai();
    	bg.ChildRideToMolokai();
    }
    
}
