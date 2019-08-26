
(function() {
    "use strict";
    window.addEventListener("load", function() {
        let form = document.getElementById("has_select");
        form.addEventListener("submit", function(event) {
            let select = $('.custom-select').val();

            /*if not select alert message*/
            if (select == null){
                $('#required_select').text('* Please choose academic');
                event.preventDefault();
                event.stopPropagation();
            }
        }, false);
    }, false);
}());

/*when select message alert clear*/
$('.custom-select').on('change', (event)=>{
    $('#required_select').text('');
})

/*Message validation front-end*/
bootstrapValidate('#name', 'required:Please enter generation name');
