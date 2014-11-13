--
-- SP PARA INSERIR UM USUARIO
--
DROP PROCEDURE IF EXISTS InsereUsuario;
DELIMITER //
CREATE PROCEDURE InsereUsuario(vNome VARCHAR(80), vEmail VARCHAR(80), vSenha VARCHAR(80), vEndereco VARCHAR(80), vComplemento VARCHAR(80), vEstado VARCHAR(2), vMunicipio VARCHAR(80), vCep VARCHAR(10), vTelefoneFixo VARCHAR(20), vTelefoneCelular VARCHAR(20), OUT vId INT)
BEGIN
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	
	INSERT INTO Usuario(dataRegistro, nome, email, senha, endereco, complemento, estado, municipio, cep, telefoneFixo, telefoneCelular)
	VALUES (now(), vNome, vEmail, vSenha, vEndereco, vComplemento, vEstado, vMunicipio, vCep, vTelefoneFixo, vTelefoneCelular);

	SET vId = LAST_INSERT_ID();
	COMMIT;
END //
DELIMITER ;

--
-- SP PARA ATUALIZAR UM USUARIO
--
DROP PROCEDURE IF EXISTS AtualizaUsuario;
DELIMITER //
CREATE PROCEDURE AtualizaUsuario(vId INT, vNome VARCHAR(80), vEmail VARCHAR(80), vEndereco VARCHAR(80), vComplemento VARCHAR(80), vEstado VARCHAR(2), vMunicipio VARCHAR(80), vCep VARCHAR(10), vTelefoneFixo VARCHAR(20), vTelefoneCelular VARCHAR(20))
BEGIN
	UPDATE Usuario
	SET nome = vNome,
	email = vEmail,
	endereco = vEndereco,
	complemento = vComplemento,
	estado = vEstado,
	municipio = vMunicipio,
	cep = vCep,
	telefoneFixo = vTelefoneFixo,
	telefoneCelular = vTelefoneCelular
	WHERE id = vId;
END //
DELIMITER ;

--
-- SP PARA REMOVER UM USUARIO DO SISTEMA
--
DROP PROCEDURE IF EXISTS RemoveUsuario;
DELIMITER //
CREATE PROCEDURE RemoveUsuario(vIdUsuario INT)
BEGIN
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	
	DELETE FROM UsuarioLogin
	WHERE idUsuario = vIdUsuario;
	
	DELETE FROM UsuarioToken
	WHERE idUsuario = vIdUsuario;
	
	DELETE FROM Usuario
	WHERE id = vIdUsuario;
	
  	COMMIT;
END //
DELIMITER ;

--
-- SP PARA ATUALIZAR A SENHA DE UM USUARIO
--
DROP PROCEDURE IF EXISTS AtualizaSenhaUsuario;
DELIMITER //
CREATE PROCEDURE AtualizaSenhaUsuario(vId INT, vSenha VARCHAR(80))
BEGIN
	UPDATE Usuario
	SET senha = vSenha,
	deveTrocarSenha = 0
	WHERE id = vId;
END //
DELIMITER ;

--
-- SP PARA REGISTRAR UM LOGIN FEITO COM SUCESSO
--
DROP PROCEDURE IF EXISTS RegistraLoginSucesso;
DELIMITER //
CREATE PROCEDURE RegistraLoginSucesso(vIdUsuario INT)
BEGIN
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	
	INSERT INTO UsuarioLogin(idUsuario, data, sucesso)
	VALUES(vIdUsuario, now(), 1);

	UPDATE Usuario
	SET deveTrocarSenha = 0
	WHERE id = vIdUsuario;
	
	COMMIT;
END //
DELIMITER ;

--
-- SP PARA REGISTRAR UM LOGIN FEITO MAL SUCEDIDO
--
DROP PROCEDURE IF EXISTS RegistraLoginFalha;
DELIMITER //
CREATE PROCEDURE RegistraLoginFalha(vIdUsuario INT)
BEGIN
	DECLARE lTentativas INT;
	DECLARE lDataSucesso DATETIME;
	
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	
	INSERT INTO UsuarioLogin(idUsuario, data, sucesso)
	VALUES(vIdUsuario, now(), 0);
	
	SELECT COALESCE(max(data), '2000-01-01')
	INTO lDataSucesso
	FROM UsuarioLogin
	WHERE idUsuario = vIdUsuario 
	AND sucesso = 1;
	
	SELECT count(*)
	INTO lTentativas
	FROM UsuarioLogin
	WHERE idUsuario = vIdUsuario
	AND data > lDataSucesso;

	IF lTentativas >= 3 THEN 
		UPDATE Usuario
		SET deveTrocarSenha = 1
		WHERE id = vIdUsuario;
	END IF;
	
  	COMMIT;
END //
DELIMITER ;

--
-- SP PARA REGISTRAR UM TOKEN PARA TROCA DE SENHA
--
DROP PROCEDURE IF EXISTS RegistraTokenResetSenha;
DELIMITER //
CREATE PROCEDURE RegistraTokenResetSenha(vIdUsuario INT, vToken VARCHAR(256))
BEGIN
	INSERT INTO UsuarioToken (idUsuario, data, token)
	VALUES (vIdUsuario, now(), vToken);
END //
DELIMITER ;
