$(document).ready(function () {

    var Admin = {
        init: function () {
            this.selectLeftTab();
        },
        selectLeftTab: function () {
            var pathname = location.pathname;
            var as = $(".nav-sidebar").find("li").find("a");
            for (var i = 0; i < as.length; i++) {
                var a = as[i];
                var href = $(a).attr("href");
                if (pathname === href && pathname.indexOf(href) !== -1){
                    $(a).parent().addClass("active");
                }
            }
        }
    };


    Admin.init();

});