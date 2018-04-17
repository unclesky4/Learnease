package org.jyu.web.dto;

/**
 * 
 * @ClassName: Result  
 * @Description: 返回结果类
 * @author unclesky4 
 * @date Mar 24, 2018 12:22:10 PM 
 *
 */
public class Result {

	private Boolean success;
	
	private Object msg;
	
	public Result(Boolean success, Object msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
	
}
