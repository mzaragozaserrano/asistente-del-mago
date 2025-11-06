#!/bin/bash

# Script para actualizar el proyecto y forzar sincronización en Android Studio

echo "🔄 Actualizando proyecto..."

# 1. Fetch de cambios remotos
echo "📥 Obteniendo cambios del repositorio remoto..."
git fetch origin

# 2. Pull con rebase
echo "⬇️  Haciendo pull con rebase..."
git pull --rebase origin feature_1

# 3. Verificar estado
echo "✅ Estado del repositorio:"
git status

echo ""
echo "📋 Últimos commits:"
git log --oneline -3

echo ""
echo "✨ Proyecto actualizado!"
echo ""
echo "💡 Próximos pasos en Android Studio:"
echo "   1. File > Invalidate Caches / Restart"
echo "   2. File > Sync Project with Gradle Files"
echo "   3. VCS > Git > Refresh File Status"

