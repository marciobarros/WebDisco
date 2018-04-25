--
-- CRIAR UMA NOVA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalInsere;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalInsere(vNome VARCHAR(80), vSigla VARCHAR(10), OUT id INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- ASSOCIAR UM GESTOR A UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalAssociaGestor;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalAssociaGestor(vIdUnidade INT, vIdUsuario INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- DESASSOCIAR UM GESTOR A UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalDesassociaGestor;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalDesassociaGestor(vIdUnidade INT, vIdUsuario INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- ATUALIZAR OS DADOS DE UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalAtualiza;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalAtualiza(vIdUnidade INT, vNome VARCHAR(80), vSigla VARCHAR(10))
BEGIN
	-- TODO implementar
END //
DELIMITER ;

--
-- REMOVER UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalRemove;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalRemove(vIdUnidade INT)
BEGIN
	-- TODO implementar
END //
DELIMITER ;
