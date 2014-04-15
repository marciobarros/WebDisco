package br.unirio.inscricaoppgi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumeração dos tópicos de pesquisa do PPGI
 * 
 * @author Marcio
 */
public enum TopicoPesquisa
{
	RCR001("RCR001", "Modelos de Representação do Conhecimento", LinhaPesquisa.RCR, "Lógicas Formais. Lógica Descritiva. Lógicas Temporais e Modais. Estruturas de Dados Complexas. Probabilidade e Incerteza. Programação em Lógica. Programação com Restrições (Constraints). Redes Neurais. Redes Bayesianas. Especificação Formal e Validação de Sistemas de Informação.", true),
	RCR002("RCR002", "Modelos e Ferramentas para Educação", LinhaPesquisa.RCR, "Educação à distância. Educação Corporativa. Composição Automática de Conteúdo para Educação. Ferramentas Colaborativas na Produção de Material Didático. Ferramentas de Acompanhamento Pedagógico. Capacitação de Professores utilizando TICs. Alfabetização Digital. Jogos Educacionais.", true),
	RCR003("RCR003", "Raciocínio Automatizado e Apoio à Decisão", LinhaPesquisa.RCR, "Planejamento Automático. Simulação. Agentes e Sistemas Inteligentes. Jogos de Negócios. Simulação e Análise de Mercado Financeiro.", true),
	RCR004("RCR004", "Ciência da Web e Web Semântica", LinhaPesquisa.RCR, "Tecnologias de conhecimento. Ontologias. Agentes. Banco de dados. Recuperação da informação. Metadados e semântica. Anotação semântica. Interoperabilidade semântica. Integração, reconciliação e alinhamento de ontologias, esquemas e dados. Conceitualização e representação. Enfoques multimodelos e multiparadigmas. Modelagem conceitual de dados. Descrição e manipulação de integridade. Evolução e versionamento de ontologias. Visões de ontologias. Data Warehousing semântico. Web social semântica. Sistemas de informação baseados em ontologias. Modelos ontológicos de usuários e personalização. Influência de novos sistemas e padrões Web na sociedade. Composição e Seleção Automática de Serviços Web.", true),
	RCR005("RCR005", "Aplicações Envolvendo Representação do Conhecimento e Raciocínio", LinhaPesquisa.RCR, "Web Social. Bancos de Dados Dedutivos. Sistemas Especialistas. Jogos Aplicados à Neuropsicologia. Extensão Semântica de Consultas, Predição de Links em Redes Sociais, Analise de Sentimentos. Outras Aplicações.", true),
	RCR006("RCR006", "TV e Entretenimento Digitais", LinhaPesquisa.RCR, "Storytelling Interativo. TV interativa. Jogos. Modelos de Interação. Enredos de Narrativas: Composição Automática, Reconhecimento e Adaptação. Composição Automática de Conteúdo Cultural e de Entretenimento.", true),
	RCR007("RCR007", "Inovações Tecnológicas para Apoiar a Educação", LinhaPesquisa.RCR, "Sistemas tutores inteligentes. Hipertexto e hipermídia. Sistemas de informação avançados para ensino/aprendizagem. Web social na educação. Web Semântica e Educação. Modelagem e representação de aspectos relevantes de conhecimento. Nova geração de tecnologias de ensino/aprendizagem. Software e conteúdo educacional. Representação e recuperação de conteúdo de ensino/aprendizagem, incluindo apoio a discussões. Modelagem e representação de conteúdo, de aprendizes e práticas educacionais. Treinamento e aprendizagem organizacional.", true),
	RCR008("RCR008", "Pesquisa Operacional e Problemas de Otimização Combinatória", LinhaPesquisa.RCR, "Otimização Aplicada a Roteamento de Veículos, Rotulação Cartográfica, Redes de Telecomunicações, etc. Algoritmos para Pesquisa Operacional e Problemas de Otimização Combinatória em Geral.", true),
	RCR009("RCR009", "Heurísticas e Meta-heurísticas", LinhaPesquisa.RCR, "Meta-heuríticas Multi-objetivo. Meta-heurísticas Híbridas. Combinação de Métodos Exatos e Heurísticos.", true),
	RCR010("RCR010", "Aprendizado Automático de Modelos", LinhaPesquisa.RCR, "Aprendizado de máquina. Mineração de dados. Mineração de texto. Aprendizado de ontologias, modelos de processo, regras de negócio, modelos lógicos, modelos probabilísticos e modelos lógico probabilísticos.", true),
	RCR011("RCR011", "Big Data", LinhaPesquisa.RCR, "Análise e extração de conhecimento em cima de grandes massas de dados. Processamento em tempo real. Processamento de dados que se modificam com grande velocidade. Processamento e integração de dados estruturados e não estruturados com grande variedade.", true),
	RCR012("RCR012", "Coleções de Dados na Web", LinhaPesquisa.RCR, "Ambientes colaborativos e participativos de informação. Extração de informação e mineração de texto. Web semântica, dados abertos e dados ligados. Impacto de bibliotecas digitais na educação. Sistemas de informação e conhecimento. Gerência da informação digital pessoal. Redes sociais. Estruturação e representação de conteúdo digital.", true),
	
	DR001("DR001", "Arquitetura Empresarial de TI e Arquitetura Orientada a Serviços", LinhaPesquisa.DR, "Este tópico trata desafios para o alinhamento dos artefatos de desenvolvimento de sistemas e gerência de dados de TI ao Negócio de uma Organização, em especial tendências de pesquisa em Arquitetura Empresarial de TI (ou Arquitetura Corporativa de TI) e Arquitetura Orientada a Serviços.", true),
	DR002("DR002", "Arquitetura Orientada a Serviços", LinhaPesquisa.DR, "Este tópico trata dos desafios em SOA (Service-Oriented Architecture ou Arquitetura Orientada a Serviços), incluindo: definição de métodos para identificação, modelagem, projeto, codificação, testes, implantação, provisão de serviços, nível de acordo de serviços e manutenção de serviços em um ciclo de vida para SOA; alinhamento entre TI e negócio através do uso de modelos de processos de negócio nas fases do ciclo de vida; técnicas para integração utilizando serviços de acesso a dados e mashups; técnicas para composição de serviços (orquestração, coreografia); gestão de regras de negócio e serviços; descoberta automática de serviços cientes do contexto; composição automática de serviços; Model-Driven Development e SOA.", true),
	DR003("DR003", "Bancos de Dados Espaciais", LinhaPesquisa.DR, "Este tópico trata dos desafios em bancos de dados espaciais envolvendo estruturas de dados espaciais, indexação espacial, otimização de consultas espaciais utilizando técnicas de distribuição e paralelismo, utilização de aproximações para melhorar desempenho na execução de consultas, processamento aproximado de consultas, visualização de dados espaciais, modelagem de dados espaciais.", true),
	DR004("DR004", "Gerência de dados em ambientes distribuídos e paralelos", LinhaPesquisa.DR, "Este tópico endereça aspectos de eficiência de sistemas com acesso intensivo a dados em ambientes distribuídos, em especial em ambientes de computação em nuvem (cloud computing) e workflows distribuídos.", true),
	DR005("DR005", "Inteligência do Negócio (BI)", LinhaPesquisa.DR, "Neste tópico são abordadas metodologias e técnicas inovadoras para suporte às atividades de Inteligência de Negócio em Organizações, visando fornecer um suporte tecnológico mais efetivo ou eficiente a processos de tomada de decisão. Este tema inclui diversas tecnologias de BI (Sistemas de ERP, Processos de ETL, Data Warehousing e OLAP), em especial tendências para BI 2.0©, aplicadas a diversos domínios, como Geoprocessamento, Administração, Engenharia, Saúde, Educação, Meio Ambiente e Transportes.", true),
	DR006("DR006", "Modelagem de Dados, Informação e Conhecimento", LinhaPesquisa.DR, "Neste tópico são tratados desafios atuais para a modelagem conceitual de organizações, através do uso de Ontologias, com o objetivo de resolver problemas como a Integração semântica de Dados e Interoperabilidade entre Sistemas de Informação. Em especial objetiva-se prover um suporte tecnológico efetivo para: Modelagem e Representação (Ontologias de Fundamentação e Modelagem Conceitual de Dados e Regras de Negócio); Organização e Gestão (Arquitetura de Informação, Transparência da Informação); Descoberta de Conhecimento Organizacional (Mineração de dados, Mineração de textos, Mineração de processos, Mineração de Regras de Negócio) a partir de dados de diversas fontes como bases de dados, documentos texto e XML, emails, blogs, histórias; e Integração de dados e Alinhamento de Ontologias.", true),
	DR007("DR007", "Análise do comportamento de tráfego em redes", LinhaPesquisa.DR, "Este tópico envolve o estudo dos padrões de comportamento dos diversos tipos de tráfego em redes de comunicação e a análise do impacto resultante em sistemas de computação e comunicação. Dentre os diversos aspectos a serem pesquisados, destacam-se: metrologia de redes; implementação e análise de protocolos de comunicação; caracterização e modelagem de tráfego; caracterização e modelagem do comportamento do usuário e de aplicações; análise de desempenho de sistemas de computação e comunicação.", true),
	DR008("DR008", "Gerenciamento de redes", LinhaPesquisa.DR, "Este tópico cobre os desafios relacionados ao gerenciamento de redes face às atuais necessidades e à importância que as infraestruturas de rede exercem no cenário atual dos negócios. Dentre os itens de pesquisa, destacam-se: monitoramento e métricas de interesse; visualização e correlação de informações; gerenciamento autonômico; sistemas de apoio ao gerenciamento de redes; gerenciamento de redes de circuitos dinâmicos.", true),
	DR009("DR009", "Redes definidas por software", LinhaPesquisa.DR, "O advento das Software Defined Networks (SDN), implementadas através do protocolo OpenFlow, trouxe um novo paradigma para o funcionamento das redes de computadores, uma vez que o plano de controle dos equipamentos de rede não mais reside nos equipamentos e passa a ser externo e programável, com regras de encaminhamento muito mais flexíveis sendo instaladas nos datapaths. Esta abordagem permite novas formas de operação e funcionamento das redes, que é o principal ponto de investigação deste tópico.", true),
	DR010("DR010", "Redes móveis ou sem fio", LinhaPesquisa.DR, "Este tópico investiga as redes locais sem fio (IEEE 802.11), redes móveis ad hoc, redes tolerantes a atraso e desconexões, redes veiculares, redes de rádio cognitivo e redes de sensores. Algoritmos e grafos para analisar as redes móveis. Protocolos das camadas de enlace, de rede e de transporte para alguns dos tipos de redes móveis ou sem fio. Representação da mobilidade de usuários sem fio. Aplicações P2P para redes móveis. Modelos analíticos, simulação e medição em redes móveis e sem fio.", true),
	DR011("DR011", "Segurança", LinhaPesquisa.DR, "Este tópico relaciona-se com o estudo de métodos para se caracterizar um padrão de comportamento de tráfego de maneira a permitir a detecção de padrões anômalos que possam estar relacionados com uma possível ameaça de segurança.", true),
	DR012("DR012", "Sistemas multimídias", LinhaPesquisa.DR, "Este tópico engloba estudos ligados às principais aplicações para transmissão de conteúdo multimídia em redes, dentre os quais: distribuição de vídeos, sistemas para transmissão de video-conferência; VoIP; IPTV; sistemas P2P; ensino a distância.", true),

	SAN001("SAN001", "Sistemas Colaborativos", LinhaPesquisa.SAN, "Processos e técnicas de trabalho em grupo: Brainstorming, Decisão, Discussão, JAD, Entrevistas, etc. Sistemas de comunicação mediada por computador: fórum de discussão, bate-papo, videoconferência, VoIP, SMS etc. Sistemas web para compartilhamento de conteúdo: notícias, fotos, vídeos, blog etc. Sistemas para rede de relacionamentos, comunidades virtuais, grupos. Sistemas para suporte à reunião (meetingware), negociação e decisão em grupo. Sistemas e ferramentas para avaliação colaborativa e formativa. Sistemas de Computação Móvel para colaboração. Sistemas Peer-to-Peer.", true),
	SAN002("SAN002", "Sistemas para Educação à Distância", LinhaPesquisa.SAN, "Sistemas e ferramentas para Educação a Distância (Ambientes de Aprendizagem). Sistemas para apoiar Métodos Educacionais (Aprendizagem Colaborativa, Baseada em Projeto, etc.).", true),
	SAN003("SAN003", "Gestão de Conhecimento", LinhaPesquisa.SAN, "Suporte à captura, armazenamento, disponibilização, compartilhamento e uso do conhecimento em organizações. Gestão de Conhecimento baseada em Contexto. Gestão de Conhecimento Orientada a Processos de Negócios. Redes Sociais Organizacionais.", true),
	SAN004("SAN004", "Aprendizagem Organizacional", LinhaPesquisa.SAN, "Suporte computacional a processos de aprendizagem em empresas.", true),
	SAN005("SAN005", "Democracia Digital", LinhaPesquisa.SAN, "Uso de TICs na promoção da participação em assuntos públicos. Colaboração. Transparência. Redes Sociais.", true),
	SAN006("SAN006", "Interface Humano Computador", LinhaPesquisa.SAN, "Acessibilidade. Usabilidade. Uso de Cores em Sistemas de Informação. Globalização de Interfaces com o usuário.", true),
	SAN007("SAN007", "Gestão de Processos de Negócios", LinhaPesquisa.SAN, "Metodologias de levantamento, modelagem e gestão de processos de negócios. Alinhamento de TI ao Negócio. Arquitetura de Tecnologia da Informação. Engenharia de Requisitos de Sistemas com base em Processos de Negócio. Derivação de arquitetura de sistemas com base em Processos de Negócios. Arquitetura Baseada em Serviços. BPMS (Sistemas de Workflow).", true),
	SAN008("SAN008", "Processos de Software", LinhaPesquisa.SAN, "Modelos de Qualidade. Modelos de Maturidade. Métodos Ágeis. Melhoria de Processos de Software. Processo de Desenvolvimento de Software Livre. Gestão de Conhecimento aplicada a Processos de Software. Controle Estatístico de Processos e Alta Maturidade. Retorno de Investimento em Iniciativas de Melhoria de Processos de Software. Ferramentas de apoio a processos de software.", true),
	SAN009("SAN009", "Gerência de projetos de desenvolvimento de software", LinhaPesquisa.SAN, ".", true),
	SAN010("SAN010", "Arquitetura Empresarial", LinhaPesquisa.SAN, "Modelagem e simulação de projetos de software. Técnicas de projeto (design) e construção de software. Medição, controle do processo de projeto (design) e construção de software. Sistemas de controle de configuração (controle de versão, requisições e build) e seu uso no apoio à gestão de projetos. Liberação (release) de software. Evolução de software.", true),
	SAN011("SAN011", "Redes Complexas e Sociais", LinhaPesquisa.SAN, "Mineração, análise e visualização de redes complexas e redes sociais. Ferramentas. Aplicação em domínios.", true),
	SAN012("SAN012", "Engenharia de Software baseada em Buscas", LinhaPesquisa.SAN, "Utilização de métodos de otimização heurística (algoritmos genéticos, simulated annealing, etc) para a resolução de problemas da Engenharia de Software (search-based software engineering), tais como projeto de software, gestão de requisitos, atribuição de tarefas em projetos, entre outros.", true),
	SAN013("SAN013", "Transparência Organizacional", LinhaPesquisa.SAN, "Transparência de Processos. Transparência de Informação. Dados Abertos Governamentais. Modelos de Transparência. Transparência de Software.", true),
	SAN014("SAN014", "Arquitetura Empresarial", LinhaPesquisa.SAN, "Frameworks. Processos de Arquitetura. Gestão de Arquitetura. Interesses Transversais em Arquitetura. Arquitetura de Processos. Arquitetura de Informação. Arquitetura de Sistemas.", true);
	
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