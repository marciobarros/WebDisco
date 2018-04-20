CREATE DATABASE UnirioChamadas
CHARACTER SET UTF8 
COLLATE utf8_general_ci;

USE UnirioChamadas;

--
-- Usuarios do sistema
--

CREATE TABLE Usuario
(
    id INT NOT NULL AUTO_INCREMENT,
	dataRegistro TIMESTAMP NULL,
	dataAtualizacao TIMESTAMP NULL,
    nome VARCHAR(80) NOT NULL,
    email VARCHAR(80) NOT NULL,
    senha VARCHAR(80) NOT NULL,
    socialId VARCHAR(256) DEFAULT NULL,
    tokenSenha VARCHAR(256) DEFAULT NULL,
    dataTokenSenha TIMESTAMP NULL,
    falhasLogin INT NOT NULL DEFAULT 0,
    bloqueado INT NOT NULL DEFAULT 0,
    dataUltimoLogin TIMESTAMP NULL,
    administrador INT NOT NULL DEFAULT 0,
    
    CONSTRAINT pkUsuario PRIMARY KEY(id),
    CONSTRAINT ctEmailUnico UNIQUE(email)
);

--
-- Divisoes funcionais da UNIRIO
--

CREATE TABLE UnidadeFuncional
(
    id INT NOT NULL AUTO_INCREMENT,
 	dataRegistro TIMESTAMP NULL,
	dataAtualizacao TIMESTAMP NULL,
	nome VARCHAR(80) NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    
    CONSTRAINT pkUnidade PRIMARY KEY(id),
    CONSTRAINT ctNomeUnidadeUnico UNIQUE(nome),
    CONSTRAINT ctSiglaUnidadeUnica UNIQUE(sigla)
);

--
-- Gestores das unidades funcionais
--

CREATE TABLE GestorUnidadeFuncional
(
    idUnidade INT NOT NULL,
    idGestor INT NOT NULL,
    
    CONSTRAINT pkGestorUnidade PRIMARY KEY(idUnidade, idGestor),
    CONSTRAINT fkUnidade FOREIGN KEY(idUnidade) REFERENCES UnidadeFuncional(id),
    CONSTRAINT fkGestor FOREIGN KEY(idGestor) REFERENCES Usuario(id)
);

--
-- Chamada realizada por uma unidade
--

CREATE TABLE Chamada
(
    id INT NOT NULL AUTO_INCREMENT,
	dataRegistro TIMESTAMP NULL,
	dataAtualizacao TIMESTAMP NULL,
	idUnidade INT NOT NULL,
    nome VARCHAR(80) NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    dataAbertura TIMESTAMP NULL,
    dataEncerramento TIMESTAMP NULL,
    cancelada INT NOT NULL DEFAULT 0,
    encerrada INT NOT NULL DEFAULT 0,

    CONSTRAINT pkChamada PRIMARY KEY(id),
    CONSTRAINT ctSiglaChamadaUnica UNIQUE(sigla),
    CONSTRAINT fkChamadaUnidade FOREIGN KEY(idUnidade) REFERENCES UnidadeFuncional(id)
);

--
-- Campos no formulario de uma chamada
--

CREATE TABLE CampoChamada
(
    id INT NOT NULL AUTO_INCREMENT,
    idChamada INT NOT NULL,
    titulo VARCHAR(40) NOT NULL,
    tipo INT NOT NULL,
    decimais INT,
    opcional INT NOT NULL,
    jsonOpcoes TEXT,			-- ["opcao 1", "opcao 2"]
    
    CONSTRAINT pkCampoChamada PRIMARY KEY(id),
    CONSTRAINT fkCampoChamada FOREIGN KEY(idChamada) REFERENCES Chamada(id)
);

--
-- Inscricao de um usuario em uma chamada
--

CREATE TABLE InscricaoChamada
(
    id INT NOT NULL AUTO_INCREMENT,
	dataRegistro TIMESTAMP NULL,
	dataAtualizacao TIMESTAMP NULL,
	idChamada INT NOT NULL,
	idUsuario INT NOT NULL,
	dataInscricao TIMESTAMP NULL,

    CONSTRAINT pkInscricaoChamada PRIMARY KEY(id),
    CONSTRAINT fkInscricaoChamada FOREIGN KEY(idChamada) REFERENCES Chamada(id),
    CONSTRAINT fkInscricaoUsuario FOREIGN KEY(idUsuario) REFERENCES Usuario(id)
);

--
-- Valor preenchido em um campo da inscricao de um usuario
--

CREATE TABLE InscricaoCampoChamada
(
    id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idCampoChamada INT NOT NULL,
	valor VARCHAR(8192),
    
    CONSTRAINT pkInscricaoCampoChamada PRIMARY KEY(id),
    CONSTRAINT fkInscricaoCampoChamadaInscricao FOREIGN KEY(idInscricao) REFERENCES InscricaoChamada(id),
    CONSTRAINT fkInscricaoCampoChamadaChamada FOREIGN KEY(idCampoChamada) REFERENCES CampoChamada(id)
);

--
-- Resultado enviado por um gestor a uma chamada
--

CREATE TABLE ResultadoChamada
(
    id INT NOT NULL AUTO_INCREMENT,
	idChamada INT NOT NULL,
	valor VARCHAR(8192),
    
    CONSTRAINT pkResultadoChamada PRIMARY KEY(id),
    CONSTRAINT fkResultadoChamadaChamada FOREIGN KEY(idChamada) REFERENCES Chamada(id)
);
