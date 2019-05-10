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
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (3, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (4, 5, 2, NULL, 5, 1, 2, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (5, 5, 2, NULL, 5, 1, 2, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (6, 5, 3, N'6.pdf', 5, 1, 4, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (7, 5, 3, N'7.pdf', 5, 1, 4, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (8, 6, 1, N'8.pdf', 5, 1, 5, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (9, 6, 1, N'9.pdf', 5, 1, 5, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (10, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (11, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (12, 6, 1, N'12.pdf', 5, 1, 3, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (13, 6, 1, N'13.pdf', 5, 1, 3, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (14, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (15, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (16, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (17, 5, 1, NULL, 5, 1, 1, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (18, 5, 2, NULL, 5, 1, 2, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (19, 5, 2, NULL, 5, 1, 2, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (20, 6, 1, N'20.pdf', 5, 1, 3, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (21, 6, 1, N'21.pdf', 5, 1, 3, 1, 1, 1)
INSERT [dbo].[envio] ([envio_id], [buzon_id], [producto_id], [ruta_autorizacion], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [tipo_envio_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (22, 6, 1, N'22.pdf', 5, 1, 3, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[envio] OFF
SET IDENTITY_INSERT [dbo].[documento] ON 

INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (1, NULL, N'', N'DIRECCION 51', 1285, N'EX00000001', N'1', N'EXACT SAC', 0, N'', N'', 1)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (2, NULL, NULL, N'DIRECCION 31', 1289, N'EX00000002', N'1', N'EXACT SAC', 0, NULL, NULL, 2)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (3, NULL, NULL, N'DIRECCION 43', 1288, N'EX00000003', N'1', N'EXACT SAC', 0, NULL, NULL, 3)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (4, NULL, NULL, N'DIRECCION 31', 1282, N'EX00000004', N'1', N'EXACT SAC', 0, NULL, NULL, 4)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (5, NULL, NULL, N'DIRECCION 41', 1290, N'EX00000005', NULL, N'EXACT SAC', 0, NULL, NULL, 5)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (6, NULL, NULL, N'DIRECCION 43', 1288, N'EX00000006', N'1', N'EXACT SAC', 0, NULL, NULL, 6)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (7, NULL, NULL, N'DIRECCION 41', 1278, N'EX00000007', N'1', N'EXACT SAC', 0, NULL, NULL, 7)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (8, NULL, N'', N'DIRECCION 31', 1288, N'EX00000008', N'1', N'EXACT SAC', 0, N'', N'', 8)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (9, NULL, NULL, N'DIRECCION 41', 1291, N'EX00000009', N'1', N'EXACT SAC', 0, NULL, NULL, 9)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (10, NULL, N'', N'DIRECCION 41', 1286, N'EX00000010', N'1', N'EXACT SAC', 0, N'', N'', 10)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (11, NULL, NULL, N'DIRECCION 11', 1464, N'EX00000011', N'1', N'EXACT SAC', 0, NULL, NULL, 11)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (12, NULL, N'', N'DIRECCION 11', 1289, N'EX00000012', N'1', N'EXACT SAC', 0, N'', N'', 12)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (13, NULL, NULL, N'DIRECCION 11', 1288, N'EX00000013', N'1', N'EXACT SAC', 0, NULL, NULL, 13)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (14, NULL, N'', N'DIRECCION 41', 1291, N'EX00000014', N'1', N'EXACT SAC', 0, N'', N'', 14)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (15, NULL, NULL, N'DIRECCION 41', 1292, N'EX00000015', N'1', N'EXACT SAC', 0, NULL, NULL, 15)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (16, NULL, NULL, N'DIRECCION 41', 1288, N'EX00000016', N'1', N'EXACT SAC', 0, NULL, NULL, 16)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (17, NULL, NULL, N'DIRECCION 11', 1288, N'EX00000017', N'1', N'EXACT SAC', 0, NULL, NULL, 17)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (18, NULL, NULL, N'DIRECCION 31', 1287, N'EX00000018', N'1', N'EXACT SAC', 0, NULL, NULL, 18)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (19, NULL, NULL, N'DIRECCION 31', 1293, N'EX00000019', N'1', N'EXACT SAC', 0, NULL, NULL, 19)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (20, NULL, N'', N'DIRECCION 31', 1292, N'EX00000020', N'1', N'EXACT SAC', 0, N'', N'', 20)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (21, NULL, NULL, N'DIRECCION 41', 1458, N'EX00000021', N'1', N'RONALD SANTOS', 0, NULL, NULL, 21)
INSERT [dbo].[documento] ([documento_id], [codigo_devolucion], [contacto_destino], [direccion], [distrito_id], [documento_autogenerado], [nro_documento], [razon_social_destino], [recepcionado], [referencia], [telefono], [envio_id]) VALUES (22, NULL, NULL, N'DIRECCION 11', 1288, N'EX00000022', N'1', N'EXACT SAC', 0, NULL, NULL, 22)
SET IDENTITY_INSERT [dbo].[documento] OFF
SET IDENTITY_INSERT [dbo].[proveedor] ON 

INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (1, 1, N'DOCFLOW')
INSERT [dbo].[proveedor] ([proveedor_id], [activo], [nombre]) VALUES (2, 1, N'URBANO')
SET IDENTITY_INSERT [dbo].[proveedor] OFF
SET IDENTITY_INSERT [dbo].[tipo_guia] ON 

INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (1, N' GUIA REGULAR')
INSERT [dbo].[tipo_guia] ([tipo_guia_id], [nombre]) VALUES (2, N' GUIA BLOQUE')
SET IDENTITY_INSERT [dbo].[tipo_guia] OFF
SET IDENTITY_INSERT [dbo].[guia] ON 

INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (1, 1, N'11223', 1, 5, 1, 1, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (2, 1, N'159487', 2, 5, 1, 2, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (3, 1, N'1478563', 1, 5, 1, 1, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (4, 1, N'152635', 1, 5, 1, 3, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (5, NULL, N'15263248', 1, 5, 1, 1, 1, 1, 1, 1)
INSERT [dbo].[guia] ([guia_id], [ambito_id], [numero_guia], [producto_id], [sede_id], [tipo_clasificacion_id], [plazo_distribucion_id], [proveedor_id], [tipo_guia_id], [tipo_seguridad_id], [tipo_servicio_id]) VALUES (6, NULL, N'878542', 2, 5, 1, 2, 1, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[guia] OFF
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (1, 1, CAST(N'2019-05-09T18:45:06.0160000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (2, 1, CAST(N'2019-05-09T18:45:06.0140000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (3, 1, CAST(N'2019-05-09T18:45:06.0160000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (4, 2, CAST(N'2019-05-09T18:46:47.5810000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (5, 2, CAST(N'2019-05-09T18:46:47.5810000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (10, 3, CAST(N'2019-05-09T18:52:34.5340000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (11, 3, CAST(N'2019-05-09T18:52:34.5340000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (12, 4, CAST(N'2019-05-09T18:56:20.1660000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (13, 4, CAST(N'2019-05-09T18:56:20.1660000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (14, 5, CAST(N'2019-05-10T08:44:05.1520000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (15, 5, CAST(N'2019-05-10T08:44:05.1530000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (16, 5, CAST(N'2019-05-10T08:44:05.1530000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (17, 5, CAST(N'2019-05-10T08:44:05.1530000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (18, 6, CAST(N'2019-05-10T08:45:33.6170000' AS DateTime2), 1)
INSERT [dbo].[documento_guia] ([documento_id], [guia_id], [fecha_asociacion], [validado]) VALUES (19, 6, CAST(N'2019-05-10T08:45:33.6170000' AS DateTime2), 1)
SET IDENTITY_INSERT [dbo].[tipo_devolucion] ON 

INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (1, N'CARGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (2, N'REZAGO')
INSERT [dbo].[tipo_devolucion] ([tipo_devolucion_id], [nombre]) VALUES (3, N'DENUNCIA')
SET IDENTITY_INSERT [dbo].[tipo_devolucion] OFF
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
SET IDENTITY_INSERT [dbo].[seguimiento_documento] ON 

INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (1, CAST(N'2019-05-09T18:40:56.9940000' AS DateTime2), NULL, NULL, 5, 1, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (2, CAST(N'2019-05-09T18:41:20.9130000' AS DateTime2), NULL, NULL, 5, 2, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (3, CAST(N'2019-05-09T18:41:35.9460000' AS DateTime2), NULL, NULL, 5, 3, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (4, CAST(N'2019-05-09T18:41:59.7970000' AS DateTime2), NULL, NULL, 5, 4, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (5, CAST(N'2019-05-09T18:42:20.1530000' AS DateTime2), NULL, NULL, 5, 5, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (6, CAST(N'2019-05-09T18:43:21.2260000' AS DateTime2), NULL, NULL, 5, 6, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (7, CAST(N'2019-05-09T18:43:57.8980000' AS DateTime2), NULL, NULL, 5, 7, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (8, CAST(N'2019-05-09T18:44:40.9250000' AS DateTime2), NULL, NULL, 3, 1, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (9, CAST(N'2019-05-09T18:44:40.9270000' AS DateTime2), NULL, NULL, 3, 2, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (10, CAST(N'2019-05-09T18:44:40.9280000' AS DateTime2), NULL, NULL, 3, 3, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (11, CAST(N'2019-05-09T18:45:48.1320000' AS DateTime2), NULL, NULL, 2, 1, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (12, CAST(N'2019-05-09T18:45:48.1350000' AS DateTime2), NULL, NULL, 2, 2, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (13, CAST(N'2019-05-09T18:45:48.1350000' AS DateTime2), NULL, NULL, 2, 3, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (14, CAST(N'2019-05-09T18:46:33.8170000' AS DateTime2), NULL, NULL, 3, 4, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (15, CAST(N'2019-05-09T18:46:33.8200000' AS DateTime2), NULL, NULL, 3, 5, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (16, CAST(N'2019-05-09T18:46:33.8210000' AS DateTime2), NULL, NULL, 3, 6, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (17, CAST(N'2019-05-09T18:46:33.8220000' AS DateTime2), NULL, NULL, 3, 7, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (18, CAST(N'2019-05-09T18:47:43.2710000' AS DateTime2), NULL, NULL, 2, 4, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (19, CAST(N'2019-05-09T18:47:43.2730000' AS DateTime2), NULL, NULL, 2, 5, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (20, CAST(N'2019-05-09T18:49:42.9140000' AS DateTime2), NULL, NULL, 6, 8, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (21, CAST(N'2019-05-09T18:50:15.8440000' AS DateTime2), NULL, NULL, 6, 9, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (22, CAST(N'2019-05-09T18:51:22.9420000' AS DateTime2), NULL, NULL, 5, 10, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (23, CAST(N'2019-05-09T18:51:56.9510000' AS DateTime2), NULL, NULL, 5, 11, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (24, CAST(N'2019-05-09T18:52:17.0300000' AS DateTime2), NULL, NULL, 3, 10, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (25, CAST(N'2019-05-09T18:52:17.0300000' AS DateTime2), NULL, NULL, 3, 11, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (26, CAST(N'2019-05-09T18:53:21.5300000' AS DateTime2), NULL, NULL, 2, 10, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (27, CAST(N'2019-05-09T18:53:21.5320000' AS DateTime2), NULL, NULL, 2, 11, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (28, CAST(N'2019-05-09T18:54:39.0020000' AS DateTime2), NULL, NULL, 6, 12, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (29, CAST(N'2019-05-09T18:55:09.8470000' AS DateTime2), NULL, NULL, 6, 13, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (30, CAST(N'2019-05-09T18:55:36.3820000' AS DateTime2), NULL, NULL, 3, 12, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (31, CAST(N'2019-05-09T18:55:36.3830000' AS DateTime2), NULL, NULL, 3, 13, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (32, CAST(N'2019-05-09T18:57:24.8330000' AS DateTime2), NULL, NULL, 2, 12, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (33, CAST(N'2019-05-09T18:57:24.8350000' AS DateTime2), NULL, NULL, 2, 13, 3, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (34, CAST(N'2019-05-10T08:41:26.9980000' AS DateTime2), NULL, NULL, 5, 14, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (35, CAST(N'2019-05-10T08:41:42.6850000' AS DateTime2), NULL, NULL, 5, 15, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (36, CAST(N'2019-05-10T08:41:56.4400000' AS DateTime2), NULL, NULL, 5, 16, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (37, CAST(N'2019-05-10T08:42:13.3190000' AS DateTime2), NULL, NULL, 5, 17, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (38, CAST(N'2019-05-10T08:42:30.0800000' AS DateTime2), NULL, NULL, 5, 18, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (39, CAST(N'2019-05-10T08:42:48.8540000' AS DateTime2), NULL, NULL, 5, 19, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (40, CAST(N'2019-05-10T08:43:10.4880000' AS DateTime2), NULL, NULL, 3, 14, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (41, CAST(N'2019-05-10T08:43:10.4890000' AS DateTime2), NULL, NULL, 3, 15, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (42, CAST(N'2019-05-10T08:43:10.4890000' AS DateTime2), NULL, NULL, 3, 16, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (43, CAST(N'2019-05-10T08:43:10.4900000' AS DateTime2), NULL, NULL, 3, 17, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (44, CAST(N'2019-05-10T08:45:19.7470000' AS DateTime2), NULL, NULL, 3, 18, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (45, CAST(N'2019-05-10T08:45:19.7480000' AS DateTime2), NULL, NULL, 3, 19, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (46, CAST(N'2019-05-10T08:47:23.3110000' AS DateTime2), NULL, NULL, 6, 20, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (47, CAST(N'2019-05-10T08:47:56.5920000' AS DateTime2), NULL, NULL, 6, 21, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (48, CAST(N'2019-05-10T08:48:31.2710000' AS DateTime2), NULL, NULL, 6, 22, 1, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (49, CAST(N'2019-05-10T08:49:09.8740000' AS DateTime2), NULL, NULL, 3, 20, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (50, CAST(N'2019-05-10T08:49:09.8750000' AS DateTime2), NULL, NULL, 3, 21, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (51, CAST(N'2019-05-10T08:49:09.8760000' AS DateTime2), NULL, NULL, 3, 22, 2, NULL)
INSERT [dbo].[seguimiento_documento] ([seguimiento_documento_id], [fecha], [link_imagen], [observacion], [usuario_id], [documento_id], [estado_documento_id], [motivo_estado_id]) VALUES (52, CAST(N'2019-05-10T08:50:08.3170000' AS DateTime2), NULL, N'El documento ha sido autorizado', 9, 22, 2, NULL)
SET IDENTITY_INSERT [dbo].[seguimiento_documento] OFF
SET IDENTITY_INSERT [dbo].[estado_autorizado] ON 

INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (1, N'PENDIENTE')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (2, N'APROBADA')
INSERT [dbo].[estado_autorizado] ([estado_autorizado_id], [nombre]) VALUES (3, N'DENEGADA')
SET IDENTITY_INSERT [dbo].[estado_autorizado] OFF
SET IDENTITY_INSERT [dbo].[seguimiento_autorizado] ON 

INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (1, CAST(N'2019-05-09T18:43:21.2410000' AS DateTime2), NULL, 5, 6, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (2, CAST(N'2019-05-09T18:43:57.9000000' AS DateTime2), NULL, 5, 7, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (3, CAST(N'2019-05-09T18:49:42.9330000' AS DateTime2), NULL, 6, 8, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (4, CAST(N'2019-05-09T18:50:15.8440000' AS DateTime2), NULL, 6, 9, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (5, CAST(N'2019-05-09T18:54:39.0020000' AS DateTime2), NULL, 6, 12, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (6, CAST(N'2019-05-09T18:55:09.8510000' AS DateTime2), NULL, 6, 13, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (7, CAST(N'2019-05-10T08:47:23.3110000' AS DateTime2), NULL, 6, 20, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (8, CAST(N'2019-05-10T08:47:56.5940000' AS DateTime2), NULL, 6, 21, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (9, CAST(N'2019-05-10T08:48:31.2720000' AS DateTime2), NULL, 6, 22, 1)
INSERT [dbo].[seguimiento_autorizado] ([seguimiento_autorizado_id], [fecha], [nombre_usuario], [usuario_id], [envio_id], [estado_autorizado_id]) VALUES (10, CAST(N'2019-05-10T08:50:08.3150000' AS DateTime2), N'ERNST ROJAS', 9, 22, 2)
SET IDENTITY_INSERT [dbo].[seguimiento_autorizado] OFF
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (2, 1)
INSERT [dbo].[estado_documento_permitido] ([estado_documento_id], [estado_documento_permitido_id]) VALUES (3, 2)
SET IDENTITY_INSERT [dbo].[estado_guia] ON 

INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (1, N'CREADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (2, N'ENVIADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (3, N'DESCARGADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (4, N'COMPLETA')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (5, N'CERRADO')
SET IDENTITY_INSERT [dbo].[estado_guia] OFF
SET IDENTITY_INSERT [dbo].[seguimiento_guia] ON 

INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (1, CAST(N'2019-05-09T18:45:06.0160000' AS DateTime2), 3, 1, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (2, CAST(N'2019-05-09T18:45:48.1300000' AS DateTime2), 2, 2, 1)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (3, CAST(N'2019-05-09T18:46:47.5820000' AS DateTime2), 3, 1, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (4, CAST(N'2019-05-09T18:47:43.2690000' AS DateTime2), 2, 2, 2)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (5, CAST(N'2019-05-09T18:52:34.5340000' AS DateTime2), 3, 1, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (6, CAST(N'2019-05-09T18:53:21.5270000' AS DateTime2), 2, 2, 3)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (7, CAST(N'2019-05-09T18:56:20.1660000' AS DateTime2), 3, 1, 4)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (8, CAST(N'2019-05-09T18:57:24.8310000' AS DateTime2), 2, 2, 4)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (9, CAST(N'2019-05-10T08:44:05.1530000' AS DateTime2), 3, 1, 5)
INSERT [dbo].[seguimiento_guia] ([seguimiento_guia_id], [fecha], [usuario_id], [estado_guia_id], [guia_id]) VALUES (10, CAST(N'2019-05-10T08:45:33.6170000' AS DateTime2), 3, 1, 6)
SET IDENTITY_INSERT [dbo].[seguimiento_guia] OFF
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
INSERT [dbo].[subambito_plazo_distribucion] ([plazo_distribucion_id], [subambito_id], [subambito], [tiempo_envio]) VALUES (1, 1, NULL, 96)
INSERT [dbo].[subambito_plazo_distribucion] ([plazo_distribucion_id], [subambito_id], [subambito], [tiempo_envio]) VALUES (2, 1, NULL, 72)
INSERT [dbo].[subambito_plazo_distribucion] ([plazo_distribucion_id], [subambito_id], [subambito], [tiempo_envio]) VALUES (3, 1, NULL, 48)
INSERT [dbo].[subambito_plazo_distribucion] ([plazo_distribucion_id], [subambito_id], [subambito], [tiempo_envio]) VALUES (4, 1, NULL, 24)
INSERT [dbo].[subambito_plazo_distribucion] ([plazo_distribucion_id], [subambito_id], [subambito], [tiempo_envio]) VALUES (5, 1, NULL, 4)
INSERT [dbo].[buzon_tipo_seguridad] ([buzon_id], [tipo_seguridad_id]) VALUES (1, 1)
