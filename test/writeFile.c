#include "syscall.h"


#define TESTFILE "hola.txt"
#define BUFSIZE 1024

char buf[BUFSIZE];
char bufRead[BUFSIZE];


int main(int argc, char** argv)
{

  int fd, amount;
  int fe;
  fd = open(TESTFILE);
  if (fd >= 0) {
    printf("%s\n", "Se ha abierto el archivo hola.txt");
  }
  strcpy(buf, "Prueba nachos\n");
  int result = write(fd, buf, sizeof(buf));
  if (result >= 0) {
    printf("%s\n", "Se ha escrito al archivo hola.txt");
  }
  //
  //write(fd, buf, 5);
  //read(fd, bufRead, 3);
  //printf("test ttttttttttttttttttttttttttttttttttt\n");
  //write(0, bufRead, 3);
  
  fe = close(fd);


  return 0;
}
  