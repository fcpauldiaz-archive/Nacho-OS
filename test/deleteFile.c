#include "syscall.h"

#define TESTFILE "hola.txt"

int main(int argc, char** argv)
{
  int fd = open(TESTFILE);
  unlink(TESTFILE);

  return 0;
}