USE [db_externa_core]

SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] ON 

INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (1, N'REGULAR')
INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (2, N'ESPECIAL')
INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (3, N'EXPRESS')
SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[plazo_distribucion] ON 

INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (1, 1, N'ESTÁNDAR', 96, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (2, 1, N'72 HORAS', 72, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (3, 1, N'48 HORAS', 48, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (4, 1, N'24 HORAS', 24, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (5, 1, N'4 HORAS', 4, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (6, 1, N'2 HORAS', 2, 3)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [activo], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (7, 1, N'DIRECTORIO', 2, 3)
SET IDENTITY_INSERT [dbo].[plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[tipo_seguridad] ON 

INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [activo], [nombre]) VALUES (1, 1, N'SIN GPS')
INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [activo], [nombre]) VALUES (2, 1, N'GPS BÁSICO')
SET IDENTITY_INSERT [dbo].[tipo_seguridad] OFF
SET IDENTITY_INSERT [dbo].[tipo_servicio] ON 

INSERT [dbo].[tipo_servicio] ([tipo_servicio_id], [activo], [nombre]) VALUES (1, 1, N'DISTRIBUCIÓN')
SET IDENTITY_INSERT [dbo].[tipo_servicio] OFF

SET IDENTITY_INSERT [dbo].[proveedor] ON 

INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (1, 1, N'DOCFLOW')
INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (2, 1, N'URBANO')
SET IDENTITY_INSERT [dbo].[proveedor] OFF

SET IDENTITY_INSERT [dbo].[tipo_estado_documento] ON 

INSERT [dbo].[tipo_estado_documento] ([tipo_estado_documento_id], [nombre]) VALUES (1, N'ESTADOS UTD')
INSERT [dbo].[tipo_estado_documento] ([tipo_estado_documento_id], [nombre]) VALUES (2, N'RESULTADOS PROVEEDOR')
SET IDENTITY_INSERT [dbo].[tipo_estado_documento] OFF
SET IDENTITY_INSERT [dbo].[estado_documento] ON 

INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (1, N'CREADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (2, N'CUSTODIADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (3, N'PENDIENTE DE ENTREGA', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (4, N'ENTREGADO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (5, N'REZAGADO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (6, N'NO DISTRIBUIBLE', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (7, N'DENEGADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (8, N'ELIMINADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (9, N'CERRADO', 1)
SET IDENTITY_INSERT [dbo].[estado_documento] OFF
SET IDENTITY_INSERT [dbo].[motivo_estado] ON 

INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (1, N'ENTREGADO A TERCERO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (2, N'ENTREGADO A TITULAR', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (3, N'ENTREGADO CON FIRMA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (4, N'ENTREGADO CON SELLO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (5, N'ENTREGADO EN AGENCIA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (6, N'ENTREGADO SIN SELLO', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (7, N'BUZON', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (8, N'BAJO PUERTA', 4)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (9, N'AUSENTE', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (10, N'DIRECCION INCORRECTA', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (11, N'SE MUDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (12, N'DESCONOCIDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (13, N'RECHAZADO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (14, N'NO PERMITE ENTREGA', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (15, N'FALLECIDO', 5)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (16, N'EXTRAVIADO O ROBADO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (17, N'FALTANTE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (18, N'ANULADO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (19, N'DEVUELTO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (20, N'ZONA PELIGROSA', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (21, N'SIN COBERTURA', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (22, N'NO EXISTE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (23, N'NO ACCESIBLE', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (24, N'SIN FISICO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (25, N'SIN RECOJO', 6)
INSERT [dbo].[motivo_estado] ([motivo_estado_id], [nombre], [estado_documento_id]) VALUES (26, N'RETENIDO', 6)
SET IDENTITY_INSERT [dbo].[motivo_estado] OFF

INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (2, 1)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (3, 2)
SET IDENTITY_INSERT [dbo].[estado_guia] ON 

INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (1, N'CREADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (2, N'ENVIADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (3, N'DESCARGADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (4, N'COMPLETA')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (5, N'CERRADO')
SET IDENTITY_INSERT [dbo].[estado_guia] OFF

SET IDENTITY_INSERT [dbo].[estado_autorizado] ON 

INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (1, N'PENDIENTE')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (2, N'APROBADA')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (3, N'DENEGADA')
SET IDENTITY_INSERT [dbo].[estado_autorizado] OFF
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (3, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (4, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (9, 4)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (13, 4)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (5, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (6, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (7, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (8, 6)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (9, 4)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (10, 4)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 4)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 4)
INSERT [dbo].[buzon_tipo_seguridad] ([buzon_id], [tipo_seguridad_id]) VALUES (1, 1)
INSERT [dbo].[subambito_plazo_distribucion] ([subambito_id], [plazo_distribucion_id], [tiempo_envio]) VALUES (1, 1, 96)
INSERT [dbo].[subambito_plazo_distribucion] ([subambito_id], [plazo_distribucion_id], [tiempo_envio]) VALUES (1, 2, 72)
INSERT [dbo].[subambito_plazo_distribucion] ([subambito_id], [plazo_distribucion_id], [tiempo_envio]) VALUES (1, 3, 48)
INSERT [dbo].[subambito_plazo_distribucion] ([subambito_id], [plazo_distribucion_id], [tiempo_envio]) VALUES (1, 4, 24)
INSERT [dbo].[subambito_plazo_distribucion] ([subambito_id], [plazo_distribucion_id], [tiempo_envio]) VALUES (1, 5, 4)

SET IDENTITY_INSERT [dbo].[tipo_envio] ON 
INSERT [dbo].[tipo_envio] ([tipo_envio_id], [nombre]) VALUES (1, N'ENVIO REGULAR')
INSERT [dbo].[tipo_envio] ([tipo_envio_id], [nombre]) VALUES (2, N'ENVIO BLOQUE')
SET IDENTITY_INSERT [dbo].[tipo_envio] OFF

SET IDENTITY_INSERT [dbo].[tipo_guia] ON 
INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (1, N' GUIA REGULAR')
INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (2, N' GUIA BLOQUE')
SET IDENTITY_INSERT [dbo].[tipo_guia] OFF

SET IDENTITY_INSERT [dbo].[tipo_devolucion] ON 
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (1, N'CARGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (2, N'REZAGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (3, N'DENUNCIA')
SET IDENTITY_INSERT [dbo].[tipo_devolucion] OFF