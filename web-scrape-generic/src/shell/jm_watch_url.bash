#!/bin/bash


# I often use this script to monitor connectivity.  The output
# can be sent to your infrastructure team to prove a point...

while [ 1 ]
do

    echo aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa >> jm_watch_url.txt
    echo `date` >> jm_watch_url.txt
    echo aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa >> jm_watch_url.txt
    curl -v https://www.google.com/recaptcha/api/siteverify 2>&1 | tee -a jm_watch_url.txt
    sleep 1200

done