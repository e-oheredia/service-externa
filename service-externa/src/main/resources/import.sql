USE [db_externa_core]

SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] ON 

INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (1,'REGULAR')
INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (2,'ESPECIAL')
SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[plazo_distribucion] ON 

INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (1,'ESTÁNDAR', 96, 1, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (2,'72 HORAS', 72, 2, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (3,'48 HORAS', 48, 2, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (4,'24 HORAS', 24, 2, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (5,'4 HORAS', 4, 2, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id], [activo]) VALUES (6,'2 HORAS', 2, 2, 1)
SET IDENTITY_INSERT [dbo].[plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[tipo_seguridad] ON 

INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [nombre], [activo]) VALUES (1,'SIN GPS', 1)
INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [nombre], [activo]) VALUES (2,'GPS BÁSICO', 1)
SET IDENTITY_INSERT [dbo].[tipo_seguridad] OFF
SET IDENTITY_INSERT [dbo].[tipo_servicio] ON 

INSERT [dbo].[tipo_servicio] ([tipo_servicio_id], [nombre], [activo]) VALUES (1,'DISTRIBUCIÓN',1)
SET IDENTITY_INSERT [dbo].[tipo_servicio] OFF

SET IDENTITY_INSERT [dbo].[tipo_estado_documento] ON 

INSERT [dbo].[tipo_estado_documento] ([tipo_estado_documento_id], [nombre]) VALUES (1,'ESTADOS UTD')
INSERT [dbo].[tipo_estado_documento] ([tipo_estado_documento_id], [nombre]) VALUES (2,'RESULTADOS PROVEEDOR')
SET IDENTITY_INSERT [dbo].[tipo_estado_documento] OFF

SET IDENTITY_INSERT [dbo].[estado_documento] ON 

INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (1,'CREADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (2,'CUSTODIADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (3,'PENDIENTE DE ENTREGA', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (4,'ENTREGADO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (5,'REZAGADO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (6,'NO DISTRIBUIBLE', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (7,'DENEGADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (8,'ELIMINADO', 1)
SET IDENTITY_INSERT [dbo].[estado_documento] OFF

INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (1,9)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (2,1)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (3,2)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (2,9)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (3,9)

SET IDENTITY_INSERT [dbo].[proveedor] ON 
INSERT [dbo].[proveedor] ([proveedor_id], [nombre], [activo]) VALUES (1,'DOCFLOW', 1)
INSERT [dbo].[proveedor] ([proveedor_id], [nombre], [activo]) VALUES (2,'URBANO', 1)
SET IDENTITY_INSERT [dbo].[proveedor] OFF

SET IDENTITY_INSERT [dbo].[estado_guia] ON 

INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (1,'CREADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (2,'ENVIADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (3,'DESCARGADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (4,'CERRADO')
SET IDENTITY_INSERT [dbo].[estado_guia] OFF

INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (3, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (4, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (9, 4)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (5, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (6, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (7, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (8, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (9, 4)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 4)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 4)
INSERT [dbo].[buzon_tipo_seguridad] ([buzon_id], [tipo_seguridad_id]) VALUES (1, 1)
SET IDENTITY_INSERT [dbo].[motivo_estado] ON
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (1, 'ENTREGADO A TERCERO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (2, 'ENTREGADO A TITULAR', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (3, 'ENTREGADO CON FIRMA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (4, 'ENTREGADO CON SELLO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (5, 'ENTREGADO EN AGENCIA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (6, 'ENTREGADO SIN SELLO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (7, 'BUZON', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (8, 'BAJO PUERTA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (9, 'AUSENTE', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (10,'DIRECCION INCORRECTA', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (11,'SE MUDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (12,'DESCONOCIDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (13,'RECHAZADO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (14,'NO PERMITE ENTREGA', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (15,'FALLECIDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (16,'EXTRAVIADO/ROBADO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (17,'FALTANTE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (18,'ANULADO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (19,'DEVUELTO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (20,'ZONA PELIGROSA', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (21,'SIN COBERTURA', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (22,'NO EXISTE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (23,'NO ACCESIBLE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (24,'SIN FISICO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (25,'SIN RECOJO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (26,'RETENIDO', 6)
SET IDENTITY_INSERT [dbo].[motivo_estado] OFF