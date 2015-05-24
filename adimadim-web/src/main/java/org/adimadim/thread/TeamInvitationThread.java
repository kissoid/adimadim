/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.thread;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.util.EmailUtil;

/**
 *
 * @author Adem
 */
public class TeamInvitationThread extends Thread {

    private Team team;
    private Account managerAccount;

    public TeamInvitationThread(Team team, Account managerAccount) {
        this.team = team;
        this.managerAccount = managerAccount;
    }

    @Override
    public void run() {

        for (TeamMember teamMember : team.getTeamMemberList()) {
            try {
                if (teamMember.getIsApproved()) {
                    continue;
                }

                StringBuilder mailContent = new StringBuilder();
                mailContent.append(createMemberInfo(teamMember));
                mailContent.append(createManagerInvitation());
                mailContent.append(createApproveLink(teamMember, true));
                mailContent.append(createApproveLink(teamMember, false));

                String receiver = teamMember.getAccount().getEmail();
                String subject = "Takim maratonu daveti";

                EmailUtil.sendMail(EmailUtil.SENDER_INFO, receiver, subject, mailContent.toString());
            } catch (Exception ex) {
                Logger.getLogger(TeamInvitationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String createMemberInfo(TeamMember teamMember) {
        StringBuilder content = new StringBuilder();
        content.append("Sayın").append(" ").append(teamMember.getAccount().getName()).append(" ").append(teamMember.getAccount().getSurname()).append(",\n");
        return content.toString();
    }

    private String createManagerInvitation() {
        String managerName = managerAccount.getName() + " " + managerAccount.getSurname();
        String raceDate = team.getRace().getRaceDate().toString();
        String raceName = team.getRace().getRaceName();

        StringBuilder templateContent = new StringBuilder();
        templateContent.append(" ").append(managerName).append(" sizi ").append(raceDate).append(" tarihindeki AdımAdım ").append(raceName).append(" yarışına davet etti.\n");
        return templateContent.toString();
    }

    private String createApproveLink(TeamMember teamMember, Boolean approve) throws MessagingException {
        String header = "";
        if (approve) {
            header = "İsteği onaylamak için lütfen aşağıdaki linke tıklayınız\n";
        } else {
            header = "İsteği reddetmek için lütfen aşağıdaki linke tıklayınız\n";
        }
        String link = "http://www.aakosu.org/TeamInvitationApproveServlet";
        link += "?race_id=" + teamMember.getTeam().getRace().getRaceId().toString();
        link += "&team_id=" + teamMember.getTeam().getTeamId().toString();
        link += "&account_id=" + teamMember.getAccount().getAccountId().toString();
        link += "&approve=" + (approve.equals(Boolean.TRUE) ? "1" : "0");
        return header + "<a href='" + link + "'>" + link + "</a>\n";
    }
}
