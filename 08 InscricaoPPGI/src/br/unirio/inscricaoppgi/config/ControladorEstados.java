package br.unirio.inscricaoppgi.config;

/**
 * Classe que representa um controlador de acesso � lista de estados brasileiros
 * 
 * @author Marcio Barros
 */
public class ControladorEstados
{
	private static String[] SIGLAS_ESTADOS = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
	
	private static String[] NOMES_ESTADOS =
	{
		"Acre",
		"Alagoas",
		"Amap�",
		"Amazonas",
		"Bahia",
		"Cear�",
		"Distrito Federal",
		"Esp�rito Santo",
		"Goi�s",
		"Maranh�o",
		"Mato Grosso",
		"Mato Grosso do Sul",
		"Minas Gerais",
		"Par�",
		"Para�ba",
		"Paran�",
		"Pernambuco",
		"Piau�",
		"Rio de Janeiro",
		"Rio Grande do Norte",
		"Rio Grande do Sul",
		"Rond�nia",
		"Roraima",
		"Santa Catarina",
		"S�o Paulo",
		"Sergipe",
		"Tocantins"
	};	

	/**
	 * Retorna as siglas dos estados brasileiros
	 */
	public static String[] getSiglas()
	{
		return SIGLAS_ESTADOS;
	}
	
	/**
	 * Retorna os nomes dos estados brasileiros
	 */
	public static String[] getNomes()
	{
		return NOMES_ESTADOS;
	}
	
	/**
	 * Retorna o nome de um estado, dada a sua sigla
	 */
	public static String getNome(String sigla)
	{
		for (int i = 0; i < SIGLAS_ESTADOS.length; i++)
			if (sigla.compareToIgnoreCase(SIGLAS_ESTADOS[i]) == 0)
				return NOMES_ESTADOS[i];
		
		return null;
	}
}