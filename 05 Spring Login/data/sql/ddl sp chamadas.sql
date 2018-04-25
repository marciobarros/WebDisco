--
-- INSERIR UMA NOVA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaInsere;
DELIMITER //
CREATE PROCEDURE ChamadaInsere(vIdUnidade INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP, OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- ATUALIZAR UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaAtualiza;
DELIMITER //
CREATE PROCEDURE ChamadaAtualiza(vIdChamada INT, vNome VARCHAR(80), vSigla VARCHAR(10), vDataAbertura TIMESTAMP, vDataEncerramento TIMESTAMP)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- REMOVER UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaRemove;
DELIMITER //
CREATE PROCEDURE ChamadaAtualiza(vIdChamada INT)
BEGIN
	-- TODO implementar: cancelado = 1
END //
DELIMITER ;

--
-- ENCERRAR UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaEncerra;
DELIMITER //
CREATE PROCEDURE ChamadaEncerra(vIdChamada INT)
BEGIN
	-- TODO implementar: encerrado = 1
END //
DELIMITER ;

--
-- INSERIR UM CAMPO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaInsere;
DELIMITER //
CREATE PROCEDURE CampoChamadaInsere(vIdChamada INT, vTitulo VARCHAR(40), vTipo INT, vDecimais INT, vOpcional INT, vOpcoes TEXT, OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- ATUALIZA OS DADOS DE UM CAMPO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaAtualiza;
DELIMITER //
CREATE PROCEDURE CampoChamadaAtualiza(vIdCampoChamada INT, vTitulo VARCHAR(40), vTipo INT, vDecimais INT, vOpcional INT, vOpcoes TEXT, OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- REMOVE UM CAMPO DE UMA CHAMADA
--

DROP PROCEDURE IF EXISTS CampoChamadaRemove;
DELIMITER //
CREATE PROCEDURE CampoChamadaRemove(vIdCampoChamada INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- INSERIR UM RESULTADO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaInsereResultado;
DELIMITER //
CREATE PROCEDURE ChamadaInsereResultado(vIdChamada INT, vValor VARCHAR(8192), OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- REMOVER UM RESULTADO EM UMA CHAMADA
--

DROP PROCEDURE IF EXISTS ChamadaRemoveResultado;
DELIMITER //
CREATE PROCEDURE ChamadaRemoveResultado(vIdResultado INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;
