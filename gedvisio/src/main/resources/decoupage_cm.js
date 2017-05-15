function arenderjs_init(arenderjs_) {
	arenderjs_.documentBuilder
			.registerSubmitAlterDocumentContentEvent(function(obj) {
				armt_onSubmitAlterDocumentContentEvent(arenderjs_, obj);
			});
	arenderjs_.documentBuilder.registerEditablePictreeNodeEvent(function(docId,
			element) {
		armt_nodeEditablePicTree(arenderjs_, docId, element);
	});
}

var selectedRubriques = [];

function armt_onSubmitAlterDocumentContentEvent(arenderjs_, obj) {

	var desc = arenderjs_.documentBuilder
			.getSubmittedAlterDocumentContentDescription(obj);
	var meta = arenderjs_.documentBuilder.getDocumentMetadata(desc, 0);
	var docId = arenderjs_.documentBuilder.getDocumentMetadataValue(meta,
			"DocumentID");
	var rubrique = selectedRubriques[docId];

	if (rubrique == "No data given") {
		alert("Veuillez renseigner le code rubrique");
		throw new Error("No data given");
	}
	
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "EntityName", "gestion");
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "skipTimeStamp", "true");
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "RUBRIC", rubrique);
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "USER_ID", "LEDOE18");
}

function armt_nodeEditablePicTree(arenderjs_, docId, element) {
	var docIdString = "" + docId;
	var nodeElement = element.querySelectorAll('.gwt-Document-Name'); // Select
	// the
	// parent
	// Node
	var selectElement = document.createElement("select"); // Create select tag
	selectElement.onmousedown = function(event) {

		stopPropagation(event);
		return true;
	}; // Let open the list Box
	queryKeyValuesList(selectElement, docIdString);
	nodeElement[0].parentNode.appendChild(selectElement); // Add the list Box
	// into the right
	// child of the node

	selectElement.onchange = function() {
		selectedRubriques[docIdString] = this.options[this.selectedIndex].value;
	}

}

function stopPropagation(e) {

	if (!e)
		var e = window.event;

	e.cancelBubble = true;
	e.returnValue = false;

	if (e.stopPropagation)
		e.stopPropagation();

}

function createListBox(selectElement, resultJson, docIdString) {
	// Visit all the part of the JsonTable
	for ( var index = 0; index < resultJson.length; index++) {
		var object = resultJson[index];
		selectElement.options[selectElement.options.length] = new Option(
				object.value, object.key);

		if (index == 0) {

			selectedRubriques[docIdString] = object.key;
		}
	}
}

function queryKeyValuesList(selectElement, docIdString) {

	var xhr = null;

	if (window.XMLHttpRequest)

	{
		xhr = new XMLHttpRequest();

	} else if (window.ActiveXObject)

	{
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {
			if (xhr.status == 200) { // if the connexion is successful
				var resultJson = eval(xhr.responseText);
				createListBox(selectElement, resultJson, docIdString);
			} else {

			}

		} else {
		}

	};

	// Call the right jsp
	xhr.open("GET", "macsfCodeRubrique.jsp.jsp?codeAppli=RS", true); 
	xhr.send(null);

}