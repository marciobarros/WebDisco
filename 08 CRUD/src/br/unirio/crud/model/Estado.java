package br.unirio.crud.model;

import lombok.Getter;

/**
 * ENumera��o dos estados brasileiros
 * 
 * @author Marcio Barros
 */
public enum Estado
{
	Acre("Acre", "AC"),
	Alagoas("Alagoas", "AL"),
	Amapa("Amap�", "AP"),
	Amazonas("Amazonas", "AM"),
	Bahia("Bahia", "BA"),
	Ceara("Cear�", "CE"),
	DistritoFederal("Distrito Federal", "DF"),
	EspiritoSanto("Esp�rito Santo", "ES"),
	Goias("Goi�s", "GO"),
	Maranhao("Maranh�o", "MA"),
	MatoGrosso("Mato Grosso", "MT"),
	MatoGrossoSul("Mato Grosso do Sul", "MS"),
	MinasGerais("Minas Gerais", "MG"),
	Para("Par�", "PA"),
	Paraiba("Para�ba", "PB"),
	Parana("Paran�", "PR"),
	Pernambuco("Pernambuco", "PE"),
	Piaui("Piau�", "PI"),
	RioJaneiro("Rio de Janeiro", "RJ"),
	RioGrandeNorte("Rio Grande do Norte", "RN"),
	RioGrandeSul("Rio Grande do Sul", "RS"),
	Rondonia("Rond�nia", "RO"),
	Roraima("Roraima", "RR"),
	SantaCatarina("Santa Catarina", "SC"),
	SaoPaulo("S�o Paulo", "SP"),
	Sergipe("Sergipe", "SE"),
	Tocantins("Tocantins", "TO");
	
	private @Getter String nome;
	private @Getter String sigla;
	
	private Estado(String nome, String sigla)
	{
		this.nome = nome;
		this.sigla = sigla;
	}

	public static Estado get(String sigla)
	{
		for (Estado estado : values())
			if (estado.getSigla().compareToIgnoreCase(sigla) == 0)
				return estado;
		
		return null;
	}
}