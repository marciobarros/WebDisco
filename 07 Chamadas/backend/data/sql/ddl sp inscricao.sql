--
-- REGISTRAR UMA NOVA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaInsere;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaInsere(vIdChamada INT, vIdUsuario INT, vValoresCampos LONGTEXT, OUT id INT)
BEGIN
	INSERT INTO InscricaoChamada (dataRegistro, dataAtualizacao, idChamada, idUsuario, valoresCampos)
	VALUES (NOW(), NOW(), vIdChamada, vIdUsuario, vValoresCampos);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;


--
-- CANCELAR UMA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaCancela;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaCancela(vIdInscricao INT)
BEGIN
	UPDATE InscricaoChamada
    SET cancelada = 1
    WHERE id = vIdInscricao;
END //
DELIMITER ;


--
-- ENVIAR UMA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaEnvia;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaEnvia(vIdInscricao INT)
BEGIN
	UPDATE InscricaoChamada
    SET dataInscricao = NOW()
    WHERE id = vIdInscricao;
END //
DELIMITER ;
