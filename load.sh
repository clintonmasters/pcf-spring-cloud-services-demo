#!/bin/sh
while true; 
	do 
		sleep 1
		echo '\n'$(date "+%H:%M:%S")
		curl dest-client.local.pcfdev.io/mydestinations 
       	done
