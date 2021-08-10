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

function prenotaFilm(prenota){
	try{
		var pshow = JSON.parse(prenota);
		var mess = pshow.messaggio;
		var res = document.getElementById("messaggio");
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}
		
		var p = document.createElement("p");
		p.innerHTML = mess;
		res.appendChild(p);
		
	}
	catch(e){}
}

function prenotazione(show){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			prenotaFilm(this.responseText);
		}
	};
	
	var url = window.location.pathname;
	if(url.search("/admin/")!=-1){
		url = url.substring(0,14);
	}
	else if(url.search("/user/")!=-1){
		url = url.substring(0,13);
	}
	
	xhttp.open("get",url+"SpettacoliControl?action=prenota&spettacolo="+show,true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

function showForFilm(spettacoliFilm){
	try{
		var showFilm = JSON.parse(spettacoliFilm);
		var shows = showFilm.info.shows;
		var res = document.getElementById("totalContainer");
		var mesi = ["Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre", +
					  "Ottobre","Novembre","Dicembre"];
		var position = window.location.pathname;
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}
		
			var h4 = document.createElement("h4");
			h4.innerHTML = shows[0].titolo;
			res.appendChild(h4);
			
			for(var i=0; i<shows.length; i++){
				var data = new Date(shows[i].data);
				var mese = mesi[data.getMonth()];
				
				var divcontainer = document.createElement("div");
				divcontainer.id = "containerShow";
				var divcalendar = document.createElement("div");
				divcalendar.className = "calendar";
				var table = document.createElement("table");
				var tr1 = document.createElement("tr");
				var td1 = document.createElement("td");
				td1.className = "month";
				var h5_1 = document.createElement("h5");
				h5_1.innerHTML = mese;
				var td2 = document.createElement("td");
				td2.rowspan = "2";
				var button = document.createElement("button");
				button.className = "hours";
				button.value = shows[i].id;
				button.innerHTML = shows[i].ora;
				if(position.search("/admin/")!=-1 || position.search("/user/")!=-1){
					button.onclick = function prenota(){
						prenotazione(this.value);
					};
				}
				else{
					button.onclick = function apriLogin(){
						document.getElementById('windowLogin').style.display='block';
					};
				}
				var tr2 = document.createElement("tr");
				var td3 = document.createElement("td");
				td3.className = "day";
				var h5_2 = document.createElement("h5");
				h5_2.innerHTML = data.getDate();
				
				td1.appendChild(h5_1);
				td2.appendChild(button);
				td3.appendChild(h5_2);
				tr1.appendChild(td1);
				tr1.appendChild(td2);
				tr2.appendChild(td3);
				table.appendChild(tr1);
				table.appendChild(tr2);
				divcalendar.appendChild(table);
				divcontainer.appendChild(divcalendar);
				
				res.appendChild(divcontainer);	
		}
	}
	catch(e1){}
}

function viewShow(showTitle){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			showForFilm(this.responseText);
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
	
	xhttp.open("get",url+"SpettacoliControl?action=recoveryShow&title="+showTitle,true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

function viewError(){
	$("#error").text("Utente non autorizzato!");
	$(".modal").show();
}