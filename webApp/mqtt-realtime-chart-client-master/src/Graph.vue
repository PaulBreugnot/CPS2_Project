<template>
    <div id="graph">
        <router-view />
    </div>
</template>

<script>
    export default {
        name: 'Graph'
    }

</script>

<style>
    body {
        font-size: 18px;
        min-height: 100%;
    }

    #graph {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        padding: 20px;
        min-height: 100%;
    }

</style>
