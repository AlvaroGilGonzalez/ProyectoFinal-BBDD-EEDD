# ProyectoFinal-BBDD-EEDD
Proyecto final de las asignaturas Base de Datos y Entornos de Desarrollo. Debemos modificar un programa que se nos ha proporcionado para que cumpla una serie de requisitos. Además debemos realizar la correspondiente documentación, pruebas y diagramas mediante técnicas vistas en clase.

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
      
V1.3: Actualmente estoy trabajando en esta rama      
      
      
      
