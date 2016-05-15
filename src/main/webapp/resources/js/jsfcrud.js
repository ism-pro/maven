function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function redirect(){
    $('#main').click(function(){
        alert("complete");
        window.location.href = "tmpl/ism-setup.xhtml";
    });
}
