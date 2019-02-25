USE [db_externa_core]
GO
SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] ON 

INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (1, N'REGULAR')
INSERT [dbo].[tipo_plazo_distribucion] ([tipo_plazo_distribucion_id], [nombre]) VALUES (2, N'ESPECIAL')
SET IDENTITY_INSERT [dbo].[tipo_plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[plazo_distribucion] ON 

INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (1, N'ESTÁNDAR', 96, 1)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (2, N'72 HORAS', 72, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (3, N'48 HORAS', 48, 2)
INSERT [dbo].[plazo_distribucion] ([plazo_distribucion_id], [nombre], [tiempo_envio], [tipo_plazo_distribucion_id]) VALUES (4, N'24 HORAS', 24, 2)
SET IDENTITY_INSERT [dbo].[plazo_distribucion] OFF
SET IDENTITY_INSERT [dbo].[tipo_seguridad] ON 

INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [nombre]) VALUES (1, N'SIN GPS')
INSERT [dbo].[tipo_seguridad] ([tipo_seguridad_id], [nombre]) VALUES (2, N'GPS BÁSICO')
SET IDENTITY_INSERT [dbo].[tipo_seguridad] OFF
SET IDENTITY_INSERT [dbo].[tipo_servicio] ON 

INSERT [dbo].[tipo_servicio] ([tipo_servicio_id], [nombre]) VALUES (1, N'DISTRIBUCIÓN')
SET IDENTITY_INSERT [dbo].[tipo_servicio] OFF


SET IDENTITY_INSERT [dbo].[proveedor] ON 

INSERT [dbo].[proveedor] ([proveedor_id], [nombre]) VALUES (1, N'DOCFLOW')
INSERT [dbo].[proveedor] ([proveedor_id], [nombre]) VALUES (2, N'URBANO')
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
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (6, N'DEVUELTO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (7, N'EXTRAVIADO', 2)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (8, N'DENEGADO', 1)
INSERT [dbo].[estado_documento] ([estado_documento_id], [nombre], [tipo_estado_documento_id]) VALUES (9, N'RETIRADO', 1)
SET IDENTITY_INSERT [dbo].[estado_documento] OFF

SET IDENTITY_INSERT [dbo].[estado_guia] ON 

INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (1, N'CREADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (2, N'ENVIADO')
INSERT [dbo].[estado_guia] ([estado_guia_id], [nombre]) VALUES (3, N'DESCARGADO')
SET IDENTITY_INSERT [dbo].[estado_guia] OFF

INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (3, 1)
INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (4, 1)
--INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (5, 1)
--INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (6, 1)
--INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (7, 1)
--INSERT [dbo].[area_plazo_distribucion] ([area_id], [plazo_distribucion_id]) VALUES (8, 1)
--INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (1, 2)
--INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (2, 1)
--INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (3, 1)
--INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (4, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (5, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (6, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (7, 1)
INSERT [dbo].[buzon_plazo_distribucion] ([buzon_id], [plazo_distribucion_id]) VALUES (8, 1)

INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (1, 4)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 1)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 2)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 3)
INSERT [dbo].[proveedor_plazo_distribucion] ([proveedor_id], [plazo_distribucion_id]) VALUES (2, 4)
INSERT [dbo].[buzon_tipo_seguridad] ([buzon_id], [tipo_seguridad_id]) VALUES (1, 1)
