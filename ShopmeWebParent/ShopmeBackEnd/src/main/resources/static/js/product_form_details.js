$(document).ready(function() {
    $("a[name='linkRemoveDetail']").each(function(index) {
        $(this).click(function() {
            removeDetailByIndex(index);
        });
    });
});
function addNextDetailSection() {
    allDivDetails = $("[id^='divDetail']");
    divDetailCount = allDivDetails.length;
    htmlDetailSection = `
        <div class="d-inline form-group" id="divDetail${divDetailCount}">
        <input type="hidden" name="detailIDs" value="0" />
        </br><label class="m-3">Name:</label>
            <input type="text" name="detailName" maxlength="255" class="w-25"/>
            <label class="m-3">Value:</label>
            <input type="text" name="detailValue" maxlength="255" class="w-25"/>
        </div>
    `;

    $("#divProductDetails").append(htmlDetailSection);

    previousDivDetailSection = allDivDetails.last();
    previousDetailID = previousDivDetailSection.attr("id");

    htmlLinkRemove = `
            <a class="fas fa-times-circle fa-2x icon-dark" href="javascript:removeDetailSectionById('${previousDetailID}')" title="Remove this detail"></a>
        `;

    previousDivDetailSection.append(htmlLinkRemove);
    $("input[name = 'detailName']").last().focus();
}
function removeDetailSectionById(id) {
    $("#" + id).remove();
}

function removeDetailByIndex(index) {
    $("#divDetail" + index).remove();
}