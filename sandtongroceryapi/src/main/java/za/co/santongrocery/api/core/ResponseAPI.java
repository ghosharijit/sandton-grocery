package za.co.santongrocery.api.core;


import org.apache.log4j.Logger;
import za.co.santongrocery.api.exception.BusinessException;
import za.co.santongrocery.api.exception.ErrorResponse;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResponseAPI {
    private static final Logger LOGGER = Logger.getLogger(ResponseAPI.class);
    protected Response buildResponse(Object obj){
        try {
            Response response = Response.status(Response.Status.OK).entity(obj).type(MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("access-control-allow-headers", "content-type")
                    .build();
            return response ;
        }
        catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorResponse.from(ex)).build();
        }
    }

    protected Response getBusinessExceptionResponse(BusinessException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ErrorResponse.from(ex)).type(MediaType.APPLICATION_JSON).build();
    }
}
