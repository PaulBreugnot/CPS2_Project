# Mosquitto broker configuration

The `conf` folder contains `mosquitto.conf` and `mosquitto.acl` files used in our project.

Both are located in the `/etc/mosquitto` folder of the server.

## mosquitto.conf

In this file, you could eventually modify :
- log parameters
- `allow_anonymous` : for simplicity reasons, we used anonymous connections, but of course **it's not safe**!
- used port for tcp and WebSocket protocols.

## mosquitto.acl

Here you can setup your topics, that will be used by modules and the Spring Boot Dataflow app to publish / gather data. So if you change them, you will need to reconfigure them also there.

Notice the usage of `+` wildcards to replace modules ids and measure types.
