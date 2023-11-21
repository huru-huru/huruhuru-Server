package com.huruhuru.huruhuru.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("classpath:/.env")
@PropertySource("classpath:application-SECRET-KEY.properties")
public class PropertyConfig {
}
