/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private String BajaLogica;

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

    public String getBajaLogica() {
        return BajaLogica;
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

    public void setBajaLogica(String BajaLogica) {
        this.BajaLogica = BajaLogica;
    }
    
    public void alta() {
       
        try (Connection cn = new Conexion().conectar()) { 
            
            String sql = "INSERT INTO Autores (ID_Autor, Nombre, Apellido, Nacionalidad) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, this.nombre);
            ps.setString(3, this.apellido);
            ps.setString(4, this.nacionalidad);
            ps.executeUpdate();
            
            respuesta = "Autor registrado con exito";
            
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
            
            String sql = "UPDATE Autores SET BajaLogica = 1 WHERE ID_autor = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.setInt(1, this.id);
            
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                respuesta = "Autor dado de baja logicamente; ha cambiado su estado dentro de la base de datos.";
            } else {
                respuesta = "No se encontró el ID para la baja.";
            }
            
        } catch (Exception e) {
            respuesta = "Error en baja lógica: " + e.getMessage();
            e.printStackTrace();
        }
        
        
    }
    
    public void consulta (){
        try (Connection cn = new Conexion().conectar()) {
            
            String sql = "SELECT * FROM Autores WHERE ID_Autor = ? AND BajaLogica = 0";
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.setInt(1, this.id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                
            respuesta = "<b>ID:</b> " + rs.getInt("ID_Autor") +
                        "<br><b>Nombre:</b> " + rs.getString("nombre") + 
                        "<br><b>Apellido:</b> " + rs.getString("apellido") +
                        "<br><b>Nacionalidad:</b> " + rs.getString("nacionalidad");
                           
            } else {
            respuesta = "No se encontró el registro del autor, es probable que su estado sea baja.";
            }
            
        } catch (Exception e) {
            respuesta = "Error en consulta: " + e.getMessage();
            e.printStackTrace();
        }
        
        
    }
    
    public void modifica(){
         try (Connection cn = new  Conexion().conectar()) {
            
            String sql = "UPDATE Autores SET Nombre = ?, Apellido = ?, Nacionalidad = ? WHERE ID_Autor = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.setString(1, this.nombre);
            ps.setString(2, this.apellido);
            ps.setString(3, this.nacionalidad);
            ps.setInt(4, this.id); 
            
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                respuesta = "Datos del autor modificados exitosamente";
            } else {
                respuesta = "No se encontró el ID para modificar.";
            }
            
        } catch (Exception e) {
            respuesta = "Error en modificación: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
        
        
    }

    

