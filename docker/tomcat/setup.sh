sudo apt-get update
sudo apt-get -y install default-jdk

sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d /home/tomcat tomcat

mkdir /home/tomcat
cd /tmp
apt-get -y install curl
curl -O http://mirror.its.dal.ca/apache/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
tar xzvf apache-tomcat-8*tar.gz -C /home/tomcat --strip-components=1

cd /home/tomcat
chgrp -R tomcat /home/tomcat
chmod -R g+r /home/tomcat/conf && chmod g+x /home/tomcat/conf
chown -R tomcat /home/tomcat/webapps/ /home/tomcat/work/ /home/tomcat/temp/ /home/tomcat/logs/

jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
export JAVA_HOME=$jdk/jre
export CATALINA_PID=/home/tomcat/temp/tomcat.pid
export CATALINA_PID=/home/tomcat/temp/tomcat.pid
export CATALINA_HOME=/home/tomcat
export CATALINA_BASE=/home/tomcat
export 'CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
export 'JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

sh /home/tomcat/bin/startup.sh &