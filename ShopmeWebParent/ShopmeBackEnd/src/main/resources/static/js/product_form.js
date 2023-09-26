var extraImagesCount = 0;
dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {
    $("#shortDescription").richText();
    $("#fullDescription").richText();

    dropdownBrands.change(function() {
        dropdownCategories.empty();
        getCategories();
    });

    getCategories();


    $("input[name='extraImage']").each(function(index) {
        extraImagesCount++;

        $(this).change(function() {
            showExtraImageThumbnail(this, index);
        });
    });
});


function showExtraImageThumbnail(fileInput, index) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        $("#extraThumbnail" + index).attr("src", e.target.result)
    }
    reader.readAsDataURL(file);

    if (index >= extraImagesCount -1) {
        addNextExtraImageSection(index + 1);
    }
}

function addNextExtraImageSection(index) {
    htmlExtraImage = `
        <div class="col border m-3 p-2 " id="divExtraImage${index}">
            <div id="extraImageHeader${index}"><label>Extra Image #${index + 1}:</label></div>
            <div class="m-2">
                <img id="extraThumbnail${index}" alt="Main image number #${index + 1} preview" class="img-fluid" th:src="@{/images/image-thumbnail.png}"/>
            </div>
            <div>
                <input type="file" onchange="showExtraImageThumbnail(this, ${index})" accept="image/png, image/jpeg"/>
            </div>
        </div>
    `;

    htmlLinkRemove = `
        <a class="btn fas fa-times-circle fa-2x icon-dark float-right" href="javascript:removeExtraImage(${index - 1})" title="Remove this image"></a>
    `;

    $("#divProducImages").append(htmlExtraImage);
    $("#extraImageHeader" + (index + 1)).append(htmlLinkRemove);

    extraImagesCount++;
}

function removeExtraImage(index) {
    $("#divExtraImage" + index).remove();
}


function getCategories() {
    brandId = dropdownBrands.val();
    url = brandModuleURL + "/" + brandId + "/categories";

    $.get(url, function(responseJson) {
        $.each(responseJson, function(index, category) {
            $("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
        });
    });
}