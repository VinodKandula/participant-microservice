package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Online;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Online
@Configuration
@EnableEurekaClient
public class EurekaConfig {
}
