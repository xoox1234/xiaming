package com.smartvillage.config;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.google.common.collect.Lists.newArrayList;
import java.util.List;

/**
 * swagger-ui的配置
 * 
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
	
			@Bean
		    public Docket api() {
		        return new Docket(DocumentationType.SWAGGER_2)
		        		.apiInfo(apiInfo())
		                .useDefaultResponseMessages(false)
		                .select()
		                .apis(RequestHandlerSelectors.any())
		                .paths(PathSelectors.regex("^(?!product).*$"))
		                .build()
		                .securitySchemes(securitySchemes())
		                .securityContexts(securityContexts());
		    }
		
		    private List<ApiKey> securitySchemes() {
		        return newArrayList(
		                new ApiKey("x-auth-token", "x-auth-token", "header"));
		    }
		
		    private List<SecurityContext> securityContexts() {
		        return newArrayList(
		                SecurityContext.builder()
		                        .securityReferences(defaultAuth())
		                        .forPaths(PathSelectors.regex("^(?!product).*$"))
		                        .build()
		        );
		    }
		
		    private List<SecurityReference> defaultAuth() {
		        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		        authorizationScopes[0] = authorizationScope;
		        return newArrayList(
		                new SecurityReference("Authorization", authorizationScopes));
		    }
		private ApiInfo apiInfo() {
		    return new ApiInfoBuilder()
		        .title("智慧乡镇接口文档")
		        .description("1431")
		        .version("1.0.0")
		        .termsOfServiceUrl("http://www.baidu.com")
		        .license("LICENSE")
		        .licenseUrl("http://www.baidu.com")
		        .build();
		}

}
