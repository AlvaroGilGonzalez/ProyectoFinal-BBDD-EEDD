# ProyectoFinal-BBDD-EEDD
Proyecto final de las asignaturas Base de Datos y Entornos de Desarrollo. Debemos modificar un programa que se nos ha proporcionado para que cumpla una serie de requisitos. Además debemos realizar la correspondiente documentación, pruebas y diagramas mediante técnicas vistas en clase.

V1.0: En esta version hemos subido la version original del programa que se nos ha proporcionado

V1.1: Se han realizado las siguientes modificaciones:
      -Arreglo de fallos en los caracteres de la documentacion del programa
      -Hemos creado paquetes personalizados para las clases
      -Hemos cambiado los Statement por PreparedStatement en las funciones getCliente() y getTablaClientes(int resultSetType, int resultSetConcurrency)
      -Se ha eliminado la funcion getTablaClientes() ya que no se estaba haciendo uso de ella en ningun momento. Todo el rato se hacia la llamada a la funcion
      getTablaClientes(int resultSetType, int resultSetConcurrency).
