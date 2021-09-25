#!/bin/bash
args=( "$@" )

if [[ -z ${args[0]} ]] ; then
    port=9998
else
    port=${args[0]}
fi
name=${args[1]}
echo "start application $port $name"
java -jar ./target/player-communication-multi-process-0.0.1-SNAPSHOT.jar $port $name