<template>
    <div id="app">
        <div>
            <a-button type="primary">按钮</a-button>
        </div>
        <h2>Grid 栅格</h2>
        <div>
            <a-row>
                <a-col :span="12">col-12</a-col>
                <a-col :span="12">col-12</a-col>
            </a-row>
            <a-row class="demo-class">
                <a-col :span="8">col-8</a-col>
                <a-col :span="8">col-8</a-col>
                <a-col :span="8">col-8</a-col>
            </a-row>
            <a-row class="demo-class">
                <a-col :span="6">col-6</a-col>
                <a-col :span="6">col-6</a-col>
                <a-col :span="6">col-6</a-col>
                <a-col :span="6">col-6</a-col>
            </a-row>
        </div>
    </div>
</template>

<script>
    import HelloWorld from './components/HelloWorld.vue'

    export default {
        name: 'app',
        components: {
            HelloWorld
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>

<style scoped>
    .ant-col-12, .ant-col-6 {
        padding: 30px 0;
        margin: 2px 0;
        background: rgba(0, 160, 233, 0.7);
    }

    .ant-col-8, .ant-col-4 {
        background: #00a0e9;
        padding: 30px 0;
        margin: 2px 0;
    }

</style>
