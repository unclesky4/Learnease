package org.jyu.web.exception;

/**
 * @ClassName: EmailNotValidateException 
 * @Description: 邮箱未验证
 * @author: unclesky4
 * @date: May 12, 2018 6:02:35 PM
 */
public class EmailNotValidateException extends RuntimeException{

	private static final long serialVersionUID = -6022746625736134900L;

	public EmailNotValidateException() {
		super();
	}

	public EmailNotValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailNotValidateException(String message) {
		super(message);
	}
	
}
