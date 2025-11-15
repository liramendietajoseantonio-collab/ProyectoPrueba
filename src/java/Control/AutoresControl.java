/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Autores;

/**
 *
 * @author garri
 */
@WebServlet(name = "AutoresControl", urlPatterns = {"/AutoresControl"})
public class AutoresControl extends HttpServlet {

    
 public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String accion = request.getParameter("boton");
        
        
        Autores p = new Autores();
        
  
        switch (accion) {
            
            case "Alta Autor":
               
                p.setId(Integer.parseInt(request.getParameter("ID_Autor")));
                p.setNombre(request.getParameter("nombre"));
                p.setApellido(request.getParameter("apellido"));
                p.setNacionalidad(request.getParameter("nacionalidad"));
                p.alta();
                break;

            case "Eliminar Autor": // Este sería el value del botón en 'bajal.html'
                p.setId(Integer.parseInt(request.getParameter("ID_autor")));
                p.bajaLogica();
                break;

            case "Consultar Persona": // Value del botón en 'consulta.html'
                p.setId(Integer.parseInt(request.getParameter("ID_autor")));
                
                // 7. Le pedimos al bean que busque los datos
                p.consulta();
                break;

            case "Modificar Persona": // Value del botón en 'modifica.html'
                p.setId(Integer.parseInt(request.getParameter("ID_autor")));
                p.setNombre(request.getParameter("nombre"));
                p.setApellido(request.getParameter("apellido"));
                p.setNacionalidad(request.getParameter("nacionalidad"));
                
                // 8. Le pedimos al bean que se actualice
                p.modifica();
                break;
                
            default:
                // Si se presiona un botón desconocido
                p.setRespuesta("Error: Acción desconocida.");
                break;
        }
        
        // 9. ENVIAR A LA RESPUESTA
        // Usamos el método RequestDispatcher. Es mejor que sendRedirect
        // porque no muestra la respuesta en la URL y maneja bien el HTML.
        request.setAttribute("respuesta", p.getRespuesta());
        request.getRequestDispatcher("respuesta.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador de altas, bajas y consultas de persona";
    }
}
