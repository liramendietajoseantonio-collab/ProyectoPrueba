/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author garri
 */
public class Autores {
    private int id;
    private String nombre;
    private String apellido;
    private String respuesta;
    private String nacionalidad;
    private String estado;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void alta() {
       
        try (Connection cn = new Conexion().conectar()) { 
            
            String sql = "INSERT INTO Autores (ID_Autor, Nombre, Apellido, Nacionalidad, Estado) VALUES (?, ?, ?, ?, ? 'Habilitado')";
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.setInt(1, this.id);
            ps.setString(2, this.nombre);
            ps.setString(3, this.apellido);
            ps.setString(4, this.nacionalidad);
            ps.setString(5,this.respuesta);
            ps.executeUpdate();
            
            respuesta = "Persona registrada exitosamente.";
            
        } catch (SQLException e) {
            respuesta = "Error en alta: " + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            respuesta = "Error de conexión: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public void bajaLogica(){
        
    }
    
    public void consulta (){
        
    }
    
    public void modifica(){
        
        
    }

    
}
