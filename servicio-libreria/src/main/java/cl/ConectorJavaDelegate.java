package cl;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named("conectorJavaDelegate")
public class ConectorJavaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        // 1. Capturamos las variables y las inicializamos para evitar el error de IntelliJ
        // Usamos String.valueOf o el casting (String) asegurando que no sean nulos
        String nombre = execution.getVariable("nombre_cliente") != null ? (String) execution.getVariable("nombre_cliente") : "Sin nombre";
        String email = execution.getVariable("email_cliente") != null ? (String) execution.getVariable("email_cliente") : "Sin email";
        String direccion = execution.getVariable("direccion_cliente") != null ? (String) execution.getVariable("direccion_cliente") : "Sin dirección";
        String telefono = execution.getVariable("telefono_cliente") != null ? (String) execution.getVariable("telefono_cliente") : "Sin teléfono";
        String origen = (String) execution.getVariable("origen_pedido");
        String descripcion = execution.getVariable("descripcion_pedido") != null ? (String) execution.getVariable("descripcion_pedido") : "";

        // 2. Configuración de conexión a SQL Server
        String url = "jdbc:sqlserver://localhost:1433;databaseName=LibreriaDB;encrypt=false;trustServerCertificate=true;";
        String user = "sa";
        String password = "romibain2026";

        // 3. Sentencia SQL (6 parámetros: cliente, email, direccion, telefono, origen, descripcion)
        String sql = "INSERT INTO Pedidos (cliente, email_cliente, direccion, telefono, origen, fecha, descripcion) VALUES (?, ?, ?, ?, ?, GETDATE(), ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignamos las variables
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, origen);
            stmt.setString(6, descripcion);

         // Ejecutamos la inserción
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Imprime el error en la consola de IntelliJ para depurar
            System.err.println("Error en la conexión o inserción SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}