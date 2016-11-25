#include "syscall.h"


#define TESTFILE "prueba.txt"
#define TESTFILE2 "prueba2.txt"
#define BUFSIZE 1024

char buf[BUFSIZE];
char bufRead[BUFSIZE];
char str[] = "Prueba nachos\n";


int main(int argc, char** argv)
{
  int amount; 
  //leer contendio archivo prueba
  int fd = open(TESTFILE);
  if (fd==-1) {
    printf("Unable to open %s\n", TESTFILE);
    return 1;
  }
  
  read(fd, buf, BUFSIZE);
  printf("%s\n", buf);
  //crear otro archivo
  int fd2 = creat(TESTFILE2);
  if (fd >= 0) {
    printf("%s\n", ("Se ha creado el archivo"));
  }
  //copiarlo a otro archivo
  int result = write(fd2, buf, BUFSIZE);
  int fd3 = open(TESTFILE2);
  if (fd3==-1) {
    printf("Unable to open %s\n", TESTFILE);
    return 1;
  }
  while ((amount = read(fd3, buf, BUFSIZE))>0) {
     printf("%s\n", ("Se ha leído el archivo"));
     //desplegar en consola
    write(1, buf, amount);
  }
 
  //cerrar archivo
  int fe = close(fd3);
  printf("%s\n", ("Se ha cerrado al archivo "));
  exit(0);
  halt();
  
  
 
}