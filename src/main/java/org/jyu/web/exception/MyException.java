package org.jyu.web.exception;

public class MyException extends RuntimeException{

	private static final long serialVersionUID = -1742528806192069444L;
	
	private String code;
	
	private String msg;

	public MyException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
