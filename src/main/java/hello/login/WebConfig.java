package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) { //WebMvcConfigure에서 제공하는 메서드 -> 인터셉터 등록 가능
        registry.addInterceptor(new LoginInterceptor()) //인터셉터 등록
                .order(1) //인터셉터 호출 순서
                .addPathPatterns("/**") //인터셉터를 적용할 URL 패턴 지정
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //인터셉터에서 제외할 패턴 지정

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"
                );

    }
}
