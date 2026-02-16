-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-02-2026 a las 05:46:49
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `telep_app_java`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registros_analistas`
--

CREATE TABLE `registros_analistas` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `cliente` varchar(100) NOT NULL,
  `campo_equipo` varchar(255) DEFAULT NULL,
  `campo_modelo` varchar(255) DEFAULT NULL,
  `campo_ram` varchar(100) DEFAULT NULL,
  `campo_almacenamiento` varchar(100) DEFAULT NULL,
  `campo_serial` varchar(150) DEFAULT NULL,
  `campo_observaciones` text DEFAULT NULL,
  `campo_estado_revision` varchar(100) DEFAULT NULL,
  `campo_otro1` varchar(255) DEFAULT NULL,
  `campo_otro2` varchar(255) DEFAULT NULL,
  `actualizado_en` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registros_analistas`
--

INSERT INTO `registros_analistas` (`id`, `id_usuario`, `fecha_registro`, `cliente`, `campo_equipo`, `campo_modelo`, `campo_ram`, `campo_almacenamiento`, `campo_serial`, `campo_observaciones`, `campo_estado_revision`, `campo_otro1`, `campo_otro2`, `actualizado_en`) VALUES
(1, 6, '2025-12-27 03:55:25', 'Bogota', 'DK', 'MQ165', '8 GB', '500 GB', 'P158456', 'N/A', NULL, NULL, NULL, NULL),
(2, 6, '2026-02-01 23:30:07', 'Nexus Prime Solutions', 'Desktop', 'ThinkClient', '8 GB', 'SSD 500 GB', 'P158459', 'Entregadp', NULL, NULL, NULL, NULL),
(3, 6, '2026-02-02 00:36:54', 'Ruta 0 Express', 'Laptop', 'Yoga', '16 GB', 'SSD 250 GB', 'P158500', 'Configurado y entregado', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`) VALUES
(1, 'admin'),
(2, 'analista');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `nombre_completo` varchar(150) DEFAULT NULL,
  `correo` varchar(150) DEFAULT NULL,
  `password_hash` varchar(128) NOT NULL,
  `rol_id` int(11) NOT NULL,
  `estado` enum('activo','inactivo') NOT NULL DEFAULT 'activo',
  `creado_en` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `nombre_completo`, `correo`, `password_hash`, `rol_id`, `estado`, `creado_en`) VALUES
(3, 'telep_java', 'Usuario Prueba Java', 'telep@telep.com', 'e036f5178b49664ac59cbd40b1c3c0d433df0265ba7f320f0ba94def11aa97c4', 1, 'activo', '2025-12-16 05:38:42'),
(6, 'analista1', 'Analista de Prueba', 'analista1@telep.com', '89308f75c856e37f719c96ff1c35501791200c877f63e2f6d8ff2ed96b13d297', 2, 'activo', '2025-12-26 04:48:55'),
(8, 'Prueba_1', 'Prueba_1', 'PRUEBA_1@PRUEBA1.COM', 'a381b2c5749a6665ce52e4b067ddfe3daf866feb097c56b2f3cacd461933e32d', 2, 'activo', '2026-01-30 04:35:53'),
(10, 'prueba_2', 'Prueba_22', 'PRUEBA_2@PRUEBA2.COM', 'a381b2c5749a6665ce52e4b067ddfe3daf866feb097c56b2f3cacd461933e32d', 2, 'activo', '2026-01-30 23:20:15'),
(21, 'telep_java_2', 'telep_java_2', 'telep_java_2@telepjava2.com', 'e036f5178b49664ac59cbd40b1c3c0d433df0265ba7f320f0ba94def11aa97c4', 1, 'activo', '2026-02-01 00:00:09');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `registros_analistas`
--
ALTER TABLE `registros_analistas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_registros_usuario` (`id_usuario`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD KEY `idx_usuarios_rol` (`rol_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `registros_analistas`
--
ALTER TABLE `registros_analistas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `registros_analistas`
--
ALTER TABLE `registros_analistas`
  ADD CONSTRAINT `registros_analistas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
