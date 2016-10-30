package com.amhzing.participant.configuration;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;


public class SwaggerConfigTest {

    private AnnotationConfigWebApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void should_contain_docket() {
        load(TestSwaggerConfig.class);
        assertThat(context.getBeansOfType(Docket.class).values()).hasOnlyElementsOfType(Docket.class);
    }

    @Test
    public void should_not_contain_docket() {
        load(null);
        assertThat(context.getBeansOfType(Docket.class).values()).isEmpty();
    }

    private static class TestSwaggerConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2);
        }

    }

    private void load(Class<?> config, String... environment) {
        context = new AnnotationConfigWebApplicationContext();

        if (config != null) {
            context.register(config);
        }

        EnvironmentTestUtils.addEnvironment(context, environment);
        context.refresh();
    }

}