--
-- REGISTRAR UMA NOVA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaInsere;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaInsere(vIdChamada INT, vIdUsuario INT, OUT id INT)
BEGIN
	INSERT INTO InscricaoChamada (dataRegistro, dataAtualizacao, idChamada, idUsuario)
	VALUES (NOW(), NOW(), vIdChamada, vIdUsuario);

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
-- REGISTRAR O VALOR DE UM CAMPO EM UMA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaInsereValorCampo;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaInsereValorCampo(vIdInscricao INT, vIdCampo INT, vValor VARCHAR(8192))
BEGIN
    INSERT INTO InscricaoCampoChamada(idInscricao, idCampoChamada, valor)
	VALUES (vIdInscricao, vIdCampo, vValor);
END //
DELIMITER ;


