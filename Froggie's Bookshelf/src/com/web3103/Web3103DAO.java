package com.web3103;

import java.math.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

/*
 * TO DO
 * 
 * PEDIR CONFIRMACIÓN A LA HORA DE BORRAR/UPDATE UN SOCIO/LIBRO
 * 
 * */
public class Web3103DAO {
	
	/*
	
	DECLARACIÓN DE TODAS LAS VARIABLES QUE VAMOS A USAR:
	
		CONNECTION CON -> CONEXIÓN CON LA DATABASE.
		
		STRING SQL -> STRING QUE USAREMOS PARA LAS SENTENCIAS SQL EN LOS STATEMENT.
		
		PREPAREDSTATEMENT STMT -> STATEMENT QUE USAREMOS PARA EDITAR Y COMUNICARNOS CON SQL.
		
		RESULTSET RS -> RESULTSET EN EL QUE GUARDAREMOS LOS DATOS ENVIADOS POR EL STMT.
						EN ALGUNOS CASOS ES DECLARADO COMO SCROLLABLE.
						
		STRINGBUILDER SB -> STRINGBUILDER PARA HACER UNIONES DE STRINGS A LA HORA DE MANDAR UNA STRING LARGA.
		
		DATE DATE -> DATE DONDE ALMACENAMOS LAS FECHAS A MANEJAR EN LA DB. SON OBJETO COM.MYSQL.DATE
		
	*/
	static Connection con;
	static String sql, msg=null;
	static PreparedStatement stmt;
	static ResultSet rs;
	static StringBuilder sb;
	static Date date;
	
	
	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
		
 
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "root");
		return con;
	}
	
	/*WIP
	protected boolean checkAdmin(Socio socio) {
		
		
		if (socio.getNombre().equals("admin") && socio.getEmail().equals("admin@mail.com")) {
			
			return true;
			
		} else {
			
			return false;
			
		}
	}
	
	public static boolean getBookStatus (Libro libro) throws SQLException, ClassNotFoundException{
		
		sql = "SELECT estado FROM libro WHERE isbn=?";
		con = initializeDatabase();
		stmt = con.prepareStatement(sql);
		stmt.setString(1,libro.getIsbn());
		rs = stmt.executeQuery();
		return rs.getBoolean(1);
	}
	
	
	*/
	public String insertarSocio(Socio socio) throws ClassNotFoundException, SQLException {
		
		msg = null;
		con = initializeDatabase();
		sql = "INSERT INTO socio (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, socio.getNombre());
		stmt.setString(2, socio.getDireccion());
		stmt.setString(3, socio.getTelefono());
		stmt.setString(4, socio.getEmail());
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    msg = "<b>Nuevo socio añadido!"
                    + "</b>";
		} else {
			msg = "<b style=\"color:#FF0000\">Something went wrong..."
                    + "</b>";
		}
		
		return msg;
	}
	public String insertarLibro(Libro libro) throws ClassNotFoundException, SQLException {
		
		msg = null;
		con = initializeDatabase();
		sql = "INSERT INTO libro (titulo, autor, isbn, estado) VALUES (?, ?, ?, ?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, libro.getTitulo());
		stmt.setString(2, libro.getAutor());
		stmt.setString(3, libro.getIsbn());
		stmt.setBoolean(4, libro.isEstado());
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
			msg = "<b>Nuevo libro añadido!"
					+ "</b>";
		} else {
			msg = "<b style=\"color:#FF0000\">Something went wrong..."
					+ "</b>";
		}
		
		return msg;
	}
	
	public String mostrarSocios() throws ClassNotFoundException, SQLException{
		
		sb = new StringBuilder();
		sb.append("<table style=\"width:100%\">"
				+ "<tr>"
					+ "<th>Nombre</th>"
					+ "<th>Direccion</th>"
					+ "<th>Telefono</th>"
					+ "<th>Email</th>"
					//+ "<th>Opciones</th>"
				+ "</tr>");
		
		con = initializeDatabase();
		sql = "SELECT * FROM socio";
		
		//AQUÍ EL STATEMENT HA DE SER SCROLLEABLE PARA PODER REACCEDER A LOS RESULTADOS DE NUEVO EN EL RESULTSET
		//Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = null;
		try {
		rs = stmt.executeQuery();
		
		//SI NO HAY NINGÚN SOCIO AÑADIDO, SQL DEVUELVE TODO NULL Y SE MANEJA COMO UN ERROR DE SINTAXIS.
		} catch (SQLSyntaxErrorException e) {
			msg = "<b>No hay ningún socio aún &#x1F438;</b>";
			return msg;
		}
		
		while (rs.next()) {
			sb.append("<tr>"
					+ "<td>" + rs.getString(2) + "</td>"
					+ "<td>" + rs.getString(3) + "</td>"
					+ "<td>" + rs.getString(4) + "</td>"
					+ "<td>" + rs.getString(5) + "</td>"
					+ "<td style=\"width:400px\">"
					+ "<div style=\"display:flex;flex-wrap:nowrap;width:300px;justify-content:space-around\">"
						//BORRAR
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Borrar socio</span>"
						+ "<a href=\"Web3103Servlet?nombre=" + rs.getString(2) + "&email=" + rs.getString(5) + "&opcion=5\">"
							+ "<i style=\"background:url('delete.png'); height:32px; width:32px;display:block\"></i>"
						+ "</a>"
						+ "</div>"
						//ACTUALIZAR
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Actualizar socio</span>"
						+ "<a href=\"Web3103.jsp?nombre=" + rs.getString(2) + "&email=" + rs.getString(5) + "&opcion=1\">"
							+ "<i style=\"background:url('refresh.png'); height:32px; width:32px;display:block;\"></i>"
						+ "</a>"
						+ "</div>"
						//MOSTRAR PRÉSTAMOS
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Ver préstamos</span>"
						+ "<a href=\"Web3103Servlet?nombre=" + rs.getString(2) + "&email=" + rs.getString(5) + "&opcion=10\">"
							+ "<i style=\"background:url('look.png'); height:32px; width:32px;display:block;\"></i>"
						+ "</a>"
						+ "</div>"
					+ "</div>"
					+ "</td>"
					+ "</tr>");
		}
		
		sb.append("</table>");
		
		msg = sb.toString();
		
		return msg;
		
	}
	public String mostrarLibros() throws ClassNotFoundException, SQLException{
		
		
		sb = new StringBuilder();
		sb.append("<table style=\"width:100%\">"
				+ "<tr>"
				+ "<th>Titulo</th>"
				+ "<th>Autor</th>"
				+ "<th>ISBN</th>"
				+ "<th>Estado</th>"
				//+ "<th>Opciones</th>"
				+ "</tr>");
		
		con = initializeDatabase();
		sql = "SELECT * FROM libro";
		
		//a través de esta string traducimos el true o false del booleano estado
		String estado;
		
		//aquí el statement ha de ser scrolleable para poder reacceder a los resultados de nuevo en el resultset
		stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		//PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//POR ALGUN MOTIVO?? USANDO EL RESULTSET ESTATICO OCURRE UN FALLO AL PEDIR EL ID DE NUEVO EN GETESOCIOFROMPRESTAMO()
		//USAMOS UN NUEVO RS
		//
		//VUELVE A FALLAR????
		ResultSet rs0;
		
		try {
			//rs = stmt.executeQuery();
			rs0 = stmt.executeQuery();
			//Si no hay ningún libro añadido, sql devuelve todo null y se maneja como un error de sintaxis.
			} catch (SQLSyntaxErrorException e) {
				msg = "<b>No hay ningún libro aún &#x1F438;</b>";
				return msg;
			}
		
		while (rs0.next()) {
			
			if (rs0.getBoolean(5)) {
				estado="Disponible";
			} else {
				
				estado="Prestado a<br>" + getNSocioFromPrestamo(rs0.getInt(1)) + " - " + getESocioFromPrestamo(rs0.getInt(1));
			}
			
			
			sb.append("<tr>"
					+ "<td>" + rs0.getString(2) + "</td>"
					+ "<td>" + rs0.getString(3) + "</td>"
					+ "<td>" + rs0.getString(4) + "</td>"
					+ "<td>" + estado + "</td>"
					+ "<td style=\"width:400px\">"
					//Borrar
					+ "<div style=\"display:flex;flex-wrap:nowrap;width:300px;justify-content:space-around\">"
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Borrar libro</span>"
						+ "<a style=\"height:32px;width:32px\"href=\"Web3103Servlet?isbn=" + rs0.getString(4)+ "&opcion=6" + "\">"
							+ "<i style=\"background:url('delete.png');height:32px;width:32px;display:block\"></i>"
						+ "</a>"
						+ "</div>"
					//Modificar
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Actualizar libro</span>"
						+ "<a href=\"Web3103.jsp?isbn=" + rs0.getString(4) + "&opcion=2\">"
							+ "<i style=\"background:url('refresh.png');height:32px;width:32px;display:block;\"></i>"
						+ "</a>"
						+ "</div>"
					//Rentar
						+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
						+ "<span class=\"tooltiptext\">Rentar libro</span>"
						+ "<a href=\"Web3103.jsp?isbn=" + rs0.getString(4) + "&opcion=3\">"
							+ "<i style=\"background:url('book.png');height:32px;width:32px;display:block\"></i>"
						+ "</a>"
						+ "</div>"
					+ "</div>"
					+ "</td>"
					+ "</tr>"
					);
		}
		
		sb.append("</table>");
		
		msg = sb.toString();
		return msg;
		
	}
	public String mostrarPrestamos(Socio socio) throws ClassNotFoundException, SQLException {
		
		con = initializeDatabase();
		sb = new StringBuilder();
		socio.setSocioID(getIDS(socio));
		
		sb.append("<h1>Préstamos de " + socio.getNombre() + "</h1>"
				+ "<br><br>"
				+ "<table style=\"width:100%\">"
				+ "<tr>"
				+ "<th>Fecha expedición</th>"
				+ "<th>Fecha devolución</th>"
				+ "<th>Titulo</th>"
				+ "<th>ISBN</th>"
				+ "</tr>");
		
		/*String sql = "SELECT socio_id FROM socio WHERE nombre=? AND email=?";
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		
		stmt.setString(1, socio.getNombre());
		stmt.setString(2, socio.getEmail());
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			
			socio.setSocioID(rs.getInt(1));
			
		}*/
		
		
		sql = "SELECT fk_libro_id, fecha_expedicion, fecha_devolucion FROM prestamolibro WHERE fk_socio_id=?";
		stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, socio.getSocioID());
		rs = stmt.executeQuery();
		
		ArrayList <Integer> id = new ArrayList<>();
		ArrayList <Date> expDate = new ArrayList<>();
		ArrayList <Date> devDate = new ArrayList<>();
			
		while (rs.next()) {
				
			id.add(rs.getInt(1));
			expDate.add(rs.getDate(2));
			devDate.add(rs.getDate(3));
			
			
		}
		if (id.isEmpty()) {
			
			return "<b>El socio no tiene ningún libro rentado &#x1F438;</b>";
			
		} else {
		
			for (Integer i: id) {
				
				sql = "SELECT titulo, isbn FROM libro WHERE libro_id=?";
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1, i);
				rs=stmt.executeQuery();
				int a=0; //int to go through our array lists
				
				while (rs.next()) {
					
					sb.append(
							
							"<td>"+ expDate.get(a) + "</td>"
							+ "<td>"+ devDate.get(a) + "</td>"
							+ "<td>"+ rs.getString(1) + "</td>"
							+ "<td>"+ rs.getString(2) + "</td>"
							+ "<td>"
							+ "<div style=\"margin-left:2em\" class=\"tooltip\">"
							+ "<span class=\"tooltiptext\">Devolver libro</span>"
								+ "<a href=Web3103Servlet?isbn="+ rs.getString(2) +"&opcion=11>"
								+ "<i style=\"background:url('return.png'); height:32px; width:32px; display:block\">"
								+ "</i>"
								+ "</a>"
							+ "</div>"
							+ "</td>"
							+ "</tr>"
							);
					a++;
				}
			}
			
			sb.append("</table>");
			
		}
		return sb.toString();
	}

	public void actualizarSocio(Socio socio) throws ClassNotFoundException, SQLException{
		
		con = initializeDatabase();
		sql = "UPDATE socio SET direccion=?, telefono=?, email=? WHERE nombre=? AND email=?";
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, socio.getDireccion());
		stmt.setString(2, socio.getTelefono());
		stmt.setString(3, socio.getEmail());
		stmt.setString(4, socio.getNombre());
		stmt.setString(5, socio.getTemp());
		
		stmt.executeUpdate();
		
	}
	public void actualizarLibro(Libro libro) throws ClassNotFoundException, SQLException{
		
		con = initializeDatabase();
		sql = "UPDATE libro SET titulo=?, autor=?, WHERE isbn=?";
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, libro.getTitulo());
		stmt.setString(2, libro.getAutor());
		stmt.setString(3, libro.getIsbn());
		stmt.executeUpdate();
		
	}

	public String prestarLibro(Socio socio, Libro libro) throws ClassNotFoundException, SQLException{
		
		
		//comprobar que el socio no tenga más de 3 libros rentados.
		//ir a la db y poner el libro como rentado.
		//ir a la db y añadir el nuevo prestamo.
		con = initializeDatabase();
		socio.setSocioID(getIDS(socio));
		libro.setLibroID(getIDL(libro));
		
		//buscamos el estado
		sql = "SELECT estado FROM libro WHERE isbn=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, libro.getIsbn());
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			
			if (!rs.getBoolean(1)) {
				msg = "<b>Libro no disponible<b>";
				return msg;
			}
		}
		
		//con el ID del socio miramos cuántos libros tiene en la tabla prestamolibro
		sql = "SELECT fk_libro_id FROM prestamolibro WHERE fk_socio_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, socio.getSocioID());
		
		try {
			rs = stmt.executeQuery();
			
		//si el user no tiene libros guardados tira un syntax error
		//y necesitamos ejecutar el código.
		//He tenido que poner el código 2 veces, en caso de que pille el catch y en caso de que no,
		//ya que en ambos casos queremos proceder con el préstamo
		} catch (SQLSyntaxErrorException e) {	
			
			int count=0;
			while(rs.next()) {
				count++;
			}
			//si tiene 3 o más libros, termina
			if (count >= 3) {
				
				msg = "<b>Tienes 3 o más libros prestados. Prestación cancelada.";
				return msg;
				
			}
				
				
			//sino, añade el préstamo a la tabla y actualiza el libro para poner como prestado
				
			date = new Date(System.currentTimeMillis());
				
			sql = "INSERT INTO prestamolibro (fecha_expedicion, fecha_devolucion, fk_libro_id, fk_socio_id) VALUES (?,?,?,?)";
				
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, date);
				
			BigDecimal bd = new BigDecimal("8.64e+8");
			long val = bd.longValue();
			date.setTime(date.getTime()+val);	//fecha de devolucion será la fecha de expedición más 10 días 
			stmt.setDate(2, date);
				
			stmt.setInt(3, libro.getLibroID());
			stmt.setInt(4, socio.getSocioID());
			count = stmt.executeUpdate();
				
			if (count > 0) {
					
				sql = "UPDATE libro SET estado=? WHERE isbn=?";
				
				stmt = con.prepareStatement(sql);
				stmt.setBoolean(1, false);
				stmt.setString(2, libro.getIsbn());
				stmt.executeUpdate();
					
				msg="<b>Prestamo realizado!</b>";
					
			}
		}
		
		int count=0;
		while(rs.next()) {
			count++;
		}
		//si tiene 3 o más libros, termina
		if (count >= 3) {
			
			msg = "<b>Tienes 3 o más libros prestados. Prestación cancelada.";
			return msg;
			
		}
			
			
		//sino, añade el préstamo a la tabla y actualiza el libro para poner como prestado
			
		date = new Date(System.currentTimeMillis());
			
		sql = "INSERT INTO prestamolibro (fecha_expedicion, fecha_devolucion, fk_libro_id, fk_socio_id) VALUES (?,?,?,?)";
			
		stmt = con.prepareStatement(sql);
		stmt.setDate(1, date);
			
		BigDecimal bd = new BigDecimal("8.64e+8");	//pasamos los ms de 10 días de notación científica a un long
		long val = bd.longValue();
		date.setTime(date.getTime()+val);	//fecha de devolucion será la fecha de expedición más 10 días 
		stmt.setDate(2, date);
			
		stmt.setInt(3, libro.getLibroID());
		stmt.setInt(4, socio.getSocioID());
		count = stmt.executeUpdate();
			
		if (count > 0) {
				
			sql = "UPDATE libro SET estado=? WHERE isbn=?";
			
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, false);
			stmt.setString(2, libro.getIsbn());
			stmt.executeUpdate();
				
			msg="<b>Prestamo realizado!</b>";
				
		}
		
		
		
			
		return msg;
	}
	public String devolverLibro(Libro libro) throws ClassNotFoundException, SQLException{
		
		con = initializeDatabase();
		libro.setLibroID(getIDL(libro));
		
		sql = "DELETE FROM prestamolibro WHERE fk_libro_id=?";
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, libro.getLibroID());
		
		int count = stmt.executeUpdate();
		if (count > 0) {
			msg = "<b>Libro devuelto!</b>";
		} else {
			return msg="<b>Algo ha ido mal...</b>";
		}
		
		sql = "UPDATE libro SET estado=true WHERE libro_id=?";
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, libro.getLibroID());
		stmt.executeUpdate();
		
		return msg;
	}

	public void borrarSocio(Socio socio) throws SQLException, ClassNotFoundException {
		
		con = initializeDatabase();
		socio.setSocioID(getIDS(socio));
		//Primero borramos los préstamos asociados a este usuario
		//sino nos dará error al mostrar los libros
		
		sql = "SELECT prestamo_id, fk_libro_id FROM prestamolibro WHERE fk_socio_id=?";
		stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, socio.getSocioID());
		rs = stmt.executeQuery();
		
		while(rs.next()) {
		
			sql = "DELETE FROM prestamolibro WHERE prestamo_id=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, rs.getInt(1));
			stmt.executeUpdate();
			
			sql = "UPDATE libro SET estado=1 WHERE libro_id=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, rs.getInt(2));
			stmt.executeUpdate();
			
		}
		
		sql = "DELETE FROM socio WHERE nombre=? AND email=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, socio.getNombre());
		stmt.setString(2, socio.getEmail());
		stmt.executeUpdate();
		
	}
	public void borrarLibro(Libro libro) throws SQLException, ClassNotFoundException {
		
		con = initializeDatabase();
		libro.setLibroID(getIDL(libro));
		
		//Primero borramos los préstamos asociados a este libro
		
		sql = "SELECT prestamo_id FROM prestamolibro WHERE fk_libro_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, libro.getLibroID());
		rs = stmt.executeQuery();
		
		rs.next();
		sql = "DELETE FROM prestamolibro WHERE prestamo_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, rs.getInt(1));
		stmt.executeUpdate();
		
		
		con = initializeDatabase();
		sql = "DELETE FROM libro WHERE isbn=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, libro.getIsbn());
		stmt.executeUpdate();
		
	}

	public static int getIDS(Socio socio) throws SQLException, ClassNotFoundException {
		
		con = initializeDatabase();
		sql = "SELECT socio_id FROM socio WHERE nombre=? AND email=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, socio.getNombre());
		stmt.setString(2, socio.getEmail());
		rs = stmt.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		//la función devuelve -1 si no se ha encontrado el ID, ya que no va a representar a ningún elemento en la tabla
		return -1;
	}
	public static int getIDL(Libro libro) throws SQLException, ClassNotFoundException {
		
		con = initializeDatabase();
		sql = "SELECT libro_id FROM libro WHERE isbn=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, libro.getIsbn());
		rs = stmt.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		//la función devuelve -1 si no se ha encontrado el ID, ya que no va a representar a ningún elemento en la tabla
		return -1;
	}

	//ESTAS DOS FUNCIONES NOS DEVUELVEN NOMBRE Y MAIL A TRAVÉS DEL ID. SON UTILIZADAS EN MOSTRAR PRESTAMOS 
	public static String getNSocioFromPrestamo(int id) throws ClassNotFoundException, SQLException {
		
		sql ="SELECT fk_socio_id FROM prestamolibro WHERE fk_libro_id=?";
		con = initializeDatabase();
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		rs.next();
		sql = "SELECT nombre FROM socio WHERE socio_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, rs.getInt(1));
		rs = stmt.executeQuery();
		rs.next();
		return rs.getString(1);
	}
	public static String getESocioFromPrestamo(int id) throws ClassNotFoundException, SQLException {
		
		sql ="SELECT fk_socio_id FROM prestamolibro WHERE fk_libro_id=?";
		con = initializeDatabase();
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		rs.next();
		sql = "SELECT email FROM socio WHERE socio_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, rs.getInt(1));
		rs = stmt.executeQuery();
		rs.next();
		return rs.getString(1);
	}

}
