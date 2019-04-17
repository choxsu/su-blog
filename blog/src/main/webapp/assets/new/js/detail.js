var Detail = {
    init: function () {
        this.zooming();
        this.showTopImg();
        this.returnTop();
        this.appendShareUrl();
    },
    appendShareUrl: function () {
        var as = document.getElementsByClassName("article-share");
        var aList = as[0].getElementsByTagName("a");
        for (var i = 0; i < aList.length; i++) {
            var src = aList[i].getAttribute("href");
            src = src + window.location;
            aList[i].setAttribute("href", src);
        }
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
        var img = document.getElementById("content-id").getElementsByTagName("img");
        for (var i = 0; i < img.length; i++) {
            var imgElement = img[i];
            var zooming = new Zooming({
                scaleBase: 1,
                preloadImage: false,
                bgColor: '#f7f7f7'
            });
            zooming.listen(imgElement);
        }
    }
};

//初始化
Detail.init();