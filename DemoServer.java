import java.io.*;
import java.net.*;

public class DemoServer {
	static ServerSocket serverSocket=null;//the server socket
	static Socket client=null;//the client socket
	static DemoServer obj;//the object of the class
	static Receive1 receiver;//the object of the inner class
	static Send1 sender;//the object of the inner class
	class Receive1 extends Thread{//inner class to receive data
		synchronized public void run(){//overriding the run method
			try{
				BufferedReader br=null;//buffered reader to read data
				try{
					InputStream is=client.getInputStream();//getting the input stream
					br=new BufferedReader(new InputStreamReader(is));//creating the buffered reader
				}
				catch(UnknownHostException e){
					e.printStackTrace();//printing the stack trace
					System.out.println("Unable to connect!");//printing the message
				}
				String str="";//the string to store the data
				while((str=br.readLine())!=null){//reading the data
					System.out.println(str);//printing the data
				}
				br.close();//	closing the buffered reader
			}
			catch(IOException e){System.exit(0);}//catching the exception
		}
	}
	class Send1 extends Thread{//inner class to send data
		synchronized public void run(){//overriding the run method
			try{
				OutputStream os=client.getOutputStream();//getting the output stream
				DataOutputStream dos=new DataOutputStream(os);//creating the data output stream
		
				String str="";
				System.out.println("Connection Established. Write \"quit\" to exit");//printing the message
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//creating the buffered reader
				while(!(str=br.readLine()).equalsIgnoreCase("quit")){//reading the data
					dos.write(str.getBytes());//writing the data
					dos.write(13);//writing the data
					dos.flush();//flushing the data
				}
				dos.close();//closing the data output stream
			}
			catch(IOException e){System.exit(0);}//catching the exception
		}
	}
	public static void main(String[] args) throws IOException{
		try{
			serverSocket=new ServerSocket(99);
			System.out.println("Socket Ready! Waiting for Client to accept...");
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Port is Busy!");
			System.exit(1);
		}
		client=serverSocket.accept();
		
		obj=new DemoServer();
		
		receiver=obj.new Receive1();
		sender=obj.new Send1();
		sender.start();
		receiver.start();
		
	}
}