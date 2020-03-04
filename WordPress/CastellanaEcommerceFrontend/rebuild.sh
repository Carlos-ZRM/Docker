#LOCAL
# sudo docker build -t ecommerce_frontend . && (
# sudo docker rm -f ecommerce_frontend;
# sudo docker run --name ecommerce_frontend -p 80:80 --network ecommerce \
# -e "ENDPOINTS=/ecommerce_backend=http://ecommerce_backend:8080/lacastellana-ecommerce" \
# -d ecommerce_frontend );

#QA
# sudo docker build -t ecommerce_frontend . && (
# sudo docker rm -f ecommerce_frontend 2>/dev/null;
# sudo docker run --name pco_frontend -p 8115:80 --network ecommerce \
# -e "ENDPOINTS=/ecommerce_backend=http://ecommerce_backend:8080/lacastellana-ecommerce" \
# -d ecommerce_frontend );

#PREPRO
# sudo docker build -t ecommerce_frontend_pre . && (
# sudo docker rm -f ecommerce_frontend_pre 2>/dev/null;
# sudo docker run --name pco_frontend_pre -p 8116:80 --network ecommerce_pre \
# -e "ENDPOINTS=/ecommerce_backend_pre=http://ecommerce_backend_pre:8080/lacastellana-ecommerce" \
# -d ecommerce_frontend_pre );

#PRODUCCION
sudo docker build -t ecommerce_frontend . && (
sudo docker rm -f pco_frontend_pro 2>/dev/null;
sudo docker run --name pco_frontend_pro -p 8116:80 --network ecommerce_pro \
-e "ENDPOINTS=/ecommerce_backend_pro=http://ecommerce_backend_pro:8080/lacastellana-ecommerce" \
-d ecommerce_frontend );