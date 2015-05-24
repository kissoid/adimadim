/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.servlet;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.adimadim.bean.AccountBean;
import org.adimadim.db.entity.Account;
import org.adimadim.util.BipNumberUtil;
import org.adimadim.util.EmailUtil;

/**
 *
 * @author Adem
 */
@WebServlet(name = "BipNumberServlet", urlPatterns = {"/insession/BipNumberServlet"})
public class BipNumberServlet extends HttpServlet {

    @Inject
    private AccountBean accountBean;

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

        OutputStream os = response.getOutputStream();
        try {
            Integer chestNumber = accountBean.getAccount().getChestNumber();
            String name = (accountBean.getAccount().getName() + " - " + accountBean.getAccount().getSurname());
            ByteArrayOutputStream baos = BipNumberUtil.createBipNumberDocument(chestNumber, name);

            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            baos.writeTo(os);
            os.flush();
        } catch (Exception ex) {
            Logger.getLogger(BipNumberServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            os.close();
        }

        try {
            sendBipNumber(accountBean.getAccount());
        } catch (Exception ex) {
            Logger.getLogger(BipNumberServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendBipNumber(Account account) throws DocumentException, BadElementException, IOException, MessagingException {
        Integer bipNumber = account.getChestNumber();
        String name = (account.getName() + " " + account.getSurname());
        String receiver = account.getEmail();
        String subject = "Gogus Numarasi";
        String content = "Gogus numaraniz PDF dosyasi olarak ektedir.";
        String fileName = "GogusNo.pdf";
        String fileFormat = "application/pdf";
        ByteArrayOutputStream file = BipNumberUtil.createBipNumberDocument(bipNumber, name);
        EmailUtil.sendMailWithAttachment(EmailUtil.SENDER_INFO, receiver, subject, content, fileName, fileFormat, file.toByteArray());
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
        processRequest(request, response);
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
