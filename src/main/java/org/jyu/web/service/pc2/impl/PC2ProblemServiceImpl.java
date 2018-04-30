package org.jyu.web.service.pc2.impl;

import java.util.HashMap;
import java.util.Map;

import org.jyu.web.conf.InitPC2;
import org.jyu.web.service.pc2.PC2ProblemService;
import org.springframework.stereotype.Service;

import edu.csus.ecs.pc2.core.model.Problem;

/**
 * 
 * @author unclesky4 09/10/2017
 *
 */
@Service(value="pc2ProblemService")
public class PC2ProblemServiceImpl implements PC2ProblemService {

	@Override
	public Map<String, Problem> getAllProblem() {
		Problem[] problems = InitPC2.getJudge_serverConnection().getIInternalContest().getProblems();
		Map<String, Problem> map = new HashMap<String, Problem>();
		for(int i=0; i<problems.length; i++) {
			map.put(problems[i].getShortName(), problems[i]);
		}
		return map;
	}

	@Override
	public void updateProblem(Problem problem) {
		InitPC2.getAdmin_serverConnection().getIInternalContest().updateProblem(problem);
	}

}
