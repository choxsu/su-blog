var Detail = {
    init: function () {
        this.zooming();
        this.showTopImg();
        this.returnTop();
    },
    showTopImg: function () {
        window.onscroll = function () {
            var t = document.documentElement.scrollTop || document.body.scrollTop;
            var top = document.getElementsByClassName("choxsu-return-top")[0];
            if (t > 500) {
                top.style.display = "block";
            } else {
                top.style.display = "none";
            }
        }
    },
    returnTop: function () {
        var top = document.getElementsByClassName("choxsu-return-top")[0];
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
        var img = document.getElementById("content_id").getElementsByTagName("img");
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


/**
 * 显示 share/feedback 的 reply 内容
 */
function showReplyContent() {
    var url = $(this).attr("data-url");
    $.ajax(url, {
        type: "GET"
        , cache: false
        , dataType: "json"
        , success: function (ret) {
            layer.msg(
                ret.reply.content
                , {
                    shift: 0
                    , shade: 0.4
                    , time: 0
                    , offset: "140px"
                    , closeBtn: 1
                    , shadeClose: true
                    , maxWidth: "650"
                }
            );
        }
    });
}

/**
 * 删除 share/feedback 的 reply 记录
 */
function deleteReply(_this, url) {
    confirmAjaxAction("确定删除?", url);
}


/**
 * 确认对话框层，点击确定才真正操作
 * @param msg 对话框的提示文字
 * @param operationUrl 点击确认后请求到的目标 url
 */
function confirmAjaxAction(msg, operationUrl) {
    layer.confirm(msg, {
        icon: 0
        , title: ''                                    // 设置为空串时，title消失，并自动切换关闭按钮样式，比较好的体验
        , shade: 0.4
        , offset: "139px"
    }, function (index) {                            // 只有点确定后才会回调该方法
        $.ajax(operationUrl, {
            type: "GET"
            , cache: false
            , dataType: "json"
            , beforeSend: function () {
            }
            , error: function (ret) {
                layer.msg(ret.statusText);
            }
            , success: function (ret) {
                if (ret.state === "ok") {
                    layer.msg("删除成功", {time: 2500, closeBtn: 1}, function () {
                        location.reload()
                    })
                } else {
                    showAjaxActionMsg(6, ret.msg);
                }
            }
        });
        layer.close(index);                           // 需要调用 layer.close(index) 才能关闭对话框
    });
}

/**
 * ajax 做通用的操作，不传递表单数据，仅传id值的那种
 */
function ajaxAction(url) {
    $.ajax(url, {
        type: "GET"
        , cache: false
        , dataType: "json"
        // , data: {	}
        , beforeSend: function () {
        }
        , error: function (ret) {
            alert(ret.statusText);
        }
        , success: function (ret) {
            if (ret.state === "ok") {
                location.reload()
                showAjaxActionMsg(0, ret.msg);
            } else {
                showAjaxActionMsg(6, ret.msg);
            }
        }
    });
}

function showAjaxActionMsg(shift, msg) {
    layer.msg(msg, {
            shift: shift
            , shade: 0.4
            , time: 0
            , offset: "140px"
            , closeBtn: 1
            , shadeClose: true
            , maxWidth: "1000"
        }, function () {
            if (shift !== 6) {
                if (env.deleteTarget) {
                    env.deleteTarget.remove();
                    env.deleteTarget = null;
                } else {
                    // location.reload();
                    $.pjax.reload('#pjax-container', {});
                }
            }
        }
    );
}

/**
 * share、feedback 详情页回复错误信息提示框，需要引入 layer.js
 *  news feed 模块的 replyNewsFeed(...) 也用到此方法，在演化时注意
 */
function showReplyErrorMsg(msg, toLogin) {
    layer.msg(msg, {
            shift: 6
            , shade: 0.4
            , time: 2000
            // , offset: "140px"
            , closeBtn: 1
            , shadeClose: true
            , maxWidth: "1000"
        }, function () {
            if (toLogin) {
                location.href = "/login?returnUrl=" + encodeURI(location.href) + "#replyContent"
            }
        }
    );
}


/**
 * share、feedback 详情页回复链接的 at 功能
 * 将 @at 填充到回复 textarea 中
 */
function atAndReply(nickName) {
    var replyContent = $('#replyContent');
    var content = replyContent.val() + "@" + nickName + " ";
    replyContent.val(content);
}

//share、feedback 详情页回复功能
function reply(url, articleId, map) {
    if (map.isLoading) {
        return;
    }

    $.ajax(url, {
        type: "POST"
        , cache: false
        , dataType: "json"
        , data: {
            articleId: articleId,
            replyContent: $('#replyContent').val()
        }
        , beforeSend: function () {
            map.isLoading = true;
            map.submit_btn.hide();
            map.submit_loading.show();
        }
        , success: function (ret) {
            if (ret.state === "ok") {
                $('#replyContent').val("");
                // 将新增的回复放在评论最前
                var $comments_list = $(".comments-list");
                var comments_item = $comments_list.find(".comments-item");
                if (comments_item.length === 0) {
                    $comments_list.append(ret.replyItem)
                } else {
                    comments_item.eq(0).before(ret.replyItem);
                }

            } else {
                showReplyErrorMsg(ret.msg, ret.toLogin);

            }
        }
        , complete: function () {
            map.submit_loading.hide();
            map.submit_btn.show();
            map.isLoading = false;
        }
    });
}

