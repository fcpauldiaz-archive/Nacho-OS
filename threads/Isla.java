package nachos.threads;
import nachos.ag.BoatGrader;

public class Isla {

  private static String nombre;
  private static int cantidadAdultos;
  private static int cantidadNinos;
  private static Condition esperandoIsla;


  public Isla(String nombre, Lock lock) {
    this.nombre = nombre;
    this.cantidadAdultos = 0;
    this.cantidadNinos = 0;
    this.esperandoIsla = new Condition(lock);
  }

  public void setPeople(int adultos, int ninos) {
    this.cantidadAdultos = adultos;
    this.cantidadNinos = ninos;
  }

  public static String getNombre() {
    return nombre;
  }

  public static int getAdults() {
    return cantidadAdultos;
  }

  public static int getChildren() {
    return cantidadNinos;
  }

  public static void sleep() {
    esperandoIsla.sleep();
  }

  public static void wakeAll() {
    esperandoIsla.wakeAll();
  }


  public static int getAllPeople() {
    return cantidadAdultos + cantidadNinos;
  }

  public static void addAdult() {
    cantidadAdultos++;
  }

  public static void addChildren() {
    cantidadNinos++;
  }

  public static void decreaseAdult() {
    cantidadAdultos--;
  }

  public static void decreaseChildren() {
    cantidadNinos--;
  }

  public String toString() {
    return "Isla -> " + this.nombre + "\n" +
    "Adultos -> " + this.cantidadAdultos + " Niños " + this.cantidadNinos;
  }

}