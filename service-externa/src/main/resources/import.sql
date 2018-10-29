insert into tipo_estado_documento values ('ESTADOS UTD');
insert into tipo_estado_documento values ('RESULTADOS PROVEEDOR')
insert into estado_documento values ('CREADO',1);
insert into estado_documento values ('CUSTODIADO',1);
insert into estado_documento values ('PENDIENTE DE ENTREGA',1);
insert into estado_documento values ('ENTREGADO',2);
insert into estado_documento values ('REZAGADO',2);
insert into estado_documento values ('DEVUELTO',2);
insert into estado_documento values ('EXTRAVIADO',2);
insert into estado_documento values ('DENEGADO',1);
insert into estado_documento values ('RETIRADO',1);

insert into tipo_plazo_distribucion(nombre) values('REGULAR');
insert into tipo_plazo_distribucion(nombre) values('ESPECIAL');
insert into plazo_distribucion(nombre, tipo_plazo_distribucion_id, tiempo_envio) values('ESTÁNDAR', 1, 96);
insert into plazo_distribucion(nombre, tipo_plazo_distribucion_id, tiempo_envio) values('72 HORAS', 2, 72);
insert into plazo_distribucion(nombre, tipo_plazo_distribucion_id, tiempo_envio) values('48 HORAS', 2, 48);
insert into plazo_distribucion(nombre, tipo_plazo_distribucion_id, tiempo_envio) values('24 HORAS', 2, 24);
insert into tipo_seguridad(nombre) values('SIN GPS');
insert into tipo_seguridad(nombre) values ('GPS BÁSICO');
insert into buzon_plazo_distribucion(buzon_id, plazo_distribucion_id) values(1,2);
insert into area_plazo_distribucion(area_id, plazo_distribucion_id) values(1,2);
insert into buzon_tipo_seguridad(buzon_id, tipo_seguridad_id) values(1,1);
insert into tipo_servicio(nombre) values('DISTRIBUCIÓN');
insert into envio(autorizado,buzon_id,ruta_autorizacion,tipo_documento_id,plazo_distribucion_id,tipo_seguridad_id,tipo_servicio_id) VALUES(1,1,'RUTA_PRUEBA1',1,1,1,1)
insert into envio(autorizado,buzon_id,ruta_autorizacion,tipo_documento_id,plazo_distribucion_id,tipo_seguridad_id,tipo_servicio_id) VALUES(1,1,'RUTA_PRUEBA2',1,1,1,1)
insert into envio(autorizado,buzon_id,ruta_autorizacion,tipo_documento_id,plazo_distribucion_id,tipo_seguridad_id,tipo_servicio_id) VALUES(1,1,'RUTA_PRUEBA3',1,1,1,1)
insert into envio(autorizado,buzon_id,ruta_autorizacion,tipo_documento_id,plazo_distribucion_id,tipo_seguridad_id,tipo_servicio_id) VALUES(1,1,'RUTA_PRUEBA4',1,1,1,1)
insert into envio(autorizado,buzon_id,ruta_autorizacion,tipo_documento_id,plazo_distribucion_id,tipo_seguridad_id,tipo_servicio_id) VALUES(1,1,'RUTA_PRUEBA5',1,1,1,1)
insert into envio_masivo(masivo_autogenerado, envio_id) VALUES('MEX00001',1)
insert into envio_masivo(masivo_autogenerado, envio_id) VALUES('MEX00002',3)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 1','DIRECCION 1',1,'A00001',1,'RAZON SOCIAL 1','','',1,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 2','DIRECCION 2',1,'A00002',1,'RAZON SOCIAL 2','','',1,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 3','DIRECCION 3',1,'A00003',1,'RAZON SOCIAL 3','','',1,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 4','DIRECCION 4',1,'A00004',1,'RAZON SOCIAL 4','','',2,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 5','DIRECCION 5',1,'A00005',1,'RAZON SOCIAL 5','','',3,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 6','DIRECCION 6',1,'A00006',1,'RAZON SOCIAL 6','','',3,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 7','DIRECCION 7',1,'A00007',1,'RAZON SOCIAL 7','','',3,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 8','DIRECCION 8',1,'A00008',1,'RAZON SOCIAL 8','','',3,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 9','DIRECCION 9',1,'A00009',1,'RAZON SOCIAL 9','','',4,0)
insert into documento(contacto_destino,direccion,distrito_id,documento_autogenerado,nro_documento,razon_social_destino,referencia,telefono,envio_id,recepcionado) values('CONTACTO 10','DIRECCION 10',1,'A00010',1,'RAZON SOCIAL 10','','',5,0)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,1,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,2,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,3,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,4,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,5,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,6,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,7,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,8,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,9,1)
insert into seguimiento_documento(fecha,link_imagen,observacion,usuario_id,documento_id,estado_documento_id) values(getdate(),'linkimagen','registro de prueba',1,10,1)
insert into proveedor(nombre) values('DOCFLOW')
insert into proveedor(nombre) values('URBANO')
insert into estado_guia(nombre) values('CREADO')
insert into estado_guia(nombre) values('ENVIADO')
insert into estado_guia(nombre) values('DESCARGADO')
  insert into proveedor_plazo_distribucion values(1,1)
  insert into proveedor_plazo_distribucion values(1,2)
  insert into proveedor_plazo_distribucion values(1,3)
  insert into proveedor_plazo_distribucion values(1,4)
  insert into proveedor_plazo_distribucion values(2,1)
  insert into proveedor_plazo_distribucion values(2,2)
  insert into proveedor_plazo_distribucion values(2,3)
  insert into proveedor_plazo_distribucion values(2,4)
