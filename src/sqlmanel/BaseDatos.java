/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlmanel;

import java.sql.Connection;
import java.sql.DriverManager;
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

}
