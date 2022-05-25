package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DBManagerTest {
	
	// Conexion a la base de datos
    public static Connection conn = null;

    // Configuracion de la conexion a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "tienda";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "usuario";
    private static final String DB_MSQ_CONN_OK = "CONEXION CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXION";

    // Configuracion de la tabla Clientes
    private static final String DB_CLI = "clientes";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    private static final String DB_CLI_ID = "id";
    private static final String DB_CLI_NOM = "nombre";
    private static final String DB_CLI_DIR = "direccion";
    
    //Antes de empezar con los test establecemos la conexion con la base de datos para poder hacer todas las pruebas
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
        } catch (SQLException ex) {
        	System.err.println("No ha sido posible conectarse a la base de datos");
        }
	}
	
	//Una vez se han realizado todos los test cerramos la conexion con la base de datos
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		try {
	         conn.close();
	    } catch (SQLException ex) {
	    	 System.err.println("No ha sido posible cerrar la conexion con la base de datos");
	    }
	}
	

	//Testeamos si el estado de la conexion se comprueba correctamente
	@Test
	void testIsConnected() {
		try {
            if (conn != null && conn.isValid(0)) {
                System.out.println(DB_MSQ_CONN_OK);
            } else {
                System.out.println("No estamos conectados a la base de datos");
            }
        } catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
        }
	}
	
	//Testeamos si la tabla clientes se imprime correctamente
	@Test
	void testPrintTablaClientes() {
		try {
			PreparedStatement stmt=conn.prepareStatement(DB_CLI_SELECT,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        	ResultSet rs=stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(DB_CLI_ID);
                String n = rs.getString(DB_CLI_NOM);
                String d = rs.getString(DB_CLI_DIR);
                System.out.println(id + "\t" + n + "\t" + d);
            }
            rs.close();
        } catch (SQLException ex) {
        	System.err.println("No ha sido posible imprimir por pantalla la tabla clientes");
        }
	}

	//Testeamos si podemos filtrar clientes introduciendo su direccion
	@Test
	void testFiltrarClientesDireccion() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call filtrarDireccion(?)}");
			cStmt.setString(1,"Valencia");
			cStmt.execute();
			
			ResultSet rs = cStmt.getResultSet();  
			
			while (rs.next()) {  
				 int id = rs.getInt(DB_CLI_ID);
	             String n = rs.getString(DB_CLI_NOM);
	             String d = rs.getString(DB_CLI_DIR);
	             System.out.println(id + "\t" + n + "\t" + d);
          }  
		} catch (SQLException e) {
			System.err.println("No ha sido posible filtrar clientes por su direccion");
		}  
	}

	//Testeamos si se comprueba correctamente la existencia de un cliente
	@Test
	void testExistsCliente() {
		try {
			String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "=5;";
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
         
            if (rs == null || !rs.first()) {
                System.out.println("Cliente  NO EXISTE");
            }

            // Si rs es null, se ha producido un error
            if (rs == null) {
                System.err.println("Se ha producido un error");
            }

            // Si no existe primer registro
            if (!rs.first()) {
                rs.close();
                System.out.println("El cliente no existe");
            }

            // Todo bien, existe el cliente
            rs.close();
            System.out.println("El cliente existe");

        } catch (SQLException ex) {
        	System.err.println("No ha sido posible comprobar si el cliente con el id indicado existe en la base de datos");
        }
	}
	
	//Testeamos que se imprima correctamente por pantalla la informacion de un cliente
	@Test
	void testPrintCliente() {
		try {
            // Realizamos la consulta SQL
        	String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "=5;";
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
         
            if (rs == null || !rs.first()) {
                System.out.println("Cliente  NO EXISTE");
            }
            
            // Imprimimos su informaciÃ³n por pantalla
            int cid = rs.getInt(DB_CLI_ID);
            String nombre = rs.getString(DB_CLI_NOM);
            String direccion = rs.getString(DB_CLI_DIR);
            System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);

        } catch (SQLException ex) {
        	System.err.println("Error al solicitar cliente 5");
        }
	}
	
	//Testeamos que se puedan añadir nuevos clientes mediante la llamada al procedimiento almacenado de la base de datos
	@Test
	void testNuevoCliente() {
		try {	
			System.out.print("Insertando cliente testJunit...");
			CallableStatement cStmt = conn.prepareCall("{call insertarCliente(?,?,?,?)}");
			cStmt.setString(1, DB_CLI_NOM);
			cStmt.setString(2, DB_CLI_DIR);  
			cStmt.setString(3,"testJunit");  
			cStmt.setString(4,"Prueba");  
			
			cStmt.execute(); 
			cStmt.close();
	        System.out.println("OK!");
	         
		} catch (SQLException e) {
			System.err.println("No ha sido posible insertar el nuevo cliente haciendo uso del procedimiento");
		} 
	}
	
	//Testeamos que se controle de manera correcta el intento de eliminar un cliente que no existe
	@Test
	void testDeleteCliente() {
		 try {
			 //Colocamos un id de cliente que no existe para comprobar que se controle de manera correcta
	          System.out.print("Eliminando cliente 24... ");

	          String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "=24;";
	          PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	          ResultSet rs = stmt.executeQuery();

	          // Si no existe el Resultset
	          if (rs == null) {
	              System.out.println("ERROR. ResultSet null.");	 
	          }

	          // Si existe y tiene primer registro, lo eliminamos
	          if (rs.first()) {
	              rs.deleteRow();
	              rs.close();
	              System.out.println("OK!");
	          } else {
	              System.out.println("ERROR. ResultSet vacio.");
	          }

	        } catch (SQLException ex) {
	        	System.err.println("No ha sido posible eliminar el cliente con el id indicado");
	        }
	}
	
	//Testeamos el correcto volcado de los datos de la tabla en un fichero
	@Test
	void testVolcarDatos() {
		String ruta2="Ficheros/volcadoDatos.txt";
    	File f=new File(ruta2);
    	
    	try {
			FileWriter escribirFichero=new FileWriter(f);
			
			escribirFichero.write(DB_NAME+"\t"+DB_CLI+"\n");
			escribirFichero.write(DB_CLI_ID+"\t"+DB_CLI_NOM+"\t\t"+DB_CLI_DIR+"\n");
			
			String sql = DB_CLI_SELECT ;
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
			
            while (rs.next()) {
                int id = rs.getInt(DB_CLI_ID);
                String n = rs.getString(DB_CLI_NOM);
                String d = rs.getString(DB_CLI_DIR);
                escribirFichero.write(id+"\t"+n+"\t\t"+d+"\n");
            }
            escribirFichero.close();
            rs.close();
            System.out.println("La informacion se ha volcado correctamente en el archivo.");
			
		} catch (IOException e) {
			System.err.println("No ha sido posible volcar los datos en el fichero con la ruta especificada");
		} catch (SQLException e) {
			System.err.println("No ha sido posible volcar los datos en el fichero con la ruta especificada");
		}
	}
	
	//Testeamos la correcta creacion de una nueva tabla en la base de datos (o el correcto control en caso de no poder crearla)
	@Test
	void testCrearTabla() {
		String sentencia="CREATE TABLE "+"prueba"+"("+"p1"+" varchar(100) primary key,"+"p2"+" varchar(100),"+"p3"+" varchar(100))";
    	try {
			PreparedStatement stmt=conn.prepareStatement(sentencia);
			stmt.execute();
			System.out.println("La nueva tabla se ha creado correctamente");
		} catch (SQLException e) {
			System.err.println("No ha sido posible crear la nueva tabla");
		}
	}

}
