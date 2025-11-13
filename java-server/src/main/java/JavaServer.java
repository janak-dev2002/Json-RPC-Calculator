import com.googlecode.jsonrpc4j.JsonRpcServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

public class JavaServer {

    public static void main(String[] args) throws Exception {

        final JsonRpcServer rpcServer = new JsonRpcServer(new CalculatorServiceImpl(), CalculatorService.class);

        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        handler.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.setContentType("application/json");
                rpcServer.handleRequest(req.getInputStream(), resp.getOutputStream());
            }
        }), "/calculator");

        server.start();
        server.join();
    }
}
