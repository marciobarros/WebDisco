package br.unirio.dsw.configuration;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import br.unirio.dsw.service.message.ExposedResourceMessageBundleSource;

/**
 * Classe que configura o framework Spring
 * 
 * @author marciobarros
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "br.unirio.dsw")
@PropertySource("classpath:configuration.properties")
@Import({ SecurityConfiguration.class })
public class SpringConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware
{
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver viewResolver()
	{
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine()
	{
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		return engine;
	}

	private ITemplateResolver templateResolver()
	{
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	/**
	 * Configura a classe que resolve o acesso aos arquivos JSP
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".html");
//		registry.viewResolver(viewResolver);
		registry.viewResolver(viewResolver());
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

	/**
	 * Prepara a leitura do arquivo de propriedades
	 */
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}

	// @Override
	// public void setApplicationContext(ApplicationContext applicationContext)
	// throws BeansException
	// {
	// this.applicationContext = applicationContext;
	// }
}