#!/bin/bash

sudo apt-get update

sudo apt-get update
sudo apt-get -y install default-jdk

jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
echo 'export JAVA_HOME='"$jdk" >> ~/.bashrc

. ~/.bashrc

echo 'export PATH='"$JAVA_HOME"'/bin:'"$PATH" >> ~/.bashrc

. ~/.bashrc

wget http://apache.mirror.gtcomm.net/cassandra/3.11.3/apache-cassandra-3.11.3-bin.tar.gz

tar -xvzf apache-cassandra-3.11.3-bin.tar.gz

cd apache-cassandra-3.11.3