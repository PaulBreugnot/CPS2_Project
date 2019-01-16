// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Graph from './Graph'
import router from './router'

import MenuScrolling from './MenuScrolling.vue'

import 'vue-tree-halower/dist/halower-tree.min.css'
import {VTree, VSelectTree} from 'vue-tree-halower'

Vue.use(VTree)
Vue.use(VSelectTree)

Vue.config.productionTip = false

new Vue({
  render: h => h(MenuScrolling)
}).$mount('#menuScrolling')

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#graph',
    router,
    components: {
        Graph
    },
    template: '<Graph/>'
})