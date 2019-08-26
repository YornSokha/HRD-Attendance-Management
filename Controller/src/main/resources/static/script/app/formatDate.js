let oldBirthDate = $('.date').val();
$(document).ready(function () {
    $('.date').on('click', function () {
        pickmeup('.date', {
            format: 'Y-m-d'
        });
        pickmeup('.date').set_date(new Date(oldBirthDate));
    });
});