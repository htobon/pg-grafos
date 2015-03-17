#labels Phase-Deploy
# Descargar proyecto #

## Requisitos ##
  * Instalar Eclipse IDE: http://www.eclipse.org/downloads/
  * Instalar Subclipse (Plugin para Eclipse IDE): http://subclipse.tigris.org/

## Procedimiento ##
  1. Inicie sesión en Gmail. Para obtener la contraseña de Google Code, entre a: http://code.google.com/p/pg-grafos/source/checkout . El usuario es el mismo de Gmail y la contraseña la obtiene en el link googlecode.com después del letrero
When prompted, enter your generated googlecode.com . Este link abrirá  http://code.google.com/hosting/settings y la contraseña la podrá ubicar donde dice Your googlecode.com password:
  1. Abrir Eclipse. En la parte superior derecha de la ventana, buscar el botón SVN y seleccionarlo. Esta es la vista del proyecto en SVN. Haga click derecho con el mouse y seleccione New > Repository Location.
  1. En el campo de URL de Google Code, escribir la dirección del proyecto en Google Code: https://pg-grafos.googlecode.com/svn/ Llenar respectivamente los datos con el Usuario de gmail y la Contraseña que se obtuvo anteriormente. Al finalizar, dar click en Siguiente
  1. Selecciona la carpeta del proyecto que se desea obtener. Esto debido a que pueden existir muchas carpetas del proyecto dependiendo de la versión del mismo. Estas versiones se pueden dar ya sea por mejoras de interfaz grafica, correcciones de bugs, o porque alguien quiere crear una para sus pruebas independientes o por los distintos cursos que puede tomar el proyecto. Para este caso se da click en Examinar para seleccionar la carpeta del repositorio y se seleeciona dentro de Runner la versión 0.1
  1. Luego, selecciona la carpeta donde desea ubicar el proyecto en el equipo en el campo de Carpeta Local. Al finalizar dar click en Terminar
  1. Una vez ya está el proyecto en el equipo, en la carpeta que se le ha destino. Ahora se puede abrir el proyecto en Eclipse. Ir en el menu a File> Open File y buscar el proyecto en su respectiva carpeta. Click en Abrir proyecto
  1. El proyecto ya debe estar visible en el navegador de Eclipse con sus respectivos elementos

Un proyecto en Java consta de su carpeta principal y adentro de esta se encuentran los paquetes, que son como las subcarpetas del proyecto
Los paquetes contienen los elementos del proyecto, en la mayoría de los casos las clases en Java.