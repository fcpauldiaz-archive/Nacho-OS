#include "syscall.h"


#define TESTFILE "hola.txt"



int main(int argc, char** argv)
{

  int fd;
  int fe;
  fd = creat(TESTFILE);
  if (fd >= 0) {
    printf("%s\n", "Se ha creado el archivo");
  }
  fe = close(fd);
  printf("%s\n", "Se ha cerrado al archivo hola.txt");
  halt();

  return 0;
}
