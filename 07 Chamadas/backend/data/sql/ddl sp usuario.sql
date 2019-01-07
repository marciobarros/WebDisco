--
-- CRIA UM NOVO USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioInsere;
DELIMITER //
CREATE PROCEDURE UsuarioInsere(vNome VARCHAR(80), vEmail VARCHAR(80), vSenha VARCHAR(80), OUT id INT)
BEGIN
	INSERT INTO Usuario (dataRegistro, dataAtualizacao, nome, email, senha)
	VALUES (NOW(), NOW(), vNome, vEmail, vSenha);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;


--
-- ATUALIZA A SENHA DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioTrocaSenha;
DELIMITER //
CREATE PROCEDURE UsuarioTrocaSenha(vId INT, vSenha VARCHAR(80))
BEGIN
	UPDATE Usuario
	SET senha = vSenha,
	bloqueado = 0,
	dataAtualizacao = NOW()
	WHERE id = vId;
END //
DELIMITER ;


--
-- REGISTRA UM LOGIN BEM SUCEDIDO DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraLoginSucesso;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraLoginSucesso(vEmail VARCHAR(80))
BEGIN
	UPDATE Usuario
	SET dataUltimoLogin = NOW(),
	falhasLogin = 0,
	dataAtualizacao = NOW()
	WHERE email = vEmail;
END //
DELIMITER ;


--
-- REGISTRA UM LOGIN MAL SUCEDIDO DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraLoginFalha;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraLoginFalha(vEmail VARCHAR(80))
BEGIN
	DECLARE lTentativas INT;

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;

	UPDATE Usuario
	SET falhasLogin = falhasLogin + 1
	WHERE email = vEmail;

	SELECT falhasLogin
	INTO lTentativas
	FROM Usuario
	WHERE email = vEmail;

	IF lTentativas >= 3 THEN 
		UPDATE Usuario
		SET bloqueado = 1,
		dataAtualizacao = NOW()
		WHERE email = vEmail;
	END IF;
	
  	COMMIT;
END //
DELIMITER ;


--
-- REGISTRA UM TOKEN DE LOGIN PARA UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraTokenResetSenha;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraTokenResetSenha(vId INT, vToken VARCHAR(256))
BEGIN
	UPDATE Usuario
	SET tokenSenha = vToken,
	dataTokenSenha = NOW(),
	dataAtualizacao = NOW()
	WHERE id = vId;
END //
DELIMITER ;


--
-- ATUALIZA O NOME E A FOTO DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioAtualiza;
DELIMITER //
CREATE PROCEDURE UsuarioAtualiza(vId INT, vNome VARCHAR(80), vImageUrl VARCHAR(512))
BEGIN
	UPDATE Usuario
	SET nome = vNome,
	imageUrl = vImageUrl,
	dataAtualizacao = NOW()
	WHERE id = vId;
END //
DELIMITER ;
