-- Script SQL simplificado para CodeQuest
-- Solo lenguajes, frameworks y librerías
-- Categorías: cliente, servidor, fullstack

-- 1. Crear la tabla codequest_technology (nombre actualizado)
DROP TABLE IF EXISTS codequest_technology;
CREATE TABLE codequest_technology (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL, -- 'lenguaje', 'framework', 'libreria'
    description TEXT,
    category VARCHAR(50), -- 'cliente', 'servidor', 'fullstack'
    difficulty VARCHAR(50) -- 'facil', 'medio', 'dificil'
);

-- 2. Crear la tabla codequest_score (nombre actualizado)
DROP TABLE IF EXISTS codequest_score;
CREATE TABLE codequest_score (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    tries INT NOT NULL DEFAULT 0,
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 3. Insertar tecnologías simplificadas enfocadas en programación

-- === LENGUAJES ===
INSERT INTO codequest_technology (name, type, description, category, difficulty) VALUES
-- Cliente (Frontend)
('JavaScript', 'lenguaje', 'Lenguaje de programación interpretado para crear interactividad en páginas web', 'cliente', 'medio'),
('TypeScript', 'lenguaje', 'Superset de JavaScript que añade tipado estático', 'cliente', 'medio'),
('HTML', 'lenguaje', 'Lenguaje de marcado para estructurar contenido web', 'cliente', 'facil'),
('CSS', 'lenguaje', 'Lenguaje de hojas de estilo para diseñar páginas web', 'cliente', 'facil'),

-- Servidor (Backend)
('Java', 'lenguaje', 'Lenguaje orientado a objetos para aplicaciones empresariales', 'servidor', 'dificil'),
('Python', 'lenguaje', 'Lenguaje interpretado versátil y fácil de aprender', 'servidor', 'medio'),
('PHP', 'lenguaje', 'Lenguaje popular para desarrollo web del lado del servidor', 'servidor', 'medio'),
('C#', 'lenguaje', 'Lenguaje de Microsoft para la plataforma .NET', 'servidor', 'dificil'),
('Go', 'lenguaje', 'Lenguaje de Google para sistemas concurrentes y eficientes', 'servidor', 'dificil'),
('Rust', 'lenguaje', 'Lenguaje de sistemas con enfoque en seguridad y rendimiento', 'servidor', 'dificil'),
('Ruby', 'lenguaje', 'Lenguaje dinámico conocido por su elegancia y productividad', 'servidor', 'medio'),

-- FullStack
('Node.js', 'lenguaje', 'JavaScript en el servidor, permite full-stack con un solo lenguaje', 'fullstack', 'medio'),

-- === FRAMEWORKS CLIENTE ===
('React', 'framework', 'Librería de JavaScript para construir interfaces de usuario', 'cliente', 'medio'),
('Vue.js', 'framework', 'Framework progresivo para interfaces de usuario interactivas', 'cliente', 'facil'),
('Angular', 'framework', 'Framework completo de Google para aplicaciones web robustas', 'cliente', 'dificil'),
('Svelte', 'framework', 'Framework compilado que genera código vanilla JavaScript optimizado', 'cliente', 'medio'),
('Bootstrap', 'framework', 'Framework CSS para diseño responsive y componentes pre-hechos', 'cliente', 'facil'),
('Tailwind CSS', 'framework', 'Framework CSS utility-first para diseño rápido y personalizable', 'cliente', 'medio'),
('Next.js', 'framework', 'Framework de React para aplicaciones con renderizado del lado del servidor', 'cliente', 'dificil'),
('Nuxt.js', 'framework', 'Framework de Vue.js para aplicaciones universales', 'cliente', 'dificil'),

-- === FRAMEWORKS SERVIDOR ===
('Spring Boot', 'framework', 'Framework de Java para crear aplicaciones empresariales rápidamente', 'servidor', 'dificil'),
('Django', 'framework', 'Framework web de Python con baterías incluidas', 'servidor', 'dificil'),
('Flask', 'framework', 'Micro-framework minimalista de Python para APIs y web apps', 'servidor', 'facil'),
('Express.js', 'framework', 'Framework web minimalista para Node.js', 'servidor', 'medio'),
('Laravel', 'framework', 'Framework elegante de PHP con sintaxis expresiva', 'servidor', 'medio'),
('Ruby on Rails', 'framework', 'Framework de Ruby que prioriza la convención sobre configuración', 'servidor', 'dificil'),
('ASP.NET Core', 'framework', 'Framework web moderno de Microsoft para aplicaciones multiplataforma', 'servidor', 'dificil'),
('FastAPI', 'framework', 'Framework moderno de Python para APIs rápidas con validación automática', 'servidor', 'medio'),
('Gin', 'framework', 'Framework HTTP ligero y rápido para Go', 'servidor', 'medio'),

-- === LIBRERÍAS CLIENTE ===
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

-- === LIBRERÍAS SERVIDOR ===
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

-- === FRAMEWORKS/LIBRERÍAS FULLSTACK ===
('Meteor', 'framework', 'Plataforma full-stack para desarrollar aplicaciones web y móviles', 'fullstack', 'dificil'),
('T3 Stack', 'framework', 'Stack full-stack type-safe con Next.js, tRPC, Prisma y NextAuth', 'fullstack', 'dificil'),
('SvelteKit', 'framework', 'Framework full-stack para aplicaciones Svelte', 'fullstack', 'medio'),
('Remix', 'framework', 'Framework full-stack que se enfoca en estándares web', 'fullstack', 'dificil'),
('Gatsby', 'framework', 'Framework para sitios estáticos y aplicaciones basado en React', 'fullstack', 'medio'),

-- === TECNOLOGÍAS DE PRUEBAS (Simplificadas) ===
('Jest', 'framework', 'Framework de testing para JavaScript con cero configuración', 'cliente', 'facil'),
('JUnit', 'framework', 'Framework estándar para testing unitario en Java', 'servidor', 'facil'),
('PyTest', 'framework', 'Framework de testing simple y escalable para Python', 'servidor', 'facil'),
('PHPUnit', 'framework', 'Framework de testing unitario para PHP', 'servidor', 'medio'),
('Cypress', 'framework', 'Framework para testing end-to-end de aplicaciones web', 'cliente', 'medio'),
('Selenium', 'framework', 'Framework para automatizar navegadores web', 'fullstack', 'dificil'),

-- === HERRAMIENTAS DE BUILD Y EMPAQUETADO ===
('Webpack', 'libreria', 'Bundler de módulos para aplicaciones JavaScript modernas', 'cliente', 'dificil'),
('Vite', 'libreria', 'Build tool rápido para desarrollo frontend moderno', 'cliente', 'medio'),
('Rollup', 'libreria', 'Bundler de módulos para librerías JavaScript', 'cliente', 'medio'),
('Parcel', 'libreria', 'Bundler web sin configuración para desarrollo rápido', 'cliente', 'facil'),
('esbuild', 'libreria', 'Bundler y minificador extremadamente rápido para JavaScript', 'cliente', 'medio'),

-- === PREPROCESADORES Y COMPILADORES ===
('Sass', 'libreria', 'Preprocesador CSS con variables, mixins y funciones', 'cliente', 'medio'),
('Less', 'libreria', 'Preprocesador CSS dinámico', 'cliente', 'medio'),
('Stylus', 'libreria', 'Preprocesador CSS expresivo y dinámico', 'cliente', 'medio'),
('PostCSS', 'libreria', 'Herramienta para transformar CSS con plugins de JavaScript', 'cliente', 'medio'),
('Babel', 'libreria', 'Compilador de JavaScript para usar características modernas', 'cliente', 'medio'),

-- === LIBRERÍAS DE ESTADO Y DATOS ===
('MobX', 'libreria', 'Librería para gestión de estado reactivo mediante observables', 'cliente', 'medio'),
('Zustand', 'libreria', 'Librería pequeña y rápida para gestión de estado en React', 'cliente', 'facil'),
('Jotai', 'libreria', 'Gestión de estado atómico para React', 'cliente', 'medio'),
('Recoil', 'libreria', 'Librería experimental de Facebook para gestión de estado en React', 'cliente', 'medio'),
('Apollo Client', 'libreria', 'Cliente GraphQL completo con caché inteligente', 'cliente', 'dificil'),
('React Query', 'libreria', 'Librería para fetching, caching y sincronización de datos', 'cliente', 'medio'),
('SWR', 'libreria', 'Librería para data fetching con cache, revalidación y más', 'cliente', 'medio');

-- === DATOS DE EJEMPLO - USUARIOS Y PUNTUACIONES ===

-- Insertar usuarios de ejemplo (solo si no existen)
INSERT IGNORE INTO users (username, password) VALUES
('alex_dev', 'password123'),
('maria_code', 'password123'),
('juan_prog', 'password123'),
('sofia_web', 'password123'),
('carlos_js', 'password123'),
('ana_react', 'password123'),
('luis_java', 'password123'),
('elena_py', 'password123'),
('diego_full', 'password123'),
('lucia_ui', 'password123');

-- Insertar puntuaciones de ejemplo para crear un ranking inicial
INSERT INTO codequest_score (user_id, score, tries, timestamp) VALUES
-- Usuario 1 (alex_dev) - Experto en JavaScript
((SELECT id FROM users WHERE username = 'alex_dev'), 95, 15, '2024-10-01 10:30:00'),
((SELECT id FROM users WHERE username = 'alex_dev'), 88, 12, '2024-10-02 14:20:00'),
((SELECT id FROM users WHERE username = 'alex_dev'), 92, 18, '2024-10-03 16:45:00'),

-- Usuario 2 (maria_code) - Experta en React
((SELECT id FROM users WHERE username = 'maria_code'), 98, 20, '2024-10-01 11:15:00'),
((SELECT id FROM users WHERE username = 'maria_code'), 85, 14, '2024-10-02 15:30:00'),
((SELECT id FROM users WHERE username = 'maria_code'), 90, 16, '2024-10-04 09:20:00'),

-- Usuario 3 (juan_prog) - Backend specialist
((SELECT id FROM users WHERE username = 'juan_prog'), 87, 13, '2024-10-01 12:00:00'),
((SELECT id FROM users WHERE username = 'juan_prog'), 93, 19, '2024-10-03 13:45:00'),
((SELECT id FROM users WHERE username = 'juan_prog'), 89, 15, '2024-10-05 10:30:00'),

-- Usuario 4 (sofia_web) - Frontend enthusiast
((SELECT id FROM users WHERE username = 'sofia_web'), 91, 17, '2024-10-02 09:30:00'),
((SELECT id FROM users WHERE username = 'sofia_web'), 86, 14, '2024-10-03 14:20:00'),
((SELECT id FROM users WHERE username = 'sofia_web'), 94, 21, '2024-10-04 16:15:00'),

-- Usuario 5 (carlos_js) - JavaScript ninja
((SELECT id FROM users WHERE username = 'carlos_js'), 89, 16, '2024-10-01 15:45:00'),
((SELECT id FROM users WHERE username = 'carlos_js'), 92, 18, '2024-10-02 11:30:00'),
((SELECT id FROM users WHERE username = 'carlos_js'), 87, 13, '2024-10-05 14:20:00'),

-- Usuario 6 (ana_react) - React specialist
((SELECT id FROM users WHERE username = 'ana_react'), 96, 22, '2024-10-02 16:30:00'),
((SELECT id FROM users WHERE username = 'ana_react'), 83, 11, '2024-10-04 10:45:00'),
((SELECT id FROM users WHERE username = 'ana_react'), 88, 15, '2024-10-05 12:20:00'),

-- Usuario 7 (luis_java) - Java master
((SELECT id FROM users WHERE username = 'luis_java'), 84, 12, '2024-10-01 13:20:00'),
((SELECT id FROM users WHERE username = 'luis_java'), 90, 17, '2024-10-03 15:30:00'),
((SELECT id FROM users WHERE username = 'luis_java'), 95, 20, '2024-10-05 11:45:00'),

-- Usuario 8 (elena_py) - Python expert
((SELECT id FROM users WHERE username = 'elena_py'), 91, 18, '2024-10-02 12:15:00'),
((SELECT id FROM users WHERE username = 'elena_py'), 87, 14, '2024-10-04 14:30:00'),
((SELECT id FROM users WHERE username = 'elena_py'), 93, 19, '2024-10-05 16:00:00'),

-- Usuario 9 (diego_full) - FullStack developer
((SELECT id FROM users WHERE username = 'diego_full'), 86, 13, '2024-10-01 17:20:00'),
((SELECT id FROM users WHERE username = 'diego_full'), 89, 16, '2024-10-03 12:45:00'),
((SELECT id FROM users WHERE username = 'diego_full'), 92, 18, '2024-10-05 13:30:00'),

-- Usuario 10 (lucia_ui) - UI/UX focused
((SELECT id FROM users WHERE username = 'lucia_ui'), 88, 15, '2024-10-02 10:30:00'),
((SELECT id FROM users WHERE username = 'lucia_ui'), 85, 12, '2024-10-04 15:45:00'),
((SELECT id FROM users WHERE username = 'lucia_ui'), 90, 17, '2024-10-05 17:15:00');