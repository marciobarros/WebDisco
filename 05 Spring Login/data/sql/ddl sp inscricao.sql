--
-- REGISTRAR UMA NOVA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaInsere;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaInsere(vIdChamada INT, vIdUsuario INT, OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- CANCELAR UMA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaCancela;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaCancela(vIdInscricao INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- REGISTRAR O VALOR DE UM CAMPO EM UMA INSCRICAO EM CHAMADA
--

DROP PROCEDURE IF EXISTS InscricaoChamadaInsereValorCampo;
DELIMITER //
CREATE PROCEDURE InscricaoChamadaInsereValorCampo(vIdInscricao INT, vIdCampo INT, vValor VARCHAR(8192))
BEGIN
	-- TODO implementar
END //
DELIMITER ;


