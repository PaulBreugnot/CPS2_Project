# CPS2_Project

# MQTT Broker
## Available topics
- DONE `emse/fayol/e0/itm/sensors/{id_module}/metrics/LUMI`
- DONE `emse/fayol/e0/itm/sensors/{id_module}/metrics/HMDT`
- DONE `emse/fayol/e0/itm/sensors/{id_module}/metrics/TMP`
- DONE `connected` when a new board is connecting, it sends the new topic available
- TODO `emse/fayol/e0/itm/sensors/{id_module}/request`
- TODO `emse/fayol/e0/itm/sensors/{id_module}/answers/{command}`

## Useful commands
### Subscribe
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic]`
Examples:
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t emse/fayol/e0/itm/sensors/[-1 / -2 / -3]/metrics/[TMP / HDMT / LUMI]`
- -1 raspberry
- -2 mkrWIFI1010
- -3 mkrWIFI1010

### Publish
`mosquitto_pub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic] -m [message]`

# mkr1010
## Connect to wifi 
- change `wifi_ssid`
- change `wifi_password`
## Connect to mqtt_host 
- change `mqtt_host`
- change `mqtt_port`
- uncomment mqtt_user & mqtt_password can be needed also in the mqtt_client.connect
## choose the topic
- change `topic`
- change also `mqtt_client.connect("??")`
- update and select the type of sensor : TEMP HMDT LUMI ...

## select the number frequence of measurment
- `#define DELTA_T`
- to ease the data analysing, we publish with the frequency given by DELTA T and not when the value has changed significantly since the last publishing.

# raspberry
## Useful CLI
- `sudo raspi-config`
- `scp document.csv loic@192.168.1.17:/home/loic/Desktop`
- `sudo apt-get install screen` : The screen let’s you initiate a process in the background as an independent process. So, you can close the terminal/SSH connection or anything at all, the screen will be running the script in the background.
- `pip3 install paho-mqtt`
- `apt-get install mosquitto`
- `apt-get install mosquitto-clients`
## AdafruitDHT3.py
- Connect to wifi
- Connect to mqtt_host
## superscript : lunch the programm using ssh
- make it chmod 777


# PostgreSQL Database
## Connect using psql
`psql -h cps2projectdb.cyppypwyycpk.us-east-1.rds.amazonaws.com -d cps2_project -U cps2_admin`

## Tables declarations
SQL dumps are available in psql/dumps

## Doc
HTML Doc is available in psql/doc
