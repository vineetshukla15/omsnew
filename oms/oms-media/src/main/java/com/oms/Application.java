package com.oms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.tavant.api.auth.security.WebSecurityConfiguration;

import com.oms.listener.LoggingRequestInterceptor;

/**
 *
 https://github.com/royclarkson/spring-rest-service-oauth
 curl -X POST -vu clientapp:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=papidakos123&username=papidakos&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
 curl http://localhost:8080/api/sample -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoicGFwaWRha29zIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTQ1Njc5NzQzNiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjNlYjA3MjIzLWY5ZTAtNGI0NS1hYTM3LTVjOGYzZDg1YTVkNCIsImNsaWVudF9pZCI6ImNsaWVudGFwcCJ9.oD3jbGe0o69mJmPlcoc9ALsLyME8hwOkn9_5TJxt3l0"
 * Standard application controller
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.oms.repository","org.tavant.api.auth.repository"})
@ComponentScan({"com.api.email.*","com.oms.*", "org.tavant.api.*"})
@EntityScan(basePackages={"com.oms.model", "org.tavant.api.auth.model"})
public class Application extends SpringBootServletInitializer {

    /**
     * Entry point for the application.
     *
     * @param args Command line arguments.
     * @throws Exception Thrown when an unexpected Exception is thrown from the
     *         application.
     */
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Application.class, args);
    
    }
    
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {   
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); 
		return restTemplate;
	}

    /**
     * Create a CacheManager implementation class to be used by Spring where
     * <code>@Cacheable</code> annotations are applied.
     *
     * @return A CacheManager instance.
     */
/*    @Bean
    public CacheManager cacheManager() {

        GuavaCacheManager cacheManager = new GuavaCacheManager("com.oms");

        return cacheManager;
    }*/
    @Autowired
    WebSecurityConfiguration webSecurityConfiguration;
}


