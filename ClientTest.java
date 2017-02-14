import java.io.*;
import java.net.*;
import java.lang.Math.*;

class ClientTest
{
	public static void main(String argv[]) throws Exception
	{
		String key;
		String value;
		String answer;
		String command;

		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		key = String.valueOf(Math.random());
		value = String.valueOf(Math.random());
		command = "PUT" + " " + key + " " + value + '\n';
		outToServer.writeBytes(command);
		answer = inFromServer.readLine();
		System.out.println(answer);
		
		

		clientSocket.close();
	}
}
