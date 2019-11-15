sudo yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install docker-ce docker-ce-cli containerd.io
sudo systemctl enable  docker
sudo systemctl start docker
sudo docker rub hello-world
sudo yum install epel-release
sudo yum install -y python-pip
sudo yum upgrade python*
docker-compose version
