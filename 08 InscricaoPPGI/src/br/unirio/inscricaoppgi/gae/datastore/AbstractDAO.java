package br.unirio.inscricaoppgi.gae.datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;

/**
 * Classe responsável pela persistência dos objetos de uma classe
 * 
 * @author Marcio Barros
 */
public abstract class AbstractDAO<T extends DataObject>
{
	private String kind;

	/**
	 * Inicializa a classe de persistência
	 */
	protected AbstractDAO(String kind)
	{
		this.kind = kind;
	}

	/**
	 * Método abstrato para preeencher um objeto com os dados de uma entidade
	 * 
	 * @param e Entidade que representa os dados
	 */
	protected abstract T load(Entity e);

	/**
	 * Método abstrato para preencher uma entidade com os dados de um objeto
	 * 
	 * @param t Objeto que será salvo na entidade
	 */
	protected abstract void save(T t, Entity e);

	/**
	 * Retorna o tipo de entidade armazenada pela classe
	 */
	protected String getKind()
	{
		return kind;
	}

	/**
	 * Retorna a lista de objetos armazenados no sistema
	 */
	public List<T> list()
	{
		return list(null);
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 * 
	 * @param start Usuário inicial
	 * @param maxlength Número máximo de usuários
	 */
	public List<T> list(int start, int maxlength)
	{
		return list(start, maxlength, null);
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 */
	public List<T> list(int start, int maxlength, String sortField, SortDirection sortDirection)
	{
		return list(start, maxlength, null, sortField, sortDirection);
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 * 
	 * @param start Usuário inicial
	 * @param maxlength Número máximo de usuários
	 */
	public List<T> list(int start, int maxlength, Filter filter)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		
		if (filter != null)
			q.setFilter(filter);
		
		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable(FetchOptions.Builder.withLimit(maxlength).offset(start)))
			result.add(load(e));

		return result;
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 */
	public List<T> list(int start, int maxlength, Filter filter, String sortField, SortDirection sortDirection)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		
		if (filter != null)
			q.setFilter(filter);
		
		q.addSort(sortField, sortDirection);
		
		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable(FetchOptions.Builder.withLimit(maxlength).offset(start)))
			result.add(load(e));

		return result;
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 */
	public List<T> list(Filter filter)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		
		if (filter != null)
			q.setFilter(filter);
		
		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable())
			result.add(load(e));

		return result;
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 */
	public List<T> list(Filter filter, String sortField, SortDirection sortDirection)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		
		if (filter != null)
			q.setFilter(filter);
		
		q.addSort(sortField, sortDirection);
		
		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable())
			result.add(load(e));

		return result;
	}

	/**
	 * Retorna a lista dos objetos armazenados no sistema
	 */
	public List<T> list(String sortField, SortDirection sortDirection)
	{
		return list(null, sortField, sortDirection);
	}

	/**
	 * Pega o próximo valor do filtro
	 */
	private String nextFilter(String search)
	{
		char c = search.charAt(search.length() - 1);

		StringBuffer buffer = new StringBuffer("0");
		buffer.setCharAt(0, (char) (c + 1));

		if (search.length() > 1)
			buffer.insert(0, search.substring(0, search.length() - 1));

		return buffer.toString();
	}

	/**
	 * Retorna a lista de objetos armazenados no sistema e compatíveis com um
	 * filtro
	 * 
	 * @param start Usuário inicial
	 * @param maxlength Número máximo de usuários
	 * @param field Campo usado no filtro
	 * @param value Filtro sendo aplicado
	 */
	public List<T> list(int start, int maxlength, String field, String value)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);

		if (value.length() > 0)
			q.setFilter(startFilter(field, value));

		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable(FetchOptions.Builder.withLimit(maxlength).offset(start)))
			result.add(load(e));

		return result;
	}

	/**
	 * Adiciona um filtro exato sobre um filtro anterior
	 */
	public Filter addExactFilter (Filter filter, String field, String value)
	{
		if (value != null && value.length() > 0)
		{
			Filter localFilter = exactFilter(field, FilterOperator.EQUAL, value);
			filter = (filter != null) ? and(filter, localFilter) : localFilter;
		}

		return filter;
	}
	
	/**
	 * Adiciona um filtro de início sobre um filtro anterior
	 */
	public Filter addStartFilter (Filter filter, String field, String value)
	{
		if (value != null && value.length() > 0)
		{
			Filter localFilter = startFilter(field, value);
			filter = (filter != null) ? and(filter, localFilter) : localFilter;
		}

		return filter;
	}

	public Filter exactFilter(String field, FilterOperator operator, String value)
	{
		return new FilterPredicate(field, operator, value);
	}

	public Filter exactFilter(String field, FilterOperator operator, int value)
	{
		return new FilterPredicate(field, operator, value);
	}

	public Filter exactFilter(String field, FilterOperator operator, long value)
	{
		return new FilterPredicate(field, operator, value);
	}

	public Filter exactFilter(String field, FilterOperator operator, double value)
	{
		return new FilterPredicate(field, operator, value);
	}

	public Filter exactFilter(String field, FilterOperator operator, Date value)
	{
		return new FilterPredicate(field, operator, value);
	}
	
	public Filter startFilter(String field, String value)
	{
		return CompositeFilterOperator.and(
				new FilterPredicate(field, FilterOperator.GREATER_THAN_OR_EQUAL, value),
				new FilterPredicate(field, FilterOperator.LESS_THAN, nextFilter(value)));
	}
	
	public Filter and(Filter filter1, Filter filter2)
	{
		return CompositeFilterOperator.and(filter1, filter2);
	}
	
	public Filter or(Filter filter1, Filter filter2)
	{
		return CompositeFilterOperator.or(filter1, filter2);
	}

	/**
	 * Retorna o número de objetos armazenados no sistema
	 */
	public int count()
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		q.setKeysOnly();
		PreparedQuery pq = ds.prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	/**
	 * Retorna o número de objetos armazenados no sistema
	 */
	public int count(Filter filter)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(kind);
		
		if (filter != null)
			q.setFilter(filter);
		
		q.setKeysOnly();
		PreparedQuery pq = ds.prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	/**
	 * Retorna um objeto armazenado no sistema, dado seu identificador
	 * 
	 * @param id Identificador do objeto desejado
	 */
	public T get(long id)
	{
		if (id <= 0)
			return null;

		try
		{
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity e = ds.get(KeyFactory.createKey(kind, id));
			return load(e);

		} catch (EntityNotFoundException e)
		{
			return null;
		}
	}

	/**
	 * Retorna um objeto armazenado no sistema, dado seu identificador
	 * 
	 * @param id Identificador do objeto desejado
	 */
	public T get(Long id)
	{
		return (id != null) ? get(id.longValue()) : null;
	}

	/**
	 * Retorna um objeto de acordo com uma cláusula de pesquisa
	 * 
	 * @param field Campo que será utilizado na comparação
	 * @param value Valor esperado para o campo
	 */
	public T get(String field, Object value)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(getKind());
		q.setFilter(new FilterPredicate(field, FilterOperator.EQUAL, value));
		PreparedQuery pq = ds.prepare(q);

		Iterable<Entity> iterable = pq.asIterable();
		
		if (iterable.iterator().hasNext())
			return load(iterable.iterator().next());
		
		return null;
	}

	public T get(Filter filter)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(getKind());
		q.setFilter(filter);
		PreparedQuery pq = ds.prepare(q);

		Iterable<Entity> iterable = pq.asIterable();
		
		if (iterable.iterator().hasNext())
			return load(iterable.iterator().next());
		
		return null;
	}

	/**
	 * Retorna uma lista de objetos de acordo com uma cláusula de pesquisa
	 * 
	 * @param field Campo que será utilizado na comparação
	 * @param value Valor esperado para o campo
	 */
	public Iterable<T> getList(String field, Object value)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(getKind());
		q.setFilter(new FilterPredicate(field, FilterOperator.EQUAL, value));

		PreparedQuery pq = ds.prepare(q);
		List<T> result = new ArrayList<T>();

		for (Entity e : pq.asIterable())
			result.add(load(e));

		return result;
	}

	/**
	 * Verifica se um objeto é novo
	 * 
	 * @param t Objeto que está sendo analisado
	 */
	private boolean isNewObject(T t)
	{
		return t.getId() <= 0;
	}

	/**
	 * Armazena um objeto armazenado no sistema
	 * 
	 * @param t Novo objeto que será registrado no sistema
	 */
	public Long put(T t)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity e = isNewObject(t) ? new Entity(getKind()) : new Entity(getKind(), t.getId());
		save(t, e);
		Key key = ds.put(e);
		t.setId((long)e.getKey().getId());
		return key.getId();
	}

	/**
	 * Remove um objeto, dado seu identificador
	 * 
	 * @param id Identificador do objeto desejado
	 */
	public void remove(Long id)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		ds.delete(KeyFactory.createKey(kind, id));
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de uma string
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected String getStringProperty(Entity e, String name)
	{
		return (String) e.getProperty(name);
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um booleano
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected Boolean getBooleanProperty(Entity e, String name)
	{
		return (Boolean) e.getProperty(name);
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um número
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected Double getDoubleProperty(Entity e, String name)
	{
		return (Double) e.getProperty(name);
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um inteiro longo
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected Long getLongProperty(Entity e, String name)
	{
		return (Long) e.getProperty(name);
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de uma data
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected Date getDateProperty(Entity e, String name)
	{
		return (Date) e.getProperty(name);
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um inteiro
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected Integer getIntProperty(Entity e, String name)
	{
		return ((Long) e.getProperty(name)).intValue();
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de uma string
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected String getStringProperty(Entity e, String name, String defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? (String) o : defValue;
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um booleano
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected Boolean getBooleanProperty(Entity e, String name, boolean defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? (Boolean) o : defValue;
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um número
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected Double getDoubleProperty(Entity e, String name, double defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? (Double) o : defValue;
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um inteiro longo
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected Long getLongProperty(Entity e, String name, long defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? (Long) o : defValue;
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de um inteiro
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected Integer getIntProperty(Entity e, String name, int defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? ((Long) o).intValue() : defValue;
	}

	protected String getTextProperty(Entity e, String name, String defValue)
	{
		Text o = (Text) e.getProperty(name);
		return (o != null) ? o.getValue() : defValue;
	}

	/**
	 * Retorna uma propriedade de uma entidade na forma de uma data
	 * 
	 * @param e			Entidade que será consultada
	 * @param name		Nome da propriedade desejada
	 * @param defValue	Valor default da propriedade
	 */
	protected Date getDateProperty(Entity e, String name, Date defValue)
	{
		Object o = e.getProperty(name);
		return (o != null) ? (Date) o : defValue;
	}

	/**
	 * Retorna uma propriedade na forma de um hashfield
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected HashField getHashFieldProperty(Entity e, String name)
	{
		HashField hs = new HashField();
		hs.fromString(getTextProperty(e, name, ""));
		return hs;
	}

	/**
	 * Retorna uma propriedade na forma de uma lista de hashfield
	 * 
	 * @param e		Entidade que será consultada
	 * @param name	Nome da propriedade desejada
	 */
	protected HashFieldList getHashFieldListProperty(Entity e, String name)
	{
		HashFieldList hsf = new HashFieldList();
		hsf.fromString(getStringProperty(e, name));
		return hsf;
	}
}