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
Mosquitto allow us to easily setup an MQTT broker. This tool is available for a lot of distribution. You can find the one that fits your needs on the (mosquitto website)[https://mosquitto.org/download/].

## Mosquitto as a service
Because we want to launch it on a server, it is better to launch mosquitto *as a service*, to be able to launch mosquitto at boot for example, and have a better logs management.

### Config and ACL
With this setup, mosquitto can be configured with the `/etc/mosquitto/mosquitto.conf` file.

Available topics can also be configured in a `/etc/mosquitto/mosquitto.acl` file.

Find our configuration files and more explanations about it (=> insert link <=)[].

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
## Connect using psql
`psql -h cps2projectdb.cyppypwyycpk.us-east-1.rds.amazonaws.com -d cps2_project -U cps2_admin`

## Tables declarations
SQL dumps are available in psql/dumps

## Doc
HTML Doc is available in psql/doc
