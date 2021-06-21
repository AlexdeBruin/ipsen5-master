package nl.hsleiden.inf2b.groep4.document;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Singleton
@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {

    private final DocumentService service;

    @Inject
    public DocumentResource(DocumentService service) {
        this.service = service;
    }

    /**
     * Method for saving a document (pdf) on the server.
     *
     * @param fileInputStream, the document itself converted to an inputStream.
     * @param contentDispositionHeader, information about the document itself. Unused.
     * @param filename, the name of the new document, passed trough by a quaryparam.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") final InputStream fileInputStream,
            @FormDataParam("file") final FormDataContentDisposition contentDispositionHeader,
            @QueryParam("filename") String filename) {
        boolean success = service.uploadFile(fileInputStream, filename);
        if(success) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public File getFile(@QueryParam("filename") String filename) {
        return service.getDocument(filename);
    }
}
