var Single = {
    init: function () {
        this.zooming();
    },
    zooming: function () {//图片放大处理
        var img = document.getElementsByClassName("article-content")[0].getElementsByTagName("img");
        for (var i = 0; i < img.length; i++) {
            var imgElement = img[i];
            var zooming = new Zooming({
                //回调函数
                //onBeforeOpen: function () {
                //onOpen: function () {
                //onBeforeClose: function () {
                //onClose: function () {
                scaleBase: 0.5
            });
            zooming.listen(imgElement);
        }
    }
};

//初始化
Single.init();