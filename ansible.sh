#!/bin/bash

USUARIO=ansible
LLAVE="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDnF+kfD+peq8OF/HMltCiVaf8EhJZBBXCrNdqz87P2M7dZ/13fL/cWGHevig9GuemMuB6ZInakcwcRnSw0oiUz4XZGCCXy+dJ/rvIPjkUgKk3EJ170bFi4B2sbKcZr4z0S2P5/cdfV3xJxvi/MxSjmGrbCcymFyKcUWjJFFMKoACtkkd0SplvbaHtcnSZ8jMGZF6S8dhOu4s8x/yHq6SUktjUEeLyw4caww+MMtFzNsMV8cF1R1Y6QySnxsMGK95tzYVDHtincEUB3GjBy8KZevRduw/dHrP1loaYYvF7PCd5aiabqhItqBOnXiVG5DjP/6pu7qUoR2RVm2uu/+Ht/ root@localhost.localdomain"
groupadd infra


useradd -m -g infra ${USUARIO}

echo "Usuario agregado"

echo "Pass agregado"

mkdir -p /home/${USUARIO}/.ssh/
touch /home/${USUARIO}/.ssh/authorized_keys
chmod 700 /home/${USUARIO}/.ssh/
chmod 600 /home/${USUARIO}/.ssh/authorized_keys
echo "${LLAVE}" >> /home/${USUARIO}/.ssh/authorized_keys
chown ${USUARIO}:infra /home/${USUARIO}/ -R
echo "llave agregada"
echo "${USUARIO}  ALL=(ALL)      NOPASSWD: ALL" >> /etc/sudoers
