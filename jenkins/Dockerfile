FROM nginx:stable

# Install nodejs
RUN apt-get update && \
    curl -sL https://deb.nodesource.com/setup_12.x | bash - && \
    apt-get install -y nodejs git && \
    mkdir -p /var/www/ && \
    git clone https://github.com/hakimel/reveal.js.git /var/www/html && \
    cd /var/www/html && npm install && \
    rm -rf package-lock.json CONTRIBUTING.md LICENSE README.md examples package.json && \
    mkdir -p /var/www/html/plugin/external/ && \
    curl -o /var/www/html/plugin/external/external.js https://raw.githubusercontent.com/calevans/external/master/external/external.js

WORKDIR /var/www/html
