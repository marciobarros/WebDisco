package br.unirio.inscricaoppgi.model;

import lombok.Getter;

/**
 * Enumera��o de universidades
 * 
 * @author Marcio
 */
public enum Universidade
{
	ABEU("ABEU", "ABEU - Faculdades Integradas"),  
	CEFET("CEFET", "CEFET - Centro Federal de Educa��o Tecnol�gica"),  
	CIAW("CIAW", "Centro de Instru��o Almirante Wandenkolk"),  
	CUCL("CUCL", "Centro Universit�rio Celso Lisboa"),  
	BENNETT("BENNET", "Centro Universit�rio Metodista Bennett"),  
	MOACYR_BASTOS("MOACYR_BASTOS", "Centro Universit�rio Moacyr Sreder Bastos"),  
	CESJF("CESJF", "CESJF - Centro de Ensino Superior de Juiz de Fora"),  
	CESUFOZ("CESUFOZ", "CESUFOZ - Centro de Ensino Superior de Foz do Igua�u"),  
	CEULJI("CEULJI", "CEULJI - Centro Universit�rio Luterano de Ji-Paran�"),  
	CPREM("CPREM", "CPREM"),  
	EGN("EGN", "Escola de Guerra Naval"),  
	ENCE("ENCE", "ENCE - Escola Nacional de Ci�ncias Estat�sticas"),  
	SUESC("SUESC", "Faculdade de Economia e Finan�as do Rio de Janeiro"),  
	MACHADO_SOBRINHO("MACHADO_SOBRINHO", "Faculdade Machado Sobrinho"),  
	GRANBERY("GRANBERY", "Faculdade Metodista Granbery"),  
	ANGLO_AMERICANO("ANGLO", "Faculdades Integradas Anglo-Americano"),  
	HELIO_ALONSO("HELIO_ALONSO", "Faculdades Integradas Helio Alonso"),  
	SIMONSEN("SIMONSEN", "Faculdades Integradas Simonsen"),  
	NUNO_LISBOA("NUNO_LISBOA", "Faculdades Reunidas Nuno Lisboa"),  
	FAMATH("FAMATH", "FAMATH"),  
	FESO("FESO", "FESO - Funda��o Educacional Serra dos �rg�os"),  
	FEUC("FEUC", "FEUC - Funda��o Educacional Unificada Campograndense"),  
	FGV("FGV", "FGV - Funda��o Getulio Vargas"),  
	FRB("FRB", "FRB - Faculdade Rui Barbosa"),  
	FUNCEFET("FUNCEFET", "FUNCEFET - Funda��o do Centro Federal de Educa��o Tecnol�gica"),  
	FUNITA("FUNITA", "FUNITA - Faculdade de Inform�tica de Itaperuna"),  
	IBMEC("IBMEC", "IBMEC"),  
	IME("IME", "IME - Instituto Militar de Engenharia"),  
	ISTCCRJ("ISTCCRJ", "Instituto Superior de Tecnologia em Ci�ncias da Computa��o do Rio de Janeiro"),  
	ISEP("ISEP", "ISEP - Instituto Superior em Estudos Pedag�gicos"),  
	LNCC("LNCC", "LNCC - Laborat�rio Nacional de Computa��o Cient�fica"),  
	PUC_MG("PUC_MG", "PUC (MG)"),  
	PUC_CAMPINAS("PUC_CAMPINAS", "PUC (Campinas)"),  
	PUC_RIO("PUC-RIO", "PUC-RIO - Pontif�cia Universidade Cat�lica do Rio de Janeiro"),  
	SESAT("SESAT", "SESAT"),  
	SUAM("SUAM", "SUAM - Centro Universit�rio Augusto Mota"),  
	UCAM("UCAM", "UCAM - Universidade C�ndido Mendes"),  
	UCP("UCP", "UCP - Universidade Cat�lica de Petr�polis"),  
	UENF("UENF", "UENF - Universidade Estadual do Norte Fluminense"),  
	UERJ("UERJ", "UERJ - Universidade Estadual do Rio de Janeiro"),  
	UESA("UESA", "UESA - Universidade Est�cio de S�"),  
	UFF("UFF", "UFF - Universidade Federal Fluminense"),  
	UFG("UFG", "UFG - Universidade Federal de Goi�s"),  
	UFJF("UFJF", "UFJF - Universidade Federal de Juiz de Fora"),  
	UFMS("UFMS", "UFMS - Universidade Federal de Mato Grosso do Sul"),  
	UFMT("UFMT", "UFMT - Universidade Federal de Mato Grosso"),  
	UFPA("UFPA", "UFPA - Universidade Federal do Par�"),  
	UFRGS("UFRGS", "UFRGS - Universidade Federal do Rio Grande do Sul"),  
	UFRJ("UFRJ", "UFRJ - Universidade Federal do Rio de Janeiro"),  
	UFRRJ("UFRRJ", "UFRRJ - Universidade Federal Rural do Rio de Janeiro"),  
	UFSC("UFSC", "UFSC - Universidade Federal de Santa Catarina"),  
	UFV("UFV", "UFV - Universidade Federal de Vi�osa"),  
	UGF("UGF", "UGF - Universidade Gama Filho"),  
	UNESP("UNESP", "UNESP"),  
	UNIC("UNIC", "UNIC - Universidade de Cuiab�"),  
	UNICARIOCA("UNICARIOCA", "UNICARIOCA - Universidade Carioca"),  
	UNIFEI("UNIFEI", "UNIFEI - Universidade Federal de Itajub�"),  
	UNIFOR("UNIFOR", "UNIFOR - Universidade de Fortaleza"),  
	UNIG("UNIG", "UNIG - Universidade de Nova Igua��"),  
	UNIGRANRIO("UNIGRANRIO", "UNIGRANRIO - Universidade do Grande Rio"),  
	UNIMONTES("UNIMONTES", "UNIMONTES - Universidade Estadual de Montes Claros"),  
	UNIRIO("UNIRIO", "UNIRIO - Universidade Federal do Estado do Rio de Janeiro"),  
	UNIVERCIDADE("UNIVERCIDADE", "UNIVERCIDADE - Centro Universit�rio da Cidade"),  
	ALTO_URUGUAI("ALTO_URUGUAI", "Universidade Regional Integrada Alto Uruguai e Miss�es"),  
	USO("USO", "USO - Universidade Salgado de Oliveira"),
	USU("USU", "USU - Universidade Santa �rsula"),  
	UVA("UVA", "UVA - Universidade Veiga de Almeida"),	
	OUTRA("OUTRA", "Outra - Indique a universidade no nome do curso");  
		
	private @Getter String codigo;
	private @Getter String nome;
	
	Universidade(String codigo, String nome)
	{
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public static Universidade get(String codigo)
	{
		for (Universidade universidade : values())
			if (universidade.getCodigo().compareToIgnoreCase(codigo) == 0)
				return universidade;
		
		return null;
	}
}