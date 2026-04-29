package cl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedido")
public class PedidoResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response probar() {
        return Response.ok("{\"mensaje\": \"Servicio de Librería activo\"}").build();
    }
}