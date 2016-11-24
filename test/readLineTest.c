#include "syscall.h"


#define TESTFILE "hola.txt"
#define BUFSIZE 1024

char buf[BUFSIZE];
char bufRead[BUFSIZE];
char str[] = "Prueba nachos\n";


int main(int argc, char** argv)
{

  readline(buf, BUFSIZE);
  int fd = creat("prueba.txt");
  //strcpy(buf, str);
  int result = write(fd, buf, BUFSIZE);
  //leer de consola
  //crear archivo
  //escribir archivo

}