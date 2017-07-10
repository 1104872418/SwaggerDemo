package com.config;



import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;


/**
 * @项目：phshopping-common-core
 *
 * @描述：数据库配置
 *
 * @作者： chang
 *
 * @创建时间：2017年3月8日
 *
 * @Copyright @2017 by Mr.chang
 */
@Configuration
public class DatabaseConfiguration implements EnvironmentAware{
	//日志对象
	private static Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
	private RelaxedPropertyResolver prop; 
	private Environment environment;
    
	@Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

	public void setEnvironment(Environment environment) {
		this.environment = environment;
        this.prop = new RelaxedPropertyResolver(environment,"spring.datasource.");
	}
	
	/**
	 * @配置数据库连接池配置
	 * @return
	 */
	@Bean(initMethod="init",destroyMethod="close",name="dataSource")
	public DataSource dataSource(){
		log.info("数据库连接池加载中··············");
		if (StringUtils.isEmpty(prop.getProperty("url"))) {
			log.error("数据库连接池配置错误,请检查spring配置文件,当前spring配置文件为："+Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("数据库连接池配置错误!");
        }else{
        	DruidDataSource druid=new DruidDataSource();
        	//设置连接池连接基本信息
        	druid.setUrl(prop.getProperty("url"));
        	druid.setUsername(prop.getProperty("username"));
        	druid.setPassword(prop.getProperty("password"));
        	druid.setDriverClassName(prop.getProperty("driverClassName"));
        	//连接池初始化参数设置
        	druid.setInitialSize(Integer.valueOf(prop.getProperty("druid.initialSize")));
        	druid.setMinIdle(Integer.valueOf(prop.getProperty("druid.minIdle")));
        	druid.setMaxActive(Integer.valueOf(prop.getProperty("druid.maxActive")));
        	druid.setMaxWait(Integer.valueOf(prop.getProperty("druid.maxWait")));
        	druid.setTimeBetweenConnectErrorMillis(Long.valueOf(prop.getProperty("druid.timeBetweenEvictionRunsMillis")));
        	druid.setMinEvictableIdleTimeMillis(Long.valueOf(prop.getProperty("druid.minEvictableIdleTimeMillis")));
        	druid.setValidationQuery(prop.getProperty("druid.validationQuery"));
        	druid.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("druid.testWhileIdle")));
        	druid.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("druid.testOnBorrow")));
        	druid.setTestOnReturn(Boolean.parseBoolean(prop.getProperty("druid.testOnReturn")));
        	druid.setConnectionProperties(prop.getProperty("druid.connectionProperties"));
        	 try {
        		 druid.setFilters("stat,wall,slf4j,config");
             } catch (SQLException e) {
                 log.error("druid数据库连接池初始化异常");
             }
        	return druid;
        }
	}
	
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean fr = new FilterRegistrationBean();
        fr.setFilter(new WebStatFilter());
        fr.addUrlPatterns("/*");
        fr.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return fr;
    }
}
