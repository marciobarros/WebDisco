--
-- INSERIR UMA NOVA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaInsere;
DELIMITER //
CREATE PROCEDURE ChamadaInsere(vIdUnidade INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP, vCampos LONGTEXT, vAnexos LONGTEXT, OUT id INT)
BEGIN
    INSERT INTO Chamada (dataRegistro, dataAtualizacao, idUnidade, nome, sigla, dataAbertura, dataEncerramento, campos, anexos)
	VALUES (NOW(), NOW(), vIdUnidade, vNome, vSigla, vDataAbertura, vDataEncerramento, vCampos, vAnexos);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

--
-- ATUALIZAR UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaAtualiza;
DELIMITER //
CREATE PROCEDURE ChamadaAtualiza(vIdChamada INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP, vCampos LONGTEXT, vAnexos LONGTEXT)
BEGIN
    UPDATE Chamada
	SET nome = vNome,
    sigla = vSigla,
    dataAbertura = vDataAbertura,
    dataEncerramento = vDataEncerramento, 
	dataAtualizacao = NOW(),
	campos = vCampos,
	anexos = vAnexos
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
