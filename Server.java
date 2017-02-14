import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Server
{
	public final static int THREAD_POOL_SIZE = 5;

	public static void main(String argv[]) throws Exception
	{
		ServerSocket welcomeSocket = new ServerSocket(6789);		
		ConcurrentHashMap<String, String> table = new ConcurrentHashMap<String, String>();

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();

			Runnable acceptor = new ConnectionAcceptor(connectionSocket, table);
			executor.execute(acceptor);

		}

		//executor.shutdown();
	}
}
