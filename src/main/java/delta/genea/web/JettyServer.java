package delta.genea.web;

import java.net.URL;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Jetty server for the genea application.
 * @author DAM
 */
public class JettyServer
{
  private Server server;

  /**
   * Start the server.
   * @throws Exception If an error occurs.
   */
  public void start() throws Exception
  {
    server=new Server();
    ServerConnector connector=new ServerConnector(server);
    connector.setPort(8080);
    server.setConnectors(new Connector[] {connector});
    ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    // Dynamic contents
    ServletHolder genea=new ServletHolder("genea",GeneaServlet.class);
    context.addServlet(genea,"/genea");
    // Static contents
    ResourceHandler resHandler = new ResourceHandler();

    ClassLoader cl=this.getClass().getClassLoader();
    URL url = cl.getResource("index.html");
    System.out.println("URL: "+url);
    String dirUrlStr = url.toString().replace("index.html","");
    URL dir = new URL(dirUrlStr);
    System.out.println("URL: "+dir);
    Resource ressource = Resource.newResource(dir);
    resHandler.setBaseResource(ressource);

    HandlerList handlers=new HandlerList();
    handlers.addHandler(resHandler);
    handlers.addHandler(context);
    server.setHandler(handlers);
    // Start server!
    server.start();
  }

  /**
   * Main method for this application.
   * @param args Not used.
   * @throws Exception If an error occurs.
   */
  public static void main(String[] args) throws Exception
  {
    new JettyServer().start();
  }
}
