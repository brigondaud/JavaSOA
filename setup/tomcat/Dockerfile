FROM ubuntu:18.04

ENV tomcat_home /home/tomcat

RUN apt-get update
RUN apt-get -y install default-jdk

RUN groupadd tomcat
RUN useradd -s /bin/false -g tomcat -d ${tomcat_home} tomcat

RUN mkdir ${tomcat_home}
RUN cd /tmp
RUN apt-get -y install curl
RUN curl -O http://mirror.its.dal.ca/apache/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
RUN tar xzvf apache-tomcat-8*tar.gz -C ${tomcat_home} --strip-components=1

RUN cd ${tomcat_home}
RUN chgrp -R tomcat ${tomcat_home}
RUN chmod -R g+r ${tomcat_home}/conf && chmod g+x ${tomcat_home}/conf
RUN chown -R tomcat ${tomcat_home}/webapps/ ${tomcat_home}/work/ ${tomcat_home}/temp/ ${tomcat_home}/logs/

RUN jdk=$(update-java-alternatives -l | tr -s ' ' | cut -d' ' -f 3)
RUN export JAVA_HOME=$jdk/jre
RUN export CATALINA_PID=${tomcat_home}/temp/tomcat.pid
RUN export CATALINA_PID=${tomcat_home}/temp/tomcat.pid
RUN export CATALINA_HOME=${tomcat_home}
RUN export CATALINA_BASE=${tomcat_home}
RUN export 'CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
RUN export 'JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

RUN sh ${tomcat_home}/bin/startup.sh