package server;

import java.net.InetAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import calculator.CalorieCalculator;

public class CalorieCalculatorServer {
	private static final Logger logger = LogManager.getLogger("CalorieCalculatorService");
	private static int port = 5700;

	public static void main(String[] args) throws Exception {

		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (hostname.equals("127.0.0.1"))
			hostname = "localhost";
		
		if (args.length >= 1)
			port = Integer.parseInt(args[0]);		
		else
			logger.info("Port not specified in arguments. Using default port: " + port);
		

		String url_str = String.format("http://%s:%d/", hostname, port);
		URI baseUrl = new URI(url_str);

		Set<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(CalorieCalculator.class);

		ResourceConfig rc = new ResourceConfig(resources);
		JdkHttpServerFactory.createHttpServer(baseUrl, rc);
		
		logger.info("CalorieCalculatorServer started: " + baseUrl);
	}
}