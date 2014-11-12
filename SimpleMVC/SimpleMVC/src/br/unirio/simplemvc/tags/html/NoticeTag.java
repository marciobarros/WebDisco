package br.unirio.simplemvc.tags.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import br.unirio.simplemvc.actions.Action;

/**
 * Apresenta as notificações associadas à ação
 * 
 * @author Marcio Barros
 */
@SuppressWarnings("serial")
public class NoticeTag extends TagSupport
{	
	private static Properties redirectMessages = null;
	
	/**
	 * Apresenta as notificações associadas à ação
	 */
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
    {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		
		try 
		{
			List<String> notices = (List<String>) request.getAttribute(Action.NOTICES_KEY);

			if (notices != null)
				for (String value : notices)
					//geraNotificacao(pageContext.getOut(), value);
					pageContext.getOut().print("<p class=\"notice\">" + StringEscapeUtils.escapeHtml4(value) + "</p>");
			
			String noticeParameter = request.getParameter(Action.NOTICES_KEY);
			
			if (noticeParameter != null)
			{
				if (redirectMessages == null)
					carregaMensagensRedirecionamento();
				
				String mensagem = converteMensagensRedirecionamento(noticeParameter);
				//geraNotificacao(pageContext.getOut(), mensagem);
				pageContext.getOut().print("<p class=\"notice\">" + StringEscapeUtils.escapeHtml4(mensagem) + "</p>");
			}

			//pageContext.getOut().print("<script type=\"text/javascript\">$(document).ready(function() { $(\"div.notice div img\").click(function() { $(this).parent().parent().hide(); return false; }); });</script>");
		}
		catch (IOException e) 
		{
			new JspException(e.getMessage());
		}

        return (SKIP_BODY);
    }
	
	/**
	 * Carrega as mensagens de redirecionamento do disco
	 */
	private void carregaMensagensRedirecionamento()
	{
		redirectMessages = new Properties();
		
		try 
		{
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("redirectmsg.properties");
			
			if (is != null)
				redirectMessages.load(is);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Captura o texto da mensagem de redirecionamento, dado seu código
	 */
	private String converteMensagensRedirecionamento(String codigo)
	{
		String msg = redirectMessages.getProperty(codigo);
		return (msg != null) ? msg : codigo;
	}

	/**
	 * Gera o HTML responsável pela apresentação da mensagem
	 */
//	private void geraNotificacao(JspWriter out, String mensagem) throws IOException
//	{
//		mensagem = StringEscapeUtils.escapeHtml4(mensagem);
//		out.print("<div class=\"notice\"><div style='float:right;padding-right:4px;padding-top:4px;'/><img src=\"/img/icons/light_cross.png\" /></div><p class=\"notice\">" + mensagem + "</p></div>");
//	}
}