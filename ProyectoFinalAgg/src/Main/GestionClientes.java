package Main;

import java.util.Scanner;
import GestionBD.DBManager;

/**
 *
 * @author alvaro
 */
public class GestionClientes {

    public static void main(String[] args) {

        DBManager.loadDriver();
        DBManager.connect();

        boolean salir = false;
        do {
            salir = menuPrincipal();
        } while (!salir);

        DBManager.close();

    }

    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Listar clientes");
        System.out.println("2. Nuevo cliente (Usamos PrepareStatement)");
        System.out.println("3. Nuevo cliente (Usamos CallableStatement");
        System.out.println("4. Modificar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("6.Filtrar Clientes por su direccion");
        System.out.println("7. Volcar contenido de la tabla en un fichero");
        System.out.println("8.Insertar cliente a partir de un fichero");
        System.out.println("9. Modificar cliente a partir de un fichero");
        System.out.println("10. Eliminar cliente a partir de un fichero");
        System.out.println("11. Salir");
        
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
            	opcionVolcarDatos();
            	return false;
            case 8:
            	opcionNuevoClienteFichero();
            	return false;
            case 9:
            	opcionModificarClienteFichero();
                return false;
            case 10:
            	opcionEliminarClienteFichero();
            	return false;
            case 11:
            	return true;
            default:
                System.out.println("Opcion elegida incorrecta");
                return false;
        }
        
    }
    
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

    public static void opcionMostrarClientes() {
        System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

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
    
    public static void opcionNuevoClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta en la que se encuentra el fichero que debemos leer");
    	String ruta=in.nextLine();
    	DBManager.nuevoClienteFichero(ruta);
    }

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
    
    public static void opcionModificarClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta del fichero");
    	String ruta=in.nextLine();
    	DBManager.modificarClienteFichero(ruta);
    }

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
    
    public static void opcionEliminarClienteFichero() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la ruta del fichero");
    	String ruta=in.nextLine();
    	DBManager.eliminarClienteFichero(ruta);
    }
    
    public static void opcionVolcarDatos() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca el nombre del fichero en el que se volcaran los datos");
    	String ruta=in.nextLine();
    	DBManager.VolcarDatos(ruta);
    }
    
    public static void opcionFiltrarClientes() {
    	Scanner in=new Scanner (System.in);
    	System.out.println("Introduzca la direccion de los clientes que desea ver");
    	String direccion=in.nextLine();
    	DBManager.filtrarClientesDireccion(direccion);
    }
}
