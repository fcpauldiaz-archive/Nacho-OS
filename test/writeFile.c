#include "syscall.h"


#define TESTFILE "hola.txt"
#define BUFSIZE 1024

char buf[BUFSIZE];
char bufRead[BUFSIZE];
char str[] = "Prueba nachos\n";


int main(int argc, char** argv)
{

  int fd, amount;
  int fe;
  fd = open(TESTFILE);
  if (fd >= 0) {
    printf("%s\n", "Se ha abierto el archivo hola.txt");
  }
  strcpy(buf, str);
  int result = write(fd, buf, strlen(str));
  if (result >= 0) {
    printf("%s\n", "Se ha escrito al archivo hola.txt");
  }
  
  fe = close(fd);
  printf("%s\n", "Se ha cerrado al archivo hola.txt");
  halt();

  return 0;
}
  