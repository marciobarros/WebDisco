package br.unirio.crud.model;

import lombok.Getter;

/**
 * ENumeracao dos estados brasileiros
 * 
 * @author Marcio Barros
 */
public enum Estado
{
	Acre("Acre", "AC"),
	Alagoas("Alagoas", "AL"),
	Amapa("Amapá", "AP"),
	Amazonas("Amazonas", "AM"),
	Bahia("Bahia", "BA"),
	Ceara("Ceará", "CE"),
	DistritoFederal("Distrito Federal", "DF"),
	EspiritoSanto("Espírito Santo", "ES"),
	Goias("Goiás", "GO"),
	Maranhao("Maranhão", "MA"),
	MatoGrosso("Mato Grosso", "MT"),
	MatoGrossoSul("Mato Grosso do Sul", "MS"),
	MinasGerais("Minas Gerais", "MG"),
	Para("Pará", "PA"),
	Paraiba("Paraíba", "PB"),
	Parana("Paraná", "PR"),
	Pernambuco("Pernambuco", "PE"),
	Piaui("Piauí", "PI"),
	RioJaneiro("Rio de Janeiro", "RJ"),
	RioGrandeNorte("Rio Grande do Norte", "RN"),
	RioGrandeSul("Rio Grande do Sul", "RS"),
	Rondonia("Rondônia", "RO"),
	Roraima("Roraima", "RR"),
	SantaCatarina("Santa Catarina", "SC"),
	SaoPaulo("São Paulo", "SP"),
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