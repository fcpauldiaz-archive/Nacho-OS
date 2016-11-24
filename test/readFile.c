#include "syscall.h"
#include "stdio.h"
#include "stdlib.h"

#define BUFSIZE 1024
#define TESTFILE "hola.txt"

char buf[BUFSIZE];

int main(int argc, char** argv)
{
  int fd, amount;


  fd = open(TESTFILE);
  if (fd==-1) {
    printf("Unable to open %s\n", TESTFILE);
    return 1;
  }
  while ((amount = read(fd, buf, BUFSIZE))>0) {
    write(1, buf, amount);
  }
  printf("%s\n", "Se ha leído el archivo hola.txt");

  close(fd);
  halt();
  //readline("     ", 5);

  return 0;
}
