/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author linkl
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Importante para las fechas

// import beans.Conexion; // Asumiendo que está en el paquete beans

public class Prestamo {

    // --- Atributos ---
    private int id_prestamo;
    private String matricula;
    private int id_libro;
    
    // Atributos de fecha (no los necesitamos como 'set' desde el form)
    // private Date fecha_prestamo;
    // private Date fecha_devolucion_esperada;
    // private Date fecha_devolucion_real;
    // private String estado;

    // --- Atributo para la respuesta ---
    private String respuesta;

    // --- Getters y Setters (solo los que recibimos del form) ---

    public int getId_prestamo() {
        return id_prestamo;
    }
    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getId_libro() {
        return id_libro;
    }
    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }
    
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    // ===================================
    // === MÉTODOS DE PROCESO (LÓGICA)
    // ===================================

    /**
     * ALTA (Solicitar Préstamo)
     * Proceso de transacción:
     * 1. Verifica que la Persona esté 'Habilitada'.
     * 2. Verifica que el Libro tenga 'Stock_Disponible' > 0.
     * 3. Inserta en 'Prestamos'.
     * 4. Resta 1 al 'Stock_Disponible' del Libro.
     */
    public void alta() {
        Connection cn = null;
        try {
            cn = new Conexion().conectar();
            // Iniciar transacción (muy importante)
            cn.setAutoCommit(false); 

            // --- 1. Verificar Persona ---
            PreparedStatement psCheckPersona = cn.prepareStatement("SELECT Estado FROM Personas WHERE Matricula = ? AND BajaLogica = 0");
            psCheckPersona.setString(1, this.matricula);
            ResultSet rsPersona = psCheckPersona.executeQuery();

            if (!rsPersona.next() || !rsPersona.getString("Estado").equals("Habilitado")) {
                throw new Exception("Error: Persona no encontrada, dada de baja o está 'Deshabilitada' (con multa pendiente).");
            }

            // --- 2. Verificar Libro ---
            PreparedStatement psCheckLibro = cn.prepareStatement("SELECT Stock_Disponible FROM Libros WHERE ID_Libro = ? AND BajaLogica = 0");
            psCheckLibro.setInt(1, this.id_libro);
            ResultSet rsLibro = psCheckLibro.executeQuery();

            if (!rsLibro.next() || rsLibro.getInt("Stock_Disponible") <= 0) {
                throw new Exception("Error: Libro no encontrado, dado de baja o sin stock disponible.");
            }

            // --- 3. Insertar Préstamo ---
            String sqlInsert = "INSERT INTO Prestamos (Matricula, ID_Libro, Estado) VALUES (?, ?, 'Activo')";
            PreparedStatement psInsert = cn.prepareStatement(sqlInsert);
            psInsert.setString(1, this.matricula);
            psInsert.setInt(2, this.id_libro);
            psInsert.executeUpdate();

            // --- 4. Actualizar Stock del Libro ---
            String sqlUpdate = "UPDATE Libros SET Stock_Disponible = Stock_Disponible - 1 WHERE ID_Libro = ?";
            PreparedStatement psUpdate = cn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, this.id_libro);
            psUpdate.executeUpdate();

            // Si todo salió bien, confirmar la transacción
            cn.commit();
            respuesta = "Préstamo registrado exitosamente.";

        } catch (Exception e) {
            // Si algo falla, deshacer todo (rollback)
            try {
                if (cn != null) cn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            respuesta = "Error en alta de préstamo: " + e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * MODIFICA (Devolver Préstamo)
     * Proceso de transacción:
     * 1. Obtiene el ID_Libro del préstamo.
     * 2. Actualiza el Préstamo a 'Devuelto' y pone Fecha_Devolucion_Real.
     * 3. Suma 1 al 'Stock_Disponible' del Libro.
     * 4. Verifica si se generó multa (y deshabilita a la persona).
     */
    public void modifica() {
        Connection cn = null;
        try {
            cn = new Conexion().conectar();
            cn.setAutoCommit(false);
            
            int idLibroDevuelto = 0;
            int diasRetraso = 0;
            String matriculaPersona = "";

            // --- 1. Obtener datos del préstamo (ID_Libro, Matricula y Días de Retraso) ---
            // Usamos GETDATE() de SQL Server para calcular el retraso al momento
            String sqlCheck = "SELECT ID_Libro, Matricula, DATEDIFF(day, Fecha_Devolucion_Esperada, GETDATE()) AS DiasRetraso FROM Prestamos WHERE ID_Prestamo = ? AND Estado = 'Activo'";
            PreparedStatement psCheck = cn.prepareStatement(sqlCheck);
            psCheck.setInt(1, this.id_prestamo);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                throw new Exception("Error: No se encontró el préstamo o ya había sido devuelto.");
            }
            idLibroDevuelto = rs.getInt("ID_Libro");
            matriculaPersona = rs.getString("Matricula");
            diasRetraso = rs.getInt("DiasRetraso");
            

            // --- 2. Actualizar Préstamo a 'Devuelto' ---
            String sqlUpdateP = "UPDATE Prestamos SET Estado = 'Devuelto', Fecha_Devolucion_Real = GETDATE() WHERE ID_Prestamo = ?";
            PreparedStatement psUpdateP = cn.prepareStatement(sqlUpdateP);
            psUpdateP.setInt(1, this.id_prestamo);
            psUpdateP.executeUpdate();

            // --- 3. Actualizar Stock del Libro ---
            String sqlUpdateL = "UPDATE Libros SET Stock_Disponible = Stock_Disponible + 1 WHERE ID_Libro = ?";
            PreparedStatement psUpdateL = cn.prepareStatement(sqlUpdateL);
            psUpdateL.setInt(1, idLibroDevuelto);
            psUpdateL.executeUpdate();

            // --- 4. Lógica de Multa ---
            String msgMulta = "Devolución registrada exitosamente.";
            
            if (diasRetraso > 0) {
                // Si hay retraso, deshabilitamos a la persona
                String sqlUpdatePer = "UPDATE Personas SET Estado = 'Deshabilitado' WHERE Matricula = ?";
                PreparedStatement psUpdatePer = cn.prepareStatement(sqlUpdatePer);
                psUpdatePer.setString(1, matriculaPersona);
                psUpdatePer.executeUpdate();

                // (NOTA: Aquí faltaría el INSERT en la tabla 'Multas' cuando la creemos)
                msgMulta = "<b>¡Devolución TARDÍA!</b> Se registró la devolución.<br>" +
                           "Se generó una multa por " + diasRetraso + " días.<br>" +
                           "La persona ha sido <b>Deshabilitada</b> para futuros préstamos.";
            }

            // Confirmar transacción
            cn.commit();
            respuesta = msgMulta;

        } catch (Exception e) {
            try {
                if (cn != null) cn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            respuesta = "Error al devolver préstamo: " + e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * CONSULTA (Ver Préstamos Activos)
     * Muestra todos los préstamos que no han sido devueltos.
     * Genera una tabla HTML como respuesta.
     */
    public void consulta() {
        try (Connection cn = new Conexion().conectar()) {
            
            // Consulta que une Préstamos, Personas y Libros
            String sql = "SELECT P.ID_Prestamo, P.Matricula, Per.Nombre, L.Titulo, P.Fecha_Prestamo, P.Fecha_Devolucion_Esperada " +
                         "FROM Prestamos P " +
                         "JOIN Personas Per ON P.Matricula = Per.Matricula " +
                         "JOIN Libros L ON P.ID_Libro = L.ID_Libro " +
                         "WHERE P.Estado = 'Activo' AND Per.BajaLogica = 0 AND L.BajaLogica = 0";
            
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            // Construir la tabla HTML
            StringBuilder tablaHtml = new StringBuilder();
            tablaHtml.append("<h2>Préstamos Activos</h2>");
            tablaHtml.append("<table border='1'>");
            tablaHtml.append("<tr><th>ID Préstamo</th><th>Matrícula</th><th>Persona</th><th>Libro</th><th>Fecha Préstamo</th><th>Fecha Devolución Esperada</th></tr>");
            
            int contador = 0;
            while (rs.next()) {
                tablaHtml.append("<tr>");
                tablaHtml.append("<td>").append(rs.getInt("ID_Prestamo")).append("</td>");
                tablaHtml.append("<td>").append(rs.getString("Matricula")).append("</td>");
                tablaHtml.append("<td>").append(rs.getString("Nombre")).append("</td>");
                tablaHtml.append("<td>").append(rs.getString("Titulo")).append("</td>");
                tablaHtml.append("<td>").append(rs.getDate("Fecha_Prestamo")).append("</td>");
                tablaHtml.append("<td>").append(rs.getDate("Fecha_Devolucion_Esperada")).append("</td>");
                tablaHtml.append("</tr>");
                contador++;
            }
            
            tablaHtml.append("</table>");
            
            if (contador == 0) {
                respuesta = "No se encontraron préstamos activos.";
            } else {
                respuesta = tablaHtml.toString();
            }
            
        } catch (Exception e) {
            respuesta = "Error en consulta de préstamos: " + e.getMessage();
            e.printStackTrace();
        }
    }
}
