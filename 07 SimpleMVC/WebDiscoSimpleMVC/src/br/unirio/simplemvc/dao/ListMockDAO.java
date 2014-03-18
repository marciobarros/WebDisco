package br.unirio.simplemvc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Mock para a implementação de DAOs para testes
 * 
 * @author marcio.barros
 */
public class ListMockDAO<T> extends SimpleMockDAO
{
	private HashMap<Long, T> instances;

	/**
	 * Inicializa o mock
	 */
	public ListMockDAO()
	{
		super();
		this.instances = new HashMap<Long, T>();
	}
	
	/**
	 * Retorna o número de instâncias no mock
	 */
	public int count()
	{
		if (isConnectionFailureMode())
			return 0;
		
		return instances.size();
	}

	/**
	 * Retorna uma página de instâncias do mock
	 */
	public List<T> page(int page, int size)
	{
		List<T> result = new ArrayList<T>();
		Iterator<T> items = instances.values().iterator();
		
		for (int i = 0; (i < page * size) && items.hasNext(); i++)
			items.next();
		
		for (int i = 0; (i < size) && items.hasNext(); i++)
			result.add(items.next());
		
		return result;
	}

	/**
	 * Retorna uma instância do mock, dado seu identificador
	 */
	protected T find(long id)
	{
		if (isConnectionFailureMode())
			return null;
		
		return instances.get(id);
	}
	
	/**
	 * Retorna uma lista de instâncias do mock
	 */
	protected Iterable<T> instances()
	{
		if (isConnectionFailureMode())
			return null;
		
		return instances.values();
	}
	
	/**
	 * Atualiza uma instância no mock, dado seu identificador
	 */
	protected boolean update(int id, T instance)
	{
		if (isConnectionFailureMode())
			return false;
		
		long lid = id;
		instances.put(lid, instance);
		return true;
	}
	
	/**
	 * Adiciona uma instância no mock, dado seu identificador
	 */
	protected boolean add(int id, T instance)
	{
		if (isConnectionFailureMode())
			return false;
		
		long lid = id;
		instances.put(lid, instance);
		return true;
	}
	
	/**
	 * Remove uma instância do mock, dado seu identificador
	 */
	protected boolean remove(long id)
	{
		if (isConnectionFailureMode())
			return false;
		
		T instance = find(id);
		
		if (instance != null)
			instances.remove(id);
		
		return true;
	}

	/**
	 * Insere uma instância no mock, para efeito de teste
	 */
	public ListMockDAO<T> setup(int id, T instance)
	{
		long lid = id;

		try
		{
			Method methodInt = instance.getClass().getDeclaredMethod("setId", int.class);
			if (methodInt != null) methodInt.invoke(instance, id);
			
			Method methodLong = instance.getClass().getDeclaredMethod("setId", long.class);
			if (methodLong != null) methodLong.invoke(instance, id);
		}
		catch (NoSuchMethodException e1)
		{
		}
		catch(SecurityException e2)
		{
		} 
		catch (IllegalAccessException e)
		{
		} 
		catch (IllegalArgumentException e)
		{
		} 
		catch (InvocationTargetException e)
		{
		}
		
		instances.put(lid, instance);
		return this;
	}
}