/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*******************************************************************************
 * Global
 * 
 ******************************************************************************/
function showStatus() {
    PF('statusDialog').show();
}
function hideStatus() {
    PF('statusDialog').hide();
}


/*******************************************************************************
 * 
 * 
 ******************************************************************************/
$(function () {


    $("_smqNCPanelInfos").mouseenter(function () {
        alert("Mouse over");
    });




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




