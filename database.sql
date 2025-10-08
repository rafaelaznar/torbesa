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
-- Indexes for dumped tables
--

--
-- Indexes for table `capitals_score`
--
ALTER TABLE `capitals_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emoji_quiz`
--

CREATE TABLE `emoji_quiz` (
  `id` bigint NOT NULL,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `correct_answer` varchar(255) NOT NULL,
  `option1` varchar(255) NOT NULL,
  `option2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `emoji_quiz`
--

INSERT INTO `emoji_quiz` (`id`, `question`, `correct_answer`, `option1`, `option2`) VALUES
(1, '🦁👑🌅🎵', 'El Rey León', 'Madagascar', 'La selva'),
(2, '🚢🧊💑🌊', 'Titanic', 'La tormenta perfecta', 'Pearl Harbor'),
(3, '🕶️💊🖥️🤯', 'Matrix', 'Tron', 'Blade Runner'),
(4, '🦖🧪🌿🏝️', 'Jurassic Park', 'King Kong', 'El mundo perdido'),
(5, '🧙‍♂️🪄🦉🏰', 'Harry Potter y la piedra filosofal', 'El Hobbit', 'Las crónicas de Narnia'),
(6, '🎹🌃💃🎺', 'La La Land', 'Whiplash', 'Cantando bajo la lluvia'),
(7, '🛡️🧪⚡🌍', 'Los Vengadores', 'Liga de la Justicia', 'X-Men'),
(8, '🛸🦝🌌🎵', 'Guardianes de la Galaxia', 'Star Trek', 'Hombres de negro'),
(9, '☠️🏴‍☠️🧭⚓', 'Piratas del Caribe: La maldición de la Perla Negra', 'Hook', 'La isla'),
(10, '💍🧝‍♂️🗻🔥', 'El Señor de los Anillos: La Comunidad del Anillo', 'Willow', 'El Hobbit: Un viaje inesperado'),
(11, '🍫🏃‍♂️🚌🦐', 'Forrest Gump', 'El indomable Will Hunting', 'Náufrago'),
(12, '🔍🐠🌊🐢', 'Buscando a Nemo', 'Shark Tale', 'Moana'),
(13, '🤠🧸🚀👦', 'Toy Story', 'Los Increíbles', 'Monstruos, S.A.'),
(14, '🌌⚔️🛰️👨‍🚀', 'Star Wars: Una nueva esperanza', 'Dune', 'Star Trek'),
(15, '🎩🐍🗿🗺️', 'Indiana Jones y los cazadores del arca perdida', 'La momia', 'Jumanji'),
(16, '🤵🍝👨‍👦🔫', 'El Padrino', 'Buenos muchachos', 'Casino'),
(17, '🥊🇺🇸🏛️🎶', 'Rocky', 'Creed', 'Warrior'),
(18, '🎸💀🌺🧓', 'Coco', 'Vivo', 'Encanto'),
(19, '❄️👭👑⛄', 'Frozen', 'Valiente', 'La reina de las nieves'),
(20, '🧠😄😢😡😱', 'Intensamente', 'Soul', 'Del revés 2'),
(21, '🕷️🧑‍🎓🌆🌀', 'Spider-Man: Sin camino a casa', 'Venom', 'El sorprendente Spider-Man'),
(22, '🦇🤵‍♂️🃏🏙️', 'El caballero oscuro', 'Joker', 'Batman Begins'),
(23, '🎈🤡📞🏠', 'Eso (It)', 'El Conjuro', 'Scream'),
(24, '✝️👧😈🌒', 'El Exorcista', 'La monja', 'Hereditary'),
(25, '🔵🐉🌳🌌', 'Avatar', 'Avatar: El sentido del agua', 'Guerrilla'),
(26, '⏰🚗⚡👟', 'Regreso al futuro', 'Cars', 'Click'),
(27, '🎭🕵️‍♂️💣🏃‍♂️', 'Misión: Imposible', 'Bourne: Identidad desconocida', 'Kingsman: Servicio secreto'),
(28, '🏜️🚚🔥🛣️', 'Mad Max: Furia en la carretera', 'Dune', 'Sicario'),
(29, '🏠🍜🪳🔍', 'Parásitos', 'La casa de papel', 'Oldboy'),
(30, '🐟🧜‍♀️🧪💙', 'La forma del agua', 'La sirenita', 'Aguas profundas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emoji_quiz_score`
--

CREATE TABLE `emoji_quiz_score` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `emoji_quiz_score`
--

INSERT INTO `emoji_quiz_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 10, 15, '2025-10-01 10:12:45'),
(2, 2, 5, 14, '2025-10-01 11:20:30'),
(3, 3, 18, 25, '2025-10-02 09:44:12'),
(4, 4, 22, 40, '2025-10-03 18:30:00'),
(5, 5, 14, 21, '2025-10-04 20:15:50'),
(29, 17, 26, 47, '2025-10-07 14:03:48');

-- --------------------------------------------------------