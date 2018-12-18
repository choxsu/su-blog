var Detail = {
    init: function () {
        this.zooming();
        this.showTopImg();
        this.returnTop();
    },
    showTopImg: function () {
        window.onscroll = function () {
            var t = document.documentElement.scrollTop || document.body.scrollTop;
            var top = document.getElementsByClassName("return-top")[0];
            if (t > 500) {
                top.style.display = "block";
            } else {
                top.style.display = "none";
            }
        }
    },
    returnTop: function () {
        var top = document.getElementsByClassName("return-top")[0];
        top.onclick = function () {
            var scrollTopTimer = setInterval(function () {
                var top = document.body.scrollTop || document.documentElement.scrollTop;
                var speed = top / 4;
                if (document.body.scrollTop !== 0) {
                    document.body.scrollTop -= speed;
                } else {
                    document.documentElement.scrollTop -= speed;
                }
                if (top === 0) {
                    clearInterval(scrollTopTimer);
                }
            }, 30);
        }
    },
    // 图片放大处理
    zooming: function () {
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