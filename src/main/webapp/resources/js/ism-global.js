/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var _sidebar = $("#sidebar");
    var _sidebarOptions = $("#sidebar-options");
    var _sidebarOptionsIconOn = $("sidebar-options-icon-on");
    var _sidebarOptionsIconOff = $("sidebar-options-icon-off");
    var _sidebarContent = $("#sidebar-content");
    
    
    $("#main").click(function () {
        /*$("#file-layout").css("padding-top", "58");*/
        $("#file-layout").animate({
            'padding-top': 58,
        }, "slow");
        $("#file-layout").animate({
            'padding-top': 58,
        }, "slow");
    });

    _sidebarOptions.click(function(){
        var displayVal = _sidebarContent.css("display");
        if(displayVal.toString()==="inline"){
           _sidebarContent.css("display", "none");
           _sidebar.css("height", "32px");
           _sidebarOptionsIconOn.css("display", "inline");
           _sidebarOptionsIconOff.css("display", "none");
        }else{
            _sidebarContent.css("display", "inline");
            _sidebarOptionsIconOn.css("display", "none");
            _sidebarOptionsIconOff.css("display", "inline");
            _sidebar.css("height", "100%");
        }
    })
    
    
});







/*
 function App_Close() {
 netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite");
 alert("This will close the window");
 window.open('', '_self');
 window.close();
 /*
 alert("App_Close");
 window.open('','_self',''); 
 window.close(); 
 /*
 if (confirm("Fermer la fenêtre ?")) {
 this.parent.close();
 }*//*
  }
  $.ready(function() {
  var _maxInactiveInterval = $("#shortcut-label-timeout").val();
  alert("Ready !" + _maxInactiveInterval);
  
  $("#test").hover(function(){
  alert('hover');
  showMe();
  });
  $("#test").click(function(){
  alert('click');
  showMe();
  });
  
  
  function showMe(){
  var _maxInactiveInterval = $("#shortcut-label-reminingTimeout").html();
  alert("Ready !" + _maxInactiveInterval);
  }
  });
  */

/*
$(function () {

    var $sidebar = $("#sidebar"),
            $window = $(window),
            offset = $sidebar.offset(),
            topPadding = 250;

    $window.scroll(function () {
        if ($window.scrollTop() > offset.top) {
            $sidebar.stop().animate({
                marginTop: $window.scrollTop() - offset.top + topPadding
            });
        } else {
            $sidebar.stop().animate({
                marginTop: 300
            });
        }
    });

});
*/




