sudo apt-get update
sudo apt-get -y install default-jdk

sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d ~/tomcat tomcat

mkdir ~/tomcat
cd /tmp
apt-get -y install curl
curl -O http://mirror.its.dal.ca/apache/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
tar xzvf apache-tomcat-8*tar.gz -C ~/tomcat --strip-components=1

cd ~/tomcat
chgrp -R tomcat ~/tomcat
chmod -R g+r ~/tomcat/conf && chmod g+x ~/tomcat/conf
chown -R tomcat ~/tomcat/webapps/ ~/tomcat/work/ ~/tomcat/temp/ ~/tomcat/logs/

sudo ufw allow 8080

jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
export JAVA_HOME=$jdk/jre
export CATALINA_PID=~/tomcat/temp/tomcat.pid
export CATALINA_PID=~/tomcat/temp/tomcat.pid
export CATALINA_HOME=~/tomcat
export CATALINA_BASE=~/tomcat
export 'CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
export 'JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

sh ~/tomcat/bin/startup.sh &