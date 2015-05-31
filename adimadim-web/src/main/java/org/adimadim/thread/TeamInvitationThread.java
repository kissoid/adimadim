/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.thread;

import com.ergo.insyst.common.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adimadim.common.util.EmailUtil;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;

/**
 *
 * @author Adem
 */
public class TeamInvitationThread extends Thread {

    private Team team;
    private Account managerAccount;
    private List<TeamMember> newMembers;

    public TeamInvitationThread(Team team, Account managerAccount, List<TeamMember> newMembers) {
        this.team = team;
        this.managerAccount = managerAccount;
        this.newMembers = newMembers;
    }

    @Override
    public void run() {

        for (TeamMember teamMember : newMembers) {
            try {
                if (teamMember.getIsApproved()) {
                    continue;
                }

                StringBuilder mailContent = new StringBuilder();
                mailContent.append(createMemberInfo(teamMember));
                mailContent.append(createManagerInvitation());

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
        content.append("Sayın");
        content.append(" ");
        content.append(teamMember.getAccount().getName());
        content.append(" ");
        content.append(teamMember.getAccount().getSurname());
        content.append(",\n");
        return content.toString();
    }

    private String createManagerInvitation() throws ParseException {
        String managerName = managerAccount.getName() + " " + managerAccount.getSurname();
        Date raceDate = team.getRace().getRaceDate();
        String raceName = team.getRace().getRaceName();

        StringBuilder templateContent = new StringBuilder();
        templateContent.append(" ");
        templateContent.append(managerName);
        templateContent.append(" sizi, ");
        templateContent.append(DateUtil.dateToString(raceDate));
        templateContent.append(" tarihindeki AdımAdım'ın düzenlediği, ");
        templateContent.append(raceName);
        templateContent.append(" yarışında, ");
        templateContent.append(team.getTeamName());
        templateContent.append(" adlı takımın koşucuları arasına ekledi ve sizi bu takımda yarışmaya davet etti.\n");
        return templateContent.toString();
    }

}
