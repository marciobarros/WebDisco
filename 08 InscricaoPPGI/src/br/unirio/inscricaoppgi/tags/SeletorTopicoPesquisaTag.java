package br.unirio.inscricaoppgi.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import br.unirio.inscricaoppgi.model.TopicoPesquisa;

public class SeletorTopicoPesquisaTag extends TagSupport
{
	private static final long serialVersionUID = 3816062477060938015L;

	private String id;
	private String nome;
	private String valor;
	private String classe;
	private String estilo;
	private String opcaoVazio;

	public SeletorTopicoPesquisaTag()
	{
		this.id = "";
		this.nome = "";
		this.valor = "";
		this.classe = "";
		this.estilo = "";
		this.opcaoVazio = "";
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String nome)
	{
		this.nome = nome;
	}

	public void setValue(String valor)
	{
		this.valor = valor;
	}

	public void setClasse(String classe)
	{
		this.classe = classe;
	}

	public void setStyle(String estilo)
	{
		this.estilo = estilo;
	}

	public void setBlankOption(String opcao)
	{
		this.opcaoVazio = opcao;
	}

	@Override
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();

		try
		{
			String identificador = (id.length() > 0) ? id : nome;

			out.write("<select name='" + StringEscapeUtils.escapeHtml4(nome) + "' size='1' id='" + StringEscapeUtils.escapeHtml4(identificador) + "'");

			if (estilo.length() > 0)
				out.write(" style='" + StringEscapeUtils.escapeHtml4(estilo) + "'");

			if (classe.length() > 0)
				out.write(" class='" + StringEscapeUtils.escapeHtml4(classe) + "'");

			out.write(">\n");

			if (opcaoVazio.length() > 0)
			{
				String selecionado = (valor.length() == 0) ? " SELECTED" : "";
				out.write("<option value=''" + selecionado + ">" + StringEscapeUtils.escapeHtml4(opcaoVazio) + "</option>\n");
			}

			for (TopicoPesquisa topico : TopicoPesquisa.values())
			{
				String selecionado = (topico.getCodigo().compareToIgnoreCase(valor) == 0) ? " SELECTED" : "";
				out.write("<option value='" + topico.getCodigo() + "'" + selecionado + ">" + StringEscapeUtils.escapeHtml4(topico.getNome()) + "</option>\n");
			}

			out.write("</select>\n");
		} catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}