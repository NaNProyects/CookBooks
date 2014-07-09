-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `usuariosEntre`(desde Date, hasta Date)
BEGIN
	select * from usuario where (fechaRegistro between(desde) and (hasta));
END