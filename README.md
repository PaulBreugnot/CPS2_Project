# CPS2_Project

# MQTT Broker
## Available topics
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/LUMI`
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/HMDT`
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/TMP`
- `emse/fayol/e0/itm/sensors/{id_module}/request`
- `emse/fayol/e0/itm/sensors/{id_module}/answers/{command}`$

## Useful commands
### Subscribe
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic]`

### Publish
`mosquitto_pub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic] -m [message]`


# mkr1010
## Connect to wifi 
- `change wifi_ssid`
- `change wifi_password`
## Connect to mqtt_host 
- `change mqtt_host`
- `change mqtt_port`
- `uncomment mqtt_user & mqtt_password is needed also in the mqtt_client.connect`
## choose the topic
- `change topic`
- `update and select the type of sensor : TEMP HMDT LUMI ...`
## select the number frequence of measurment
- `#define DELTA_T`

# raspberry
## Useful CLI
- `scp document.csv loic@192.168.1.17:/home/loic/Desktop`
- `sudo apt-get install screen : The screen let’s you initiate a process in the background as an independent process. So, you can close the terminal/SSH connection or anything at all, the screen will be running the script in the background.`
- `pip3 install paho-mqtt`
- `apt-get install mosquitto`
- `apt-get install mosquitto-clients`
## AdafruitDHT3.py
- `Connect to wifi `
- `Connect to mqtt_host `
## superscript : lunch the programm after log in


# PostgreSQL Database
## Connect using psql
`psql -h cps2projectdb.cyppypwyycpk.us-east-1.rds.amazonaws.com -d cps2_project -U cps2_admin`

## Tables declarations
SQL dumps are available in psql/dumps

## Doc
HTML Doc is available in psql/doc
