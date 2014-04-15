package br.unirio.inscricaoppgi.model;

import lombok.Getter;

/**
 * Enumeração de universidades
 * 
 * @author Marcio
 */
public enum Universidade
{
	ABEU("ABEU", "ABEU - Faculdades Integradas"),  
	CEFET("CEFET", "CEFET - Centro Federal de Educação Tecnológica"),  
	CIAW("CIAW", "Centro de Instrução Almirante Wandenkolk"),  
	CUCL("CUCL", "Centro Universitário Celso Lisboa"),  
	BENNETT("BENNET", "Centro Universitário Metodista Bennett"),  
	MOACYR_BASTOS("MOACYR_BASTOS", "Centro Universitário Moacyr Sreder Bastos"),  
	CESJF("CESJF", "CESJF - Centro de Ensino Superior de Juiz de Fora"),  
	CESUFOZ("CESUFOZ", "CESUFOZ - Centro de Ensino Superior de Foz do Iguaçu"),  
	CEULJI("CEULJI", "CEULJI - Centro Universitário Luterano de Ji-Paraná"),  
	CPREM("CPREM", "CPREM"),  
	EGN("EGN", "Escola de Guerra Naval"),  
	ENCE("ENCE", "ENCE - Escola Nacional de Ciências Estatísticas"),  
	SUESC("SUESC", "Faculdade de Economia e Finanças do Rio de Janeiro"),  
	MACHADO_SOBRINHO("MACHADO_SOBRINHO", "Faculdade Machado Sobrinho"),  
	GRANBERY("GRANBERY", "Faculdade Metodista Granbery"),  
	ANGLO_AMERICANO("ANGLO", "Faculdades Integradas Anglo-Americano"),  
	HELIO_ALONSO("HELIO_ALONSO", "Faculdades Integradas Helio Alonso"),  
	SIMONSEN("SIMONSEN", "Faculdades Integradas Simonsen"),  
	NUNO_LISBOA("NUNO_LISBOA", "Faculdades Reunidas Nuno Lisboa"),  
	FAMATH("FAMATH", "FAMATH"),  
	FESO("FESO", "FESO - Fundação Educacional Serra dos Órgãos"),  
	FEUC("FEUC", "FEUC - Fundação Educacional Unificada Campograndense"),  
	FGV("FGV", "FGV - Fundação Getulio Vargas"),  
	FRB("FRB", "FRB - Faculdade Rui Barbosa"),  
	FUNCEFET("FUNCEFET", "FUNCEFET - Fundação do Centro Federal de Educação Tecnológica"),  
	FUNITA("FUNITA", "FUNITA - Faculdade de Informática de Itaperuna"),  
	IBMEC("IBMEC", "IBMEC"),  
	IME("IME", "IME - Instituto Militar de Engenharia"),  
	ISTCCRJ("ISTCCRJ", "Instituto Superior de Tecnologia em Ciências da Computação do Rio de Janeiro"),  
	ISEP("ISEP", "ISEP - Instituto Superior em Estudos Pedagógicos"),  
	LNCC("LNCC", "LNCC - Laboratório Nacional de Computação Científica"),  
	PUC_MG("PUC_MG", "PUC (MG)"),  
	PUC_CAMPINAS("PUC_CAMPINAS", "PUC (Campinas)"),  
	PUC_RIO("PUC-RIO", "PUC-RIO - Pontifícia Universidade Católica do Rio de Janeiro"),  
	SESAT("SESAT", "SESAT"),  
	SUAM("SUAM", "SUAM - Centro Universitário Augusto Mota"),  
	UCAM("UCAM", "UCAM - Universidade Cândido Mendes"),  
	UCP("UCP", "UCP - Universidade Católica de Petrópolis"),  
	UENF("UENF", "UENF - Universidade Estadual do Norte Fluminense"),  
	UERJ("UERJ", "UERJ - Universidade Estadual do Rio de Janeiro"),  
	UESA("UESA", "UESA - Universidade Estácio de Sá"),  
	UFF("UFF", "UFF - Universidade Federal Fluminense"),  
	UFG("UFG", "UFG - Universidade Federal de Goiás"),  
	UFJF("UFJF", "UFJF - Universidade Federal de Juiz de Fora"),  
	UFMS("UFMS", "UFMS - Universidade Federal de Mato Grosso do Sul"),  
	UFMT("UFMT", "UFMT - Universidade Federal de Mato Grosso"),  
	UFPA("UFPA", "UFPA - Universidade Federal do Pará"),  
	UFRGS("UFRGS", "UFRGS - Universidade Federal do Rio Grande do Sul"),  
	UFRJ("UFRJ", "UFRJ - Universidade Federal do Rio de Janeiro"),  
	UFRRJ("UFRRJ", "UFRRJ - Universidade Federal Rural do Rio de Janeiro"),  
	UFSC("UFSC", "UFSC - Universidade Federal de Santa Catarina"),  
	UFV("UFV", "UFV - Universidade Federal de Viçosa"),  
	UGF("UGF", "UGF - Universidade Gama Filho"),  
	UNESP("UNESP", "UNESP"),  
	UNIC("UNIC", "UNIC - Universidade de Cuiabá"),  
	UNICARIOCA("UNICARIOCA", "UNICARIOCA - Universidade Carioca"),  
	UNIFEI("UNIFEI", "UNIFEI - Universidade Federal de Itajubá"),  
	UNIFOR("UNIFOR", "UNIFOR - Universidade de Fortaleza"),  
	UNIG("UNIG", "UNIG - Universidade de Nova Iguaçú"),  
	UNIGRANRIO("UNIGRANRIO", "UNIGRANRIO - Universidade do Grande Rio"),  
	UNIMONTES("UNIMONTES", "UNIMONTES - Universidade Estadual de Montes Claros"),  
	UNIRIO("UNIRIO", "UNIRIO - Universidade Federal do Estado do Rio de Janeiro"),  
	UNIVERCIDADE("UNIVERCIDADE", "UNIVERCIDADE - Centro Universitário da Cidade"),  
	ALTO_URUGUAI("ALTO_URUGUAI", "Universidade Regional Integrada Alto Uruguai e Missões"),  
	USO("USO", "USO - Universidade Salgado de Oliveira"),
	USU("USU", "USU - Universidade Santa Úrsula"),  
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