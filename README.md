# CPS2_Project
## Project architecture
This project is constituted by different components, that work more or less independently. In our case, we chose AWS to host and access them, but all of them can be install on common Linux architectures, with a few setup modifications.

This guide will present the structure of each component and how to configure them.

- Arduino / Raspberry Pi modules with temperature, humidity and luminosity sensors.
- A mosquitto MQTT broker.
- A PostgreSQL database, that store informations about rooms and sensors and the collected data.
- A MapServer, that provides a map display.
- A Spring Boot app, also embedded in the server, that collect published data through MQTT and store them in the database.
- A WebApp and its Node.js app to perform map and data display.
- A static RDF Graph generated using R2RML, accessible from a Fuseki SparQL endpoint.

# Modules
This section describes how to configure Arduino and RPi in our context. Notice that any system could be used, given that they can publish data using MQTT.

## Arduino mkr1010
### Connect to wifi 
- change `wifi_ssid`
- change `wifi_password`
### Connect to mqtt_host 
- change `mqtt_host`
- change `mqtt_port`
- uncomment mqtt_user & mqtt_password can be needed also in the mqtt_client.connect
### choose the topic
- change `topic`
- change also `mqtt_client.connect("??")`
- update and select the type of sensor : TEMP HMDT LUMI ...

### select the number frequence of measurment
- `#define DELTA_T`
- to ease the data analysing, we publish with the frequency given by DELTA T and not when the value has changed significantly since the last publishing.

## Raspberry Pi
### Useful CLI
- `sudo raspi-config`
- `scp document.csv loic@192.168.1.17:/home/loic/Desktop`
- `sudo apt-get install screen` : The screen let’s you initiate a process in the background as an independent process. So, you can close the terminal/SSH connection or anything at all, the screen will be running the script in the background.
- `pip3 install paho-mqtt`
- `apt-get install mosquitto`
- `apt-get install mosquitto-clients`
### AdafruitDHT3.py
- Connect to wifi
- Connect to mqtt_host
### superscript : lunch the programm using ssh
- make it chmod 777


# MQTT Broker
## Installation
Mosquitto allow us to easily setup an MQTT broker. This tool is available for a lot of distribution. You can find the one that fits your needs on the [mosquitto website](https://mosquitto.org/download/).

## Mosquitto as a service
Because we want to launch it on a server, it is better to launch mosquitto *as a service*, to be able to launch mosquitto at boot for example, and have a better logs management.

### Config and ACL
With this setup, mosquitto can be configured with the `/etc/mosquitto/mosquitto.conf` file.

Available topics can also be configured in a `/etc/mosquitto/mosquitto.acl` file.

Find our configuration files and more explanations about it [in the mqtt folder](https://github.com/PaulBreugnot/CPS2_Project/tree/master/mqtt).

### Run mosquitto

On Ubuntu as on many other Linux systems today, mosquitto can then be launch using `systemctl start mosquitto` .

## Available topics
- [x] `test_topic`
- [x] `emse/fayol/e0/itm/sensors/{id_module}/metrics/LUMI`
- [x] `emse/fayol/e0/itm/sensors/{id_module}/metrics/HMDT`
- [x] `emse/fayol/e0/itm/sensors/{id_module}/metrics/TMP`
- [x] `connected` (when a new module is connecting, it sends the new topic available)
- [ ] `disconnected` (should be used as will-topic by modules to warn that they disconnected) 
- [ ] `emse/fayol/e0/itm/sensors/{id_module}/request`
- [ ] `emse/fayol/e0/itm/sensors/{id_module}/answers/{command}`

## Useful commands
###
For testing purposes, [mosquitto_pub](https://mosquitto.org/man/mosquitto_pub-1.html) and [mosquitto_sub](https://mosquitto.org/man/mosquitto_sub-1.html)can be used.

### Subscribe
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic]`
Examples:
- Subscribe to a particular data topic : `mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t emse/fayol/e0/itm/sensors/[-1 / -2 / -3 / ...]/metrics/[TMP / HDMT / LUMI]`

Where -1 / -2 / -3 / ... correspond to your module ids. In our case :
- -1 raspberry
- -2 mkrWIFI1010
- -3 mkrWIFI1010
- ...

- Subscribe to all data topics :
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t emse/fayol/e0/itm/sensors/[-1 / -2 / -3 / ...]/metrics/[TMP / HDMT / LUMI]`

### Publish
`mosquitto_pub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic] -m [message]`

# PostgreSQL Database

A complete description of our database schema can be found (=> insert link <=)[].

PSQL dumps are also available in [psql/dumps](https://github.com/PaulBreugnot/CPS2_Project/tree/master/psql/dumps) if you want to easily reproduce our structure.

## Host your DB

We used (AWS RDS)[https://aws.amazon.com/rds/] to host our database.

However, it is quite simple to host a PostgreSQL database on many systems. Check the documentation relative to your distribution to see how.

### PostGIS

The only extra step is that you need to install and enable the PostGIS module in order to be able to work with geographical data.

To do so, a PostGIS package might be available, depending on your distribution.

Once install, the extension can easily be enable using [pgAdmin](https://www.pgadmin.org/), in the `extensions` menu.

![PGADMIN extension menu](/psql/pgadmin_extensions.png)

Check the [PostGIS documentation](https://postgis.net/install/) if you need more information about how to install PostGIS.

## Connect using psql
`psql -h cps2projectdb.cyppypwyycpk.us-east-1.rds.amazonaws.com -d cps2_project -U cps2_admin`

# MapServer

## Installation

Installing the MapServer can be quite tricky and very platform dependent. However, we currently don't have better source than the [MapServer documentation](https://mapserver.org/introduction.html) to help you!

## Configuration

The MapServer will :
- Access our PostgreSQL database to retrieve geographical sensor and room informations. The database connection and SQL querries are set up directly in the MapFile. Our example is available [there](https://github.com/PaulBreugnot/CPS2_Project/tree/master/mapserver). You should configure it with your own database information, and store it in a directory accessible by the Apache server that you should have install and run along with the MapServer.
- The WebApp will access the MapFile through the MapServer thanks to the URL that you should configure [insert where].

# Spring Boot Dataflow
We developed a Spring Boot application, called **dataflow**, that can :
- Subscribe to data topics
- Structure all informations received on topics, and save them directly to the PostgreSQL database
- Provide lists of available rooms and sensors over a REST API (useful for our WebApp)

## Install and configure
A compiled .jar is available [there](https://github.com/PaulBreugnot/CPS2_Project/tree/master/dataflow/release).

The app can then be configured using a Spring property file. An example is available [there](https://github.com/PaulBreugnot/CPS2_Project/tree/master/dataflow/release/example.properties)

### Note
- If several instance of the app are running, don't forget to modify the `mqttconnection.client_id` for each instance, otherwise the client won't connect to the MQTT broker.
- You might change the `server.port` used for the REST API, in case it is already used.

You can then lauch a Dataflow instance using :
`java -jar dataflow-0.0.1-SNAPSHOT.jar --spring.config.location=file:/absolute/path/to/your/config/example.properties`

## Usage
- Data gathering and database feeding is completely automatic, once configured.
- You can perform the following GET queries :
  - `http://your.dataflow.host:8090/api/sensorlayers` : all sensor layers with associated sensors
  - `http://your.dataflow.host:8090/api/sensors` : get all available sensors
  - `http://your.dataflow.host:8090/api/sensors?layer=[id]` : get sensors in the specified sensor layer.

All outputs are returned in JSON.
  
## Dataflow as a Service
In order to run it on a server, it can be useful to run Dataflow as a service.
You can do so following [this tutorial](https://www.baeldung.com/spring-boot-app-as-a-service).

Using `systemd`, you should have something like

`ExecStart=/usr/bin/java -jar /your/path/to/dataflow-0.0.1-SNAPSHOT.jar --spring.config.location=file:/absolute/path/to/your/config/example.properties`

in a unit file `/etc/systemd/system/dataflow.service`, for common Linux distributions.

# RDF Graph

We used [R2RML](https://www.w3.org/TR/r2rml/) to generate an RDF graph from our SQL database, thanks to the [r2rml-parser implementation](https://github.com/nkons/r2rml-parser).

Two of our mapping files are available [there](https://github.com/PaulBreugnot/CPS2_Project/tree/master/r2rml/mapping) :
- `mapping.r2rml` can be seen as the *theoritical* mapping file, that can be used to map the complete graph from all the data.
- However, in pratice, the `observation` table can be so big that it becomes impossible to parse it directly, so `test.r2rml` is a version that does not generate the graph from the whole `observation` table. You can customize the amount of data used through the `sqlQuery` parameter at **line 203** in the `test.r2rml` file.

## Run r2rml-parser

To learn how to use `r2rml-parser`, please check the [r2rml-parser documentation](https://github.com/nkons/r2rml-parser/wiki/Getting-started).

Once install, set up your PostgreSQL database information, the `mapping.file` and the output parameters in the [r2rml.properties file](https://github.com/nkons/r2rml-parser/blob/master/r2rml.properties).

Finally you can execute the program using, for example :
`./r2rml-parser.sh -p r2rml.properties` to generate your RDF file.

We provide some [result examples](https://github.com/PaulBreugnot/CPS2_Project/tree/master/r2rml/results) ir RDF/XML and Turtle, for 1000 observations.

## Exploring the graph
### RDF Validator

You can use the [w3 RDF Validator](https://www.w3.org/RDF/Validator/) to perform some basic graph visualization of the generated RDF/XML.

### Jena Fuseki

To perform SparQL query on the generated graph, we used the Apache Jena Fuseki server. Follow [the documentation](https://jena.apache.org/documentation/fuseki2/) to learn how to set it up in the way you prefer.

Once done, you should be able to import the generated RDF files directly into Fuseki, and perform operations on it through the Fuseki UI.

Notice that we also experimented Fuseki as a service directly on our server, and it seems to work pretty well.

# Web App

The Web Application allows you to perform map and data visualization from a web browser.

To see how to configure and run it, check [this repository](https://github.com/PaulBreugnot/CPS2_Project/tree/master/webApp).
