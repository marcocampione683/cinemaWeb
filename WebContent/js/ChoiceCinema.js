var modal = document.getElementById("modalRegioni");

		
function visualizza(){
	$(".modalReg").show();
}

$(window).onclick = function(event) {
	if (event.target == modal) {
		modal.style.display = "none";
	}
}

function openSearch() {
	 document.getElementById("myOverlay").style.display = "block";
}

function closeSearch() {
	document.getElementById("myOverlay").style.display = "none";
}

function getXmlHttpRequest() {

	var xhr = false;
	var activeXoptions = new Array("Microsoft.XmlHttp", "MSXML4.XmlHttp",
			"MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp");

	try {
		xhr = new XMLHttpRequest();
		console.log("Get XML http request");
	} catch (e) {
	}

	if (!xhr) {
		var created = false;
		for (var i = 0; i < activeXoptions.length && !created; i++) {
			try {
				xhr = new ActiveXObject(activeXoptions[i]);
				created = true;
				console.log("Get ActiveXObject XML http request");
			} catch (e) {
			}
		}
	}
	return xhr;
}

//////////////////////////// scegli il cinema

function buildModalCinema(province,country){
	try{
		var prov = JSON.parse(province);
		var provCinema = prov.info;
		var url = "SpettacoliControl?action=recoveryAll";
		url = encodeURI(url);
		var res = document.getElementById("modalRegioni");
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}
		
		var divglobal = document.createElement("div");
		divglobal.className = "choice-country";
		var divheader = document.createElement("div");
		divheader.className = "header";
		var span = document.createElement("span");
		span.className = "closereg";
		span.onclick = function chiudi(){
			document.getElementById("modalRegioni").style.display = "none";
		};
		span.innerHTML = "&times;";
		var h2 = document.createElement("h2");
		h2.innerHTML = country;
		var divbody = document.createElement("div");
		divbody.className = "body";
		divheader.appendChild(span);
		divheader.appendChild(h2);
		divglobal.appendChild(divheader);
		divglobal.appendChild(divbody);
		
		for(var i=0; i<provCinema.length; i++){
			var citta = provCinema[i];
			var divdrop = document.createElement("div");
			divdrop.className = "dropdown-cinema";
			var buttondrop = document.createElement("button");
			buttondrop.className = "dropbtn-cinema";
			buttondrop.innerHTML = citta[0].city;
			var divdropcontent = document.createElement("div");
			divdropcontent.className = "dropdown-content-cinema";
			divdrop.appendChild(buttondrop);
			divdrop.appendChild(divdropcontent);
			
			for(var j=0; j<citta.length; j++){
				var form = document.createElement("form");
				form.action = url;
				form.method = "post";
				var button = document.createElement("button");
				button.value = citta[j].address;
				button.id = "cinButton";
				button.name = "cinema";
				button.innerHTML = citta[j].name;
				form.appendChild(button);
				divdropcontent.appendChild(form);
			}
			
			divbody.appendChild(divdrop);
			var br = document.createElement("br");
			divbody.appendChild(br);
		}
		res.appendChild(divglobal);
		visualizza();
	}
	catch(e1){}
}


function showCinema(regione){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			buildModalCinema(this.responseText,regione);
		}
	};
	
	var url = window.location.pathname;
	if(url.search("/admin/")!=-1){
		url = url.substring(0,14);
	}
	else if(url.search("/user/")!=-1){
		url = url.substring(0,13);
	}
	else{
		url = url.substring(0,8);
	}
	
	xhttp.open("get",url+"LocalControl?action=recovery&country="+regione,true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

///////////////////////////////////barra di ricerca

function resultSearch(result,str){
	try{
		var cinemaSelected = JSON.parse(result);
		var cinema = cinemaSelected.info.cinema;
		var url = "SpettacoliControl?action=recoveryAll";
		url = encodeURI(url);
		var res = document.getElementById("resultSearch");
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}
		
		for(var i=0; i<cinema.length; i++){
			var form = document.createElement("form");
			form.action = url;
			form.method = "post";
			var button = document.createElement("button");
			button.value = cinema[i].indirizzo;
			button.id = "cinemaButton";
			button.name = "cinema";
			button.innerHTML = cinema[i].nome;
			form.appendChild(button);
		//	var br = document.createElement("br");
			res.appendChild(form);
		//	res.appendChild(br);
		}
	}
	catch(e1){}
}

function suggerimento(str){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			resultSearch(this.responseText,str);
		}
	};
		
	var url = window.location.pathname;
	if(url.search("/admin/")!=-1){
		url = url.substring(0,14);
	}
	else if(url.search("/user/")!=-1){
		url = url.substring(0,13);
	}
	else{
		url = url.substring(0,8);
	}
		
	xhttp.open("get",url+"LocalControl?action=search&key="+str,true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}
