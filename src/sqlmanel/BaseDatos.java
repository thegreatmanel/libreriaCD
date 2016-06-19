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
       public static int eliminar(String tabla, String primaryKeyCol, String primaryKeyVal) throws SQLException {
        s = conexion.createStatement();
        int n= s.executeUpdate("delete from " + tabla + " where " + primaryKeyCol + "=" + "'" + primaryKeyVal + "';");
        return n;
    }
        public static int modificar(String tabla, String columna, String primaryKeyCol, String primaryKeyVal, String valor) throws SQLException {
        int n = s.executeUpdate("update " + tabla + " set " + columna + " = +'" + valor + "' where " + primaryKeyCol + " = '"
                + primaryKeyVal + "';");
        return n;
    }
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
}
