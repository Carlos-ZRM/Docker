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
La primera instrucción siempre es **From** esta instrucción indica la Imagen base. 


##### Variables de entorno
-   `ADD` Copia nuevos archivos y directorios remotos del host \<src\> hacía el destino dentro del contenedor \<dest\> 
		``` ADD  <src> <dest> ```

-   `COPY` Copia nuevos archivos y directorios remotos del host \<src\> hacía el destino dentro del contenedor \<dest\> 					
		```COPY <src> <dest>	```
-   `ENV`La instrucción ENV establece la variable de entorno <key> en el valor <value>. Este valor estará en el entorno para todas las instrucciones posteriores en la etapa de compilación.
	```
	ENV <key> <value>
	ENV <key>=<value> ...
	```
-   `EXPOSE` La instrucción EXPOSE informa a Docker que el contenedor escucha en los puertos de red especificados en tiempo de ejecución. Puede especificar si el puerto escucha en TCP o UDP, y el valor predeterminado es TCP si no se especifica el protocolo.
		``` EXPOSE <port> [<port>/<protocol>...] ```
-   `FROM`
-   `LABEL`
-   `STOPSIGNAL`
-   `USER`
-   `VOLUME`
-   `WORKDIR`
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
eyJoaXN0b3J5IjpbMjQ2NTQ0ODgyLC0xMDk1NDc0MDQ3LDE3OD
E0NDI5MjksLTE2ODgyODgwNDddfQ==
-->