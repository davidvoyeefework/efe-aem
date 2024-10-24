(function () {
	"use strict";

	var isScriptLoaded = false;

	function onDocumentReady() {

	}

	if (document.readyState !== "loading") {
		onDocumentReady();
	} else {
		document.addEventListener("DOMContentLoaded", onDocumentReady);
	}

})();