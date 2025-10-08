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

-- ...existing code...
--
-- Constraints for dumped tables
--

--
-- Constraints for table `capitals_score`
--
ALTER TABLE `capitals_score`
  ADD CONSTRAINT `capitals_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;
=======



>>>>>>> profesor/main


-- --------------------------------------------------------

--
-- ...existing code...
-- Table structure for table `codequest_technology`
--

CREATE TABLE `codequest_technology` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL, -- 'lenguaje', 'framework', 'libreria'
    description TEXT,
    category VARCHAR(50), -- 'cliente', 'servidor', 'fullstack'
    difficulty VARCHAR(50) -- 'facil', 'medio', 'dificil'
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Table structure for table `codequest_score`
--

CREATE TABLE `codequest_score` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    tries INT NOT NULL DEFAULT 0,
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `codequest_technology`
--

INSERT INTO codequest_technology (name, type, description, category, difficulty) VALUES
('JavaScript', 'lenguaje', 'Lenguaje de programación interpretado para crear interactividad en páginas web', 'cliente', 'medio'),
('TypeScript', 'lenguaje', 'Superset de JavaScript que añade tipado estático', 'cliente', 'medio'),
('HTML', 'lenguaje', 'Lenguaje de marcado para estructurar contenido web', 'cliente', 'facil'),
('CSS', 'lenguaje', 'Lenguaje de hojas de estilo para diseñar páginas web', 'cliente', 'facil'),
('Java', 'lenguaje', 'Lenguaje orientado a objetos para aplicaciones empresariales', 'servidor', 'dificil'),
('Python', 'lenguaje', 'Lenguaje interpretado versátil y fácil de aprender', 'servidor', 'medio'),
('PHP', 'lenguaje', 'Lenguaje popular para desarrollo web del lado del servidor', 'servidor', 'medio'),
('C#', 'lenguaje', 'Lenguaje de Microsoft para la plataforma .NET', 'servidor', 'dificil'),
('Go', 'lenguaje', 'Lenguaje de Google para sistemas concurrentes y eficientes', 'servidor', 'dificil'),
('Rust', 'lenguaje', 'Lenguaje de sistemas con enfoque en seguridad y rendimiento', 'servidor', 'dificil'),
('Ruby', 'lenguaje', 'Lenguaje dinámico conocido por su elegancia y productividad', 'servidor', 'medio'),
('Node.js', 'lenguaje', 'JavaScript en el servidor, permite full-stack con un solo lenguaje', 'fullstack', 'medio'),
('React', 'framework', 'Librería de JavaScript para construir interfaces de usuario', 'cliente', 'medio'),
('Vue.js', 'framework', 'Framework progresivo para interfaces de usuario interactivas', 'cliente', 'facil'),
('Angular', 'framework', 'Framework completo de Google para aplicaciones web robustas', 'cliente', 'dificil'),
('Svelte', 'framework', 'Framework compilado que genera código vanilla JavaScript optimizado', 'cliente', 'medio'),
('Bootstrap', 'framework', 'Framework CSS para diseño responsive y componentes pre-hechos', 'cliente', 'facil'),
('Tailwind CSS', 'framework', 'Framework CSS utility-first para diseño rápido y personalizable', 'cliente', 'medio'),
('Next.js', 'framework', 'Framework de React para aplicaciones con renderizado del lado del servidor', 'cliente', 'dificil'),
('Nuxt.js', 'framework', 'Framework de Vue.js para aplicaciones universales', 'cliente', 'dificil'),
('Spring Boot', 'framework', 'Framework de Java para crear aplicaciones empresariales rápidamente', 'servidor', 'dificil'),
('Django', 'framework', 'Framework web de Python con baterías incluidas', 'servidor', 'dificil'),
('Flask', 'framework', 'Micro-framework minimalista de Python para APIs y web apps', 'servidor', 'facil'),
('Express.js', 'framework', 'Framework web minimalista para Node.js', 'servidor', 'medio'),
('Laravel', 'framework', 'Framework elegante de PHP con sintaxis expresiva', 'servidor', 'medio'),
('Ruby on Rails', 'framework', 'Framework de Ruby que prioriza la convención sobre configuración', 'servidor', 'dificil'),
('ASP.NET Core', 'framework', 'Framework web moderno de Microsoft para aplicaciones multiplataforma', 'servidor', 'dificil'),
('FastAPI', 'framework', 'Framework moderno de Python para APIs rápidas con validación automática', 'servidor', 'medio'),
('Gin', 'framework', 'Framework HTTP ligero y rápido para Go', 'servidor', 'medio'),
('jQuery', 'libreria', 'Librería que simplifica la manipulación del DOM y AJAX', 'cliente', 'facil'),
('Lodash', 'libreria', 'Librería de utilidades para JavaScript con funciones helper', 'cliente', 'facil'),
('Axios', 'libreria', 'Cliente HTTP basado en promesas para navegadores y Node.js', 'cliente', 'facil'),
('Redux', 'libreria', 'Librería para gestión predecible del estado en aplicaciones JavaScript', 'cliente', 'medio'),
('Vuex', 'libreria', 'Librería de gestión de estado para aplicaciones Vue.js', 'cliente', 'medio'),
('RxJS', 'libreria', 'Librería para programación reactiva usando observables', 'cliente', 'dificil'),
('D3.js', 'libreria', 'Librería para visualización de datos dinámicos e interactivos', 'cliente', 'dificil'),
('Chart.js', 'libreria', 'Librería simple para crear gráficos responsive con canvas HTML5', 'cliente', 'facil'),
('Three.js', 'libreria', 'Librería para crear gráficos 3D en el navegador con WebGL', 'cliente', 'dificil'),
('Moment.js', 'libreria', 'Librería para manipular, validar y formatear fechas en JavaScript', 'cliente', 'facil'),
('Spring Security', 'libreria', 'Framework de seguridad para aplicaciones Spring', 'servidor', 'dificil'),
('Hibernate', 'libreria', 'Framework ORM para mapear objetos Java a bases de datos relacionales', 'servidor', 'dificil'),
('Jackson', 'libreria', 'Librería para procesamiento de JSON en Java', 'servidor', 'medio'),
('Requests', 'libreria', 'Librería HTTP elegante y simple para Python', 'servidor', 'facil'),
('SQLAlchemy', 'libreria', 'ORM y toolkit SQL para Python', 'servidor', 'medio'),
('Pandas', 'libreria', 'Librería de Python para análisis y manipulación de datos', 'servidor', 'medio'),
('NumPy', 'libreria', 'Librería fundamental para computación científica en Python', 'servidor', 'medio'),
('Eloquent', 'libreria', 'ORM incluido en Laravel para interactuar con bases de datos', 'servidor', 'medio'),
('Gorm', 'libreria', 'ORM para Go con características avanzadas', 'servidor', 'medio'),
('Entity Framework', 'libreria', 'ORM de Microsoft para aplicaciones .NET', 'servidor', 'dificil'),
('Meteor', 'framework', 'Plataforma full-stack para desarrollar aplicaciones web y móviles', 'fullstack', 'dificil'),
('T3 Stack', 'framework', 'Stack full-stack type-safe con Next.js, tRPC, Prisma y NextAuth', 'fullstack', 'dificil'),
('SvelteKit', 'framework', 'Framework full-stack para aplicaciones Svelte', 'fullstack', 'medio'),
('Remix', 'framework', 'Framework full-stack que se enfoca en estándares web', 'fullstack', 'dificil'),
('Gatsby', 'framework', 'Framework para sitios estáticos y aplicaciones basado en React', 'fullstack', 'medio'),
('Jest', 'framework', 'Framework de testing para JavaScript con cero configuración', 'cliente', 'facil'),
('JUnit', 'framework', 'Framework estándar para testing unitario en Java', 'servidor', 'facil'),
('PyTest', 'framework', 'Framework de testing simple y escalable para Python', 'servidor', 'facil'),
('PHPUnit', 'framework', 'Framework de testing unitario para PHP', 'servidor', 'medio'),
('Cypress', 'framework', 'Framework para testing end-to-end de aplicaciones web', 'cliente', 'medio'),
('Selenium', 'framework', 'Framework para automatizar navegadores web', 'fullstack', 'dificil'),
('Webpack', 'libreria', 'Bundler de módulos para aplicaciones JavaScript modernas', 'cliente', 'dificil'),
('Vite', 'libreria', 'Build tool rápido para desarrollo frontend moderno', 'cliente', 'medio'),
('Rollup', 'libreria', 'Bundler de módulos para librerías JavaScript', 'cliente', 'medio'),
('Parcel', 'libreria', 'Bundler web sin configuración para desarrollo rápido', 'cliente', 'facil'),
('esbuild', 'libreria', 'Bundler y minificador extremadamente rápido para JavaScript', 'cliente', 'medio'),
('Sass', 'libreria', 'Preprocesador CSS con variables, mixins y funciones', 'cliente', 'medio'),
('Less', 'libreria', 'Preprocesador CSS dinámico', 'cliente', 'medio'),
('Stylus', 'libreria', 'Preprocesador CSS expresivo y dinámico', 'cliente', 'medio'),
('PostCSS', 'libreria', 'Herramienta para transformar CSS con plugins de JavaScript', 'cliente', 'medio'),
('Babel', 'libreria', 'Compilador de JavaScript para usar características modernas', 'cliente', 'medio'),
('MobX', 'libreria', 'Librería para gestión de estado reactivo mediante observables', 'cliente', 'medio'),
('Zustand', 'libreria', 'Librería pequeña y rápida para gestión de estado en React', 'cliente', 'facil'),
('Jotai', 'libreria', 'Gestión de estado atómico para React', 'cliente', 'medio'),
('Recoil', 'libreria', 'Librería experimental de Facebook para gestión de estado en React', 'cliente', 'medio'),
('Apollo Client', 'libreria', 'Cliente GraphQL completo con caché inteligente', 'cliente', 'dificil'),
('React Query', 'libreria', 'Librería para fetching, caching y sincronización de datos', 'cliente', 'medio'),
('SWR', 'libreria', 'Librería para data fetching con cache, revalidación y más', 'cliente', 'medio');

--
-- Dumping data for table `codequest_score`
--

INSERT INTO codequest_score (user_id, score, tries, timestamp) VALUES
((SELECT id FROM users WHERE username = 'alex_dev'), 95, 15, '2024-10-01 10:30:00'),
((SELECT id FROM users WHERE username = 'alex_dev'), 88, 12, '2024-10-02 14:20:00'),
((SELECT id FROM users WHERE username = 'alex_dev'), 92, 18, '2024-10-03 16:45:00'),
((SELECT id FROM users WHERE username = 'maria_code'), 98, 20, '2024-10-01 11:15:00'),
((SELECT id FROM users WHERE username = 'maria_code'), 85, 14, '2024-10-02 15:30:00'),
((SELECT id FROM users WHERE username = 'maria_code'), 90, 16, '2024-10-04 09:20:00'),
((SELECT id FROM users WHERE username = 'juan_prog'), 87, 13, '2024-10-01 12:00:00'),
((SELECT id FROM users WHERE username = 'juan_prog'), 93, 19, '2024-10-03 13:45:00'),
((SELECT id FROM users WHERE username = 'juan_prog'), 89, 15, '2024-10-05 10:30:00'),
((SELECT id FROM users WHERE username = 'sofia_web'), 91, 17, '2024-10-02 09:30:00'),
((SELECT id FROM users WHERE username = 'sofia_web'), 86, 14, '2024-10-03 14:20:00'),
((SELECT id FROM users WHERE username = 'sofia_web'), 94, 21, '2024-10-04 16:15:00'),
((SELECT id FROM users WHERE username = 'carlos_js'), 89, 16, '2024-10-01 15:45:00'),
((SELECT id FROM users WHERE username = 'carlos_js'), 92, 18, '2024-10-02 11:30:00'),
((SELECT id FROM users WHERE username = 'carlos_js'), 87, 13, '2024-10-05 14:20:00'),
((SELECT id FROM users WHERE username = 'ana_react'), 96, 22, '2024-10-02 16:30:00'),
((SELECT id FROM users WHERE username = 'ana_react'), 83, 11, '2024-10-04 10:45:00'),
((SELECT id FROM users WHERE username = 'ana_react'), 88, 15, '2024-10-05 12:20:00'),
((SELECT id FROM users WHERE username = 'luis_java'), 84, 12, '2024-10-01 13:20:00'),
((SELECT id FROM users WHERE username = 'luis_java'), 90, 17, '2024-10-03 15:30:00'),
((SELECT id FROM users WHERE username = 'luis_java'), 95, 20, '2024-10-05 11:45:00'),
((SELECT id FROM users WHERE username = 'elena_py'), 91, 18, '2024-10-02 12:15:00'),
((SELECT id FROM users WHERE username = 'elena_py'), 87, 14, '2024-10-04 14:30:00'),
((SELECT id FROM users WHERE username = 'elena_py'), 93, 19, '2024-10-05 16:00:00'),
((SELECT id FROM users WHERE username = 'diego_full'), 86, 13, '2024-10-01 17:20:00'),
((SELECT id FROM users WHERE username = 'diego_full'), 89, 16, '2024-10-03 12:45:00'),
((SELECT id FROM users WHERE username = 'diego_full'), 92, 18, '2024-10-05 13:30:00'),
((SELECT id FROM users WHERE username = 'lucia_ui'), 88, 15, '2024-10-02 10:30:00'),
((SELECT id FROM users WHERE username = 'lucia_ui'), 85, 12, '2024-10-04 15:45:00'),
((SELECT id FROM users WHERE username = 'lucia_ui'), 90, 17, '2024-10-05 17:15:00');

--
-- Indexes for table `codequest_technology`
--
ALTER TABLE `codequest_technology`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `codequest_score`
--
ALTER TABLE `codequest_score`
=======
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
>>>>>>> profesor/main
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
<<<<<<< HEAD
-- AUTO_INCREMENT for table `codequest_technology`
--
ALTER TABLE `codequest_technology`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `codequest_score`
--
ALTER TABLE `codequest_score`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- Constraints for table `codequest_score`
--
ALTER TABLE `codequest_score`
  ADD CONSTRAINT `codequest_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

-- Insertar usuarios de ejemplo (solo si no existen)
INSERT IGNORE INTO users (username, password) VALUES
('alex_dev', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('maria_code', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('juan_prog', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('sofia_web', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('carlos_js', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('ana_react', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('luis_java', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('elena_py', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('diego_full', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e'),
('lucia_ui', '7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e');


=======
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
>>>>>>> profesor/main
