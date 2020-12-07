<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style.css" rel="stylesheet" type="text/css" />
<title>Proyecto ASR new...</title>
</head>
<body>
<h1>Ejemplo de Proyecto de ASR con Cloudant ahora con DevOps y Marina</h1>
<hr />

<ul>
<li><a href="listar">Listar</a></li>
<li><a href="insertar?palabra=hola">Insertar</a></li>
<li>
			
			 <form action="detectarImagen" method="post" >
		
		  		<p>URL de tu imágen<input type="text" name="url">
				<input type="submit" value="detectarImagen"></p>
		
			</form>
		</li>
</ul>
</body>
</html>