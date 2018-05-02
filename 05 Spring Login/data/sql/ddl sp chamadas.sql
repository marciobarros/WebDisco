--
-- INSERIR UMA NOVA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaInsere;
DELIMITER //
CREATE PROCEDURE ChamadaInsere(vIdUnidade INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP, OUT id INT)
BEGIN
    INSERT INTO Chamada (dataRegistro, dataAtualizacao, idUnidade, nome, sigla, dataAbertura, dataEncerramento)
	VALUES (NOW(), NOW(), vIdUnidade, vNome, vSigla, vDataAbertura, vDataEncerramento);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

--
-- ATUALIZAR UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaAtualiza;
DELIMITER //
CREATE PROCEDURE ChamadaAtualiza(vIdChamada INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP)
BEGIN
    UPDATE Chamada
	SET nome = vNome,
    sigla = vSigla,
    dataAbertura = vDataAbertura,
    dataEncerramento = vDataEncerramento, 
	dataAtualizacao = NOW()
	WHERE id = vIdChamada;
END //
DELIMITER ;

--
-- REMOVER UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaRemove;
DELIMITER //
CREATE PROCEDURE ChamadaRemove(vIdChamada INT)
BEGIN
    UPDATE Chamada
    SET cancelada = 1,
	dataAtualizacao = NOW()
    WHERE id = vIdChamada;
END //
DELIMITER ;

--
-- ENCERRAR UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaEncerra;
DELIMITER //
CREATE PROCEDURE ChamadaEncerra(vIdChamada INT)
BEGIN
    UPDATE Chamada
    SET encerrada = 1,
	dataAtualizacao = NOW()
    WHERE id = vIdChamada;
END //
DELIMITER ;

--
-- INSERIR UM CAMPO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaInsere;
DELIMITER //
CREATE PROCEDURE CampoChamadaInsere(vIdChamada INT, vTitulo VARCHAR(40), vTipo INT, vDecimais INT, vOpcional INT, vOpcoes TEXT, OUT id INT)
BEGIN
    INSERT INTO CampoChamada (idChamada, titulo, tipo, decimais, opcional, jsonOpcoes)
	VALUES (vIdChamada, vTitulo, vTipo, vDecimais, vOpcional, vOpcoes);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

--
-- ATUALIZA OS DADOS DE UM CAMPO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaAtualiza;
DELIMITER //
CREATE PROCEDURE CampoChamadaAtualiza(vIdCampoChamada INT, vTitulo VARCHAR(40), vTipo INT, vDecimais INT, vOpcional INT, vOpcoes TEXT)
BEGIN
    UPDATE CampoChamada
	SET  titulo = vTitulo, 
    tipo = vTipo, 
    decimais = vDecimais, 
    opcional = vOpcional, 
    jsonOpcoes = vOpcoes
	WHERE id = vIdCampoChamada;
END //
DELIMITER ;

--
-- REMOVE UM CAMPO DE UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaRemove;
DELIMITER //
CREATE PROCEDURE CampoChamadaRemove(vIdCampoChamada INT)
BEGIN
    DELETE 
	FROM CampoChamada 
	WHERE id = vIdCampoChamada;
END //
DELIMITER ;

--
-- INSERIR UM RESULTADO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaInsereResultado;
DELIMITER //
CREATE PROCEDURE ChamadaInsereResultado(vIdChamada INT, vValor VARCHAR(8192), OUT id INT)
BEGIN
	INSERT INTO ResultadoChamada (idChamada, valor)
	VALUES (vIdChamada, vValor);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

--
-- REMOVER UM RESULTADO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaRemoveResultado;
DELIMITER //
CREATE PROCEDURE ChamadaRemoveResultado(vIdResultado INT)
BEGIN
	DELETE FROM ResultadoChamada 
	WHERE id = vIdResultado;
END //
DELIMITER ;
