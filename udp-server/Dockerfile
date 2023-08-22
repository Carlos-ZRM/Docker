FROM registry.access.redhat.com/ubi8/ubi:8.7-1112

RUN dnf install nc -y

RUN useradd udpserver; \
echo udpserver:10000:5000 > /etc/subuid; \
echo udpserver:10000:5000 > /etc/subgid;

USER udpserver

WORKDIR /home/udpserver

RUN chmod 777 /home/udpserver
EXPOSE 9999/udp
COPY server.py .
ENTRYPOINT python3  server.py

