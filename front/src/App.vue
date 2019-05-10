<template>
    <div id="app">
        <h3>bootstrap测试</h3>
        <div class="content-wrap ">
            <button type="button" class="btn btn-info" data-toggle="tooltip" data-placement="left" title="Tooltip on left">Tooltip on left</button><br/>
            <button type="button" class="btn btn-primary" data-toggle="tooltip" data-placement="top" title="Tooltip on top">Tooltip on top</button><br/>
            <button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom" title="Tooltip on bottom">Tooltip on bottom</button><br/>
            <button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="right" title="Tooltip on right">Tooltip on right</button><br/>
        </div>
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

    export default {
        name: 'app'
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
