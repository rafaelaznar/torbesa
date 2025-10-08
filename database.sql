-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: database:3306
-- Generation Time: Sep 14, 2025 at 10:47 PM
-- Server version: 8.1.0
-- PHP Version: 8.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `torbesa`
--

-- --------------------------------------------------------

--
-- Table structure for table `capitals_score`
--

CREATE TABLE `capitals_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `capitals_score`
--

INSERT INTO `capitals_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(5, 1, 8, 19, '2025-09-14 12:03:52'),
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23');

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `trivial_score`
--

CREATE TABLE `trivial_score` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `tries` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) COLLATE utf32_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'alice', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(2, 'bob', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(3, 'anna', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(4, 'ralph', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(5, 'rafa', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e');

--
-- Table structure for table `pokemon_score`

CREATE TABLE `pokemon_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;






--
-- Indexes for dumped tables
--

--
-- Indexes for table `capitals_score`
--
ALTER TABLE `capitals_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `trivial_score`
--
ALTER TABLE `trivial_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indices de la tabla `pokemon_score`
--

ALTER TABLE `pokemon_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pokemon_score_ibfk_1` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

-- AUTO_INCREMENT for table `capitals_score`
--
ALTER TABLE `capitals_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;


--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `pokemon_score`
--
ALTER TABLE `pokemon_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;






-- --------------------------------------------------------

--
-- Table structure for table `harrypotter_score`
--

CREATE TABLE `harrypotter_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Indexes for table `harrypotter_score`
--
ALTER TABLE `harrypotter_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);




--
-- AUTO_INCREMENT for table `harrypotter_score`
--
ALTER TABLE `harrypotter_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 07-10-2025 a las 08:23:23
-- Versión del servidor: 8.4.6
-- Versión de PHP: 8.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

COMMIT;

-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 08-10-2025 a las 07:09:49
-- Versión del servidor: 8.4.6
-- Versión de PHP: 8.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `torbesa`
--

-- --------------------------------------------------------

--
-- Base de datos: `torbesa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genshin_score`
--

CREATE TABLE `genshin_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Volcado de datos para la tabla `genshin_score`
--

INSERT INTO `genshin_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(5, 1, 8, 19, '2025-09-14 12:03:52'),
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23'),
(10, 11, 7, 18, '2025-10-07 08:20:57');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `genshin_score`

COMMIT;


-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-10-2025 a las 20:57:55
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `torbesa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personajes_score`
--
ALTER TABLE `genshin_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `genshin_score`
--
ALTER TABLE `genshin_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;




-- -------------------------------------------------------
-- -------------------------------------------------------
-- LUCIA CASTAÑERA - JUEGO TAYLOR SWIFT


-- --------------------------------------------------------
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-10-2025 a las 19:43:20
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `torbesa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `song_score`
--

CREATE TABLE `song_score` (
  `id` bigint(255) NOT NULL,
  `user_id` int(255) NOT NULL,
  `score` int(255) NOT NULL,
  `tries` int(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `song_score`
--

INSERT INTO `song_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(2, 5, 1989, 2000, '2025-10-07 17:37:51'),
(4, 3, 1313, 1500, '2025-10-07 17:38:10');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `song_score`
-- Estructura de tabla para la tabla `kimetsu_score`
--
ALTER TABLE `song_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `indice` (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `song_score`
--
ALTER TABLE `song_score`
  MODIFY `id` bigint(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

-- --------------------------------------------------------



-- Nota: la clave ajena se elimina, no se aplicará a nivel de SQL
-- ALTER TABLE `capitals_score`
--   ADD CONSTRAINT `capitals_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);


CREATE TABLE `math_scores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `score` INT NOT NULL,
  `tries` INT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `kimetsu_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `kimetsu_score`
--

INSERT INTO `kimetsu_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 6, 8, '2025-10-07 08:39:21'),
(2, 11, 33, 110, '2025-10-08 07:03:11');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `kimetsu_score`
--
ALTER TABLE `kimetsu_score`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `kimetsu_score`
--
ALTER TABLE `kimetsu_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

CREATE TABLE `personajes_score` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `tries` int(11) NOT NULL,
  `timestamp` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personajes_score`
--

INSERT INTO `personajes_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 20, 4, 6, '2025-10-07 20:10:05');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `personajes_score`
--
ALTER TABLE `personajes_score`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `personajes_score`
--
ALTER TABLE `personajes_score`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
-- Constraints for dumped tables
--

--
-- Constraints for table `capitals_score`
--



--
-- Table structure for table `languages_score`
--

CREATE TABLE `languages_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY(`id`),
  KEY `user_id`(`user_id`),
  CONSTRAINT `languages_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

INSERT INTO `languages_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(5, 1, 8, 19, '2025-09-14 12:03:52'),
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23');

ALTER TABLE `languages_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

COMMIT;


-- Ronald
-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 07-10-2025 a las 19:25:53
-- Versión del servidor: 8.4.6
-- Versión de PHP: 8.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `torbesa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trivialReyna_score`
--

CREATE TABLE `trivialReyna_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Volcado de datos para la tabla `trivialReyna_score`
--

INSERT INTO `trivialReyna_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 3, 50, 65, '2025-10-07 19:23:45'),
(2, 3, 50, 65, '2025-10-07 19:23:45');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `trivialReyna_score`
--
ALTER TABLE `trivialReyna_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `trivialReyna_score`
--
ALTER TABLE `trivialReyna_score`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;