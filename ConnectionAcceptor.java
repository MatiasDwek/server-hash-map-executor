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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectionAcceptor implements Runnable {
	Socket connectionSocket;
	ConcurrentHashMap<String, String> table;

	public ConnectionAcceptor(Socket connectionSocket, ConcurrentHashMap<String, String> table) {
		this.connectionSocket = connectionSocket;
		this.table = table;
		
	}

	@Override //on sait jamais
	public void run(){
		String clientSentence;
		String capitalizedSentence;

		

		try {
			Thread.sleep(4000);

			//System.out.println(Server.table.get("lolo"));

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			clientSentence = inFromClient.readLine();
			String[] words = clientSentence.split("\\s+");

			if (words[0].equals("PUT")) {
				String key = words[1];
				String value = words[2];
				
				table.put(key, value);
				outToClient.writeBytes("ok\n");
			} else if (words[0].equals("KEYS")) {
				List<String> listOfKeys = new ArrayList<String>(table.keySet());
				String answer = new String();
				
				for (int i = 0; i < listOfKeys.size(); i++) {
					answer = answer + listOfKeys.get(i) + "; ";
				}
				answer = answer + "\n";
				outToClient.writeBytes(answer);
			
			} else if (words[0].equals("GET")) {
				String key = words[1];
				String answer;
				if (table.get(key) != null) {
					answer = table.get(key);
				} else {
					answer = "ko";
				}

				
				outToClient.writeBytes(answer + '\n');
			} else if (words[0].equals("DEL")) {
				String key = words[1];
				String answer;
				if (table.remove(key) != null) {
					answer = "ok";
				} else {
					answer = "ko";
				}

				
				outToClient.writeBytes(answer + '\n');
			}
			

			//capitalizedSentence = clientSentence.toUpperCase() + '\n';

			//outToClient.writeBytes(capitalizedSentence);

		} catch (Exception e) {

		}

		
		
    }
}
