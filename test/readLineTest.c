#include "syscall.h"


#define TESTFILE "prueba.txt"
#define BUFSIZE 1024

char buf[BUFSIZE];
char bufRead[BUFSIZE];
char str[] = "Prueba nachos\n";


int main(int argc, char** argv)
{

  readline(buf, BUFSIZE);
  int fd = creat(TESTFILE);
  //strcpy(buf, str);
  int result = write(fd, buf, BUFSIZE);
  halt();
  //leer de consola
  //crear archivo
  //escribir archivo

}