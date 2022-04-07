FROM nginx:latest
WORKDIR /html
COPY nginx.conf /etc/nginx/nginx.conf
RUN true
COPY build/ ./
EXPOSE 80
