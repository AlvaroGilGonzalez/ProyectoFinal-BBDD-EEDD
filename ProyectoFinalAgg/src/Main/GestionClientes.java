package Main;

import java.util.Scanner;
import GestionBD.DBManager;

/**
 * Clase principal de este programa, en ella se muestra al usuario las opciones disponibles y ademas se le pide por teclado todos los datos necesarios para realizar las posteriores
 * consultas o actualizaciones en la base de datos (todo ello haciendo llamada a los metodos que encontramos en DBManager)
 * @author alvaro
 */
public class GestionClientes {
	
	/**
	 * Metodo Main de nuestro programa
	 * @param args
	 */
    public static void main(String[] args) {

        DBManager.loadDriver();
        DBManager.connect();

        boolean salir = false;
        do {
            salir = menuPrincipal();
        } while (!salir);

        DBManager.close();

    }

    /**
     * Muestra un menu con las distintas opciones que podemos elegir
     * @return (false para todos las opciones y true para la opcion salir)
     */
    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Listar clientes");
        System.out.println("2. Nuevo cliente (Usamos PrepareStatement)");
        System.out.println("3. Nuevo cliente (Usamos CallableStatement");
        System.out.println("4. Modificar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("6. Filtrar Clientes por su direccion");
        System.out.println("7. Crear una nueva tabla en la base de datos");
        System.out.println("8. Volcar contenido de la tabla en un fichero");
        System.out.println("9. Insertar cliente a partir de un fichero");
        System.out.println("10.Modificar cliente a partir de un fichero");
        System.out.println("11.Eliminar cliente a partir de un fichero");
        System.out.println("12.Salir");
        
        Scanner in = new Scanner(System.in);
            
        int opcion = pideInt("Elige una opcion: ");
        
        switch (opcion) {
            case 1:
                opcionMostrarClientes();
                return false;
            case 2:
                opcionNuevoClientePreparedStatement();
                return false;
            case 3:
                opcionNuevoClienteCallableStatement();
                return false; 
            case 4:
                opcionModificarCliente();
                return false;
            case 5:
                opcionEliminarCliente();
                return false;
            case 6:
            	opcionFiltrarClientes();
            	return false;
            case 7:
            	opcionCrearTabla();
            	return false;
            case 8:
            	opcionVolcarDatos();
            	return false;
            case 9:
            	opcionNuevoClienteFichero();
            	return false;
            case 10:
            	opcionModificarClienteFichero();
                return false;
            case 11:
            	opcionEliminarClienteFichero();
            	return false;
            case 12:
            	return true;
            default:
                System.out.println("Opcion elegida incorrecta");
                return false;
        }
        
    }
    
    /**
     * Pide un numero entero por teclado
     * @param mensaje (mensaje que se le mostrara al usuario justo antes de pedir el dato)
     * @return (el numero entero introducido por teclado)
     */
    public static int pideInt(String mensaje){
        
        while(true) {
            try {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                int valor = in.nextInt();
                //in.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("No has introducido un numero entero. Vuelve a intentarlo.");
            }
        }
    }
    
    /**
     * Pide una linea por teclado
     * @param mensaje (mensaje que se le mostrara al usuario justo antes de pedir el dato)
     * @return (la linea introducida por teclado)
     */
    public static String pideLinea(String mensaje){
        
        while(true) {
            try {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                String linea = in.nextLine();
                return linea;
            } catch (Exception e) {
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }
    
    /**
     * Nos muestra un mensaje y llama a la funcion printTablaClientes()
     */
    public static void opcionMostrarClientes() {
        System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

    /**
     * Pide por teclado los datos necesarios para crear un nuevo cliente y llama a la funcion insertCliente()
     */
    public static void opcionNuevoClientePreparedStatement() {
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String direccion = pideLinea("Direccion: ");

        boolean res = DBManager.insertCliente(nombre, direccion);

        if (res) {
            System.out.println("Cliente registrado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }
    
    /**
     * Pide por teclado los datos necesarios para crear un nuevo cliente y llama a la funcion nuevoCliente()
     */
    public static void opcionNuevoClienteCallableStatement() {
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String direccion = pideLinea("Direccion: ");

        boolean res = DBManager.nuevoCliente(nombre, direccion);

        if (res) {
            System.out.println("Cliente registrado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }
    
    /**
     * Pide por teclado la ruta donde se encuentra el fichero con la informacion de los nuevos clientes y llama a la funcion nuevoClienteFichero()
     */
    public static void opcionNuevoClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta en la que se encuentra el fichero que debemos leer");
    	String ruta=in.nextLine();
    	DBManager.nuevoClienteFichero(ruta);
    }
 
    /**
     * Pide por teclado los datos necesarios para modificar un cliente y llama a la funcion updateCliente()
     */
    public static void opcionModificarCliente() {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Mostramos datos del cliente a modificar
        DBManager.printCliente(id);

        // Solicitamos los nuevos datos
        String nombre = pideLinea("Nuevo nombre: ");
        String direccion = pideLinea("Nueva direccion: ");

        // Registramos los cambios
        boolean res = DBManager.updateCliente(id, nombre, direccion);

        if (res) {
            System.out.println("Cliente modificado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }
    
    /**
     * Pide por teclado la ruta del fichero con la informacion de los clientes a modificar y llama a la funcion modificarClienteFichero()
     */
    public static void opcionModificarClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta del fichero");
    	String ruta=in.nextLine();
    	DBManager.modificarClienteFichero(ruta);
    }

    /**
     * Pide por teclado los datos necesarios para eliminar a un cliente y llama a la funcion deleteCliente()
     */
    public static void opcionEliminarCliente() {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Eliminamos el cliente
        boolean res = DBManager.deleteCliente(id);

        if (res) {
            System.out.println("Cliente eliminado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }
    
    /**
     * Pide por teclado la ruta donde se encuentra el fichero con la informacion del cliente a eliminar y llama a la funcion eliminarClienteFichero()
     */
    public static void opcionEliminarClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta del fichero");
    	String ruta=in.nextLine();
    	DBManager.eliminarClienteFichero(ruta);
    }
    
    /**
     * Pide por teclado la ruta del archivo en la que volcaremos los datos de la tabla y llama a la funcion VolcarDatos()
     */
    public static void opcionVolcarDatos() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca el nombre del fichero en el que se volcaran los datos");
    	String ruta=in.nextLine();
    	DBManager.VolcarDatos(ruta);
    }
    
    /**
     * Pide por teclado la direccion de los clientes que se quieren filtar y llama a la funcion filtrarClientesDireccion()
     */
    public static void opcionFiltrarClientes() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la direccion de los clientes que desea ver");
    	String direccion=in.nextLine();
    	DBManager.filtrarClientesDireccion(direccion);
    }
    
    /**
     * Pide por teclado los datos necesarios para crear una nueva tabla y llama a la funcion crearTabla()
     */
    public static void opcionCrearTabla() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca el nombre de la nueva tabla");
    	String nomTabla=in.nextLine();
    	System.out.println("Introduzca el nombre de la primera columna (sera la clave primaria)");
    	String c1=in.nextLine();
    	System.out.println("Introduzca el nombre de la segunda columna");
    	String c2=in.nextLine();
    	System.out.println("Introduzca el nombre de la tercera columna");
    	String c3=in.nextLine();
    	DBManager.crearTabla(nomTabla, c1, c2, c3);
    }
}
