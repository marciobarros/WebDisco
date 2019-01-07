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
    tokenSenha VARCHAR(256) DEFAULT NULL,
    dataTokenSenha TIMESTAMP NULL,
    falhasLogin INT NOT NULL DEFAULT 0,
    dataUltimoLogin TIMESTAMP NULL,
    bloqueado INT NOT NULL DEFAULT 0,
    administrador INT NOT NULL DEFAULT 0,
--  socialId VARCHAR(256) DEFAULT NULL,
--  providerId VARCHAR(255) DEFAULT NULL,
--  providerUserId VARCHAR(255) DEFAULT NULL,
--  profileUrl VARCHAR(512) DEFAULT NULL,
--  imageUrl VARCHAR(512) DEFAULT NULL,
--  accessToken VARCHAR(255) DEFAULT NULL,
--  secret VARCHAR(255) DEFAULT NULL,
--  refreshToken VARCHAR(255) DEFAULT NULL,
--  expireTime bigint(20) DEFAULT NULL,

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
    campos LONGTEXT DEFAULT "",
    anexos LONGTEXT DEFAULT "",

    CONSTRAINT pkChamada PRIMARY KEY(id),
    CONSTRAINT ctSiglaChamadaUnica UNIQUE(sigla),
    CONSTRAINT fkChamadaUnidade FOREIGN KEY(idUnidade) REFERENCES UnidadeFuncional(id)
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
	cancelada INT NOT NULL DEFAULT 0,
    valoresCampos LONGTEXT DEFAULT "",

    CONSTRAINT pkInscricaoChamada PRIMARY KEY(id),
    CONSTRAINT fkInscricaoChamada FOREIGN KEY(idChamada) REFERENCES Chamada(id),
    CONSTRAINT fkInscricaoUsuario FOREIGN KEY(idUsuario) REFERENCES Usuario(id)
);