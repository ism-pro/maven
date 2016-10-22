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
/*
 function handleRibbonTabHomeClick() {
 PF('fpLayoutWidget').toggle('north');
 alert('Hello handleRibbonTabHomeClick');
 PF('fpLayoutWidget').toggle('north');
 }
 
 $("#main").click(function () {
 alert("Tab Acceuil click");
 PF('fpLayoutWidget').toggle('north')
 });
 */




/*******************************************************************************
 * 
 * 
 ******************************************************************************/
$(function () {
    $('#main').click(function () {
        //alert($('#unitNorth').height());

    });

});

    function ext() {
//this = chart widget instance
//this.cfg = options
        this.cfg.seriesDefaults = {
            shadowDepth: 0
        };
    }
