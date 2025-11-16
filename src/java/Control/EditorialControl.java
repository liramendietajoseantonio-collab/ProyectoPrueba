/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import beans.Editorial;
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
@WebServlet(name = "Editorial", urlPatterns = {"/Editorial"})
public class EditorialControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String accion = request.getParameter("boton");
        
        
       Editorial p = new Editorial();
        
  
        switch (accion) {
            
            case "Alta Editorial":
                p.setId(Integer.parseInt(request.getParameter("ID_Editorial")));
                p.setNombre(request.getParameter("nombre"));
                p.setPais(request.getParameter("pais"));
                p.alta();
                break;

            case "Eliminar Editorial": 
                p.setId(Integer.parseInt(request.getParameter("ID_Editorial")));
                p.bajaLogica();
                break;

            case "Consultar Editorial": 
                p.setId(Integer.parseInt(request.getParameter("ID_Editorial")));
                p.consulta();
                break;

            case "Modificar Editorial": 
                p.setId(Integer.parseInt(request.getParameter("ID_Editorial")));
                p.setNombre(request.getParameter("nombre"));
                p.setPais(request.getParameter("pais"));
            
                p.modifica();
                break;
                
            default:
                
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