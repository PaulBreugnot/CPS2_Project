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
