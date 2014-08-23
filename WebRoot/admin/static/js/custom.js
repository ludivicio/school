/* JS */


/* Navigation */

$(document).ready(function() {

  $(window).resize(function() {
    if ($(window).width() >= 765) {
      $(".sidebar #nav").slideDown(350);
    } else {
      $(".sidebar #nav").slideUp(350);
    }
  });


  $("#nav > li > a").on('click', function(e) {
    if ($(this).parent().hasClass("has_sub")) {
      e.preventDefault();
    }

    if (!$(this).hasClass("subdrop")) {
      // hide any open menus and remove all other classes
      $("#nav li ul").slideUp(350);
      $("#nav li a").removeClass("subdrop");

      // open our new menu and add the open class
      $(this).next("ul").slideDown(350);
      $(this).addClass("subdrop");
    } else if ($(this).hasClass("subdrop")) {
      $(this).removeClass("subdrop");
      $(this).next("ul").slideUp(350);
    }

  });
});

$(document).ready(function() {
  $(".sidebar-dropdown a").on('click', function(e) {
    e.preventDefault();

    if (!$(this).hasClass("open")) {
      // hide any open menus and remove all other classes
      $(".sidebar #nav").slideUp(350);
      $(".sidebar-dropdown a").removeClass("open");

      // open our new menu and add the open class
      $(".sidebar #nav").slideDown(350);
      $(this).addClass("open");
    } else if ($(this).hasClass("open")) {
      $(this).removeClass("open");
      $(".sidebar #nav").slideUp(350);
    }
  });

});


$(window).resize(autosize);

$(document).ready(function() {
    autosize();
});

function autosize(){
    var webBodyHeight = getBodySize("h");
    
    var h = (webBodyHeight - 60);
    
    h = h < 400 ? 400 : h;

    $('.mainbar').css('height', (h + 10) + 'px');
}

/**
 * 获取网页的宽度和高度
 * @param  {string} data    需要的宽（w）或高（h）
 * @return 如果参数get存在，则返回相应宽或高，如果get没有写则返回数组
 */
function getBodySize(data) {
    var bodySize = [];
    bodySize['w'] = ($(document.body).width()>$(window).width())? $(document.body).width():$(window).width();
    bodySize['h'] = ($(document.body).height()>$(window).height())? $(document.body).height():$(window).height();
    return data ? bodySize[data] : bodySize;
}