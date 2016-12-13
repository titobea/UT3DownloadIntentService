# UT3DownloadIntent
Ejercicio de descarga de archivos mediante un IntentService

## Objetivos

Deberás crear una aplicación que tenga el siguiente interfaz:

- Un botón para descargar
- Un TextView donde escribir una URL

Si existe una URL y se aprieta el botón deberá descargar en segundo plano (mediante un IntentService) el archivo. 

En caso de que no exista el archivo se indicará el error mediante un Toast.

En caso de que exista se descargará en un archivo temporal y se intentará abrir con un implicit Intent, eligiendo la app adecuada para abrirlo.

El Intent debe comunicarse durante la descarga con la Activity para mostrar una barra de progreso de la descarga.
