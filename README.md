# Temario DevOPS

# Introducción a DevOPS
- GItlab
- Jenkins
- Grafana, 
# Introduccion a los contenedores
- introduccion a contenedores
- volumenes
- redes
- Docker run 
- Dockerfile
- Instalacion de Docker 
-  Nginx 

# GCP y Cloud computing

# Docker

# Practicas
- 1. Desplegar contenedor python, node, java, ubuntu desde imagen base
	- Ejecutar programas dentro de contenedores docker 
	
- 2. Desplegar Gitlab con docker 
- 3. Creacion de Docker file para Flask/Django
- 4.  

- Desplegar sitio web estatico con NGINX
-  Desplegar nginx ReverseProxy para servicios
	- Desplegar sitio webestatico  con volumenes
	- Crear imagen personalizada de NGINX/ Instalacion manual NGINX 

- Desplegar contenedor MySQL/SQL lite permitiendo conexiones remotas
	- Hacer persistente las conexiones remotas a MySQL

- Creacion de Docker multistage 
- Docker compose / Podman POD
	- Conectar Django/Flask y MySQL/SQLite
	- Conectar con NGINX 

Docker es una plataforma para construir, compartir y desplegar aplicaciones con contenedores. 
- Flexible
- Ligeras
- Portable 
- Escalable 
- Seguro
- Acoplamiento isolado dentro del host

## Introdución 

### Contenedores. 
El término Contenedores de Linux denota un grupo de tecnologías que se utilizan para empaquetar y aislar aplicaciones, sus dependencias y sus tiempos de ejecución, de forma portátil, de modo que se pueda mover entre entornos conservando la funcionalidad completa.

Los contenedores son procesos encapsulados dentro del sistema operativo. Esto permite que estén aislados de los demás procesos del Sistema operativo (SO) . Cada contenedor tiene su propio sistema de archivos , puertos, procesos, usuarios.


## Componentes Docker  

![Docker](http://www.arquitectoit.com/images/dockers/arquitectura-docker.jpg) 


##  Imágenes

Las imágenes incluyen lo necesario para ejecutar una aplicación : código o archivos binarios, runtimes, dependencias y otros objetos. 
Son construidas a partir de un archivo llamado **Dockerfile**. Este funciona como una plantilla que le indicara al contenedor todas las intrucciones necesarias para crearse y ejecutarse por primera vez  

``` dockerfile

FROM centos:7
RUN yum -y update &&\
	 yum install -y  postgresql-server postgresql-contrib postgresql-plpython postgresql-pltcl &&\ 
	rm -rf /var/lib/pgsql/data/*
RUN su - postgres -c "initdb --encoding=UTF8 -D /var/lib/pgsql/data/"

EXPOSE 5432

VOLUME ["/var/lib/pgsql/data","/var/lib/pgsql/data/pg_log"]

COPY spacewalk-postgres-entrypoint.sh /usr/local/bin/spacewalk-postgres-entrypoint.sh
RUN chmod a+x /usr/local/bin/spacewalk-postgres-entrypoint.sh
ENTRYPOINT ["/usr/local/bin/spacewalk-postgres-entrypoint.sh"]
 
```
## Contenedores 

### Contenedores vs Maquinas Virtuales. 
Una maquina virtual ejecuta por completo un SO huesped por lo que accede a mas recursos del host de los necesarios para la lógica de la aplicación desplegada. 
![Imagenes](https://docs.docker.com/images/Container%402x.png)

## Arquitectura y tecnologías
![Imagen](https://www.docker.com/sites/default/files/d8/styles/large/public/2018-11/Docker-Website-2018-Diagrams-071918-V5_a-Docker-Engine-page-first-panel.png?itok=TFiL1wtt)


## Administración de Imagenes
```console 

# Descargar imagenes de un Regestry 
docker pull ubuntu 

# Construir imagenes predeterminadas
docker build -f ./  

# Listar imagenes 
docker image ls 

``` 
## Dockercompose

```
version: '3.3'

services:
   db:
     image: mysql:5.7
     volumes:
       - db_data:/var/lib/mysql
     restart: always
     environment:
       MYSQL_ROOT_PASSWORD: somewordpress
       MYSQL_DATABASE: wordpress
       MYSQL_USER: wordpress
       MYSQL_PASSWORD: wordpress

   wordpress:
     depends_on:
       - db
     image: wordpress:latest
     ports:
       - "8000:80"
     restart: always
     environment:
       WORDPRESS_DB_HOST: db:3306
       WORDPRESS_DB_USER: wordpress
       WORDPRESS_DB_PASSWORD: wordpress
       WORDPRESS_DB_NAME: wordpress
volumes:
    db_data: {}

```

### cgroups
### systemd

### Linux Namespaces

## Instalación en CentOS.
```
# Se actualizan los repositorios
sudo yum update -y
# Instalacion de dependencias
sudo yum install -y yum-utils \
 device-mapper-persistent-data \
  lvm2
# Se agrega el repositorio 
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

# Instalación de Docker
sudo yum install docker-ce docker-ce-cli containerd.io

# Iniciar docker
sudo systemctl enable docker
sudo systemctl start docker
sudo yum install epel-release
sudo yum install -y python-pip
sudo yum upgrade python*
docker-compose version
```


  

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTkzNDkyMzIyOCwxMzg4MDk4ODgxLC0xND
YxMjMxNjMwLDE1ODUxMTQ5NDQsLTEzNDk0OTI3NTQsMTY4NzE4
MzE4OCw3NjM0NzA5NTUsMTAwNDUwNTE3NywxMTI0MjI1OTUsLT
MxOTQ1NjUzNywtMTI4NTE3OTc2NSwxOTU4NDE0MzUsLTU3OTA0
OTExMSwtMTQ4NTI1MTQwNiwtMTgyNTIwNzc3N119
-->