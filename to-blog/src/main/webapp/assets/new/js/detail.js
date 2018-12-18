var Detail = {
    init: function () {
        this.zooming();
    },
    zooming: function () {//图片放大处理
        var img = document.getElementsByClassName("article-content")[0].getElementsByTagName("img");
        for (var i = 0; i < img.length; i++) {
            var imgElement = img[i];
            var zooming = new Zooming({
                scaleBase: 1,
                preloadImage: true,
                bgColor: 'rgb(205, 255, 255)'
            });
            zooming.listen(imgElement);
        }
    }
};

//初始化
Detail.init();