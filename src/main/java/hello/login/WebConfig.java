package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    //필터 등록
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터 지정
        filterRegistrationBean.setOrder(1); //필터는 체인이므로 순서 등록
        filterRegistrationBean.addUrlPatterns("/*"); //필터 적용할 URL 패턴 지정

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoginCheckFilter()); //로그인 필터 등록
        filterRegistrationBean.setOrder(2); //순서 2번, 로그 필터(1) -> 로그인 필터(2) 적용
        filterRegistrationBean.addUrlPatterns("/*"); //모든 요청에 로그인 필터 적용

        return filterRegistrationBean;
    }
}
