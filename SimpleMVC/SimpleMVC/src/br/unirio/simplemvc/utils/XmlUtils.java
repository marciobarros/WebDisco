package br.unirio.simplemvc.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Classe de apoio para carga de arquivos em formato XML
 * 
 * @author marcio.barros
 */
public class XmlUtils
{
	/**
	 * Retorna um nó filho único de uma tag
	 */
	public static Element getSingleElement(Element element, String tagName)
	{
		NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() != 1)
			return null;
		
		return (Element) nodeList.item(0);
	}
	
	/**
	 * Loads an optional string attribute from a XML element
	 */
	public static String getStringAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);
		return (value != null) ? value : "";
	}
	
	/**
	 * Loads an optional integer attribute from a XML element
	 */
	public static int getIntAttribute(Element element, String name) 
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return 0;
		
		if (value.length() == 0)
			return 0;
		
		return Integer.parseInt(value);
	}

	/**
	 * Loads an optional double attribute from a XML element
	 */
	public static double getDoubleAttribute(Element element, String name) 
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return 0.0;
		
		if (value.length() == 0)
			return 0.0;
		
		return Double.parseDouble(value);
	}

	/**
	 * Loads an optional datetime attribute from a XML element
	 */
	public static DateTime getDateAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return null;
		
		if (value.length() == 0)
			return null;
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtf.parseDateTime(value);
	}

	/**
	 * Loads an optional string element value from a XML element
	 */
	public static String getStringNode(Element element, String name)
	{
		Element child = getSingleElement(element, name);
		
		if (child == null)
			return "";
		
		String value = child.getTextContent();
		
		if (value == null)
			return "";
		
		return value;
	}
	
	/**
	 * Loads an optional integer element value from a XML element
	 */
	public static int getIntNode(Element element, String name) 
	{
		Element child = getSingleElement(element, name);
		
		if (child == null)
			return 0;
		
		String value = child.getTextContent();
		
		if (value == null)
			return 0;
		
		if (value.length() == 0)
			return 0;
		
		return Integer.parseInt(value);
	}

	/**
	 * Loads an optional double element value from a XML element
	 */
	public static double getDoubleNode(Element element, String name) 
	{
		Element child = getSingleElement(element, name);
		
		if (child == null)
			return 0.0;
		
		String value = child.getTextContent();
		
		if (value == null)
			return 0.0;
		
		if (value.length() == 0)
			return 0.0;
		
		return Double.parseDouble(value);
	}

	/**
	 * Loads an optional datetime element value from a XML element
	 */
	public static DateTime getDateNode(Element element, String name)
	{
		Element child = getSingleElement(element, name);
		
		if (child == null)
			return null;
		
		String value = child.getTextContent();
		
		if (value == null)
			return null;
		
		if (value.length() == 0)
			return null;
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtf.parseDateTime(value);
	}
}