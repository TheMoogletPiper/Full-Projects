<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.web3103.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<link rel="stylesheet" href="WebStyle.css">
<title>Librería de libros</title>
</head>
<body style="background-color:#cadc9f; font-family:Arial; text-align:center">
<div style="width:32px; height:32px; margin-left:2em; margin-top:2em">
<a href="index.html" style="width:32px; height:32px">

	<i  style="background:url('frogicon.png'); height:32px; width:32px; display:block"></i>

</a>
</div>


<!-- 

PÁGINA JSP. AQUÍ SE VA A MOSTRAR LA INFORMACIÓN REMITIDA POR EL SERVLET O POR CUALQUIER
OTRO MÉTODO 


EN VEZ DE REALIZAR VARIAS PÁGINAS JSP HE DECIDIDO CREAR UNA SOLA
EN LA QUE SE MOSTRARÁN DIFERENTES COSAS EN BASE AL PARÁMETRO "OPCIÓN".

CADA VEZ QUE SE LLAME A JSP HAY QUE INTRODUCIR UN PARÁMETRO "OPCIÓN" OBLIGATORIAMENTE
EL CUAL SERÁ UN NÚMERO EN FORMATO STRING

EL VALOR DE ESTE PARÁMETRO VA A DETERMINAR QUÉ SE VA A MOSTRAR EN PANTALLA.


 -->

<%
	switch (request.getParameter("opcion")){
	
	
	/*
	CASO 0-> MUESTRA "MSG". 
	EL ATRIBUTO "MSG" ES UN ATRIBUTO CON UN NOMBRE GENERALIZADO A LO LARGO DEL PROYECTO 
	EL CUAL VA A CONTENER LA INFORMACIÓN REMITIDA POR Y HACIA EL SERVLET.
	*/
	
	case ("0"):
				
		out.println(request.getAttribute("msg"));
		break;
		
	/*
	CASO 1-> FORM PARA ACTUALIZAR UN USUARIO.
	EN NUESTRO MÉTODO DE ACTUALIZAR UTILIZAREMOS EL NOMBRE COMO REFERENCIA. ES DECIR,
	TODO ES ACTUALIZABLE MENOS EL NOMBRE (SUJETO A CAMBIOS).
	A ESTE CASO SE LLEGA A TRAVÉS DE LA OPCIÓN DE ACTUALIZAR USUARIO EN LA FUNCIÓN DE MOSTRAR USUARIOS,
	POR LO TANTO, RECOGERÁ LA INFORMACIÓN NOMBRE E EMAIL DEL USUARIO INDICADO (LA COMBINACIÓN DE AMBOS SERÁ UNA CLAVE ÚNICA),
	SE PEDIRÁ LA NUEVA INFORMACIÓN
	Y LA MANDARÁ AL SERVLET	
	*/
	
	case ("1"):
		
		out.println("<h1>Actualización de usuario</h1><br><br>"
		+ "<form action=\"Web3103Servlet\">"
		+ "<b>Nueva dirección&emsp;</b><input type=\"text\" name=\"direccion\" required>"
		+ "<b>Nuevo teléfono&emsp;</b><input type=\"text\" name=\"telefono\" required>"
		+ "<b>Nuevo email&emsp;</b><input type=\"text\" name=\"email\" required>"
		+ "<input type=\"hidden\" name=\"nombre\" value=" + request.getParameter("nombre") + ">"
		+ "<input type=\"hidden\" name=\"oldemail\" value=" + request.getParameter("email") + ">"
		+ "<input type=\"hidden\" name=\"opcion\" value=\"7\">"
		+ "<input class=\"button\" type=\"submit\" value=\"Actualizar! &#x1F438;\">"
		+ "</form>");
		break;
		
	/*
	CASO 2-> CASO 1 PERO CON LIBRO. SE UTILIZA EL ISBN COMO REFERENCIA Y NO SE PUEDE CAMBIAR.
	*/
		
	case("2"):
		
		out.println("<h1>Actualización de libro</h1><br><br>"
		+ "<form action=\"Web3103Servlet\">"
		+ "<b>Nuevo título&emsp;</b><input type=\"text\" name=\"titulo\" required><br><br>"
		+ "<b>Nuevo autor&emsp;</b><input type=\"text\" name=\"autor\" required><br><br>"
		+ "<input type=\"hidden\" name=\"nombre\" value=" + request.getParameter("isbn") + ">"
		+ "<input type=\"hidden\" name=\"opcion\" value=\"8\">"
		+ "<input class=\"button\" type=\"submit\" value=\"Actualizar! &#x1F438;\">"
		+ "</form>");
		break;
		
	/*
	CASO 3-> PRÉSTAMO DE LIBRO.
	RECOGEREMOS LA INFORMACIÓN DEL ISBN DEL LIBRO INDICADO POR EL MÉTODO DE MOSTRAR LIBROS
	Y PEDIREMOS AL USUARIO QUE INTRODUZCA SU NOMBRE Y MAIL PARA SABER QUÉ USUARIO QUIERE RENTAR LOS LIBROS
	ESTA INFO SE PASA AL SERVLET.
	*/
	
	case("3"):
		
		out.println("<h1>Préstamo de libro</h1><br>"
		+ "<h2>Introduce tu nombre y mail para verificar el préstamo</h2>"
		+ "<form action=\"Web3103Servlet\">"
		//+ "<div style=\"margin-left:40em; margin-right:40em;width:400px; position:relative\">"
		+ "<b>Nombre&emsp;</b><input type=\"text\" name=\"nombre\" required><br><br>"
		+ "<b>Email&emsp;</b><input type=\"text\" name=\"email\" required><br><br>"
		+ "<input type=\"hidden\" name=\"isbn\" value=\"" + request.getParameter("isbn") + "\">"
		+ "<input type=\"hidden\" name=\"opcion\" value=\"9\">"
		+ "<input class=\"button\"type=\"submit\" value=\"Prestar! &#x1F438;\">"
		//+ "</div>"
		+ "</form>");
		break;
		
	/*
	CASO 4 & 5-> AÑADIR NUEVO SOCIO/LIBRO. MUESTRA EL FORMULARIO PARA INTRODUCIR UNA NUEVA ENTRADA EN LA DB
	*/
		
	case("4"):
		
		out.println(
		  "<div class='parent'>"
			+ "<h1  style=\"text-align:center\">Rellena los campos para añadir un nuevo socio!</h1><br><br>"
			+ "<form action=\"Web3103Servlet\" method=\"post\">"
				+ "<div>"
					+ "<b>Nombre:&emsp;&emsp;</b><input style='text-align:right'type=\"text\" name=\"nombre\" required><br><br>"
					+ "<b>Dirección:&emsp;&emsp;</b><input type=\"text\" name=\"direccion\" required><br><br>"
					+ "<b>Teléfono:&emsp;&emsp;</b><input type=\"text\" name=\"telefono\" required><br><br>"
					+ "<b>Email:&emsp;&emsp;</b><input type=\"text\" name=\"email\" required><br><br>"
					+ "<input type=\"hidden\" name=\"opcion\" value=\"1\">"
					+ "<input class=\"button\" type=\"submit\" value=\"Añadir a la DB! &#x1F438;\">"
				+ "</div>"
			+ "</form>"
		+ "</div>"
		);
		break;

	case("5"):
		
		out.println(
			  	  "<h1  style=\"text-align:center\">Rellena los campos para añadir un nuevo libro!</h1><br><br>"
			  	+ "<div class='parent'>"
				+ "<form action=\"Web3103Servlet\" method=\"post\">"
					+ "<div>"
						+ "<b>Título:&emsp;&emsp;</b><input type=\"text\" name=\"titulo\" required><br><br>"
						+ "<b>Autor:&emsp;&emsp;</b><input type=\"text\" name=\"autor\" required><br><br>"
						+ "<b>ISBN:&emsp;&emsp;</b><input type=\"text\" name=\"isbn\" required><br><br>"
						+ "<input type=\"hidden\" name=\"opcion\" value=\"2\">"
						+ "<input class=\"button\" type=\"submit\" value=\"Añadir a la DB! &#x1F438;\">"
					+ "</div>"
				+ "</form>"
			+ "</div>"
		);
		break;
		
	/*
	CASO -1 -> INDICE. INDEX.HTML SIGUE EXISTIENDO.
	
	
	case("-1"):
		out.println("<h1>Librería de libros</h1>"
		+ "<br>"
		+ "<h3>Qué quieres hacer?&#x1F438;</h3>"
		+ "<br>"
		+ "<br>"
		+ "<b>Añadir <a href=\"Web3103.jsp?opcion=4\">un nuevo socio.</a></b><br><br>"
		+ "<b>Añadir <a href=\"Web3103.jsp?opcion=5\">un nuevo libro.</a></b><br><br>"
		+ "<b>Mostrar la <a href=\"Web3103Servlet?opcion=3\">lista de socios.</a></b><br><br>"
		+ "<b>Mostrar la <a href=\"Web3103Servlet?opcion=4\">lista de libros.</a></b><br><br>"
		);
		break;
		
	*/
	}


%>






</body>
</html>