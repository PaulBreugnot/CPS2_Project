#  *  17/01/2019 
#  *  Paul B.
#  *  Chloé G.
#  *  Nicolas L.
#  *  Loïc C.
#  *  Master CPS2 based on  :

# Copyright (c) 2014 Adafruit Industries
# Author: Tony DiCola

# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:

# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.

# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
import sys
import time
from datetime import datetime
import Adafruit_DHT
import paho.mqtt.client as mqttClient

Connected = False #global variable for the state of the connection

def on_connect(client, userdata, flags, rc):
 
    if rc == 0:
        print("Connected to broker")
        global Connected                #Use global variable
        Connected = True                #Signal connection 
 
    else:
 
        print("Connection failed")
 
topic = "emse/fayol/e0/itm/sensors/-1/metrics/"
broker_address= "ec2-54-236-113-5.compute-1.amazonaws.com"
port = 1883
user = "yourUser"
password = "yourPassword"


 
client1 = mqttClient.Client(topic+"TMP")               #create new instance
client2 = mqttClient.Client(topic+"HMDT")               #create new instance
client3 = mqttClient.Client("connected")               #create new instance

#client1.username_pw_set(user, password=password)    #set username and password
client1.on_connect= on_connect                      #attach function to callback
client1.connect(broker_address, port=port)          #connect to broker

#client2.username_pw_set(user, password=password)    #set username and password
client2.on_connect= on_connect                      #attach function to callback
client2.connect(broker_address, port=port)          #connect to broker

#client3.username_pw_set(user, password=password)    #set username and password
client3.on_connect= on_connect                      #attach function to callback
client3.connect(broker_address, port=port)          #connect to broker
 
client1.loop_start()        #start the loop
client2.loop_start()        #start the loop
client3.loop_start()        #start the loop

client3.publish("connected",topic+"TMP")
client3.publish("connected",topic+"HMDT")

while Connected != True:    #Wait for connection
    time.sleep(0.1)
 
try:
    # Parse command line parameters.
    sensor_args = { '11': Adafruit_DHT.DHT11,
                    '22': Adafruit_DHT.DHT22,
                    '2302': Adafruit_DHT.AM2302 }
    if len(sys.argv) == 3 and sys.argv[1] in sensor_args:
        sensor = sensor_args[sys.argv[1]]
        pin = sys.argv[2]
    else:
        print('Usage: sudo ./Adafruit_DHT.py [11|22|2302] <GPIO pin number>')
        print('Example: sudo ./Adafruit_DHT.py 2302 4 - Read from an AM2302 connected to GPIO pin #4')
        sys.exit(1)

    # with open('/home/pi/document.csv','a') as fd:
    while (1):
        humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
        myCsvRow=str(datetime.now())+","
        if humidity is not None and temperature is not None:
            myCsvRow+=str(temperature) + ','+ str(humidity)
            print(myCsvRow)

            client1.publish(topic+"TMP",str(temperature))
            client2.publish(topic+"HMDT",str(humidity))
        else:
            print('Failed to get reading!')
        # fd.write(myCsvRow)
        time.sleep(1)

except KeyboardInterrupt:
 
    client1.disconnect()
    client1.loop_stop()
    client2.disconnect()
    client2.loop_stop()
    client3.disconnect()
    client3.loop_stop()