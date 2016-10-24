package nachos.threads;
import nachos.ag.BoatGrader;

public class Isla {

  private  String nombre;
  private  int cantidadAdultos;
  private  int cantidadNinos;
  private  Condition esperandoIsla;
  private  int cantidadOtra;


  public Isla(String nombre, Lock lock) {
    this.nombre = nombre;
    this.cantidadAdultos = 0;
    this.cantidadNinos = 0;
    this.esperandoIsla = new Condition(lock);
    this.cantidadOtra = 0;
  }

  public void setCantidadOtra (int val) {
    cantidadOtra = val;
  }
  public int getCantidadOtra() {
    return cantidadOtra;
  }

  public void setPeople(int adultos, int ninos) {
    cantidadAdultos = adultos;
    cantidadNinos = ninos;
  }

  public  String getNombre() {
    return nombre;
  }

  public  int getAdults() {
    return cantidadAdultos;
  }

  public  int getChildren() {
    return cantidadNinos;
  }

  public  void sleep() {
    esperandoIsla.sleep();
  }

  public  void wakeAll() {
    esperandoIsla.wakeAll();
  }

  public  void wake() {
    esperandoIsla.wake();
  }

  public  int getAllPeople() {
    return cantidadAdultos + cantidadNinos;
  }

  public  void addAdult() {
    cantidadAdultos++;
  }

  public  void addChildren() {
    cantidadNinos++;
  }

  public  void decreaseAdult() {
    cantidadAdultos--;
  }

  public  void decreaseChildren() {
    cantidadNinos--;
  }

  public String toString() {
    return "Isla -> " + this.nombre + "\n" +
    "Adultos -> " + this.cantidadAdultos + " Niños " + this.cantidadNinos;
  }

}