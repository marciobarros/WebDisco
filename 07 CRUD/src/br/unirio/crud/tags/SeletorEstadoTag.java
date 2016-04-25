package br.unirio.crud.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Setter;

import org.apache.commons.lang3.StringEscapeUtils;

import br.unirio.crud.model.Estado;

public class SeletorEstadoTag extends TagSupport
{
	private static final long serialVersionUID = 3816062477060938015L;

	private @Setter String id = "";
	private @Setter String name = "";
	private @Setter String value = "";
	private @Setter String classe = "";
	private @Setter String style = "";
	private @Setter String blankOption = "";

	@Override
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();

		try
		{
			String identificador = (id.length() > 0) ? id : name;
			out.write("<select name='" + StringEscapeUtils.escapeHtml4(name) + "' size='1' id='" + StringEscapeUtils.escapeHtml4(identificador) + "'");

			if (style.length() > 0)
				out.write(" style='" + StringEscapeUtils.escapeHtml4(style) + "'");

			if (classe.length() > 0)
				out.write(" class='" + StringEscapeUtils.escapeHtml4(classe) + "'");

			out.write(">\n");

			if (blankOption.length() > 0)
			{
				String selecionado = (value.length() == 0) ? " SELECTED" : "";
				out.write("<option value=''" + selecionado + ">" + StringEscapeUtils.escapeHtml4(blankOption) + "</option>\n");
			}

			for (Estado estado : Estado.values())
			{
				String selecionado = (value.compareToIgnoreCase(estado.getSigla()) == 0) ? " SELECTED" : "";
				out.write("<option value='" + estado.getSigla() + "'" + selecionado + ">" + StringEscapeUtils.escapeHtml4(estado.getNome()) + "</option>\n");
			}

			out.write("</select>\n");
		} catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}