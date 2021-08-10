// Get the modal
var modal = document.getElementById('id01');

var modal02 = document.getElementById('id02');

var modal03 = document.getElementById('id03');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

window.onclick = function(event) {
	  if (event.target == modal02) {
	    modal02.style.display = "none";
	  }
	}

window.onclick = function(event) {
	  if (event.target == modal03) {
	    modal03.style.display = "none";
	  }
}
///////////////////funzione elimina locali amministratore

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

function buildoption(opzioni){
	try{
		var options = JSON.parse(opzioni);
		var cinema = options.info.cinema;
		var res = document.getElementById("listCinema");
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}

		for(var i=0; i<cinema.length; i++){
			var option = document.createElement("option");
			option.className = "opzioni";
			option.value = cinema[i].indirizzo;
			option.innerHTML = cinema[i].nome;
			res.appendChild(option);
		}
		document.getElementById('id02').style.display='block';
	}
	catch(e1){}
}

function openForm(){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			buildoption(this.responseText);
		}
	};
	xhttp.open("get","/cinema/admin/LocalControl?action=recoveryfordelete",true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

///////////////////funzione aggiorna spettacoli amministratore

function buildoptionShowUpdate(opzioni){
	try{
		var options = JSON.parse(opzioni);
		var spettacoli = options.info.spettacoli;
		var res = document.getElementById("listShowUpdate");
		
		if(res!=null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}

		for(var i=0; i<spettacoli.length; i++){
			var option = document.createElement("option");
			option.value = spettacoli[i].id;
			option.innerHTML = spettacoli[i].id+" "+spettacoli[i].titolo+", ora:"+spettacoli[i].ora+", data:"+spettacoli[i].data+", sala:"+spettacoli[i].sala+", prezzo:"+spettacoli[i].prezzo;
			res.appendChild(option);
		}
		
		document.getElementById('id03').style.display='block';
	}
	catch(e1){}
}

function openFormUpdate(){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			buildoptionShowUpdate(this.responseText);
		}
	};
	xhttp.open("get","/cinema/admin/SpettacoliControl?action=recoveryfordelete",true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

///////////////////funzione elimina spettacoli amministratore

function buildoptionShow(opzioni){
	try{
		var options = JSON.parse(opzioni);
		var spettacoli = options.info.spettacoli;
		var res = document.getElementById("listShow");
		
		if(res!=null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}

		for(var i=0; i<spettacoli.length; i++){
			var option = document.createElement("option");
			option.value = spettacoli[i].id;
			option.innerHTML = spettacoli[i].titolo+", ora:"+spettacoli[i].ora+", data:"+spettacoli[i].data+", sala:"+spettacoli[i].sala;
			res.appendChild(option);
		}
		
		document.getElementById('id02').style.display='block';
	}
	catch(e1){}
}

function openFormDelete(){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			buildoptionShow(this.responseText);
		}
	};
	xhttp.open("get","/cinema/admin/SpettacoliControl?action=recoveryfordelete",true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

///////////////////funzione elimina utente(amministratore)

function option(opzioni){
	try{
		var options = JSON.parse(opzioni);
		var utenti = options.info.utenti;
		var res = document.getElementById("listUser");
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}

		for(var i=0; i<utenti.length; i++){
			var option = document.createElement("option");
			option.className = "opz";
			option.value = utenti[i].email;
			option.innerHTML = utenti[i].nome+" "+utenti[i].cognome+", email: "+utenti[i].email+", data nascita: "+utenti[i].dataNascita;
			res.appendChild(option);
		}
		document.getElementById('id01').style.display='block';
	}
	catch(e1){}
}

function deleteUser(){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			option(this.responseText);
		}
	};
	xhttp.open("get","/cinema/admin/UserControl?action=recoveryForDeleteFromAdmin",true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

///////////validazione form operazioni di modifica

function formValidationModify(type, operation){
	if(type == "film"){
		if(operation == "aggiungi"){
			var genere = document.getElementById("genere");
			var durata = document.getElementById("durata");
			var regia = document.getElementById("regia");
			var cod_video = document.getElementById("cod_video");
			
			if(validationOfName(genere,"#spanGenere")){
				if(timeFormatValidation(durata,"#spanDurata")){
					if(validationOfName(regia,"#spanRegia")){
						if(validationCod(cod_video,"#spanCod")){
							return;
						}
					}
				}
			}
		}	
		else if(operation == "aggiorna"){
			var genere = document.getElementById("genereUpdate");
			var durata = document.getElementById("durataUpdate");
			var regia = document.getElementById("regiaUpdate");
			var cod_video = document.getElementById("cod_videoUpdate");
			
			if(genere.value == "" || validationOfName(genere,"#spanGenereUpdate")){
				if(durata.value == "" || timeFormatValidation(durata,"#spanDurataUpdate")){
					if(regia.value == "" || validationOfName(regia,"#spanRegiaUpdate")){
						if(cod_video == "" || validationCod(cod_video,"#spanCodUpdate")){
							return;
						}
					}
				}
			}
		}	
	}
	else if(type == "locali"){
		if(operation == "aggiungi"){
			var city = document.getElementById("city");
			var country = document.getElementById("reg");
			var maps = document.getElementById("mappa");
			var provincia = document.getElementById("prov");
			
			if(validationOfName(city,"#spanCity")){
				if(validationCountry(country,"#spanRegione")){
					if(validationMap(maps,"#spanMap")){
						if(validationProv(provincia,"#spanProv")){
							return;
						}
					}
				}
			}
		}	
		else if(operation == "aggiorna"){
			var city = document.getElementById("cityUpdate");
			var country = document.getElementById("regUpdate");
			var maps = document.getElementById("mappaUpdate");
			var provincia = document.getElementById("provUpdate");
			
			if(city.value == "" || validationOfName(city,"#spanCityUpdate")){
				if(country.value == "" || validationCountry(country,"#spanRegioneUpdate")){
					if(maps.value == "" || validationMap(maps,"#spanMapUpdate")){
						if(provincia.value == "" || validationProv(provincia,"#spanProvUpdate")){
							return;
						}
					}
				}
			}
		}	
	}
	else if(type == "spettacoli"){
		if(operation == "aggiungi"){
			var idSpettacolo = document.getElementById("id_spettacolo");
			var data = document.getElementById("data");
			var ora = document.getElementById("ora");
			var sala = document.getElementById("sala");
			
			if(validationId(idSpettacolo,"#spanId")){
				if(validationData(data,"#spanData")){
					if(timeFormatValidation(ora,"#spanOra")){
						if(validationHall(sala,"#spanSala")){
							return;
						}
					}
				}
			}
		}	
		else if(operation == "aggiorna"){
			var idSpettacolo = document.getElementById("id_spettacoloUpdate");
			var data = document.getElementById("dataUpdate");
			var ora = document.getElementById("oraUpdate");
			var sala = document.getElementById("salaUpdate");
			var prezzo = document.getElementById("prezzoUpdate");
			var idOldSpettacolo = document.getElementById("id_old_spettacolo");
			
			if(idOldSpettacolo.value == "" || validationId(idOldSpettacolo,"#spanIdOld")){
				if(idSpettacolo.value == "" || validationId(idSpettacolo,"#spanId")){
					if(data.value == "" || validationData(data,"#spanDataUpdate")){
						if(ora.value == "" || timeFormatValidation(ora,"#spanOraUpdate")){
							if(sala.value == "" || validationHall(sala,"#spanSalaUpdate")){
								return;
							}
						}
					}
				}
			}
		}	
	}
	return false;
} 

function validationId(id,span){
	var spett = id.value;
	var numbers = /^[0-9]+$/;
	var letters = /^[A-Z]+$/;
	
	if(spett.length == 2 && spett.charAt(0).match(letters) && spett.charAt(1).match(numbers)){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(id).css("background-color","red");
		return false;
	}
}

function validationHall(sala,span){
	var hall = sala.value;
	var numbers = /^[1-9]+$/;
	
	if(hall.length == 1 && hall.match(numbers)){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(sala).css("background-color","red");
		return false;
	}
}

function validationData(data,span){
	var d = data.value;
	var year = d.substring(0, 4);
	var month = d.substring(5, 7);
	var day = d.substring(8);
	var numbers = /^[0-9]+$/;
	
	if(year.match(numbers) && month.match(numbers) && day.match(numbers) && (d.charAt(4) == "-") && (d.charAt(7) == "-")){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(data).css("background-color","red");
		return false;
	}
	
}

function validationOfName(id,span){
	var letters = /^[A-Za-z' ']+$/;
	if(id.value.match(letters)){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(id).css("background-color","red");
		return false;
	}
}

function timeFormatValidation(id,span){
	var orario = id.value;
	var hh = orario.substring(0,2);
	var mm = orario.substring(3,5);
	var ss = orario.substring(6);
	var numbers = /^[0-9]+$/;
	
	if(hh.match(numbers) && mm.match(numbers) && ss.match(numbers) && orario.charAt(2) == ":" && orario.charAt(5) == ":"){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(id).css("background-color","red");
		return false;
	}
}

function validationCod(id,span){
	var cod_len = id.value.length;
	
	if(cod_len == 11){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(id).css("background-color","red");
		return false;
	}
}

function validationCountry(country, span){
	var regione = country.value;
	var letters = /^[A-Za-z\s-]+$/;
	var allReg = ["Valle d'Aosta", "Piemonte", "Liguria", "Lombardia", "Trentino-Alto Adige", "Veneto", 
		   "Friuli-Venezia Giulia", "Emilia Romagna", "Toscana", "Umbria", "Marche", "Lazio", 
		   "Abruzzo", "Molise", "Campania", "Puglia", "Basilicata", "Calabria", "Sicilia","Sardegna"];
	
	var trovato = false;
	if(regione.match(letters)){
		for(var i=0; i<allReg.length; i++){
			if(regione == allReg[i]){
				trovato = true;
				break;
			}
		}
	}
	if(trovato){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(country).css("background-color","red");
		return false;
	}
		
}

function validationMap(map,span){
	var urlMap = map.value;
	var domainMap = urlMap.substring(0,37);
	
	if(domainMap == "https://www.google.com/maps/embed?pb="){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(map).css("background-color","red");
		return false;
	}
}

function validationProv(prov,span){
	var provincia = prov.value;
	var letters = /^[A-Z]+$/;
	
	if(provincia.length == 2 && provincia.match(letters)){
		return true;
	}
	else{
		$(span).show();
		$(span).css("color","red");
		$(prov).css("background-color","red");
		return false;
	}
}

$(document).ready(function(){
//////film
	////aggiungi
	$("#genere").focus(function(){
		$("#spanGenere").hide();
		$("#genere").css("background-color","yellow");
	});
	
	$("#durata").focus(function(){
		$("#spanDurata").hide();
		$("#durata").css("background-color","yellow");
	});
	
	$("#regia").focus(function(){
		$("#spanRegia").hide();
		$("#regia").css("background-color","yellow");
	});
	
	$("#cod_video").focus(function(){
		$("#spanCod").hide();
		$("#cod_video").css("background-color","yellow");
	});
	//////aggiorna
	$("#genereUpdate").focus(function(){
		$("#spanGenereUpdate").hide();
		$("#genereUpdate").css("background-color","yellow");
	});
	
	$("#durataUpdate").focus(function(){
		$("#spanDurataUpdate").hide();
		$("#durataUpdate").css("background-color","yellow");
	});
	
	$("#regiaUpdate").focus(function(){
		$("#spanRegiaUpdate").hide();
		$("#regiaUpdate").css("background-color","yellow");
	});
	
	$("#cod_videoUpdate").focus(function(){
		$("#spanCodUpdate").hide();
		$("#cod_videoUpdate").css("background-color","yellow");
	});
	
	
////////locali
	////aggiungi
	$("#city").focus(function(){
		$("#spanCity").hide();
		$("#city").css("background-color","yellow");
	});
	
	$("#reg").focus(function(){
		$("#spanRegione").hide();
		$("#reg").css("background-color","yellow");
	});
	
	$("#mappa").focus(function(){
		$("#spanMap").hide();
		$("#mappa").css("background-color","yellow");
	});
	
	$("#prov").focus(function(){
		$("#spanProv").hide();
		$("#prov").css("background-color","yellow");
	});
	//////aggiorna
	$("#cityUpdate").focus(function(){
		$("#spanCityUpdate").hide();
		$("#cityUpdate").css("background-color","yellow");
	});
	
	$("#regUpdate").focus(function(){
		$("#spanRegioneUpdate").hide();
		$("#regUpdate").css("background-color","yellow");
	});
	
	$("#mappaUpdate").focus(function(){
		$("#spanMapUpdate").hide();
		$("#mappaUpdate").css("background-color","yellow");
	});
	
	$("#provUpdate").focus(function(){
		$("#spanProvUpdate").hide();
		$("#provUpdate").css("background-color","yellow");
	});
	
//////////////spettacoli
	////aggiungi
	$("#id_spettacolo").focus(function(){
		$("#spanId").hide();
		$("#id_spettacolo").css("background-color","yellow");
	});
	
	$("#data").focus(function(){
		$("#spanData").hide();
		$("#data").css("background-color","yellow");
	});
	
	$("#ora").focus(function(){
		$("#spanOra").hide();
		$("#ora").css("background-color","yellow");
	});
	
	$("#sala").focus(function(){
		$("#spanSala").hide();
		$("#sala").css("background-color","yellow");
	});
	//////aggiorna
	$("#id_spettacoloUpdate").focus(function(){
		$("#spanIdUpdate").hide();
		$("#id_spettacoloUpdate").css("background-color","yellow");
	});
	
	$("#dataUpdate").focus(function(){
		$("#spanDataUpdate").hide();
		$("#dataUpdate").css("background-color","yellow");
	});
	
	$("#oraUpdate").focus(function(){
		$("#spanOraUpdate").hide();
		$("#oraUpdate").css("background-color","yellow");
	});
	
	$("#salaUpdate").focus(function(){
		$("#spanSalaUpdate").hide();
		$("#salaUpdate").css("background-color","yellow");
	});
	
	$("#id_old_spettacolo").focus(function(){
		$("#spanIdOld").hide();
		$("#id_old_spettacolo").css("background-color","yellow");
	});
});