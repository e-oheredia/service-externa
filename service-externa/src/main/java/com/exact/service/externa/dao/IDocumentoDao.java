package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.entity.TipoServicio;

@Repository
public interface IDocumentoDao extends CrudRepository<Documento, Long> {
	
	@Query(value="SELECT TOP 1 documento_autogenerado FROM documento ORDER BY documento_id DESC", nativeQuery=true)
	public String getMaxDocumentoAutogenerado();
	
	@Query("FROM Documento d WHERE d.envio IN (SELECT e FROM Envio e WHERE "
			+ "e.autorizado = 1 AND e.plazoDistribucion.id = ?1 AND e.tipoServicio.id = ?2 AND e.tipoSeguridad.id = ?3) AND "
			+ "d IN (SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = 2) AND "
			+ "d.id NOT IN (SELECT dg.documento.id FROM DocumentoGuia dg)" )
	public Iterable<Documento> findByPlazoDistribucionAndTipoServicioAndTipoSeguridad(Long plazoDistribucionId, Long tipoServicioId, Long tipoSeguridadId);
	
	
	
	
	
}
