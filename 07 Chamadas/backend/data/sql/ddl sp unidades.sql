--
-- CRIAR UMA NOVA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalInsere;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalInsere(vNome VARCHAR(80), vSigla VARCHAR(10), OUT id INT)
BEGIN
    INSERT INTO UnidadeFuncional (dataRegistro, dataAtualizacao, nome, sigla)
	VALUES (NOW(), NOW(), vNome, vSigla);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

--
-- ATUALIZAR OS DADOS DE UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalAtualiza;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalAtualiza(vIdUnidade INT, vNome VARCHAR(80), vSigla VARCHAR(10))
BEGIN
	UPDATE UnidadeFuncional
	SET nome = vNome,
	sigla = vSigla,
	dataAtualizacao = NOW()
	WHERE id = vIdUnidade;
END //
DELIMITER ;

--
-- REMOVER UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalRemove;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalRemove(vIdUnidade INT)
BEGIN
	DELETE
	FROM GestorUnidadeFuncional
	WHERE idUnidade = vIdUnidade;
	
    DELETE 
	FROM UnidadeFuncional 
	WHERE id = vIdUnidade;
END //
DELIMITER ;

--
-- ASSOCIAR UM GESTOR A UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalAssociaGestor;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalAssociaGestor(vIdUnidade INT, vIdUsuario INT)
BEGIN
    INSERT INTO GestorUnidadeFuncional (idUnidade, idGestor)
	VALUES (vIdUnidade, vIdUsuario);
END //
DELIMITER ;

--
-- DESASSOCIAR UM GESTOR A UMA UNIDADE FUNCIONAL
--

DROP PROCEDURE IF EXISTS UnidadeFuncionalDesassociaGestores;
DELIMITER //
CREATE PROCEDURE UnidadeFuncionalDesassociaGestores(vIdUnidade INT)
BEGIN
	DELETE
	FROM GestorUnidadeFuncional
	WHERE idUnidade = vIdUnidade;
END //
DELIMITER ;
