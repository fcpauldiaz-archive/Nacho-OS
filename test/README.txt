Adolfo Morales 13014
Pablo Díaz 13203


copyFile.c
  Con este programa copiamos un archivo llamado "prueba.txt" a "prueba2.txt"
  Los pasos son:
    Leer contenido del archivo "prueba.txt"
    Crear un nuevo archivo "prueba2.txt"
    Copiar contenido leído (BUF) y lo copiamos al archivo "prueba2.txt"
    Imprimimos lo copiado en consola
    Cerramos el archivo

createFile.c
  Con este programa creamos un nuevo archivo
  Solo se crea un archivo con el nombre "hola.txt"

deleteFile.c
  Con este programa eliminamos un archivo
  Los pasos son los siguientes:
    Abrimos el archivo para obtener la referencia
    Y le hacemos unlink() al archivo
  
readFile.c
  Con este programa leemos un archivo "hola.txt"
  Los pasos son:
    Abrimos el archivo "hola.txt"
    Leemos cada uno de los caracteres en un buf y lo imprimimos

readLineTest.c
  Con este programa leemos lo que el usuario escriba en la consola
  Los pasos son:
    Iniciamos un Readline que recibe el buf en donde vamos a guardar y el BUFSIZE que es como un límite de lo que vamos a leer
    Luego creamos un archivo "prueba.txt" y escribimos lo leído allí

writeFile.c
  Con este programa escribimos contenido en un archivo "hola.txt"
  Los pasos son:
    Abrimos el archivo
    Escribimos en el archivo lo que cargamos en el BUF
    Cerramos el archivo