DROP PROCEDURE IF EXISTS InsereCompactDisc;
DELIMITER //
CREATE PROCEDURE InsereCompactDisc(vTitulo VARCHAR(80), vPreco NUMERIC(14, 2), vEstoque NUMERIC(14, 2), OUT id INT)
BEGIN
	INSERT INTO CompactDisc (title, price, stock)
	VALUES (vTitulo, vPreco, vEstoque);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS AtualizaCompactDisc;
DELIMITER //
CREATE PROCEDURE AtualizaCompactDisc(vId INT, vTitulo VARCHAR(80), vPreco NUMERIC(14, 2), vEstoque NUMERIC(14, 2))
BEGIN
	UPDATE CompactDisc
	SET title = vTitulo,
	price = vPreco,
	stock = vEstoque
	WHERE id = vId;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS RemoveCompactDisc;
DELIMITER //
CREATE PROCEDURE RemoveCompactDisc(vId INT)
BEGIN
	DELETE 
	FROM CompactDisc 
	WHERE id = vId;
END //
DELIMITER ;
