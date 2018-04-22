package br.unirio.dsw.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import br.unirio.dsw.service.message.ExposedResourceMessageBundleSource;

/**
 * Classe que configura o framework Spring
 * 
 * @author marciobarros
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "br.unirio.dsw")
public class SpringConfiguration extends WebMvcConfigurerAdapter
{
	/**
	 * Configura a classe que resolve o acesso aos arquivos JSP
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	/**
	 * Registra os diretórios onde são armazenados os recursos do sistema 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	/**
	 * Habilita a configuração do framework por anotações
	 */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 
    {
        configurer.enable();
    }
 
    /**
     * Configura a classe que resolve exceções do sistema
     */
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() 
    {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
 
        Properties exceptionMappings = new Properties();
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
        exceptionResolver.setExceptionMappings(exceptionMappings);
 
        Properties statusCodes = new Properties();
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
        exceptionResolver.setStatusCodes(statusCodes);
 
        return exceptionResolver;
    }
    
	/**
	 * Retorna o objeto responsável pela tradução de mensagens
	 */
	@Bean
    public ExposedResourceMessageBundleSource messageSource() 
    {
		ExposedResourceMessageBundleSource messageSource = new ExposedResourceMessageBundleSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}