package org.jyu.web.pc2;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import edu.csus.ecs.pc2.core.IInternalController;
import edu.csus.ecs.pc2.core.Utilities;
import edu.csus.ecs.pc2.core.exception.MultipleIssuesException;
import edu.csus.ecs.pc2.core.model.ClientSettings;
import edu.csus.ecs.pc2.core.model.ContestInformation;
import edu.csus.ecs.pc2.core.model.IInternalContest;
import edu.csus.ecs.pc2.ui.AutoJudgingMonitor;

/**
 *
 * @author unclesky4
 * @date Feb 28, 2018 4:17:33 PM
 *
 */

public class AutoJudge {

	private IInternalContest contest;
	private IInternalController controller;
	private AutoJudgingMonitor autoJudgingMonitor = new AutoJudgingMonitor();

	public void setContestAndController(IInternalContest inContest, IInternalController inController) {
		contest = inContest;
		controller = inController;
		autoJudgingMonitor.setContestAndController(getContest(), getController());
		try {
			ContestInformation ci = contest.getContestInformation();
			if (ci != null) {
				String cdpPath = ci.getJudgeCDPBasePath();
				Utilities.validateCDP(contest, cdpPath);
			}
		} catch (MultipleIssuesException e) {
			System.err.println("Cannot perform Judging");
			String[] issueList = e.getIssueList();
			StringBuffer message = new StringBuffer();
			message.append("The following errors exist:\n");
			for (int i = 0; i < issueList.length; i++) {
				message.append(issueList[i] + "\n");
			}
			message.append("\nPlease correct and restart");
			System.err.println(message);
		}

		if (isAutoJudgingEnabled()) {
			startAutoJudging();
		}
	}

	protected String getL10nDateTime() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(3, 3, Locale.getDefault());
		return dateFormatter.format(new Date());
	}

	protected void startAutoJudging() {
		if (isAutoJudgingEnabled()) {
			new Thread(new Runnable()
			{
				public void run()
				{
					autoJudgingMonitor.startAutoJudging();
				}
	       }).start();
		} else {
			System.out.println("Administrator has turned off Auto Judging");
		}
	}

	private boolean isAutoJudgingEnabled() {
		ClientSettings clientSettings = getContest().getClientSettings();
		if ((clientSettings != null) && (clientSettings.isAutoJudging())) {
			return true;
		}
		return false;
	}

	public IInternalContest getContest() {
		return contest;
	}

	public IInternalController getController() {
		return controller;
	}
}
