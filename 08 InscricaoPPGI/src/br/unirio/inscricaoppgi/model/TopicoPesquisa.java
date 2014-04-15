package br.unirio.inscricaoppgi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumera��o dos t�picos de pesquisa do PPGI
 * 
 * @author Marcio
 */
public enum TopicoPesquisa
{
	RCR001("RCR001", "Modelos de Representa��o do Conhecimento", LinhaPesquisa.RCR, "L�gicas Formais. L�gica Descritiva. L�gicas Temporais e Modais. Estruturas de Dados Complexas. Probabilidade e Incerteza. Programa��o em L�gica. Programa��o com Restri��es (Constraints). Redes Neurais. Redes Bayesianas. Especifica��o Formal e Valida��o de Sistemas de Informa��o.", true),
	RCR002("RCR002", "Modelos e Ferramentas para Educa��o", LinhaPesquisa.RCR, "Educa��o � dist�ncia. Educa��o Corporativa. Composi��o Autom�tica de Conte�do para Educa��o. Ferramentas Colaborativas na Produ��o de Material Did�tico. Ferramentas de Acompanhamento Pedag�gico. Capacita��o de Professores utilizando TICs. Alfabetiza��o Digital. Jogos Educacionais.", true),
	RCR003("RCR003", "Racioc�nio Automatizado e Apoio � Decis�o", LinhaPesquisa.RCR, "Planejamento Autom�tico. Simula��o. Agentes e Sistemas Inteligentes. Jogos de Neg�cios. Simula��o e An�lise de Mercado Financeiro.", true),
	RCR004("RCR004", "Ci�ncia da Web e Web Sem�ntica", LinhaPesquisa.RCR, "Tecnologias de conhecimento. Ontologias. Agentes. Banco de dados. Recupera��o da informa��o. Metadados e sem�ntica. Anota��o sem�ntica. Interoperabilidade sem�ntica. Integra��o, reconcilia��o e alinhamento de ontologias, esquemas e dados. Conceitualiza��o e representa��o. Enfoques multimodelos e multiparadigmas. Modelagem conceitual de dados. Descri��o e manipula��o de integridade. Evolu��o e versionamento de ontologias. Vis�es de ontologias. Data Warehousing sem�ntico. Web social sem�ntica. Sistemas de informa��o baseados em ontologias. Modelos ontol�gicos de usu�rios e personaliza��o. Influ�ncia de novos sistemas e padr�es Web na sociedade. Composi��o e Sele��o Autom�tica de Servi�os Web.", true),
	RCR005("RCR005", "Aplica��es Envolvendo Representa��o do Conhecimento e Racioc�nio", LinhaPesquisa.RCR, "Web Social. Bancos de Dados Dedutivos. Sistemas Especialistas. Jogos Aplicados � Neuropsicologia. Extens�o Sem�ntica de Consultas, Predi��o de Links em Redes Sociais, Analise de Sentimentos. Outras Aplica��es.", true),
	RCR006("RCR006", "TV e Entretenimento Digitais", LinhaPesquisa.RCR, "Storytelling Interativo. TV interativa. Jogos. Modelos de Intera��o. Enredos de Narrativas: Composi��o Autom�tica, Reconhecimento e Adapta��o. Composi��o Autom�tica de Conte�do Cultural e de Entretenimento.", true),
	RCR007("RCR007", "Inova��es Tecnol�gicas para Apoiar a Educa��o", LinhaPesquisa.RCR, "Sistemas tutores inteligentes. Hipertexto e hiperm�dia. Sistemas de informa��o avan�ados para ensino/aprendizagem. Web social na educa��o. Web Sem�ntica e Educa��o. Modelagem e representa��o de aspectos relevantes de conhecimento. Nova gera��o de tecnologias de ensino/aprendizagem. Software e conte�do educacional. Representa��o e recupera��o de conte�do de ensino/aprendizagem, incluindo apoio a discuss�es. Modelagem e representa��o de conte�do, de aprendizes e pr�ticas educacionais. Treinamento e aprendizagem organizacional.", true),
	RCR008("RCR008", "Pesquisa Operacional e Problemas de Otimiza��o Combinat�ria", LinhaPesquisa.RCR, "Otimiza��o Aplicada a Roteamento de Ve�culos, Rotula��o Cartogr�fica, Redes de Telecomunica��es, etc. Algoritmos para Pesquisa Operacional e Problemas de Otimiza��o Combinat�ria em Geral.", true),
	RCR009("RCR009", "Heur�sticas e Meta-heur�sticas", LinhaPesquisa.RCR, "Meta-heur�ticas Multi-objetivo. Meta-heur�sticas H�bridas. Combina��o de M�todos Exatos e Heur�sticos.", true),
	RCR010("RCR010", "Aprendizado Autom�tico de Modelos", LinhaPesquisa.RCR, "Aprendizado de m�quina. Minera��o de dados. Minera��o de texto. Aprendizado de ontologias, modelos de processo, regras de neg�cio, modelos l�gicos, modelos probabil�sticos e modelos l�gico probabil�sticos.", true),
	RCR011("RCR011", "Big Data", LinhaPesquisa.RCR, "An�lise e extra��o de conhecimento em cima de grandes massas de dados. Processamento em tempo real. Processamento de dados que se modificam com grande velocidade. Processamento e integra��o de dados estruturados e n�o estruturados com grande variedade.", true),
	RCR012("RCR012", "Cole��es de Dados na Web", LinhaPesquisa.RCR, "Ambientes colaborativos e participativos de informa��o. Extra��o de informa��o e minera��o de texto. Web sem�ntica, dados abertos e dados ligados. Impacto de bibliotecas digitais na educa��o. Sistemas de informa��o e conhecimento. Ger�ncia da informa��o digital pessoal. Redes sociais. Estrutura��o e representa��o de conte�do digital.", true),
	
	DR001("DR001", "Arquitetura Empresarial de TI e Arquitetura Orientada a Servi�os", LinhaPesquisa.DR, "Este t�pico trata desafios para o alinhamento dos artefatos de desenvolvimento de sistemas e ger�ncia de dados de TI ao Neg�cio de uma Organiza��o, em especial tend�ncias de pesquisa em Arquitetura Empresarial de TI (ou Arquitetura Corporativa de TI) e Arquitetura Orientada a Servi�os.", true),
	DR002("DR002", "Arquitetura Orientada a Servi�os", LinhaPesquisa.DR, "Este t�pico trata dos desafios em SOA (Service-Oriented Architecture ou Arquitetura Orientada a Servi�os), incluindo: defini��o de m�todos para identifica��o, modelagem, projeto, codifica��o, testes, implanta��o, provis�o de servi�os, n�vel de acordo de servi�os e manuten��o de servi�os em um ciclo de vida para SOA; alinhamento entre TI e neg�cio atrav�s do uso de modelos de processos de neg�cio nas fases do ciclo de vida; t�cnicas para integra��o utilizando servi�os de acesso a dados e mashups; t�cnicas para composi��o de servi�os (orquestra��o, coreografia); gest�o de regras de neg�cio e servi�os; descoberta autom�tica de servi�os cientes do contexto; composi��o autom�tica de servi�os; Model-Driven Development e SOA.", true),
	DR003("DR003", "Bancos de Dados Espaciais", LinhaPesquisa.DR, "Este t�pico trata dos desafios em bancos de dados espaciais envolvendo estruturas de dados espaciais, indexa��o espacial, otimiza��o de consultas espaciais utilizando t�cnicas de distribui��o e paralelismo, utiliza��o de aproxima��es para melhorar desempenho na execu��o de consultas, processamento aproximado de consultas, visualiza��o de dados espaciais, modelagem de dados espaciais.", true),
	DR004("DR004", "Ger�ncia de dados em ambientes distribu�dos e paralelos", LinhaPesquisa.DR, "Este t�pico endere�a aspectos de efici�ncia de sistemas com acesso intensivo a dados em ambientes distribu�dos, em especial em ambientes de computa��o em nuvem (cloud computing) e workflows distribu�dos.", true),
	DR005("DR005", "Intelig�ncia do Neg�cio (BI)", LinhaPesquisa.DR, "Neste t�pico s�o abordadas metodologias e t�cnicas inovadoras para suporte �s atividades de Intelig�ncia de Neg�cio em Organiza��es, visando fornecer um suporte tecnol�gico mais efetivo ou eficiente a processos de tomada de decis�o. Este tema inclui diversas tecnologias de BI (Sistemas de ERP, Processos de ETL, Data Warehousing e OLAP), em especial tend�ncias para BI 2.0�, aplicadas a diversos dom�nios, como Geoprocessamento, Administra��o, Engenharia, Sa�de, Educa��o, Meio Ambiente e Transportes.", true),
	DR006("DR006", "Modelagem de Dados, Informa��o e Conhecimento", LinhaPesquisa.DR, "Neste t�pico s�o tratados desafios atuais para a modelagem conceitual de organiza��es, atrav�s do uso de Ontologias, com o objetivo de resolver problemas como a Integra��o sem�ntica de Dados e Interoperabilidade entre Sistemas de Informa��o. Em especial objetiva-se prover um suporte tecnol�gico efetivo para: Modelagem e Representa��o (Ontologias de Fundamenta��o e Modelagem Conceitual de Dados e Regras de Neg�cio); Organiza��o e Gest�o (Arquitetura de Informa��o, Transpar�ncia da Informa��o); Descoberta de Conhecimento Organizacional (Minera��o de dados, Minera��o de textos, Minera��o de processos, Minera��o de Regras de Neg�cio) a partir de dados de diversas fontes como bases de dados, documentos texto e XML, emails, blogs, hist�rias; e Integra��o de dados e Alinhamento de Ontologias.", true),
	DR007("DR007", "An�lise do comportamento de tr�fego em redes", LinhaPesquisa.DR, "Este t�pico envolve o estudo dos padr�es de comportamento dos diversos tipos de tr�fego em redes de comunica��o e a an�lise do impacto resultante em sistemas de computa��o e comunica��o. Dentre os diversos aspectos a serem pesquisados, destacam-se: metrologia de redes; implementa��o e an�lise de protocolos de comunica��o; caracteriza��o e modelagem de tr�fego; caracteriza��o e modelagem do comportamento do usu�rio e de aplica��es; an�lise de desempenho de sistemas de computa��o e comunica��o.", true),
	DR008("DR008", "Gerenciamento de redes", LinhaPesquisa.DR, "Este t�pico cobre os desafios relacionados ao gerenciamento de redes face �s atuais necessidades e � import�ncia que as infraestruturas de rede exercem no cen�rio atual dos neg�cios. Dentre os itens de pesquisa, destacam-se: monitoramento e m�tricas de interesse; visualiza��o e correla��o de informa��es; gerenciamento auton�mico; sistemas de apoio ao gerenciamento de redes; gerenciamento de redes de circuitos din�micos.", true),
	DR009("DR009", "Redes definidas por software", LinhaPesquisa.DR, "O advento das Software Defined Networks (SDN), implementadas atrav�s do protocolo OpenFlow, trouxe um novo paradigma para o funcionamento das redes de computadores, uma vez que o plano de controle dos equipamentos de rede n�o mais reside nos equipamentos e passa a ser externo e program�vel, com regras de encaminhamento muito mais flex�veis sendo instaladas nos datapaths. Esta abordagem permite novas formas de opera��o e funcionamento das redes, que � o principal ponto de investiga��o deste t�pico.", true),
	DR010("DR010", "Redes m�veis ou sem fio", LinhaPesquisa.DR, "Este t�pico investiga as redes locais sem fio (IEEE 802.11), redes m�veis ad hoc, redes tolerantes a atraso e desconex�es, redes veiculares, redes de r�dio cognitivo e redes de sensores. Algoritmos e grafos para analisar as redes m�veis. Protocolos das camadas de enlace, de rede e de transporte para alguns dos tipos de redes m�veis ou sem fio. Representa��o da mobilidade de usu�rios sem fio. Aplica��es P2P para redes m�veis. Modelos anal�ticos, simula��o e medi��o em redes m�veis e sem fio.", true),
	DR011("DR011", "Seguran�a", LinhaPesquisa.DR, "Este t�pico relaciona-se com o estudo de m�todos para se caracterizar um padr�o de comportamento de tr�fego de maneira a permitir a detec��o de padr�es an�malos que possam estar relacionados com uma poss�vel amea�a de seguran�a.", true),
	DR012("DR012", "Sistemas multim�dias", LinhaPesquisa.DR, "Este t�pico engloba estudos ligados �s principais aplica��es para transmiss�o de conte�do multim�dia em redes, dentre os quais: distribui��o de v�deos, sistemas para transmiss�o de video-confer�ncia; VoIP; IPTV; sistemas P2P; ensino a dist�ncia.", true),

	SAN001("SAN001", "Sistemas Colaborativos", LinhaPesquisa.SAN, "Processos e t�cnicas de trabalho em grupo: Brainstorming, Decis�o, Discuss�o, JAD, Entrevistas, etc. Sistemas de comunica��o mediada por computador: f�rum de discuss�o, bate-papo, videoconfer�ncia, VoIP, SMS etc. Sistemas web para compartilhamento de conte�do: not�cias, fotos, v�deos, blog etc. Sistemas para rede de relacionamentos, comunidades virtuais, grupos. Sistemas para suporte � reuni�o (meetingware), negocia��o e decis�o em grupo. Sistemas e ferramentas para avalia��o colaborativa e formativa. Sistemas de Computa��o M�vel para colabora��o. Sistemas Peer-to-Peer.", true),
	SAN002("SAN002", "Sistemas para Educa��o � Dist�ncia", LinhaPesquisa.SAN, "Sistemas e ferramentas para Educa��o a Dist�ncia (Ambientes de Aprendizagem). Sistemas para apoiar M�todos Educacionais (Aprendizagem Colaborativa, Baseada em Projeto, etc.).", true),
	SAN003("SAN003", "Gest�o de Conhecimento", LinhaPesquisa.SAN, "Suporte � captura, armazenamento, disponibiliza��o, compartilhamento e uso do conhecimento em organiza��es. Gest�o de Conhecimento baseada em Contexto. Gest�o de Conhecimento Orientada a Processos de Neg�cios. Redes Sociais Organizacionais.", true),
	SAN004("SAN004", "Aprendizagem Organizacional", LinhaPesquisa.SAN, "Suporte computacional a processos de aprendizagem em empresas.", true),
	SAN005("SAN005", "Democracia Digital", LinhaPesquisa.SAN, "Uso de TICs na promo��o da participa��o em assuntos p�blicos. Colabora��o. Transpar�ncia. Redes Sociais.", true),
	SAN006("SAN006", "Interface Humano Computador", LinhaPesquisa.SAN, "Acessibilidade. Usabilidade. Uso de Cores em Sistemas de Informa��o. Globaliza��o de Interfaces com o usu�rio.", true),
	SAN007("SAN007", "Gest�o de Processos de Neg�cios", LinhaPesquisa.SAN, "Metodologias de levantamento, modelagem e gest�o de processos de neg�cios. Alinhamento de TI ao Neg�cio. Arquitetura de Tecnologia da Informa��o. Engenharia de Requisitos de Sistemas com base em Processos de Neg�cio. Deriva��o de arquitetura de sistemas com base em Processos de Neg�cios. Arquitetura Baseada em Servi�os. BPMS (Sistemas de Workflow).", true),
	SAN008("SAN008", "Processos de Software", LinhaPesquisa.SAN, "Modelos de Qualidade. Modelos de Maturidade. M�todos �geis. Melhoria de Processos de Software. Processo de Desenvolvimento de Software Livre. Gest�o de Conhecimento aplicada a Processos de Software. Controle Estat�stico de Processos e Alta Maturidade. Retorno de Investimento em Iniciativas de Melhoria de Processos de Software. Ferramentas de apoio a processos de software.", true),
	SAN009("SAN009", "Ger�ncia de projetos de desenvolvimento de software", LinhaPesquisa.SAN, ".", true),
	SAN010("SAN010", "Arquitetura Empresarial", LinhaPesquisa.SAN, "Modelagem e simula��o de projetos de software. T�cnicas de projeto (design) e constru��o de software. Medi��o, controle do processo de projeto (design) e constru��o de software. Sistemas de controle de configura��o (controle de vers�o, requisi��es e build) e seu uso no apoio � gest�o de projetos. Libera��o (release) de software. Evolu��o de software.", true),
	SAN011("SAN011", "Redes Complexas e Sociais", LinhaPesquisa.SAN, "Minera��o, an�lise e visualiza��o de redes complexas e redes sociais. Ferramentas. Aplica��o em dom�nios.", true),
	SAN012("SAN012", "Engenharia de Software baseada em Buscas", LinhaPesquisa.SAN, "Utiliza��o de m�todos de otimiza��o heur�stica (algoritmos gen�ticos, simulated annealing, etc) para a resolu��o de problemas da Engenharia de Software (search-based software engineering), tais como projeto de software, gest�o de requisitos, atribui��o de tarefas em projetos, entre outros.", true),
	SAN013("SAN013", "Transpar�ncia Organizacional", LinhaPesquisa.SAN, "Transpar�ncia de Processos. Transpar�ncia de Informa��o. Dados Abertos Governamentais. Modelos de Transpar�ncia. Transpar�ncia de Software.", true),
	SAN014("SAN014", "Arquitetura Empresarial", LinhaPesquisa.SAN, "Frameworks. Processos de Arquitetura. Gest�o de Arquitetura. Interesses Transversais em Arquitetura. Arquitetura de Processos. Arquitetura de Informa��o. Arquitetura de Sistemas.", true);
	
	private @Getter String codigo;
	private @Getter String nome;
	private @Getter LinhaPesquisa linhaPesquisa;
	private @Getter String descricao;
	private @Getter boolean ativo;
	
	TopicoPesquisa(String codigo, String nome, LinhaPesquisa linha, String descricao, boolean ativo)
	{
		this.codigo = codigo;
		this.nome = nome;
		this.linhaPesquisa = linha;
		this.descricao = descricao;
		this.ativo = ativo;
	}
	
	public static List<TopicoPesquisa> getAtivos()
	{
		List<TopicoPesquisa> topicos = new ArrayList<TopicoPesquisa>();
		
		for (TopicoPesquisa topico : values())
			if (topico.isAtivo())
				topicos.add(topico);
		
		return topicos;
	}

	public static TopicoPesquisa get(String codigo)
	{
		for (TopicoPesquisa topico : values())
			if (topico.getCodigo().compareToIgnoreCase(codigo) == 0)
				return topico;
		
		return null;
	}
}