#!/bin/bash

docker run -it --rm --name certbot -v ~/shoppy/certbot/conf/:/etc/letsencrypt -v ~/shoppy/certbot/www:/var/lib/www certbot/certbot certonly

