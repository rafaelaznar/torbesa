-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: database:3306
-- Generation Time: Oct 08, 2025 at 06:34 PM
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
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `capitals_score`
--

INSERT INTO `capitals_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23'),
(15, 1, 99, 2, '2025-10-08 17:00:35');

-- --------------------------------------------------------

--
-- Table structure for table `codequest_score`
--

CREATE TABLE `codequest_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  `tries` int NOT NULL DEFAULT '0',
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `codequest_score`
--

INSERT INTO `codequest_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 92, 36, '2025-10-08 17:00:39'),
(2, 1, 92, 36, '2025-10-08 17:00:39'),
(3, 1, 92, 36, '2025-10-08 17:00:39'),
(4, 4, 0, 2, '2025-10-08 16:31:37');

-- --------------------------------------------------------

--
-- Table structure for table `codequest_technology`
--

CREATE TABLE `codequest_technology` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `type` varchar(50) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf32 COLLATE utf32_unicode_ci,
  `category` varchar(50) CHARACTER SET utf32 COLLATE utf32_unicode_ci DEFAULT NULL,
  `difficulty` varchar(50) CHARACTER SET utf32 COLLATE utf32_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `codequest_technology`
--

INSERT INTO `codequest_technology` (`id`, `name`, `type`, `description`, `category`, `difficulty`) VALUES
(1, 'JavaScript', 'lenguaje', 'Lenguaje de programación interpretado para crear interactividad en páginas web', 'cliente', 'medio'),
(2, 'TypeScript', 'lenguaje', 'Superset de JavaScript que añade tipado estático', 'cliente', 'medio'),
(3, 'HTML', 'lenguaje', 'Lenguaje de marcado para estructurar contenido web', 'cliente', 'facil'),
(4, 'CSS', 'lenguaje', 'Lenguaje de hojas de estilo para diseñar páginas web', 'cliente', 'facil'),
(5, 'Java', 'lenguaje', 'Lenguaje orientado a objetos para aplicaciones empresariales', 'servidor', 'dificil'),
(6, 'Python', 'lenguaje', 'Lenguaje interpretado versátil y fácil de aprender', 'servidor', 'medio'),
(7, 'PHP', 'lenguaje', 'Lenguaje popular para desarrollo web del lado del servidor', 'servidor', 'medio'),
(8, 'C#', 'lenguaje', 'Lenguaje de Microsoft para la plataforma .NET', 'servidor', 'dificil'),
(9, 'Go', 'lenguaje', 'Lenguaje de Google para sistemas concurrentes y eficientes', 'servidor', 'dificil'),
(10, 'Rust', 'lenguaje', 'Lenguaje de sistemas con enfoque en seguridad y rendimiento', 'servidor', 'dificil'),
(11, 'Ruby', 'lenguaje', 'Lenguaje dinámico conocido por su elegancia y productividad', 'servidor', 'medio'),
(12, 'Node.js', 'lenguaje', 'JavaScript en el servidor, permite full-stack con un solo lenguaje', 'fullstack', 'medio'),
(13, 'React', 'framework', 'Librería de JavaScript para construir interfaces de usuario', 'cliente', 'medio'),
(14, 'Vue.js', 'framework', 'Framework progresivo para interfaces de usuario interactivas', 'cliente', 'facil'),
(15, 'Angular', 'framework', 'Framework completo de Google para aplicaciones web robustas', 'cliente', 'dificil'),
(16, 'Svelte', 'framework', 'Framework compilado que genera código vanilla JavaScript optimizado', 'cliente', 'medio'),
(17, 'Bootstrap', 'framework', 'Framework CSS para diseño responsive y componentes pre-hechos', 'cliente', 'facil'),
(18, 'Tailwind CSS', 'framework', 'Framework CSS utility-first para diseño rápido y personalizable', 'cliente', 'medio'),
(19, 'Next.js', 'framework', 'Framework de React para aplicaciones con renderizado del lado del servidor', 'cliente', 'dificil'),
(20, 'Nuxt.js', 'framework', 'Framework de Vue.js para aplicaciones universales', 'cliente', 'dificil'),
(21, 'Spring Boot', 'framework', 'Framework de Java para crear aplicaciones empresariales rápidamente', 'servidor', 'dificil'),
(22, 'Django', 'framework', 'Framework web de Python con baterías incluidas', 'servidor', 'dificil'),
(23, 'Flask', 'framework', 'Micro-framework minimalista de Python para APIs y web apps', 'servidor', 'facil'),
(24, 'Express.js', 'framework', 'Framework web minimalista para Node.js', 'servidor', 'medio'),
(25, 'Laravel', 'framework', 'Framework elegante de PHP con sintaxis expresiva', 'servidor', 'medio'),
(26, 'Ruby on Rails', 'framework', 'Framework de Ruby que prioriza la convención sobre configuración', 'servidor', 'dificil'),
(27, 'ASP.NET Core', 'framework', 'Framework web moderno de Microsoft para aplicaciones multiplataforma', 'servidor', 'dificil'),
(28, 'FastAPI', 'framework', 'Framework moderno de Python para APIs rápidas con validación automática', 'servidor', 'medio'),
(29, 'Gin', 'framework', 'Framework HTTP ligero y rápido para Go', 'servidor', 'medio'),
(30, 'jQuery', 'libreria', 'Librería que simplifica la manipulación del DOM y AJAX', 'cliente', 'facil'),
(31, 'Lodash', 'libreria', 'Librería de utilidades para JavaScript con funciones helper', 'cliente', 'facil'),
(32, 'Axios', 'libreria', 'Cliente HTTP basado en promesas para navegadores y Node.js', 'cliente', 'facil'),
(33, 'Redux', 'libreria', 'Librería para gestión predecible del estado en aplicaciones JavaScript', 'cliente', 'medio'),
(34, 'Vuex', 'libreria', 'Librería de gestión de estado para aplicaciones Vue.js', 'cliente', 'medio'),
(35, 'RxJS', 'libreria', 'Librería para programación reactiva usando observables', 'cliente', 'dificil'),
(36, 'D3.js', 'libreria', 'Librería para visualización de datos dinámicos e interactivos', 'cliente', 'dificil'),
(37, 'Chart.js', 'libreria', 'Librería simple para crear gráficos responsive con canvas HTML5', 'cliente', 'facil'),
(38, 'Three.js', 'libreria', 'Librería para crear gráficos 3D en el navegador con WebGL', 'cliente', 'dificil'),
(39, 'Moment.js', 'libreria', 'Librería para manipular, validar y formatear fechas en JavaScript', 'cliente', 'facil'),
(40, 'Spring Security', 'libreria', 'Framework de seguridad para aplicaciones Spring', 'servidor', 'dificil'),
(41, 'Hibernate', 'libreria', 'Framework ORM para mapear objetos Java a bases de datos relacionales', 'servidor', 'dificil'),
(42, 'Jackson', 'libreria', 'Librería para procesamiento de JSON en Java', 'servidor', 'medio'),
(43, 'Requests', 'libreria', 'Librería HTTP elegante y simple para Python', 'servidor', 'facil'),
(44, 'SQLAlchemy', 'libreria', 'ORM y toolkit SQL para Python', 'servidor', 'medio'),
(45, 'Pandas', 'libreria', 'Librería de Python para análisis y manipulación de datos', 'servidor', 'medio'),
(46, 'NumPy', 'libreria', 'Librería fundamental para computación científica en Python', 'servidor', 'medio'),
(47, 'Eloquent', 'libreria', 'ORM incluido en Laravel para interactuar con bases de datos', 'servidor', 'medio'),
(48, 'Gorm', 'libreria', 'ORM para Go con características avanzadas', 'servidor', 'medio'),
(49, 'Entity Framework', 'libreria', 'ORM de Microsoft para aplicaciones .NET', 'servidor', 'dificil'),
(50, 'Meteor', 'framework', 'Plataforma full-stack para desarrollar aplicaciones web y móviles', 'fullstack', 'dificil'),
(51, 'T3 Stack', 'framework', 'Stack full-stack type-safe con Next.js, tRPC, Prisma y NextAuth', 'fullstack', 'dificil'),
(52, 'SvelteKit', 'framework', 'Framework full-stack para aplicaciones Svelte', 'fullstack', 'medio'),
(53, 'Remix', 'framework', 'Framework full-stack que se enfoca en estándares web', 'fullstack', 'dificil'),
(54, 'Gatsby', 'framework', 'Framework para sitios estáticos y aplicaciones basado en React', 'fullstack', 'medio'),
(55, 'Jest', 'framework', 'Framework de testing para JavaScript con cero configuración', 'cliente', 'facil'),
(56, 'JUnit', 'framework', 'Framework estándar para testing unitario en Java', 'servidor', 'facil'),
(57, 'PyTest', 'framework', 'Framework de testing simple y escalable para Python', 'servidor', 'facil'),
(58, 'PHPUnit', 'framework', 'Framework de testing unitario para PHP', 'servidor', 'medio'),
(59, 'Cypress', 'framework', 'Framework para testing end-to-end de aplicaciones web', 'cliente', 'medio'),
(60, 'Selenium', 'framework', 'Framework para automatizar navegadores web', 'fullstack', 'dificil'),
(61, 'Webpack', 'libreria', 'Bundler de módulos para aplicaciones JavaScript modernas', 'cliente', 'dificil'),
(62, 'Vite', 'libreria', 'Build tool rápido para desarrollo frontend moderno', 'cliente', 'medio'),
(63, 'Rollup', 'libreria', 'Bundler de módulos para librerías JavaScript', 'cliente', 'medio'),
(64, 'Parcel', 'libreria', 'Bundler web sin configuración para desarrollo rápido', 'cliente', 'facil'),
(65, 'esbuild', 'libreria', 'Bundler y minificador extremadamente rápido para JavaScript', 'cliente', 'medio'),
(66, 'Sass', 'libreria', 'Preprocesador CSS con variables, mixins y funciones', 'cliente', 'medio'),
(67, 'Less', 'libreria', 'Preprocesador CSS dinámico', 'cliente', 'medio'),
(68, 'Stylus', 'libreria', 'Preprocesador CSS expresivo y dinámico', 'cliente', 'medio'),
(69, 'PostCSS', 'libreria', 'Herramienta para transformar CSS con plugins de JavaScript', 'cliente', 'medio'),
(70, 'Babel', 'libreria', 'Compilador de JavaScript para usar características modernas', 'cliente', 'medio'),
(71, 'MobX', 'libreria', 'Librería para gestión de estado reactivo mediante observables', 'cliente', 'medio'),
(72, 'Zustand', 'libreria', 'Librería pequeña y rápida para gestión de estado en React', 'cliente', 'facil'),
(73, 'Jotai', 'libreria', 'Gestión de estado atómico para React', 'cliente', 'medio'),
(74, 'Recoil', 'libreria', 'Librería experimental de Facebook para gestión de estado en React', 'cliente', 'medio'),
(75, 'Apollo Client', 'libreria', 'Cliente GraphQL completo con caché inteligente', 'cliente', 'dificil'),
(76, 'React Query', 'libreria', 'Librería para fetching, caching y sincronización de datos', 'cliente', 'medio'),
(77, 'SWR', 'libreria', 'Librería para data fetching con cache, revalidación y más', 'cliente', 'medio');

-- --------------------------------------------------------

--
-- Table structure for table `dog_score`
--

CREATE TABLE `dog_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emoji_quiz`
--

CREATE TABLE `emoji_quiz` (
  `id` bigint NOT NULL,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `correct_answer` varchar(255) NOT NULL,
  `option1` varchar(255) NOT NULL,
  `option2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `emoji_quiz`
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
-- Table structure for table `emoji_quiz_score`
--

CREATE TABLE `emoji_quiz_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `emoji_quiz_score`
--

INSERT INTO `emoji_quiz_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 10, 15, '2025-10-01 10:12:45'),
(2, 2, 5, 14, '2025-10-01 11:20:30'),
(3, 3, 18, 25, '2025-10-02 09:44:12'),
(5, 5, 14, 21, '2025-10-04 20:15:50'),
(29, 17, 26, 47, '2025-10-07 14:03:48'),
(30, 4, 23, 41, '2025-10-08 20:24:35');

-- --------------------------------------------------------

--
-- Table structure for table `f1_score`
--

CREATE TABLE `f1_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genshin_score`
--

CREATE TABLE `genshin_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `genshin_score`
--

INSERT INTO `genshin_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(5, 1, 8, 19, '2025-09-14 12:03:52'),
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23'),
(10, 11, 7, 18, '2025-10-07 08:20:57');

-- --------------------------------------------------------

--
-- Table structure for table `harrypotter_score`
--

CREATE TABLE `harrypotter_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `harrypotter_score`
--

INSERT INTO `harrypotter_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(6, 1, 0, 6, '2025-10-08 17:00:30'),
(7, 4, 0, 1, '2025-10-08 16:33:12');

-- --------------------------------------------------------

--
-- Table structure for table `kimetsu_score`
--

CREATE TABLE `kimetsu_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kimetsu_score`
--

INSERT INTO `kimetsu_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 6, 8, '2025-10-07 08:39:21'),
(2, 11, 33, 110, '2025-10-08 07:03:11');

-- --------------------------------------------------------

--
-- Table structure for table `languages_score`
--

CREATE TABLE `languages_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `languages_score`
--

INSERT INTO `languages_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(5, 1, 8, 19, '2025-09-14 12:03:52'),
(6, 2, 6, 8, '2025-09-14 14:24:57'),
(7, 3, 19, 32, '2025-09-14 12:12:38'),
(8, 4, 31, 69, '2025-09-14 22:46:18'),
(9, 5, 22, 30, '2025-09-14 22:36:23');

-- --------------------------------------------------------

--
-- Table structure for table `math_scores`
--

CREATE TABLE `math_scores` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `math_scores`
--

INSERT INTO `math_scores` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 1, 30, 18, '2025-10-08 17:00:35'),
(2, 2, 0, 0, '2025-10-08 17:00:35');

-- --------------------------------------------------------

--
-- Table structure for table `personajes_score`
--

CREATE TABLE `personajes_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personajes_score`
--

INSERT INTO `personajes_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 20, 4, 6, '2025-10-07 20:10:05');

-- --------------------------------------------------------

--
-- Table structure for table `pokemon_score`
--

CREATE TABLE `pokemon_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pokemon_score`
--

INSERT INTO `pokemon_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(12, 1, 200, 4, '2025-10-08 17:00:35');

-- --------------------------------------------------------

--
-- Table structure for table `song_score`
--

CREATE TABLE `song_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `song_score`
--

INSERT INTO `song_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(2, 5, 1989, 2000, '2025-10-07 17:37:51'),
(4, 3, 1313, 1500, '2025-10-07 17:38:10'),
(5, 4, 3, 4, '2025-10-08 16:33:56');

-- --------------------------------------------------------

--
-- Table structure for table `starwars_score`
--

CREATE TABLE `starwars_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int DEFAULT '0',
  `tries` int DEFAULT '0',
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `starwars_score`
--

INSERT INTO `starwars_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 11, 14, 19, '2025-10-08 07:47:55');

-- --------------------------------------------------------

--
-- Table structure for table `trivialReyna_score`
--

CREATE TABLE `trivialReyna_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `trivialReyna_score`
--

INSERT INTO `trivialReyna_score` (`id`, `user_id`, `score`, `tries`, `timestamp`) VALUES
(1, 3, 50, 65, '2025-10-07 19:23:45'),
(2, 3, 50, 65, '2025-10-07 19:23:45');

-- --------------------------------------------------------

--
-- Table structure for table `trivial_score`
--

CREATE TABLE `trivial_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trivia_score`
--

CREATE TABLE `trivia_score` (
  `user_id` bigint NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  `streak` int NOT NULL DEFAULT '0',
  `best_score` int NOT NULL DEFAULT '0',
  `best_streak` int NOT NULL DEFAULT '0',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `trivia_score`
--

INSERT INTO `trivia_score` (`user_id`, `score`, `streak`, `best_score`, `best_streak`, `updated_at`) VALUES
(1, 0, 0, 0, 0, '2025-10-08 17:00:30'),
(4, 0, 0, 0, 0, '2025-10-08 18:22:47');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `username` varchar(50) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'alice', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(2, 'bob', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(3, 'anna', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(4, 'ralph', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(5, 'rafa', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(8, 'alex_dev', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(9, 'maria_code', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(10, 'juan_prog', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(11, 'sofia_web', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(12, 'carlos_js', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(13, 'ana_react', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(14, 'luis_java', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(15, 'elena_py', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(16, 'diego_full', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
(17, 'lucia_ui', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e');

-- --------------------------------------------------------

--
-- Table structure for table `wtpokemon_score`
--

CREATE TABLE `wtpokemon_score` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `tries` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `wtpokemon_score`
--

INSERT INTO `wtpokemon_score` (`id`, `user_id`, `score`, `tries`) VALUES
(1, 17, 9, 27),
(5, 4, 0, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `capitals_score`
--
ALTER TABLE `capitals_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `codequest_score`
--
ALTER TABLE `codequest_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `codequest_technology`
--
ALTER TABLE `codequest_technology`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dog_score`
--
ALTER TABLE `dog_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emoji_quiz`
--
ALTER TABLE `emoji_quiz`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emoji_quiz_score`
--
ALTER TABLE `emoji_quiz_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `f1_score`
--
ALTER TABLE `f1_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `genshin_score`
--
ALTER TABLE `genshin_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `harrypotter_score`
--
ALTER TABLE `harrypotter_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kimetsu_score`
--
ALTER TABLE `kimetsu_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `languages_score`
--
ALTER TABLE `languages_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `math_scores`
--
ALTER TABLE `math_scores`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `personajes_score`
--
ALTER TABLE `personajes_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pokemon_score`
--
ALTER TABLE `pokemon_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `song_score`
--
ALTER TABLE `song_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `starwars_score`
--
ALTER TABLE `starwars_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trivialReyna_score`
--
ALTER TABLE `trivialReyna_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trivial_score`
--
ALTER TABLE `trivial_score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trivia_score`
--
ALTER TABLE `trivia_score`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wtpokemon_score`
--
ALTER TABLE `wtpokemon_score`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `capitals_score`
--
ALTER TABLE `capitals_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `codequest_score`
--
ALTER TABLE `codequest_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `codequest_technology`
--
ALTER TABLE `codequest_technology`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;

--
-- AUTO_INCREMENT for table `dog_score`
--
ALTER TABLE `dog_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `emoji_quiz`
--
ALTER TABLE `emoji_quiz`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `emoji_quiz_score`
--
ALTER TABLE `emoji_quiz_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `f1_score`
--
ALTER TABLE `f1_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genshin_score`
--
ALTER TABLE `genshin_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `harrypotter_score`
--
ALTER TABLE `harrypotter_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `kimetsu_score`
--
ALTER TABLE `kimetsu_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `languages_score`
--
ALTER TABLE `languages_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `math_scores`
--
ALTER TABLE `math_scores`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `personajes_score`
--
ALTER TABLE `personajes_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pokemon_score`
--
ALTER TABLE `pokemon_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `song_score`
--
ALTER TABLE `song_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `starwars_score`
--
ALTER TABLE `starwars_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `trivialReyna_score`
--
ALTER TABLE `trivialReyna_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `trivial_score`
--
ALTER TABLE `trivial_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `trivia_score`
--
ALTER TABLE `trivia_score`
  MODIFY `user_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;



CREATE TABLE `scoreSalinas` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `username` VARCHAR(50) NOT NULL,
  `score` INT NOT NULL DEFAULT 0,
  `tries` INT NOT NULL DEFAULT 0,
  `timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- AUTO_INCREMENT for table `wtpokemon_score`
--
ALTER TABLE `capitals_score`
  ADD CONSTRAINT `capitals_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;
