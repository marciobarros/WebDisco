package br.unirio.simplemvc.utils;

import java.util.ArrayList;

/**
 * Utility class that represents a list of strings without duplicates
 * 
 * @author marcio.barros
 *
 */
public class StringCollection extends ArrayList<String>
{
	private static final long serialVersionUID = -1167565667836803570L;

	@Override
	public boolean add(String s)
	{
		for (String s1 : this)
			if (s1.compareToIgnoreCase(s) == 0)
				return false;
		
		return super.add(s);
	}
}