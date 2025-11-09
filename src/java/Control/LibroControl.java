/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Libro;

/**
 *
 * @author linkl
 */
@WebServlet(name = "LibroControl", urlPatterns = {"/LibroControl"})
public class LibroControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LibroControl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LibroControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 3. Obtiene la acción del botón (ej. value="Alta Libro")
        String accion = request.getParameter("boton");
        
        // 4. Crea el bean de Libro
        Libro l = new Libro();
        
        // 5. Un switch para cada botón
        switch (accion) {
            
            case "Alta Libro":
                // 6. Llena el bean con los datos del formulario 'alta_libro.html'
                l.setTitulo(request.getParameter("titulo"));
                l.setIsbn(request.getParameter("isbn"));
                l.setStock_total(Integer.parseInt(request.getParameter("stock_total")));
                
                // 7. Llama al método 'alta()' del bean
                l.alta();
                break;
                
            case "Baja Libro":
                // 8. Llena el bean con los datos de 'bajal_libro.html'
                l.setId_libro(Integer.parseInt(request.getParameter("id_libro")));
                
                // 9. Llama al método 'bajaLogica()'
                l.bajaLogica();
                break;
                
            case "Consultar Libros": // Asumiendo que tu 'consulta_libro.html' pide un ID
                l.setId_libro(Integer.parseInt(request.getParameter("id_libro")));
                
                l.consulta();
                break;
                
            case "Modificar Libro":
                // 10. Llena el bean con todos los datos de 'modifica_libro.html'
                l.setId_libro(Integer.parseInt(request.getParameter("id_libro")));
                l.setTitulo(request.getParameter("titulo"));
                l.setIsbn(request.getParameter("isbn"));
                l.setStock_total(Integer.parseInt(request.getParameter("stock_total")));
                l.setStock_disponible(Integer.parseInt(request.getParameter("stock_disponible")));
                
                l.modifica();
                break;
                
            default:
                l.setRespuesta("Error: Acción desconocida en LibroControl.");
                break;
        }
        
        // 11. Envía la 'respuesta' (generada por el bean) al 'respuesta.jsp'
        request.setAttribute("respuesta", l.getRespuesta());
        request.getRequestDispatcher("respuesta.jsp").forward(request, response);
    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
