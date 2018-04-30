package org.jyu.web.service.pc2;

import java.util.Map;

import edu.csus.ecs.pc2.core.model.Problem;

/**
 * 关于PC2的Problem操作接口
 * @author unclesky4 09/10/2017
 *
 */
public interface PC2ProblemService {

	/**
	 * 获取所有的Problem
	 * @return
	 */
	public Map<String, Problem> getAllProblem();
	
	/**
	 * 更新一个Problem
	 */
	public void updateProblem(Problem problem);
}
