# Docker
Docker es una plataforma para construir, compartir y desplegar aplicaciones con contenedores. 
-Flexible
- Ligeras
- Portable 
- Escalable 
- Seguro
- Acoplamiento isolado dentro del host

## Introdución 
### Contenedores. 
Los contenedores son procesos encapsulados dentro del sistema operativo. Esto permite que estén aislados de los demás procesos del Sistema operativo (SO) . Cada contenedor tiene su propio sistema de archivos privado. Este sistema de archivos es proporcionado una Imagen.

###  Imágenes
Las imágenes incluyen lo necesario para ejecutar una aplicación : código o archivos binarios, runtimes, dependencias y otros objetos. 
### Contenedores vs Maquinas Virtuales. 
Una maquina virtual ejecuta por completo un SO invitado por lo que accede a mas recursos del host de los necesarios para la lógica de la aplicación desplegada. 
![Imagenes](https://docs.docker.com/images/Container%402x.png)

## Arquitectura 
![Imagen](https://www.docker.com/sites/default/files/d8/styles/large/public/2018-11/Docker-Website-2018-Diagrams-071918-V5_a-Docker-Engine-page-first-panel.png?itok=TFiL1wtt)
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


```

  

<!--stackedit_data:
eyJoaXN0b3J5IjpbNzAyMDQwOTE4LC0xNDg1MjUxNDA2LC0xOD
I1MjA3Nzc3XX0=
-->