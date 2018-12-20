$(document).ready(function () {
    var pathname = location.pathname;
    var as = $(".nav-sidebar").find("li").find("a");
    for (var i = 0; i < as.length; i++) {
        var a = as[i];
        if ($(a).attr("href") === pathname){
            $(a).parent().addClass("active");
        }
    }
});