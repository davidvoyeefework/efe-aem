(function () {
	"use strict";

	var isScriptLoaded = false;

	function onDocumentReady() {

		var elements = document.getElementsByClassName("formselector");
		if (elements != undefined || elements != null) {
			Array.from(elements).forEach(loadForm);
		}
	}

	function loadForm(element) {

		var formUrl = element.getAttribute("data-form-url");
		if (formUrl == undefined) {
			return;
		}

		fetch(formUrl).then(response => response.json())
			.then(jsonData => {
				if (jsonData && jsonData.htmlResponse && jsonData.scriptUrl) {
					var htmlContent = JSON.parse(jsonData.htmlResponse).body;
					if (htmlContent) {
						var newDiv = document.createElement('div');
						newDiv.innerHTML = htmlContent;
						element.appendChild(newDiv);
						if (!isScriptLoaded) {
							loadScript(jsonData.scriptUrl);
						}
						var formEvent = new CustomEvent("pmapiFormLoaded", {detail:{containerElement:element}});
						document.dispatchEvent(formEvent);
					}
				}
			}).catch(err => {
				console.log(err);
			})

	}

	function loadScript(url) {
		const script = document.createElement('script');
		script.src = url;
		document.head.appendChild(script);
		isScriptLoaded = true;
	}

	if (document.readyState !== "loading") {
		onDocumentReady();
	} else {
		document.addEventListener("DOMContentLoaded", onDocumentReady);
	}

})();