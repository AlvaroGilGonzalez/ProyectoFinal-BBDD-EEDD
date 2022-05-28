# ProyectoFinal-BBDD-EEDD
Proyecto final de las asignaturas Base de Datos y Entornos de Desarrollo. Debemos modificar un programa que se nos ha proporcionado para que cumpla una serie de requisitos. Además debemos realizar la correspondiente documentación, pruebas y diagramas mediante técnicas vistas en clase.

Actualmente en la V1.3 todos los requisitos y funcionalidades establecidos en la práctica funcionan de manera correcta con la base de datos tienda y su tabla cliente.

Los cambios y funcionalidades que se han implementado en cada una de las ramas son los siguientes:

V1.0: En esta version hemos subido la version original del programa que se nos ha proporcionado

V1.1: Se han realizado las siguientes modificaciones:
      -Arreglo de fallos en los caracteres de la documentacion del programa
      -Hemos creado paquetes personalizados para las clases
      -Hemos cambiado los Statement por PreparedStatement en las funciones getCliente() y getTablaClientes(int resultSetType, int resultSetConcurrency)
      -Se ha eliminado la funcion getTablaClientes() ya que no se estaba haciendo uso de ella en ningun momento. Todo el rato se hacia la llamada a la funcion
      getTablaClientes(int resultSetType, int resultSetConcurrency).
      
V1.2: Se han añadido las siguientes funcionalidades:
      -Opcion de crear cliente mediante un procedimiento(asi hacemos uso de la clase callableStatement)
      -Opcion para volcar todo el contenido de la tabla en un fichero
      -Opcion de crear clientes desde un fichero
      -Opcion de modificar clientes desde un fichero
      -Opcion para eliminar clientes desde un fichero
      -Nota:Se ha creado una carpeta llamada Ficheros para centralizar en ella todo el trabajo con los mismos. La estructura utilizada en los ficheros que se deben leer        es la sugerida en el enunciado de la practica. 
      
V1.3: Se han añadido las siguientes funcionalidades:
      -Opcion de filtrar filas de clientes introduciendo su direccion
      -Opcion para crear una nueva tabla en la base de datos
      Se han mejorado los siguientes aspectos del codigo:
      -Se han comentado todos los metodos de las clases con JavaDoc
      -Se ha optimizado la pedida de datos por teclado en algunos metodos
      -Se ha modificado los mensajes que lanzan las excepciones al ser capturadas para hacerlos más acordes a aquello que el usuario deberia ver.
      Se ha incluido la siguiente documentación dentro de la carpeta del proyecto:
      -Documentación generada con Javadoc
      -Diagrama de clase
      -Diagramas de paquetes
      -Diagramas de secuencia
      -Diagrama de casos de uso
      -Pruebas unitarias realizadas con Junit

      
      
      
