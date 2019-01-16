# CPS2_Project

# MQTT Broker
## Available topics
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/LUMI`
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/HMDT`
- `emse/fayol/e0/itm/sensors/{id_module}/metrics/TMP`
- `emse/fayol/e0/itm/sensors/{id_module}/request`
- `emse/fayol/e0/itm/sensors/{id_module}/answers/{command}`

## Useful commands
### Subscribe
`mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic]`

### Publish
`mosquitto_pub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t [topic] -m [message]`

# PostgreSQL Database
## Connect using psql
`psql -h cps2projectdb.cyppypwyycpk.us-east-1.rds.amazonaws.com -d cps2_project -U cps2_admin`

## Tables declarations
SQL dumps are available in psql/dumps

## Doc
HTML Doc is available in psql/doc
