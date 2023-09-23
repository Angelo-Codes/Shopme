$(document).ready(function() {
    $("#buttonCancel").on("click", function() {
        window.location = moduleURL;
    });

    $("#fileImage").change(function() {
        fileSize = this.files[0].size;

        if (fileSize > 1048576) {
            this.setCustomValidity("You must choose an image less than 1mb!");
            this.reportValidity();
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    });
});

function showImageThumbnail(fileInput) {
    var file =  fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        $("#thumbnail").attr("src", e.target.result);
    }
    reader.readAsDataURL(file);
}

function showModalDialog(title, message) {
    $("#modalDialog").modal("show");
    $("#modalTile").text(title);
    $("#modalBody").text(message);
}

function showErrorModal(message) {
    showModalDialog("Error", message);
}

function showWarningModal(message) {
    showModalDialog("Warning", message);
}
