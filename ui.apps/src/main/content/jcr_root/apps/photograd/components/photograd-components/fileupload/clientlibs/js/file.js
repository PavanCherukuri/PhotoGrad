var isJpg = function(name) {
    return name.match(/jpg$/i)
};
    
var isPng = function(name) {
    return name.match(/png$/i)
};

$(document).ready(function() {
    var file = $('[name="file"]');
    var imgContainer = $('#imgContainer');
    
    $('#btnUpload').on('click', function() {
        var filename = $.trim(file.val());

         if (!(isJpg(filename) || isPng(filename))) {
            alert('Please browse a JPG/PNG file to upload ...');
            return;
        }

        $.ajax({
            url: '/bin/uploadServlet.json?name=pavan',
            type: "POST",
            data: new FormData(document.getElementById("fileForm")),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false
          }).done(function(data) {
               $('#imgContainer').html(data);
          }).fail(function(jqXHR, textStatus) {
              //alert(jqXHR.responseText);
              alert('File upload failed ...');
          });
        
    });
    
    $('#btnClear').on('click', function() {
        imgContainer.html('');
        file.val('');
    });
});
