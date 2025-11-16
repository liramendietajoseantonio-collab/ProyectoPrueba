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
import java.sql.Date;



public class Prestamo {

   
    private int id_prestamo;
    private String matricula;
    private int id_libro;
    private Date fecha_prestamo;
    
    private String respuesta;

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    
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
    
    public void alta() {
    try (Connection cn = new Conexion().conectar()) {
        
        String sql = "INSERT INTO Prestamos (Matricula, ID_Libro, Fecha_Prestamo) VALUES (?, ?, ?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        
        ps.setString(1, this.matricula);
        ps.setInt(2, this.id_libro);
        ps.setDate(3, this.fecha_prestamo);
        
        ps.executeUpdate();
        
        respuesta = "Prestamo registrado con exito";
        
    } catch (SQLException e) {
        respuesta = "Error en alta: " + e.getMessage();
        e.printStackTrace();
    } catch (Exception e) {
        respuesta = "Error de conexión: " + e.getMessage();
        e.printStackTrace();
    }
    }

   public void bajaLogica(){
    try (Connection cn = new Conexion().conectar()) {
        
        String sql = "UPDATE Prestamos SET Estado = 'Cancelado' WHERE ID_Prestamo = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        
        ps.setInt(1, this.id_prestamo);
        
        int filas = ps.executeUpdate();
        
        if (filas > 0) {
            respuesta = "Prestamo dado de baja logicamente; ha cambiado su estado dentro de la base de datos.";
        } else {
            respuesta = "No se encontró el ID para la baja.";
        }
        
    } catch (Exception e) {
        respuesta = "Error en baja lógica: " + e.getMessage();
        e.printStackTrace();
    }
}

    
    
    public void consulta() {
    try (Connection cn = new Conexion().conectar()) {
        
        String sql = "SELECT * FROM Prestamos WHERE ID_Prestamo = ? AND Estado = 'Activo'";
        PreparedStatement ps = cn.prepareStatement(sql);
        
        ps.setInt(1, this.id_prestamo);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            respuesta = "<b>ID Préstamo:</b> " + rs.getInt("ID_Prestamo") +
                        "<br><b>Matrícula (Persona):</b> " + rs.getString("Matricula") +
                        "<br><b>ID Libro:</b> " + rs.getInt("ID_Libro") +
                        "<br><b>Fecha Préstamo:</b> " + rs.getDate("Fecha_Prestamo") +
                        "<br><b>Fecha Devolución Esperada:</b> " + rs.getDate("Fecha_Devolucion_Esperada") +
                        "<br><b>Estado:</b> " + rs.getString("Estado");
        } else {
            respuesta = "No se encontró el préstamo o no está activo.";
        }
        
    } catch (Exception e) {
        respuesta = "Error en consulta: " + e.getMessage();
        e.printStackTrace();
    }
}

    
    public void modifica() {
    try (Connection cn = new Conexion().conectar()) {
        
        String sql = "UPDATE Prestamos SET Estado = 'Devuelto', Fecha_Devolucion_Real = GETDATE() WHERE ID_Prestamo = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        
        ps.setInt(1, this.id_prestamo);
        
        int filas = ps.executeUpdate();
        
        if (filas > 0) {
            respuesta = "Prestamo devuelto exitosamente";
        } else {
            respuesta = "No se encontró el ID para modificar.";
        }
        
    } catch (Exception e) {
        respuesta = "Error en modificación: " + e.getMessage();
        e.printStackTrace();
    }
}
}

   

  
