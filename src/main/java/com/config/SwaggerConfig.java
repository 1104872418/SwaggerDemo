package com.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @项目：phshopping-parent
 * @描述：swagger配置
 * @作者： Mr.chang
 * @创建时间：2017/6/15
 * @Copyright @2017 by Mr.chang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
    	//可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("token") //参数名
                .defaultValue("") //默认值
                .description("header中token值")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        List<Parameter> aParameters = new ArrayList<Parameter>();
        Parameter build = aParameterBuilder.build();
        aParameters.add(build);
    	
    	
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo()).globalOperationParameters(aParameters);
    }
    private ApiInfo apiInfo() {
        Contact contact=new Contact("Mr.Chang","www.phds315.com","511098425@qq.com");
        return new ApiInfoBuilder()
                .title("测试文档")
                .description("测试接口文档")
                .contact(contact)
                .version("1.0")
                .license("测试")
                .licenseUrl("www.ceshi.com")
                .build();
    }
}
