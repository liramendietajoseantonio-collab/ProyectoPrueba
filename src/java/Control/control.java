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
import beans.Persona;

@WebServlet(name = "control", urlPatterns = {"/Control"})
public class control extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtenemos la acción del botón (ej. value="Alta Alumno")
        String accion = request.getParameter("boton");
        
        // 2. Creamos el bean "todo-en-uno"
        Persona p = new Persona();
        
        // 3. Usamos un switch para decidir qué hacer
        switch (accion) {
            
            case "Alta":
                // 4. Llenamos el bean con los datos del formulario
                p.setMatricula(request.getParameter("matricula"));
                p.setNombre(request.getParameter("nombre"));
                p.setApellido(request.getParameter("apellido"));
                p.setTipo(request.getParameter("tipo"));
                
                // 5. Le pedimos al bean que se guarde a sí mismo
                p.alta();
                break;

            case "Baja Alumno": // Este sería el value del botón en 'bajal.html'
                p.setMatricula(request.getParameter("matricula"));
                
                // 6. Le pedimos al bean que aplique la baja lógica
                p.bajaLogica();
                break;

            case "Consultar Alumno": // Value del botón en 'consulta.html'
                p.setMatricula(request.getParameter("matricula"));
                
                // 7. Le pedimos al bean que busque los datos
                p.consulta();
                break;

            case "Modificar Alumno": // Value del botón en 'modifica.html'
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
