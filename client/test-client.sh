#!/bin/bash

if [ $# -eq 0 ]
  then
    echo "Usage: ./test-client.sh <sparkMasterIP>"
    exit 1
fi

cd ~/JavaSOA/client

mvn test -DsparkMasterIP="$1"