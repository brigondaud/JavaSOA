#!/bin/bash

sudo apt-get update

wget http://apache.mirror.gtcomm.net/cassandra/3.11.3/apache-cassandra-3.11.3-bin.tar.gz

tar -xvzf apache-cassandra-3.11.3-bin.tar.gz

cd apache-cassandra-3.11.3

bin/cassandra