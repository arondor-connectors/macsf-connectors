/**
 * Code spécifique côté client pour le découpage CM
 *
 * Inséré dans ARender.jsp avec <script type="text/javascript" language="javascript" src="macsf_decoupage_cm.js"></script>
 *
 * Version 2.0 incluant la sélection du code rubrique
 */

function arenderjs_init(arenderjs_)
{
	/**
	 * Abonnement a l'événement de preparation à l'enregistrement du document modifié
	 */
	arenderjs_.documentBuilder.registerSubmitAlterDocumentContentEvent(function(obj){armt_onSubmitAlterDocumentContentEvent(arenderjs_,obj);});

	arenderjs_.documentBuilder.registerEditablePictreeNodeEvent(function(docId,
			element) {
		armt_nodeEditablePicTree(arenderjs_, docId, element);
	});	
}

var selectedRubriques = [];

function armt_onSubmitAlterDocumentContentEvent(arenderjs_,obj)
{
	/**
	 * Remplissage des méta-données du document à injecter
	 */
	var desc = arenderjs_.documentBuilder.getSubmittedAlterDocumentContentDescription(obj);
	var meta = arenderjs_.documentBuilder.getDocumentMetadata(desc, 0);
	
	/**
	 * EntityName : nom de la classe CM
	 */	 
	//arenderjs_.documentBuilder.addDocumentMetadata(meta, "EntityName", "gestion");
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "EntityName", getParamValue('entityName',location.url));
	
	/**
	 * TIMESTAMP est une Data de NOINDEX uniquement, non applicable pour gestion
	 */
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "skipTimeStamp", "true");
	
	/**
	 * USER_ID utilisé uniquement pour l'appel COBOL
	 */
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "USER_ID", "CTGUSR");

	/**
	 * REFERENCE_ORIGINE : Recalcul à partir des informations de Gest_index_tmp et Gest_date_cre
	 */
	var Gest_index_tmp = zeroPadding(getParamValue('Gest_index_tmp',location.url), 9);
	var Gest_date_cre = getParamValue('Gest_date_cre',location.url);
	var referenceOrigine = Gest_date_cre + Gest_index_tmp;
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "REFERENCE_ORIGINE", referenceOrigine);
	


	/**
         * Identification du DocumentId temporaire généré par ARender
	 */ 
	var docId = arenderjs_.documentBuilder.getDocumentMetadataValue(meta, "DocumentID");

	/**
         * Identification de la rubrique sélectionnée
	 */
	var rubrique = selectedRubriques[docId];
	if (rubrique == "No data given") {
		alert("Veuillez renseigner le code rubrique");
		throw new Error("No data given");
	}
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "Rubrique", rubrique);

	/**
	 * CODE_APPLICATION : Récupération à partir de l'URL
	 */
	var codeApplication = getParamValue('CODE_APPLICATION',location.url);
	arenderjs_.documentBuilder.addDocumentMetadata(meta, "CODE_APPLICATION", codeApplication);
}

/**
 * Retourne la valeur d'un paramètre d'une url
 * 
 * @param string param
 * nom du paramètre dont on souhaite avoir la valeur
 * @param url
 * url dans laquel on souhaite récupérer le paramètre ou rien si l'on souhaite travailler sur l'url courante
 * @return String
 * @author Labsmedia
 * @see http://www.labsmedia.com
 * @licence GPL
 */
function getParamValue(param,url)
{
	var u = url == undefined ? document.location.href : url;
	var reg = new RegExp('(\\?|&|^)'+param+'=(.*?)(&|$)');
	matches = u.match(reg);
	return matches[2] != undefined ? decodeURIComponent(matches[2]).replace(/\+/g,' ') : '';
}


function zeroPadding(n, p, c) {
    var pad_char = typeof c !== 'undefined' ? c : '0';
    var pad = new Array(1 + p).join(pad_char);
    return (pad + n).slice(-pad.length);
}

function armt_nodeEditablePicTree(arenderjs_, docId, element) {
	var docIdString = "" + docId;
	var nodeElement = element.querySelectorAll('.gwt-Document-Name'); 
	var selectElement = document.createElement("select"); // Create select tag
	selectElement.onmousedown = function(event) {
		stopPropagation(event);
		return true;
	}; // Open the list Box
	queryKeyValuesList(selectElement, docIdString);
	nodeElement[0].parentNode.parentNode.parentNode.parentNode.parentNode.appendChild(selectElement); // Add the list Box

	selectElement.onchange = function() {
		selectedRubriques[docIdString] = this.options[this.selectedIndex].value;
	}

}

function stopPropagation(e) {
	if (!e)
	{
		var e = window.event;
	}

	e.cancelBubble = true;
	e.returnValue = false;

	if (e.stopPropagation)
		e.stopPropagation();

}

function createListBox(selectElement, resultJson, docIdString) {
	// Visit all the elements of the JsonTable
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
	var codeApplication = getParamValue('CODE_APPLICATION',location.url);
	xhr.open("GET", "macsfCodeRubrique.jsp?codeAppli=" + codeApplication, true); 
	xhr.send(null);
}
