/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import beans.Prestamo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author linkl
 */
@WebServlet(name = "PrestamoControl", urlPatterns = {"/PrestamoControl"})
public class PrestamoControl extends HttpServlet {

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
            out.println("<title>Servlet PrestamoControl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PrestamoControl at " + request.getContextPath() + "</h1>");
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
        String accion = request.getParameter("boton");
        
        // 4. Crea el bean de Prestamo
        Prestamo p = new Prestamo();

        // 5. Un switch para cada botón
        switch (accion) {
            
            case "Solicitar Prestamo":
                // 6. Llena el bean con datos de 'alta_prestamo.html'
                p.setMatricula(request.getParameter("matricula"));
                p.setId_libro(Integer.parseInt(request.getParameter("id_libro")));
                
                // 7. Llama al método 'alta()' (que hace la transacción)
                p.alta();
                break;

            case "Devolver Prestamo":
                // 8. Llena el bean con datos de 'modifica_prestamo.html'
                p.setId_prestamo(Integer.parseInt(request.getParameter("id_prestamo")));
                
                // 9. Llama al método 'modifica()' (que devuelve y checa multas)
                p.modifica();
                break;

            case "Consultar Prestamos Activos":
                // 10. No necesita parámetros, solo llama a la consulta
                p.consulta();
                break;
                
            default:
                p.setRespuesta("Error: Acción desconocida en PrestamoControl.");
                break;
        }

        // 11. Envía la 'respuesta' (generada por el bean) al 'respuesta.jsp'
        request.setAttribute("respuesta", p.getRespuesta());
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
