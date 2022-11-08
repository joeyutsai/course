package com.example.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 讓 Spring boot 來載入該類別設定
@EnableSwagger2 // 啟用 Swaggger2.createRestApi函數 建立 Docket的 Bean
public class SwaggerConfig {
	// 建立 API的基本資訊(這些資訊會顯示在文件頁面)
	// 造訪網址： http://專案實際IP:port/swagger-ui.html
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
			.title("Restful API")
			.build();
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO) // 顯示API基本資訊，可不加
				.select() // 回傳一個ApiSelectorBuilder實例，用來控制哪些介面可以給swagger來展現
				// 設定套件掃描路徑
				// swagger 會掃描套件下所有 controller定義的API，並產生文件
				// 若不想 API產生文件，可在 API上加上 @ApiIgnore
				.apis(RequestHandlerSelectors.basePackage("com.example.course.controller"))
				.paths(PathSelectors.any())
				.build();
	}
}


