sudo apt-get update
apt-get install -y curl

curl -O http://apache.mirror.gtcomm.net/cassandra/3.11.3/apache-cassandra-3.11.3-bin.tar.gz

tar -xvzf apache-cassandra-3.11.3-bin.tar.gz

cd apache-cassandra-3.11.3

bin/cassandra