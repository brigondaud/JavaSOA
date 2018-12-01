#!/bin/bash

sudo apt-get update

sudo apt-get -y install default-jdk

sudo ufw disable
sudo iptables -F

jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
echo 'export JAVA_HOME='"$jdk" >> ~/.bashrc

. ~/.bashrc

wget http://apache.forsale.plus/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz

tar -xvzf spark-2.4.0-bin-hadoop2.7.tgz

sudo cp -f ~/JavaSOA/setup/spark/spark-env.sh ~/spark-2.4.0-bin-hadoop2.7/conf/
sudo cp -f ~/JavaSOA/setup/spark/spark-defaults.conf ~/spark-2.4.0-bin-hadoop2.7/conf/