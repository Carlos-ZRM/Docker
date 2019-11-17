## Docker Images
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
eyJoaXN0b3J5IjpbNDUyMjExNjExLC0xNjg4Mjg4MDQ3XX0=
-->