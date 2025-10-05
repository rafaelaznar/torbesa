#!/bin/bash

echo "🚀 Instalando la base de datos de CodeQuest..."
echo "Por favor, introduce tus credenciales de MySQL:"

read -p "Usuario MySQL: " mysql_user
read -s -p "Contraseña MySQL: " mysql_password
echo
read -p "Nombre de la base de datos: " database_name

echo "📊 Ejecutando script de base de datos..."
mysql -u "$mysql_user" -p"$mysql_password" "$database_name" < database_codequest.sql

if [ $? -eq 0 ]; then
    echo "✅ Base de datos de CodeQuest instalada correctamente!"
    echo "📈 Se han creado:"
    echo "   - 80+ tecnologías de programación"
    echo "   - 10 usuarios de ejemplo"
    echo "   - 30+ puntuaciones para ranking"
    echo ""
    echo "👥 Usuarios de ejemplo creados:"
    echo "   - alex_dev (Experto JavaScript)"
    echo "   - maria_code (Experta React)"
    echo "   - juan_prog (Backend specialist)"
    echo "   - sofia_web (Frontend enthusiast)"
    echo "   - carlos_js (JavaScript ninja)"
    echo "   - ana_react (React specialist)"
    echo "   - luis_java (Java master)"
    echo "   - elena_py (Python expert)"
    echo "   - diego_full (FullStack developer)"
    echo "   - lucia_ui (UI/UX focused)"
    echo ""
    echo "🔑 Contraseña para todos: password123"
    echo ""
    echo "🎮 ¡Ahora puedes jugar CodeQuest en: http://localhost:9090/torbesa/codequest/landing.jsp"
else
    echo "❌ Error al instalar la base de datos"
    echo "Verifica tus credenciales y que la base de datos existe"
fi