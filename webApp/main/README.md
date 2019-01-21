# Web application

This part of the big project **Workshop context acquisition and representation in ITM’Factory** is design to have a graphical interface for the user and allow him to actually see the results of the others part of the project. You can find more information about this project [here](https://www.emse.fr/~picard/cours/cps2/cps2-projects.html#trues2-workshop-context-acquisition-and-representation-in-itm-factory).

This web app is a Vue.js client application, which works with [mqtt-realtime-chart-client](https://github.com/NickJokic/mqtt-realtime-chart-client) [vue-tree](https://github.com/halower/vue-tree) and [vue-scrollactive](https://github.com/eddiemf/vue-scrollactive).


## Features
### Map
+ displays a map, its layers and theirs sensors
+ not dynamic, need to reload the map if sensors are added

### Chart
+ displays all the sensors available, within their respective layers
+ displays the value of the selected sensors on a chart 

## Getting Started

### Prerequisites

Before you run the client, be sure you have these downloaded/installed on your machine:

+ node.js
+ npm
+ [the current project](https://github.com/PaulBreugnot/CPS_Project)

### Installing

To get started with this project, follow the 2-step installation, described here.

## 1. Web application* *\(this repo)\*

Open a new terminal window and navigate inside the root folder of the web application, then run:

```
npm install
```

```
npm start
```

After compilation, you will see the link in the terminal *(e.g. http://localhost:8081)* where the application is currently running. Use your browser to navigate to that link. 

If everything went smoothly, you should see a description of the global project, a map and a chart.


## Built With

### Client
* [Vue.js](https://github.com/vuejs/vue) - JavaScript framework for building UI on the web.
* [Rickshaw.js](https://github.com/shutterstock/rickshaw) - JavaScript toolkit for creating interactive real-time graphs
* [Socket.io-client](https://github.com/socketio/socket.io) - real-time bidirectional event-based communication using websockets (client)
* [Bootstrap](https://github.com/twbs/bootstrap) - HTML, CSS, and JavaScript framework for developing responsive, mobile first projects on the web.


## Authors

* **Nicolas LAGAILLARDIE** - [GitHub](https://github.com/NicolasLagaillardie)


## License

This project is licensed under the [EMSE](https://www.mines-stetienne.fr/) License and MIT License.

