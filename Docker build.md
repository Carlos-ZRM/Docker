## Dockerfile
Docker puede construir imágenes automáticamente leyendo las instrucciones de un Dockerfile. Un Dockerfile es un documento de texto que contiene todos los comandos que un usuario necesita llamar en una terminar para ensamblar una imagen.  Aquí están todas las instrucciones necesarias para crear la aplicación y desplegar el Host. Los usuarios de Docker Build pueden crear una compilación automatizada que ejecuta varias instrucciones de línea de comandos en sucesión.
**Nota** 
Una imagen en Docker funciona creando capas o niveles de contenedores.  Una capa base puede ser la imagen de un sistema operativo u otra Imagen en el [Dockerhub ](https://hub.docker.com/),  regestry o en su equipo.

Al desplegar el contenedor se  crea la ultima capa para la operación del microservicio. Esta capa puede modificarse y convertila en una Imagen. 


### Creación del dockerfile 
#### Formato
```Dockerfile
# Comment
INSTRUCTION arguments
```
La primera instrucción siempre es **From** esta instrucción indica el contene
### 
## Dockerbuild
Docker build es el comando que construye las Imágenes de docker y crea el contexto necesario para  la creación del microservicio. 
**Nota** El directorio en el que esta el Dockerfile es el directorio raíz que tomará Docker.  Por esto solo se puede tener un archivo con el nombre **Dockerfile** en un directorio. Usualmente los archivos o directorios que se necesitan copiar al contenedor desde el host están localizados en el directorio del dockerfile.
El comando para correr el docker build a partir de un docker file es el siguiente: 


```
# docker build <<Path dockerfile>>
docker build . 
```


> Written with [StackEdit](https://stackedit.io/).
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MzY4MDgxMiwtMTY4ODI4ODA0N119
-->