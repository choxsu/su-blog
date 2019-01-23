/**
 * 存放当前页面环境变量
 * @type {{ueditor: null, deleteTarget: null}}
 */
var env = {
    ueditor: null,
    deleteTarget: null
};

/**
 * 显示工具
 * @type {{showAjaxActionMsg: ShowUtil.showAjaxActionMsg, showFailMsg: ShowUtil.showFailMsg}}
 */
var ShowUtil = {
    showOk: function (msg) {
        layer.msg(msg);
    },
    showFailMsg: function (msg, callback) {
        layer.msg(
            msg
            , {
                shift: 6
                , shade: 0.4
                , time: 0
                , offset: "140px"
                , closeBtn: 1
                , shadeClose: true
                , maxWidth: "1000"
            }
            , callback
        );
    },
    showAjaxActionMsg: function (shift, msg) {
        layer.msg(msg, {
                shift: shift
                , shade: 0.4
                , time: 3000
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
                        $.pjax.reload('#pjax-container', {});
                    }
                }
            }
        );
    }

};
/**
 * 后台相关业务
 * @type {{sendPjax: Admin.sendPjax, nprogressStart: Admin.nprogressStart, nprogressDone: Admin.nprogressDone, ajaxAction: Admin.ajaxAction, confirmAjaxAction: Admin.confirmAjaxAction, selectLeftTab: Admin.selectLeftTab, deleteArticle: Admin.deleteArticle}}
 */
var Admin = {
    nprogressStart: function () {
        NProgress.configure({showSpinner: false}).start();
    },
    nprogressDone: function () {
        NProgress.done();
    },
    selectLeftTab: function () {
        var pathname = location.pathname;
        $(".nav-sidebar li a[href]").each(function (index, element) {
            var href = $(element).attr("href");
            if (pathname === '/admin' || pathname === '/admin/') {
                $(".main-menu[home='true']").addClass("home-active");
                return false;
            }
            if (href !== '/admin' && pathname.indexOf(href) >= 0) {
                var currentMenu = $(".nav-sidebar li a[href='" + href + "']");
                currentMenu.parent().addClass("active");
                return false;
            }
            0
        })
    },
    sendPjax: function (url, container, extData) {
        $.pjax({
            url: url,
            container: container,
            extData: extData
        });
    },
    deleteArticle: function (event) {
        event.preventDefault();
        var $this = $(this);
        env.deleteTarget = $this.closest("tr");	// 传递给 showAjaxActionMsg() 删除一行数据
        var action = $this.attr("data-action");
        var title = $this.attr("data-title");
        Admin.confirmAjaxAction("确定删除【" + title + "】?删除后无法恢复！", action);
    },
    confirmAjaxAction: function (msg, operationUrl) {
        layer.confirm(msg, {
            icon: 0
            , title: ''                                    // 设置为空串时，title消失，并自动切换关闭按钮样式，比较好的体验
            , shade: 0.4
            , offset: "139px"
        }, function (index) {                            // 只有点确定后才会回调该方法
            // location.href = operationUrl;     // 操作是一个 GET 链接请求，并非 ajax
            Admin.ajaxAction(operationUrl);
            layer.close(index);                           // 需要调用 layer.close(index) 才能关闭对话框
        });
    },
    ajaxAction: function (url, before, complete) {
        $.ajax(url, {
            type: "GET"
            , cache: false
            , dataType: "json"
            // , data: {	}
            , beforeSend: function () {
                var type = typeof before;
                if (type === 'function') {
                    before();
                }
            }
            , error: function (ret) {
                alert(ret.statusText);
            }
            , success: function (ret) {
                if (ret.state === "ok") {
                    ShowUtil.showAjaxActionMsg(0, ret.msg);
                } else {
                    ShowUtil.showAjaxActionMsg(6, ret.msg);
                }
            }, complete: function () {
                var type = typeof complete;
                if (type === 'function') {
                    complete();
                }
            }
        });
    },
};

/**
 * 菜单业务处理
 * @type {{clickMenu: Menu.clickMenu}}
 */
var Menu = {
    clickMenu: function (event) {
        event.preventDefault();	// 取代 return false 防止页面跳转
        var $this = $(this);
        var url = $this.attr("href");
        var home = $this.attr("home");
        if (home){
            $(".nav-sidebar li").removeClass("active");
            $this.addClass("home-active");
        } else {
            // 设置当前选中菜单样式
            $(".nav-sidebar li").removeClass("active");
            $(".main-menu").removeClass("home-active");
            $this.parent().addClass("active");
        }
        Admin.sendPjax(url, "#pjax-container");

    },
    clickMainMenu: function (event) {
        event.preventDefault();	// 取代 return false 防止页面跳转
        var $this = $(this);
        var subMenu = $this.next(".nav-sidebar");
        console.log(subMenu);
        $(subMenu).slideToggle("fast");

        var i = $this.children(".right-icon");
        if (i.hasClass("fa-angle-down")) {
            i.removeClass("fa-angle-down");
            i.addClass("fa-angle-left");
        } else {
            i.removeClass("fa-angle-left");
            i.addClass("fa-angle-down");
        }
    }
};

/**
 * 状态图标业务
 * @type {{resetMagicInput: Magic.resetMagicInput, initMagicInput: Magic.initMagicInput}}
 */
var Magic = {

    initMagicInput: function (prepareAction) {
        var magicInput = $("input.mgc-switch,input.mgc");
        // 锁定开关绑定事件
        magicInput.on("click", function (event) {
            var $this = $(event.target);	// 或者 $(this)
            var state = $this.get(0).checked ? true : false;
            var action = prepareAction($this, state);

            $.ajax(action.url, {
                type: "POST"
                , cache: false
                , dataType: "json"
                , data: action.data
                , error: function (ret) {
                    alert(ret.statusText);
                    Magic.resetMagicInput($this);
                }
                , success: function (ret) {
                    if (ret.state === "fail") {
                        ShowUtil.showFailMsg(ret.msg);
                        Magic.resetMagicInput($this);
                    }
                }
            });
        });
    },
    resetMagicInput: function ($checkbox) {
        var checkbox = $checkbox.get(0);
        checkbox.checked = !checkbox.checked;
    }
};


$(document).ready(function () {
    Admin.nprogressStart();
    // 绑定首页菜单事件
    $(".main-menu[home='true']").bind("click", Menu.clickMenu);
    // 绑定主菜单事件
    $(".main-menu[home='false']").on("click", Menu.clickMainMenu);
    // 绑定首页菜单事件
    $(".nav-sidebar li a").bind("click", Menu.clickMenu);
    // table 操作栏按钮绑定 click 事件，动态添加的元素必须使用 $(...).on(...) 才能绑定
    $('#pjax-container').on("click", "a[data-delete]", Admin.deleteArticle);
    // pjax timeout 配置
    $.pjax.defaults.timeout = 5000;

    // data-pjax 属性与 a 标签组合选择器绑定 pjax，例如分页链接、操作按钮
    $(document).pjax('[data-pjax] a, a[data-pjax]', '#pjax-container');

    // 进度条效果
    $(document).on('pjax:start', function () {
        Admin.nprogressStart()
    });
    $(document).on('pjax:end', function () {
        Admin.nprogressDone()
    });
    //页面加载完成关闭进度条
    $(window).load(function () {
        Admin.nprogressDone();
    });

    //选中当前菜单
    Admin.selectLeftTab();
});
