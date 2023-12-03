package kr.taps.app.api.common.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        List<RequestParameter> globalParameters = getRequestParameters();

        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("kr.taps.app"))
            .paths(PathSelectors.any())
//            .apis(RequestHandlerSelectors.withMethodAnnotation(JwtTokenValidation.class))
            .build()
            .apiInfo(apiInfo())
            .globalRequestParameters(globalParameters);
    }

    // Swagger에서 Authorization 헤더를 보여주게 합니다.
    // 필수는 아님..
    private static List<RequestParameter> getRequestParameters() {
        List<RequestParameter> globalParameters = new ArrayList<>();
        globalParameters.add(
            new RequestParameterBuilder()
                .name("Authorization")
                .description("JWT Token")
                .in(ParameterType.HEADER)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)))
                .required(false)
                .build()
        );
        return globalParameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TAPS-AGGREGATOR-API")
                .version("1.0.0")
                .description("TAPS AGGREGATOR API")
                .build();
    }


}