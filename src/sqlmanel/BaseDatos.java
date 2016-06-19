/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlmanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author propa
 */
public class BaseDatos {

    private static Connection conexion;
    private static Statement s;
    /**
     * Necesario para utilizar la librería, crea el flujo de datos entre
     * el programa y la base de datos.
     * @param servidor Ip o nombre del servidor
     * @param bd Nombre de la base de datos
     * @param usuario Nombre de usuario
     * @param contraseña Contraseña del usuario
     * @return Mensaje de la conexión
     */

    public static String conectar(String servidor, String bd, String usuario, String contraseña) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + bd, usuario, contraseña);
            return "Conexión a base de datos " + servidor + " ... OK";
        } catch (ClassNotFoundException ex) {
            return "Error cargando el Driver MySQL JDBC ... FAIL";
        } catch (SQLException ex) {
            return "Imposible realizar conexion con " + servidor + " ... FAIL";
        }

    }
    /**
     * Sirve para insertar registros en la tabla deseada
     * @param tabla Nombre de la tabla
     * @param valores Un array de valores para insertar en la tabla
     * @return Número de filas afectadas
     * @throws SQLException 
     */
      public static int insertar(String tabla, String[] valores) throws SQLException {

        String consulta = "insert into " + tabla + " values(";
        for (int i = 0; i < valores.length; i++) {
            if (i < valores.length-1) {
                consulta += "'" + valores[i] + "',";
            } else {
                consulta += "'" + valores[i] + "'";
            }
        }
        consulta += ");";

        s = conexion.createStatement();
        return s.executeUpdate(consulta);
    }
      /**
     * Devuelve todos los registros de la tabla deseada
     *
     * @param tabla EL nombre de la tabla que se quiere consultar
     * @param campos Los nombres de las columnas que se quieren consultar
     * @return Un objeto ResultSet que almacena los registros de la tabla
     * @throws java.sql.SQLException
     */
      public static ResultSet consultarDatos(String tabla, String[] campos) throws SQLException {
        ResultSet rs;
        String consulta = "select ";
        s = conexion.createStatement();
        for (int i = 0; i < campos.length; i++) {
            if (i == campos.length - 1) {
                consulta += campos[i] + " from " + tabla + " ;";
            } else {
                consulta += campos[i] + ",";
            }
        }
        rs = s.executeQuery(consulta);
        return rs;
    }
      /**
     * Elimina un registro de una tabla
     *
     * @param tabla EL nombre de la tabla en la que se quiere eliminar un
     * registro
     * @param primaryKeyCol El nombre de la columna que es clave primaria
     * @param primaryKeyVal El valor de la clave primaria
     * @return Número de filas afectadas
     * @throws java.sql.SQLException
     */
       public static int eliminar(String tabla, String primaryKeyCol, String primaryKeyVal) throws SQLException {
        s = conexion.createStatement();
        int n= s.executeUpdate("delete from " + tabla + " where " + primaryKeyCol + "=" + "'" + primaryKeyVal + "';");
        return n;
    }
       /**
     * Elimina un registro de una tabla
     *
     * @param tabla EL nombre de la tabla en la que se quiere eliminar un
     * registro
     * @param primaryKeyCol El nombre de la columna que es clave primaria
     * @param primaryKeyVal El valor de la clave primaria
     * @return Número de filas afectadas
     * @throws java.sql.SQLException
     */
        public static int modificar(String tabla, String columna, String primaryKeyCol, String primaryKeyVal, String valor) throws SQLException {
        int n = s.executeUpdate("update " + tabla + " set " + columna + " = +'" + valor + "' where " + primaryKeyCol + " = '"
                + primaryKeyVal + "';");
        return n;
    }
         /**
     * Devuelve los registros de la tabla donde la columna de búsqueda contenga el valor de búsqueda
     * @param colBusqueda nombre de la columna en la cual se quiere buscar la cadena de valorBusqueda
     * @param valorBusqueda es la cadena de caracteres que se va a buscar en la columna de búsqueda
     * @param tabla EL nombre de la tabla que se quiere consultar
     * @param campos Los nombres de las columnas que se quieren consultar
     * @return Un objeto ResultSet que almacena los registros de la tabla
     * @throws java.sql.SQLException
     */
public static ResultSet buscar(String tabla, String[] campos,String colBusqueda,String valorBusqueda)throws SQLException{
        ResultSet rs;
        String consulta = "select ";
        s = conexion.createStatement();
        for (int i = 0; i < campos.length; i++) {
            if (i == campos.length - 1) {
                consulta += campos[i] + " from " + tabla;
            } else {
                consulta += campos[i] + ",";
            }
        }
        consulta+=" where " + colBusqueda + " = " + "'" + valorBusqueda + "';";
        rs = s.executeQuery(consulta);
        return rs;
    }
/**
     * Cierra el flujo de datos con el servidor y libera todos los recursos
     * asociados a este
     *
     * @throws java.sql.SQLException
     */
public static void Desconectar() throws SQLException{
        s.close();
        conexion.close();
    }
}
