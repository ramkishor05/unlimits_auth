package com.brijframework.authorization.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brijframework.authorization.constant.Constants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	private final String moduleName = "Auth";
	private final String apiVersion = "1.1";

	@Value("${openapi.service.url}")
	private String serverUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = Constants.AUTHORIZATION;
		final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));

		return new OpenAPI().addServersItem(new Server().url(serverUrl))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName)).components(

						new Components().addSecuritySchemes(securitySchemeName,
								new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT").in(SecurityScheme.In.HEADER)))

				.info(new Info().title(apiTitle).version(apiVersion));
	}

}
