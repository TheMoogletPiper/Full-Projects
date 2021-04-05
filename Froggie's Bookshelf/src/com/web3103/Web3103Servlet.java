package com.web3103;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Web3103Servlet")
public class Web3103Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Web3103DAO dao;
	Socio socio;
	Libro libro;
	ServletContext sc;
	RequestDispatcher rd;
	String msg;

    public Web3103Servlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		if (dao==null) {
			dao = new Web3103DAO();
		}
		
		switch(request.getParameter("opcion")) {
		
		case ("3"):
			
			try {
				msg = dao.mostrarSocios();
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case ("4"):
			
			try {
				msg = dao.mostrarLibros();
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
		
		case("5"):
			
			socio = new Socio();
			socio.setNombre(request.getParameter("nombre"));
			socio.setEmail(request.getParameter("email"));
			
			try {
				dao.borrarSocio(socio);
				request.setAttribute("msg", dao.mostrarSocios());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case("6"):
			
			libro = new Libro();
			libro.setIsbn(request.getParameter("isbn"));
			try {
				dao.borrarLibro(libro);
				request.setAttribute("msg", dao.mostrarLibros());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case("7"):
			
			socio = new Socio();
			socio.setDireccion(request.getParameter("direccion"));
			socio.setEmail(request.getParameter("email"));
			socio.setNombre(request.getParameter("nombre"));
			socio.setTelefono(request.getParameter("telefono"));
			socio.setTemp(request.getParameter("oldemail"));
			try {
				dao.actualizarSocio(socio);	//actualiza socio y vuelve a mostrar la lista
				msg = dao.mostrarSocios();
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case("8"):
			
			libro = new Libro();
			libro.setIsbn(request.getParameter("isbn"));
			libro.setAutor(request.getParameter("autor"));
			libro.setTitulo(request.getParameter("titulo"));
			
			try {
				dao.actualizarLibro(libro);
				msg = dao.mostrarLibros();
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case("9"):
			
			libro = new Libro();
			socio = new Socio();
			
			libro.setIsbn(request.getParameter("isbn"));
			
			socio.setEmail(request.getParameter("email"));
			socio.setNombre(request.getParameter("nombre"));
		
			try {
				msg = dao.prestarLibro(socio, libro);
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		case("10"):
			
			socio = new Socio();
			
			socio.setEmail(request.getParameter("email"));
			socio.setNombre(request.getParameter("nombre"));
			
			try {
				msg = dao.mostrarPrestamos(socio);
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				request.setAttribute("msg", "<b>El socio no tiene ningún libro rentado &#x1F438;</b>");
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
		
		case("11"):
			
			libro = new Libro();
			libro.setIsbn(request.getParameter("isbn"));
			
			try {
				msg = dao.devolverLibro(libro);
				request.setAttribute("msg", msg);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
		
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if (dao==null) {
			dao = new Web3103DAO();
		}
		
		switch(request.getParameter("opcion")){
			
		case("1"):
			
			socio = new Socio();
			socio.setNombre(request.getParameter("nombre"));
			socio.setDireccion(request.getParameter("direccion"));
			socio.setTelefono(request.getParameter("telefono"));
			socio.setEmail(request.getParameter("email"));
		
			try {
				msg = dao.insertarSocio(socio);
				request.setAttribute("msg", msg);
				request.setAttribute("socio", socio);	//paso objetos socio y libro por si lo necesito en algun punto
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
		
		
		case("2"):
			
			libro = new Libro();
			libro.setTitulo(request.getParameter("titulo"));
			libro.setAutor(request.getParameter("autor"));
			libro.setIsbn(request.getParameter("isbn"));
			libro.setEstado(true);
			
			try {
				msg = dao.insertarLibro(libro);
				request.setAttribute("msg", msg);
				request.setAttribute("libro", libro);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			sc = getServletContext();
			rd = sc.getRequestDispatcher("/Web3103.jsp?opcion=0");
			
			rd.forward(request, response);
			break;
			
		/*case("42"):
			
			socio = new Socio();
			socio.setNombre(request.getParameter("nombreA"));
			socio.setEmail(request.getParameter("emailA"));
			if (dao.checkAdmin(socio)) {
				request.setAttribute("nombre", (request.getParameter("nombre")));
				socio.setEmail(request.getParameter("email"));
				request.setAttribute("opcion",(request.getParameter("opcion0")));
				break;
				
			}
			*/
			
			
		}
	
	
	}

}
