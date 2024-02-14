package delta.genea.web;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

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
    server.setHandler(context);
    // Dynamic contents
    ServletHolder genea=new ServletHolder("genea",GeneaServlet.class);
    context.addServlet(genea,"/genea");
    // Static contents
    ServletHolder holderHome=new ServletHolder("static-home",DefaultServlet.class);
    holderHome.setInitParameter("resourceBase","ressources");
    holderHome.setInitParameter("dirAllowed","true");
    holderHome.setInitParameter("pathInfoOnly","true");
    context.addServlet(holderHome,"/ressources/*");

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
