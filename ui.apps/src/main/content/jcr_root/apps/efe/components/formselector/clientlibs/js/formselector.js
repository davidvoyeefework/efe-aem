$(document).ready(function() {
    var element = document.getElementsByClassName("formselector")[1];
    var formUrl = element.getAttribute("data-form-url");
    var formsJsUrl = element.getAttribute("data-form-js-url");
    debugger;
    $.ajax({
        url: formUrl,
        type: "GET",
        dataType: "json",
        success: function(data) {
            var htmlContent = data.body;
            var newDiv = document.createElement('div');
            newDiv.innerHTML = htmlContent;
            element.appendChild(newDiv);
            loadScript(formsJsUrl);
        },
        error: function(xhr, status, error) {
        }
    });
    function loadScript(url) {
        const script = document.createElement('script');
        script.src = url;
        document.head.appendChild(script);
    }

})