package br.unirio.simplemvc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Mock para a implementa��o de DAOs para testes
 * 
 * @author marcio.barros
 */
public class ListMockDAO<T> extends SimpleMockDAO
{
	private HashMap<Long, T> instances;
	private List<T> insertionOrder;

	/**
	 * Inicializa o mock
	 */
	public ListMockDAO()
	{
		super();
		this.instances = new HashMap<Long, T>();
		this.insertionOrder = new ArrayList<T>();
	}
	
	/**
	 * Retorna o n�mero de inst�ncias no mock
	 */
	public int count()
	{
		if (isConnectionFailureMode())
			return 0;
		
		return instances.size();
	}

	/**
	 * Retorna uma inst�ncia do mock, dado seu identificador
	 */
	protected T find(long id)
	{
		if (isConnectionFailureMode())
			return null;
		
		return instances.get(id);
	}
	
	/**
	 * Retorna uma lista de inst�ncias do mock
	 */
	protected Iterable<T> instances()
	{
		if (isConnectionFailureMode())
			return null;
		
		return instances.values();
	}
	
	/**
	 * Atualiza uma inst�ncia no mock, dado seu identificador
	 */
	protected boolean update(int id, T instance)
	{
		if (isConnectionFailureMode())
			return false;
		
		long lid = id;
		instances.put(lid, instance);
		
		insertionOrder.set(insertionOrder.indexOf(instance), instance);
		return true;
	}
	
	/**
	 * Adiciona uma inst�ncia no mock, dado seu identificador
	 */
	protected boolean add(int id, T instance)
	{
		if (isConnectionFailureMode())
			return false;
		
		long lid = id;
		instances.put(lid, instance);
		insertionOrder.add(instance);
		return true;
	}
	
	/**
	 * Remove uma inst�ncia do mock, dado seu identificador
	 */
	protected boolean remove(long id)
	{
		if (isConnectionFailureMode())
			return false;
		
		T instance = find(id);
		
		if (instance != null)
			instances.remove(id);
		
		insertionOrder.remove(instance);
		return true;
	}

	/**
	 * Insere uma inst�ncia no mock, para efeito de teste
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
		insertionOrder.add(instance);
		return this;
	}
	
	/**
	 * Returns a page of instances
	 */
	public List<T> page(int pageNumber, int pageSize)
	{
		List<T> result = new ArrayList<T>();
		
		for (int i = 0; i < pageSize; i++)
		{
			int index = pageNumber * pageSize + i;
			
			if (insertionOrder.size() > index)
				result.add(insertionOrder.get(index));
		}
		
		return result;
	}
}