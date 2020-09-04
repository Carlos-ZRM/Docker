# Docker

Docker es una plataforma para construir, compartir y desplegar aplicaciones con contenedores. 
- Flexible
- Ligeras
- Portable 
- Escalable 
- Seguro
- Acoplamiento isolado dentro del host

## Introdución 

### Contenedores. 
Los contenedores son procesos encapsulados dentro del sistema operativo. Esto permite que estén aislados de los demás procesos del Sistema operativo (SO) . Cada contenedor tiene su propio sistema de archivos , puertos, procesos, usuarios.

###  Imágenes

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
 
´´´ 


### Contenedores vs Maquinas Virtuales. 
Una maquina virtual ejecuta por completo un SO huesped por lo que accede a mas recursos del host de los necesarios para la lógica de la aplicación desplegada. 
![Imagenes](https://docs.docker.com/images/Container%402x.png)

## Arquitectura y tecnologías
![Imagen](https://www.docker.com/sites/default/files/d8/styles/large/public/2018-11/Docker-Website-2018-Diagrams-071918-V5_a-Docker-Engine-page-first-panel.png?itok=TFiL1wtt)

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
eyJoaXN0b3J5IjpbLTEyODUxNzk3NjUsMTk1ODQxNDM1LC01Nz
kwNDkxMTEsLTE0ODUyNTE0MDYsLTE4MjUyMDc3NzddfQ==
-->
