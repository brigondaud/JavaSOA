#!/bin/bash

#Expect the Cassandra VM @IP as first argument
if [ $# -eq 0 ]
  then
    echo "Usage: setup.sh <IP@Cassandra> [TODO: IP@SparkMaster]"
    exit 1
fi

sudo apt-get update
sudo apt-get -y install default-jdk

sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d ~/tomcat tomcat

mkdir ~/tomcat
cd /tmp
sudo apt-get -y install curl
sudo curl -O http://mirror.its.dal.ca/apache/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
sudo tar xzvf apache-tomcat-8*tar.gz -C ~/tomcat --strip-components=1

cd ~/tomcat
sudo chgrp -R tomcat ~/tomcat
sudo chmod -R g+r ~/tomcat/conf && chmod g+x ~/tomcat/conf
sudo chown -R tomcat ~/tomcat/webapps/ ~/tomcat/work/ ~/tomcat/temp/ ~/tomcat/logs/

sudo ufw allow 8080

cd ~

jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
echo "export PATH=JAVA_HOME=${jdk}/jre/bin:$PATH" >> .bashrc
echo "export CATALINA_PID=~/tomcat/temp/tomcat.pid" >> .bashrc
echo "export CATALINA_PID=~/tomcat/temp/tomcat.pid" >> .bashrc
echo "export CATALINA_HOME=~/tomcat" >> .bashrc
echo "export CATALINA_BASE=~/tomcat" >> .bashrc
echo "export 'CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'" >> .bashrc
echo "export 'JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'" >> .bashrc

cd /usr/local
sudo wget http://www-eu.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz

sudo tar -xzf apache-maven-3.5.4-bin.tar.gz
sudo ln -s apache-maven-3.5.4 apache-maven

cd ~

echo "export M2_HOME=/usr/local/apache-maven" >> .bashrc
echo "export MAVEN_HOME=/usr/local/apache-maven" >> .bashrc

. ~/.bashrc

echo "export PATH=${M2_HOME}/bin:${PATH}" >> .bashrc
echo "export dbAddr=$1" >> .bashrc

. ~/.bashrc