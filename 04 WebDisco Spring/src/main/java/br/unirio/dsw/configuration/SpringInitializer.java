package br.unirio.dsw.configuration;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Classe que prepara a execução do framework Spring
 * 
 * @author marciobarros
 */
public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	/**
	 * Indica a classe de configuração do framewokr
	 */
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[] { SpringConfiguration.class };
	}

	/**
	 * Indica a classe com as configurações do servlet roteador
	 */
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return null;
	}

	/**
	 * Indica a máscara de ativação do servlet roteador
	 */
	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}

	/**
	 * Indica os filtros de servlets
	 */
	@Override
	protected Filter[] getServletFilters()
	{
		return new Filter[] { new CORSFilter() };
	}

	/**
	 * Configura as ações de inicialização da aplicação
	 */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException 
    {
		// Indica o contexto da aplicação
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringConfiguration.class);

        // Liga o servlet da camada de controle do framework
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
 
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        // Indica o encoding das requisições
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
 
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
 
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}

/**
 * Filtro de servlet que permite acesso a recursos de diferentes origens (Cross-origin requests)
 * 
 * Veja também: https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
 * 
 * @author marciobarros
 */
class CORSFilter implements Filter
{
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "X-requested-with, Content-Type");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig)
	{
	}

	public void destroy()
	{
	}
}