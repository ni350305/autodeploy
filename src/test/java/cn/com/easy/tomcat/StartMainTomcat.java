package cn.com.easy.tomcat;

import org.apache.catalina.startup.Embedded;

/**
 * The Class StartMainTomcat.
 * */
public class StartMainTomcat {

	/** The Constant PORT. */
	public static final int PORT = 80;

	/** The Constant CONTEXT. */
	public static final String CONTEXT = "/";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("catalina.base", System.getProperty("user.dir") + "/server");
		System.setProperty("log.sql.port", "80");
		Embedded server = TomcatUtils.buildNormalServer(PORT, CONTEXT, "AutoDeplojyTemplate", "utf-8");
		server.start();
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}

}
