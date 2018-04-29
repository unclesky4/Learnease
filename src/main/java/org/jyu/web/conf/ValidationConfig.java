package org.jyu.web.conf;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 参数校验配置类
 * @ClassName: ValidationConfig 
 * @Description: 
 * @author: unclesky4
 * @date: Apr 29, 2018 8:12:40 PM
 */

@Configuration
@EnableAutoConfiguration
public class ValidationConfig {
	
	@Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
     return new MethodValidationPostProcessor();
    }
	
	/**
	 	@AssertFalse 校验false
		@AssertTrue 校验true
		@DecimalMax(value=,inclusive=) 小于等于value，
		inclusive=true,是小于等于
		@DecimalMin(value=,inclusive=) 与上类似
		@Max(value=) 小于等于value
		@Min(value=) 大于等于value
		@NotNull  检查Null
		@Past  检查日期
		@Pattern(regex=,flag=)  正则
		@Size(min=, max=)  字符串，集合，map限制大小
		@Valid 对po实体类进行校验
	 */

}
