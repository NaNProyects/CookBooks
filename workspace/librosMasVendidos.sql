-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `librosMasVendidos`(desde Date, hasta Date, cantidad INT)
BEGIN
	select isbn, titulo, precio, genero, reseña, vistazo, editorial, idioma, nombre, apellido, idAutor
	from libro inner join autor on idautor = autor
			inner join libroPedido on libro = isbn
	where pedido in (select idPedido from pedido where fecha between (entre) and (hasta))
	group by isbn
	order by count(*) desc
	LIMIT cantidad;
END