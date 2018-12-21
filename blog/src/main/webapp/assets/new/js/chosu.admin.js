$(document).ready(function () {
    var pathname = location.pathname;
    console.log(pathname)
    console.log("================")
    var as = $(".nav-sidebar").find("li").find("a");
    for (var i = 0; i < as.length; i++) {
        var a = as[i];
        var href = $(a).attr("href");
        /*console.log(href)
        console.log(pathname.substring(0,href.length).indexOf(href));
        console.log("--------------")*/
        if (pathname === href && pathname.indexOf(href) !== -1){
            $(a).parent().addClass("active");
        }
    }
});