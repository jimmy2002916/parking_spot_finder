# Parking Lot Finder
This project is a streaming data pipeline project, which is aiming to help us to find a real-time available parking lot spot. 

# Features
Project is built for handling real-time streaming data pipelines. The data first get collected by Kafka, then flow down to the Flink to do some data transformation, and then sink it to the Mysql. 

On top of the main functionality this project has, the project also uses AWS S3 as one of its data stores, for the purpose of analysing the parking lot usages. With the features of both Flink and Kafka have, we can have more flexibility and options to use data efficiently. It also allows us to make the pipeline more scalable and extendable if needed. 

# Motivation
Parking Lot is not easy to find here in Taiwan, especially in Taipei. Having a system that provide the functionality this project has is an exciting things for people who is suffering from looking for parking lot daily. Meanwhile, it is not that easy to handle real-time streaming data properly and perfectly. As a result, the challenge this project has is to be designed well enough for an operator to maintain easily and user friendly. Components included in this project is used as minimum as possible, for the sake of simplicity and debug-friendly.

### Components
Parking Lot Finder uses a number of open source projects to work properly, below is the version I use:
* Kafka - 2.4.0
* Flink - 1.9.1
* ZooKeeper - 3.4.14
* Mysql - 5.7.22
* Hbase - 1.4.10
* Hive - 2.3.6
* Hadoop - 2.8.5
* Spark - 2.4.4
* AWS S3
* php

### Specification of Environmets
The whole application is build on the AWS. You have to have an AWS account to run this project. 

The Flink Application is running on AWS EMR 5.29.0(master: c4.8xlarge, core: c4.8xlarge). The Kafka is built on AWS EC2 version 2(c4.4xlarge). 

| Application   | Instance Type | Version           |
| ------------- |:-------------------------------:| -----------------:|
| Kafka         | c4.4xlarge                      | AWS EMR 5.29.0    |
| Flink         | c4.8xlarge(1 master, 1 core)    | AWS EC2 version 2 |

# Getting Startted
## Installation
You need to have java installed. 
```sh
$ sudo yum -y install java-1.8.0-openjdk*
```
install Kafka server.
```sh
$ sudo easy_install pip
$ pip install kafka-python
$ wget http://ftp.jaist.ac.jp/pub/apache/kafka/2.4.0/kafka_2.12-2.4.0.tgz
$ tar -xzf kafka_2.12-2.4.0.tgz 
$ cd kafka_2.12-2.4.0
```
## Run Application
### Start Kafka Server
start Zookeeper
```sh
echo "stop zookeeper first then start zookeeper ..."
$ bin/zookeeper-server-stop.sh config/zookeeper.properties
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```
To prevent the 
```sh
Exception in thread “Thread-0” org.apache.kafka.common.KafkaException: Failed to construct kafka producer
```
Try with the following command,
```sh
sudo sh -c "ulimit -n 65535 && exec su $LOGNAME"
```
To start the Kafka server, you should change your host name before start it. Please refer to the [link](https://blog.csdn.net/caoshangpa/article/details/79899419) for more details. You should also open the port in your security group if you are using AWS. Set the port of 9092 and 2181 to be accessible at anywhere.
```sh
$ cd ~
$ vim /etc/hosts
your_ip_here ip-xxx-xxx-xxx-xxx  localhost
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
::1         localhost6 localhost6.localdomain6 
```
After saving your file, go back to where your kafka installed, and start the Kafka server.
```sh
bin/kafka-server-start.sh config/server.properties
```
Create the topic if it dosn't exists
```sh
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic web_access
```
Start the consumer, so you can find out if producer, broker is working properly
```sh
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic web_access --from-beginning
```
### Start the producer
Run the python file to produce data to Kafka broker. 
```sh
$ python producer_streaming_data.py
```
### Start the Flink Application
Before you run this line, ensure you have an EMR running for you.
```sh
$ /usr/bin/flink run -m yarn-cluster -yn 3 -yjm 1024 -ytm 4096 parking_lot_streaming_data.jar
```
## Build a FrontEnd
This is the [page](https://github.com/durgesh-sahani/google-map-php-mysql) I refer to build a frontend. 
### How to show parking lot spot on the google map?
1. Run the "parking_lot_php_mysql_google_map", you might need to change the info of DB in the "DbConnect.php". You also need to create database, and table beforehead.
2. Config phpmyadmin to connect with aws rds before you use xampp to run this frontend file. https://github.com/andrewpuch/phpmyadmin_connect_to_rds https://scottontechnology.com/connect-to-amazon-rds-mysql-with-phpmyadmin/ 
```sh
$ cd /opt/lampp/phpmyadmin 
$ vim config.inc.php
```
3. Open the google map where should you put your frontend project file at: https://timetoprogram.com/run-php-project-xampp/ 
4. how to start and restart XAMPP [Link](https://medium.com/@RahulShukla754/amazon-ec2-setup-with-ubuntu-and-xampp-installation-37c3c0eeb51d)
5. how to get google api key. [Link](https://developers.google.com/maps/documentation/javascript/get-api-key)
```sh
$ yum -y install git-all 
$ cd /opt/lampp/htdocs
$ git clone https://github.com/jimmy2002916/parking_lot_php_mysql_google_map.git // make some necessary changes here, or put "parking_lot_php_mysql_google_map" folder under "htdocs"
$ sudo /opt/lampp/lampp restart
```
6. Open http://your_aws_ec2_public_ip_here/parking-lot/ (or without http) then you should be able to see the map on the website
![Available Parking Lot in New Taipei City, Taiwan](https://github.com/jimmy2002916/parking_lot_streaming_data/blob/master/Screen%20Shot%202020-03-31%20at%202.31.14%20PM.png)

#### Credits

Here is the [link](https://stackoverflow.com/questions/3004811/how-do-you-run-multiple-programs-in-parallel-from-a-bash-script) showing you how to run multiple programs in the same script.
Here is the [link](https://medium.com/@jimmy2002916/problems-that-you-might-run-into-with-kafka-flink-mysql-and-hbase-5192c403b36a) that might be helpful to you. This record most of the error I ran into while building this project. 
