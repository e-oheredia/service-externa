USE [db_externa_core]
GO
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
SET IDENTITY_INSERT [dbo].[tipo_envio] ON 

INSERT [dbo].[tipo_envio] ([tipo_envio_id], [nombre]) VALUES (1, N'ENVIO REGULAR')
INSERT [dbo].[tipo_envio] ([tipo_envio_id], [nombre]) VALUES (2, N'ENVIO BLOQUE')
SET IDENTITY_INSERT [dbo].[tipo_envio] OFF
SET IDENTITY_INSERT [dbo].[tipo_seguridad] ON 

INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [activo], [nombre]) VALUES (1, 1, N'SIN GPS')
INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [activo], [nombre]) VALUES (2, 1, N'GPS BÁSICO')
SET IDENTITY_INSERT [dbo].[tipo_seguridad] OFF
SET IDENTITY_INSERT [dbo].[tipo_servicio] ON 

INSERT [dbo].[tipo_servicio] ([tipo_servicio_id], [activo], [nombre]) VALUES (1, 1, N'DISTRIBUCIÓN')
SET IDENTITY_INSERT [dbo].[tipo_servicio] OFF
SET IDENTITY_INSERT [dbo].[envio] ON 

INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (1, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (2, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (3, 5, 1, NULL, 5, 1, 2, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[envio] OFF
SET IDENTITY_INSERT [dbo].[documento] ON 

INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (1, NULL, N'RONALD SANTOS', N'DIR70', 1, N'EX00000001', N'DOC000001', N'RONALD SANTOS', 0, N'ABC', N'5556', 1)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (2, NULL, N'RONALD SANTOS', N'DIR72', 1, N'EX00000002', N'DOC000003', N'RONALD SANTOS', 0, N'ABC', N'5558', 1)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (3, NULL, N'RONALD SANTOS', N'DIR71', 1, N'EX00000003', N'DOC000002', N'RONALD SANTOS', 0, N'ABC', N'5557', 1)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (4, NULL, N'RONALD SANTOS', N'DIR73', 1, N'EX00000004', N'DOC000004', N'RONALD SANTOS', 0, N'ABC', N'5559', 1)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (5, NULL, N'RONALD SANTOS', N'DIR70', 1, N'EX00000005', N'DOC000005', N'RONALD SANTOS', 0, N'ABC', N'5556', 2)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (6, NULL, N'RONALD SANTOS', N'DIR73', 1, N'EX00000006', N'DOC000008', N'RONALD SANTOS', 0, N'ABC', N'5559', 2)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (7, NULL, N'RONALD SANTOS', N'DIR71', 1, N'EX00000007', N'DOC000006', N'RONALD SANTOS', 0, N'ABC', N'5557', 2)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (8, NULL, N'RONALD SANTOS', N'DIR72', 1, N'EX00000008', N'DOC000007', N'RONALD SANTOS', 0, N'ABC', N'5558', 2)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (9, NULL, N'RONALD SANTOS', N'DIR73', 1, N'EX00000009', N'DOC000016', N'RONALD SANTOS', 0, N'ABC', N'5559', 3)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (10, NULL, N'RONALD SANTOS', N'DIR70', 1, N'EX00000010', N'DOC000013', N'RONALD SANTOS', 0, N'ABC', N'5556', 3)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (11, NULL, N'RONALD SANTOS', N'DIR71', 1, N'EX00000011', N'DOC000014', N'RONALD SANTOS', 0, N'ABC', N'5557', 3)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (12, NULL, N'RONALD SANTOS', N'DIR72', 1, N'EX00000012', N'DOC000015', N'RONALD SANTOS', 0, N'ABC', N'5558', 3)
SET IDENTITY_INSERT [dbo].[documento] OFF
INSERT [dbo].[envio_masivo] ([masivo_autogenerado], [envio_id]) VALUES (N'MEX00000001', 1)
INSERT [dbo].[envio_masivo] ([masivo_autogenerado], [envio_id]) VALUES (N'MEX00000002', 2)
INSERT [dbo].[envio_masivo] ([masivo_autogenerado], [envio_id]) VALUES (N'MEX00000003', 3)
SET IDENTITY_INSERT [dbo].[estado_autorizado] ON 

INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (1, N'PENDIENTE')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (2, N'APROBADA')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (3, N'DENEGADA')
SET IDENTITY_INSERT [dbo].[estado_autorizado] OFF
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
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (9, N'RECEPCIONADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (10, N'CERRADO', 1)
SET IDENTITY_INSERT [dbo].[estado_documento] OFF
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (2, 1)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (3, 2)
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
SET IDENTITY_INSERT [dbo].[seguimiento_documento] ON 

INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (1, CAST(N'2019-05-27T09:01:16.7950000' AS DateTime2), NULL, NULL, 5, 1, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (2, CAST(N'2019-05-27T09:01:16.7990000' AS DateTime2), NULL, NULL, 5, 2, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (3, CAST(N'2019-05-27T09:01:16.8010000' AS DateTime2), NULL, NULL, 5, 3, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (4, CAST(N'2019-05-27T09:01:16.8020000' AS DateTime2), NULL, NULL, 5, 4, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (5, CAST(N'2019-05-27T09:01:32.8200000' AS DateTime2), NULL, NULL, 5, 5, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (6, CAST(N'2019-05-27T09:01:32.8230000' AS DateTime2), NULL, NULL, 5, 6, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (7, CAST(N'2019-05-27T09:01:32.8240000' AS DateTime2), NULL, NULL, 5, 7, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (8, CAST(N'2019-05-27T09:01:32.8250000' AS DateTime2), NULL, NULL, 5, 8, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (9, CAST(N'2019-05-27T09:02:17.1490000' AS DateTime2), NULL, NULL, 5, 9, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (10, CAST(N'2019-05-27T09:02:17.1500000' AS DateTime2), NULL, NULL, 5, 10, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (11, CAST(N'2019-05-27T09:02:17.1510000' AS DateTime2), NULL, NULL, 5, 11, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (12, CAST(N'2019-05-27T09:02:17.1520000' AS DateTime2), NULL, NULL, 5, 12, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (13, CAST(N'2019-05-27T09:02:38.4710000' AS DateTime2), NULL, NULL, 3, 4, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (14, CAST(N'2019-05-27T09:02:38.4740000' AS DateTime2), NULL, NULL, 3, 3, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (15, CAST(N'2019-05-27T09:02:38.4740000' AS DateTime2), NULL, NULL, 3, 2, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (16, CAST(N'2019-05-27T09:02:38.4750000' AS DateTime2), NULL, NULL, 3, 1, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (17, CAST(N'2019-05-27T09:02:44.0680000' AS DateTime2), NULL, NULL, 3, 7, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (18, CAST(N'2019-05-27T09:02:44.0690000' AS DateTime2), NULL, NULL, 3, 6, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (19, CAST(N'2019-05-27T09:02:44.0690000' AS DateTime2), NULL, NULL, 3, 8, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (20, CAST(N'2019-05-27T09:02:44.0700000' AS DateTime2), NULL, NULL, 3, 5, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (21, CAST(N'2019-05-27T09:02:50.3230000' AS DateTime2), NULL, NULL, 3, 9, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (22, CAST(N'2019-05-27T09:02:50.3240000' AS DateTime2), NULL, NULL, 3, 12, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (23, CAST(N'2019-05-27T09:02:50.3250000' AS DateTime2), NULL, NULL, 3, 11, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (24, CAST(N'2019-05-27T09:02:50.3250000' AS DateTime2), NULL, NULL, 3, 10, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (25, CAST(N'2019-05-27T09:04:10.9220000' AS DateTime2), NULL, NULL, 3, 2, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (26, CAST(N'2019-05-27T09:04:10.9240000' AS DateTime2), NULL, NULL, 3, 3, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (27, CAST(N'2019-05-27T09:04:10.9250000' AS DateTime2), NULL, NULL, 3, 4, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (28, CAST(N'2019-05-27T09:04:10.9250000' AS DateTime2), NULL, NULL, 3, 1, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (29, CAST(N'2019-05-27T09:04:13.3510000' AS DateTime2), NULL, NULL, 3, 10, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (30, CAST(N'2019-05-27T09:04:13.3510000' AS DateTime2), NULL, NULL, 3, 9, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (31, CAST(N'2019-05-27T09:04:14.9080000' AS DateTime2), NULL, NULL, 3, 5, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (32, CAST(N'2019-05-27T09:04:14.9090000' AS DateTime2), NULL, NULL, 3, 7, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (33, CAST(N'2019-05-27T09:04:14.9090000' AS DateTime2), NULL, NULL, 3, 6, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (34, CAST(N'2019-05-27T09:04:14.9090000' AS DateTime2), NULL, NULL, 3, 8, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (35, CAST(N'2019-05-27T09:04:16.3730000' AS DateTime2), NULL, NULL, 3, 12, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (36, CAST(N'2019-05-27T09:04:16.3730000' AS DateTime2), NULL, NULL, 3, 11, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (37, CAST(N'2019-05-28T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 1, 4, 5)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (38, CAST(N'2019-05-29T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 2, 4, 5)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (39, CAST(N'2019-05-30T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 3, 4, 5)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (40, CAST(N'2019-05-31T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 4, 4, 5)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (41, CAST(N'2019-05-28T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 9, 5, 11)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (42, CAST(N'2019-05-29T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 10, 5, 10)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (43, CAST(N'2019-05-28T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 5, 5, 11)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (44, CAST(N'2019-05-29T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 6, 5, 10)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (45, CAST(N'2019-05-30T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 7, 5, 11)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (46, CAST(N'2019-05-31T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 8, 5, 10)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (47, CAST(N'2019-05-28T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 11, 4, 1)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (48, CAST(N'2019-05-29T00:00:00.0000000' AS DateTime2), N'link', NULL, 3, 12, 4, 2)
SET IDENTITY_INSERT [dbo].[seguimiento_documento] OFF
SET IDENTITY_INSERT [dbo].[estado_guia] ON 

INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (1, N'CREADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (2, N'ENVIADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (3, N'DESCARGADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (4, N'COMPLETA')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (5, N'CERRADO')
SET IDENTITY_INSERT [dbo].[estado_guia] OFF
SET IDENTITY_INSERT [dbo].[proveedor] ON 

INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (1, 1, N'DOCFLOW')
INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (2, 1, N'URBANO')
SET IDENTITY_INSERT [dbo].[proveedor] OFF
SET IDENTITY_INSERT [dbo].[tipo_guia] ON 

INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (1, N' GUIA REGULAR')
INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (2, N' GUIA BLOQUE')
SET IDENTITY_INSERT [dbo].[tipo_guia] OFF
SET IDENTITY_INSERT [dbo].[guia] ON 

INSERT [dbo].[guia] ([guia_id], [numero_guia], [producto_id], [region_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (1, N'WDWDWDW', 1, 1, 5, 1, 1, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [numero_guia], [producto_id], [region_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (2, N'EDEDEFE', 1, 1, 5, 1, 2, 2, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [numero_guia], [producto_id], [region_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (3, N'AWDFAF', 1, 1, 5, 1, 1, 2, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [numero_guia], [producto_id], [region_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (4, N'SDSDSDSD', 1, 1, 5, 1, 2, 1, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[guia] OFF
SET IDENTITY_INSERT [dbo].[seguimiento_guia] ON 

INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (1, CAST(N'2019-05-27T09:03:02.2270000' AS DateTime2), 3, 1, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (2, CAST(N'2019-05-27T09:03:23.9640000' AS DateTime2), 3, 1, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (3, CAST(N'2019-05-27T09:03:42.6540000' AS DateTime2), 3, 1, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (4, CAST(N'2019-05-27T09:04:02.3820000' AS DateTime2), 3, 1, 4)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (5, CAST(N'2019-05-27T09:04:10.9210000' AS DateTime2), 3, 2, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (6, CAST(N'2019-05-27T09:04:13.3490000' AS DateTime2), 3, 2, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (7, CAST(N'2019-05-27T09:04:14.9080000' AS DateTime2), 3, 2, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (8, CAST(N'2019-05-27T09:04:16.3720000' AS DateTime2), 3, 2, 4)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (9, CAST(N'2019-05-27T09:04:59.4400000' AS DateTime2), 3, 3, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (10, CAST(N'2019-05-27T09:05:40.7330000' AS DateTime2), 3, 4, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (11, CAST(N'2019-05-27T09:05:52.4110000' AS DateTime2), 3, 3, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (12, CAST(N'2019-05-27T09:06:40.7960000' AS DateTime2), 3, 4, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (13, CAST(N'2019-05-27T09:06:42.3570000' AS DateTime2), 3, 3, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (14, CAST(N'2019-05-27T09:07:33.4240000' AS DateTime2), 3, 4, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (15, CAST(N'2019-05-27T09:07:46.1700000' AS DateTime2), 3, 3, 4)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (16, CAST(N'2019-05-27T09:08:25.9390000' AS DateTime2), 3, 4, 4)
SET IDENTITY_INSERT [dbo].[seguimiento_guia] OFF
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (1, 1, CAST(N'2019-05-27T09:03:02.1730000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (2, 1, CAST(N'2019-05-27T09:03:02.2260000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (3, 1, CAST(N'2019-05-27T09:03:02.2260000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (4, 1, CAST(N'2019-05-27T09:03:02.2270000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (5, 3, CAST(N'2019-05-27T09:03:42.6540000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (6, 3, CAST(N'2019-05-27T09:03:42.6540000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (7, 3, CAST(N'2019-05-27T09:03:42.6540000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (8, 3, CAST(N'2019-05-27T09:03:42.6540000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (9, 2, CAST(N'2019-05-27T09:03:23.9640000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (10, 2, CAST(N'2019-05-27T09:03:23.9640000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (11, 4, CAST(N'2019-05-27T09:04:02.3820000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (12, 4, CAST(N'2019-05-27T09:04:02.3820000' AS DateTime2), 1)
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
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (1, 1, NULL, 96)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (2, 1, NULL, 72)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (3, 1, NULL, 48)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (4, 1, NULL, 24)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (5, 1, NULL, 4)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (6, 1, NULL, 2)
INSERT [dbo].[region_plazo_distribucion] ([plazo_distribucion_id], [region_id], [region], [tiempo_envio]) VALUES (7, 1, NULL, 2)
SET IDENTITY_INSERT [dbo].[tipo_devolucion] ON 

INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (1, N'CARGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (2, N'REZAGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (3, N'DENUNCIA')
SET IDENTITY_INSERT [dbo].[tipo_devolucion] OFF
INSERT [dbo].[buzon_tipo_seguridad] ([buzon_id], [tipo_seguridad_id]) VALUES (1, 1)
INSERT [dbo].[resultado_tipo_devolucion] ([tipo_devolucion_id], [estado_documento_id]) VALUES (1, 4)
INSERT [dbo].[resultado_tipo_devolucion] ([tipo_devolucion_id], [estado_documento_id]) VALUES (1, 5)
INSERT [dbo].[resultado_tipo_devolucion] ([tipo_devolucion_id], [estado_documento_id]) VALUES (2, 5)
INSERT [dbo].[resultado_tipo_devolucion] ([tipo_devolucion_id], [estado_documento_id]) VALUES (2, 6)
INSERT [dbo].[resultado_tipo_devolucion] ([tipo_devolucion_id], [estado_documento_id]) VALUES (3, 6)