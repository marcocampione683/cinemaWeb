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

function resultSearch(result){
	try{
		var filmSelected = JSON.parse(result);
		var films = filmSelected.info.films;
		var url = "FilmControl?action=filmView";
		url = encodeURI(url);
		var res = document.getElementById("rowFilm");//tr
		
		if(res != null){
			while(res.hasChildNodes()){
				res.removeChild(res.childNodes[0]);
			}
		}
		
		var position = window.location.pathname;
		
		if(position.search("/admin/")!=-1 || position.search("/user/")!=-1){
			for(var i=0; i<films.length; i++){
				//creo td
				var td = document.createElement("td");
				//creo form
				var form = document.createElement("form");
				//aggiungo attributo a form
				form.action = url;
				form.method = "post";
				//creo input
				var input = document.createElement("input");
				//setto gli attributi di input
				input.type = "image";
				input.src = "../image/immagini_film/"+films[i].locandina;
				input.name = "film";
				input.value = films[i].titolo;
				input.id = "locandina";
				//creo div
				var div = document.createElement("div");
				//creo h4
				var h4 = document.createElement("h4");
				//inserisco la stringa in h4
				h4.innerHTML = films[i].titolo;
				//aggiungo gli elementi ai vari elementi padre
				form.appendChild(input);
				div.appendChild(h4);
				td.appendChild(form);
				td.appendChild(div);
				
				res.appendChild(td);
			}
		}
		else{
			for(var i=0; i<films.length; i++){
				//creo td
				var td = document.createElement("td");
				//creo form
				var form = document.createElement("form");
				//aggiungo attributo a form
				form.action = url;
				form.method = "post";
				//creo input
				var input = document.createElement("input");
				//setto gli attributi di input
				input.type = "image";
				input.src = "image/immagini_film/"+films[i].locandina;
				input.name = "film";
				input.value = films[i].titolo;
				input.id = "locandina";
				//creo div
				var div = document.createElement("div");
				//creo h4
				var h4 = document.createElement("h4");
				//inserisco la stringa in h4
				h4.innerHTML = films[i].titolo;
				//aggiungo gli elementi ai vari elementi padre
				form.appendChild(input);
				div.appendChild(h4);
				td.appendChild(form);
				td.appendChild(div);
				
				res.appendChild(td);
			}
		}
	}
	catch(e1){}
}

function suggerimento(str){
	var xhttp = getXmlHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			resultSearch(this.responseText);
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
	
	xhttp.open("get",url+"FilmControl?action=search&key="+str,true);
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhttp.send();
}

//////////////Slide show home.jsp

	var slideIndex = 1;
	showSlides(slideIndex);
	
	function plusSlides(n) {
		showSlides(slideIndex += n);
	}

	function currentSlide(n) {
		showSlides(slideIndex = n);
	}

	function showSlides(n) {
	  var i;
	  var slides = document.getElementsByClassName("mySlides");
	  var dots = document.getElementsByClassName("dot");
	  if (n > slides.length) {slideIndex = 1}    
	  if (n < 1) {slideIndex = slides.length}
	  for (i = 0; i < slides.length; i++) {
	      slides[i].style.display = "none";  
	  }
	  for (i = 0; i < dots.length; i++) {
	      dots[i].className = dots[i].className.replace(" active", "");
	  }
	  slides[slideIndex-1].style.display = "block";  
	  dots[slideIndex-1].className += " active";
	}