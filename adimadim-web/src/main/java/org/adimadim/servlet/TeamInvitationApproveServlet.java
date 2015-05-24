/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.service.TeamService;

/**
 *
 * @author Ergo
 */
@WebServlet(name = "ActivationServlet", urlPatterns = {"/ActivationServlet"})
public class TeamInvitationApproveServlet extends HttpServlet {

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
            try {

                Integer teamId = Integer.parseInt(request.getParameter("team_id") == null ? "0" : request.getParameter("team_id"));
                Integer raceId = Integer.parseInt(request.getParameter("race_id") == null ? "0" : request.getParameter("race_id"));
                Integer accountId = Integer.parseInt(request.getParameter("account_id") == null ? "0" : request.getParameter("account_id"));
                Integer approve = Integer.parseInt(request.getParameter("approve") == null ? "-1" : request.getParameter("approve"));
                Context context = new InitialContext();
                TeamService teamService = (TeamService) context.lookup("java:app/adimadim-service-1.0/teamService");
                TeamMember teamMember = teamService.findTeamMember(teamId, raceId, accountId);

                if (teamMember == null) {
                    out.write("İlgili takım kişi kaydı bulunamadı");
                    return;
                }
                if (approve == 0) {
                    teamService.deleteTeamMember(teamMember);
                    out.write("Takımdan çıktınız");
                }
                if (approve == 0) {
                    teamMember.setIsApproved(Boolean.TRUE);
                    teamService.saveTeamMember(teamMember);
                    out.write("takım isteğini onayladınız");
                }
            } catch (Exception ex) {
                out.write("Hata oluştu");
            }
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
