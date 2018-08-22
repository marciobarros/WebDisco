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
-- CONECTA UM USUARIO A UMA REDE SOCIAL
--

DROP PROCEDURE IF EXISTS UsuarioConecta;
DELIMITER //
CREATE PROCEDURE UsuarioConecta(vId INT, vProviderId VARCHAR(255), vProviderUserId VARCHAR(255), vProfileUrl VARCHAR(512), vImageUrl VARCHAR(512), vAccessToken VARCHAR(255), vSecret VARCHAR(255), vRefreshToken VARCHAR(255), vExpireTime BIGINT(20))
BEGIN
	UPDATE Usuario
	SET providerId = vProviderId,
	providerUserId = vProviderUserId,
	profileUrl = vProfileUrl,
	imageUrl = vImageUrl,
	accessToken = vAccessToken,
	secret = vSecret,
	refreshToken = vRefreshToken,
	expireTime = vExpireTime,
	dataAtualizacao = NOW()
	WHERE id = vId;
END //
DELIMITER ;


--
-- ATUALIZA A SENHA DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioTrocaSenha;
DELIMITER //
CREATE PROCEDURE UsuarioTrocaSenha(vId INT, vSenha VARCHAR(1024))
BEGIN
	UPDATE Usuario
	SET senha = vSenha,
	bloqueado = 0,
	dataAtualizacao = NOW()
	WHERE id = VId;
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
	SET dataAtualizacao = NOW(),
	tokenSenha = vToken,
	dataTokenSenha = NOW(),
	dataAtualizacao = NOW()
	WHERE id = vId;
END //
DELIMITER ;
