package nachos.threads;
import nachos.ag.BoatGrader;
import nachos.machine.*;
public class Boat
{
    static BoatGrader bg;
  
    //cantidad de pasajeros en el bote actual
    //la cantidad maxima de pasajeros es de 2
    public static int cantidadPasajeros = 2;
    public static Condition boatCondition;
    public static boolean barquito = false;
    public static Lock boatLock;
    public static Isla oahu;
    public static Isla molokai;; 
    public static char dbChar = 'b';

    public Boat() {
      this.boatLock = new Lock();
      this.boatCondition = new Condition(boatLock);
      this.oahu = new Isla("Oahu", boatLock);
      this.molokai = new Isla("Monokai", boatLock);
      selfTest();
    }

    public static void selfTest()
    {
    	BoatGrader b = new BoatGrader();
    	
    	System.out.println("\n ***Testing Boats with only 2 children***");
    	begin(1, 2, b);

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
            ChildItinerary();
          };
      };

      Runnable runnable_adultos = new Runnable() {

          public void run() {
              //thread local varialbe, indicate where person is
              AdultItinerary();
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
        
    

    }

    static void AdultItinerary()
    {
    	/* This is where you should put your solutions. Make calls
    	   to the BoatGrader to show that it is synchronized. For
    	   example:
    	       bg.AdultRowToMolokai();
    	   indicates that an adult has rowed the boat across to Molokai
    	*/
      boolean islaActual = false;
      boatLock.acquire();
      while (true) {
        //si el thread adulto está en oahu
        if (islaActual == false) {
          //si no hay esapcio
            if (barquito == false) {
              if (!(cantidadPasajeros > 1)) {
                oahu.sleep();
              }
              else if (oahu.getChildren() > 2) {
                oahu.sleep();
              }
              else {
                //cambia de isla
                islaActual = true;
                cantidadPasajeros = 0;
                oahu.decreaseAdult();
                
                bg.AdultRowToMolokai(); 
                Lib.debug(dbChar, "-----> currentThread " + KThread.currentThread().getName());
                Lib.debug(dbChar, "");
                //sale de la isla
                
                molokai.addAdult();
                molokai.setCantidadOtra(oahu.getAllPeople());
                //System.out.println("Adults on molokai " + molokai.getAdults());
                
               // Lib.assertTrue(molokai.getChildren() > 0);
                molokai.wakeAll();
                cantidadPasajeros = 2;
                barquito = true;
                 //System.out.println("Adultss on oahu" + oahu.getAdults());
                molokai.sleep();
                }
              } else {
              oahu.sleep();
            }

          }else if (islaActual == true) {
            molokai.sleep();
          }
          else {
            break;
          }

          
      }
      //boatLock.release();

    }

    static void ChildItinerary()
    {
      boolean islaActual = false;
      boatLock.acquire();
   
      while (true) {
        //isla al inicar en oahu
        if (islaActual == false) {
          //si la canoa esta llena, dormir
          if (barquito == false) {
          
            if (oahu.getChildren() < 2) {
              oahu.sleep();
            }
            if (cantidadPasajeros == 2) {

              islaActual = true;
              oahu.decreaseChildren();
              cantidadPasajeros = 1;
              
              bg.ChildRowToMolokai();
              Lib.debug(dbChar, "-----> currentThread " + KThread.currentThread().getName());
              Lib.debug(dbChar, "");
              if (oahu.getChildren() > 0) {
                oahu.addChildren();
                oahu.wakeAll();
                molokai.sleep();
              }
              else {
                barquito = true;
                cantidadPasajeros = 2;
                molokai.setCantidadOtra(oahu.getAllPeople());
                molokai.addChildren();
                molokai.wakeAll();
                molokai.sleep();
              }
            } else if (cantidadPasajeros == 1) {
              islaActual = true;
              barquito = true;
              oahu.decreaseChildren();
              oahu.decreaseChildren();
              bg.ChildRideToMolokai();
              Lib.debug(dbChar, "-----> currentThread " + KThread.currentThread().getName());
              Lib.debug(dbChar, "");

              cantidadPasajeros = 2;
              molokai.setCantidadOtra(oahu.getAllPeople());
              molokai.addChildren();
              molokai.addChildren();
              molokai.wakeAll();
              molokai.sleep();
            }
            else {
              oahu.sleep();
            }
          } else {
            oahu.sleep();
          }
          }else if (islaActual == true) {
            if (molokai.getCantidadOtra() == 0) {
              molokai.sleep();
            }
            else {
              if (barquito == true) {
                cantidadPasajeros = 1;
                islaActual = false;
                barquito = false;
              
                bg.ChildRowToOahu();
                Lib.debug(dbChar, "-----> currentThread " + KThread.currentThread().getName());
                Lib.debug(dbChar, "");
                
                molokai.decreaseChildren();
                cantidadPasajeros = 2;
                oahu.addChildren();
                oahu.setCantidadOtra(molokai.getAllPeople());
                oahu.wakeAll();
                oahu.sleep();

              }
              else {
                molokai.sleep();
              }
            }
          }
          else {
            break;
          }
         
      }

      //boatLock.release();

    }

    static void SampleItinerary()
    {
  	  // Please note that this isn't a valid solution (you can't fit
    	// all of them on the boat). Please also note that you may not
    	// have a single thread calculate a solution and then just play
    	// it back at the autograder -- you will be caught.
    	//System.out.println("\n ***Everyone piles on the boat and goes to Molokai***");
    	bg.AdultRowToMolokai();
    	bg.ChildRideToMolokai();
    	bg.AdultRideToMolokai();
    	bg.ChildRideToMolokai();
    }
    
}
