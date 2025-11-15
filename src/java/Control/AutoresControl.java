/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import beans.Persona;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author garri
 */
@WebServlet(name = "AutoresControl", urlPatterns = {"/AutoresControl"})
public class AutoresControl extends HttpServlet {

    
 public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String accion = request.getParameter("boton");
        
        
        Persona p = new Persona();
        
  
        switch (accion) {
            
            case "Alta Autor":
                // 4. Llenamos el bean con los datos del formulario
                p.setId(request.getParameter("id"));
                p.setNombre(request.getParameter("nombre"));
                p.setApellido(request.getParameter("apellido"));
                p.setTipo(request.getParameter("tipo"));
                
                // 5. Le pedimos al bean que se guarde a sí mismo
                p.alta();
                break;

            case "Eliminar Persona": // Este sería el value del botón en 'bajal.html'
                p.setMatricula(request.getParameter("matricula"));
                p.bajaLogica();
                break;

            case "Consultar Persona": // Value del botón en 'consulta.html'
                p.setMatricula(request.getParameter("matricula"));
                
                // 7. Le pedimos al bean que busque los datos
                p.consulta();
                break;

            case "Modificar Persona": // Value del botón en 'modifica.html'
                p.setMatricula(request.getParameter("matricula"));
                p.setNombre(request.getParameter("nombre"));
                p.setApellido(request.getParameter("apellido"));
                p.setTipo(request.getParameter("tipo"));
                
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
