package org.jyu.web.conf;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.jyu.web.dto.Result;
import org.jyu.web.exception.MyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 拦截异常并统一处理
 * @author unclesky4
 *
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

	/**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "unclesky4");
    }
    
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public Map<String, Object> errorHandler(Exception ex) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("success", false);
//        map.put("msg", ex.getMessage());
//        return map;
//    }
    
    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Result myErrorHandler(MyException ex) {
        Result result = new Result(false, ex.getMessage());
        return result;
    }
    
    /**
     * 空指针异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public Result NullPointerException(NullPointerException ex) {
    	Result result = new Result(false, "操作失败");
    	ex.printStackTrace();
        return result;
    }
    
    @ResponseBody
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public Result IncorrectCredentialsException(IncorrectCredentialsException ex) {
    	Result result = new Result(false, "密码错误");
    	return result;
    }
    
    @ResponseBody
    @ExceptionHandler(value = UnknownAccountException.class)
    public Result UnknownAccountException(UnknownAccountException ex) {
    	Result result = new Result(false, "用户名不存在");
    	return result;
    }
}
