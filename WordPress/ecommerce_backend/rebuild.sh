#LOCAL
#sudo docker build -t ecommerce_backend . && (
#sudo docker rm -f ecommerce_backend;
#sudo docker run --network ecommerce --name ecommerce_backend -d ecommerce_backend);

#QA
sudo docker build -t ecommerce_backend . && (
sudo docker rm -f ecommerce_backend 2>/dev/null;
sudo docker run --network ecommerce --name ecommerce_backend -p 8086:8080 -d ecommerce_backend);

#PREPRO
#sudo docker build -t ecommerce_backend_pre . && (
#sudo docker rm -f ecommerce_backend_pre 2>/dev/null;
#sudo docker run --network ecommerce_pre --name ecommerce_backend_pre -p 8087:8080 -d ecommerce_backend_pre);

#PRODUCCION
#sudo docker build -t ecommerce_backend . && (
#sudo docker rm -f ecommerce_backend_pro 2>/dev/null;
#sudo docker run --network ecommerce_pro --name ecommerce_backend_pro -p 8087:8080 -d ecommerce_backend);
