<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style.css" rel="stylesheet" type="text/css" />
<title>Proyecto ASR </title>
</head>
<body>
<h1>Aprende Idiomas con Cloud!</h1>
<hr />

<p id="autoras">Practica Final: Andrea Fari�a, Marina Alonso y Carmen Ollero</p>

<hr />

<form action="insertar" method="get" >

<h2>1. Elige el idioma</h2>

<p class = "idioma_origen">Idioma de origen </p>  

          <select name="idioma_entrada">

			<option>Espa�ol</option>
			
			<option>Ingl�s</option>
			
			<option>Franc�s</option>
			
			<option>Alem�n</option>
			
			
		 </select>
          
<p class = "idioma_destino">Idioma de destino </p>  

         <select name="idioma_salida">

			<option>Espa�ol</option>
			
			<option>Ingl�s</option>
			
			<option>Franc�s</option>
			
			<option>Alem�n</option>
			
			
		 </select>

<hr />

<h2>2. Elige la acci�n </h2>

<ul>
<li><a href="listar">Acceder a la base de datos</a></li>

<li>
			
	
		
		 <p>Insertar una palabra nueva en la base de datos<input type="text" name="palabra">
		<input type="submit" value="Insertar"></p>
		
			
</li>

<li>
			
			
		
<p>Detectar qu� es tu imagen (introducir una URL)<input type="text" name="url" value="https://upload.wikimedia.org/wikipedia/commons/f/ff/Anas_platyrhynchos_qtl1.jpg">
<input type="submit" value="detectar" formaction="detectarImagen"></p>
		
			
</li>

<li>Leer palabra en ingl�s:

	<input type="text" name="palabra_audio">
	<button type="submit" formaction="hablar">Reproducir en ingl�s</button>

</li>

</ul>
</form>
</body>
</html>