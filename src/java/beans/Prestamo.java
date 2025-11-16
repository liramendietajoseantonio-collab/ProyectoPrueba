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

    public void baja(){
     
     } 
    
    
    public void consulta(){
        
    }
    
    public void modifica(){
        
    }
}

   

  
