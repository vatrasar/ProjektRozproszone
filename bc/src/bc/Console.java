
	// A simple Java Console for your application (Swing version)
	// Requires Java 1.1.5 or higher
	//
	// Disclaimer the use of this source is at your own risk. 
	//
	// Permision to use and distribute into your own applications
	//
	// RJHM van den Bergh , rvdb@comweb.nl
	package bc;
	import bc.messages.PeerAddress;

	import java.net.UnknownHostException;
	import java.util.List;
	import java.io.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.util.Scanner;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import javax.swing.*;


	public class Console  extends WindowAdapter implements WindowListener, ActionListener, Runnable
	{
		private JFrame frame;
		private JTextArea textArea;
		private Thread reader;
		private Thread reader2;
		private boolean quit;
		JTextField in;
						
		private final PipedInputStream pin=new PipedInputStream(); 
		private final PipedInputStream pin2=new PipedInputStream(); 

		Thread errorThrower; // just for testing (Throws an Exception at this Console
		
		public Console()
		{
			// create all components and add them
			frame=new JFrame("Java Console");
			 in = new JTextField(16);
			Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
			int x=(int)(frameSize.width/2);
			int y=(int)(frameSize.height/2);
			frame.setBounds(x,y,frameSize.width,frameSize.height);
			
			textArea=new JTextArea();
			textArea.setEditable(false);
			JButton button2=new JButton("send");
			
			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
			frame.getContentPane().add(in,BorderLayout.PAGE_START);
			frame.getContentPane().add(button2,BorderLayout.SOUTH);
			frame.setVisible(true);		
			
			frame.addWindowListener(this);		
			button2.addActionListener(this);
			
			try
			{
				PipedOutputStream pout=new PipedOutputStream(this.pin);
				System.setOut(new PrintStream(pout,true)); 
			} 
			catch (java.io.IOException io)
			{
				textArea.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
			}
			catch (SecurityException se)
			{
				textArea.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
		    } 
			
			try 
			{
				PipedOutputStream pout2=new PipedOutputStream(this.pin2);
				System.setErr(new PrintStream(pout2,true));
			} 
			catch (java.io.IOException io)
			{
				textArea.append("Couldn't redirect STDERR to this console\n"+io.getMessage());
			}
			catch (SecurityException se)
			{
				textArea.append("Couldn't redirect STDERR to this console\n"+se.getMessage());
		    } 		
				
			quit=false; // signals the Threads that they should exit
					
			// Starting two seperate threads to read from the PipedInputStreams				
			//
			reader=new Thread(this);
			reader.setDaemon(true);	
			reader.start();	
			//
			reader2=new Thread(this);	
			reader2.setDaemon(true);	
			reader2.start();
					
			
		}
		
		public synchronized void windowClosed(WindowEvent evt)
		{
			quit=true;
			this.notifyAll(); // stop all threads
			try { reader.join(1000);pin.close();   } catch (Exception e){}		
			try { reader2.join(1000);pin2.close(); } catch (Exception e){}
			System.exit(0);
		}		
			
		public synchronized void windowClosing(WindowEvent evt)
		{
			frame.setVisible(false); // default behaviour of JFrame	
			frame.dispose();
		}
		
		public synchronized void actionPerformed(ActionEvent evt)
		{
			//obs³uga wpisanych danych
			String t = in.getText();
			if(t=="c"){ // c connect
				Polaczenie p=new Polaczenie();
				try {
					p.pol();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("blad");//pisanie do konsoli
				}
			}
					
			
		}

		public synchronized void run()
		{
			try
			{			
				while (Thread.currentThread()==reader)
				{
					try { this.wait(100);}catch(InterruptedException ie) {}
					if (pin.available()!=0)
					{
						String input=this.readLine(pin);
						textArea.append(input);
					}
					if (quit) return;
				}
			
				while (Thread.currentThread()==reader2)
				{
					try { this.wait(100);}catch(InterruptedException ie) {}
					if (pin2.available()!=0)
					{
						String input=this.readLine(pin2);
						textArea.append(input);
					}
					if (quit) return;
				}			
			} catch (Exception e)
			{
				textArea.append("\nConsole reports an Internal error.");
				textArea.append("The error is: "+e);			
			}
			
			// just for testing (Throw a Nullpointer after 1 second)
			if (Thread.currentThread()==errorThrower)
			{
				try { this.wait(1000); }catch(InterruptedException ie){}
				throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
			}

		}
		
		public synchronized String readLine(PipedInputStream in) throws IOException
		{
			String input="";
			do
			{
				int available=in.available();
				if (available==0) break;
				byte b[]=new byte[available];
				in.read(b);
				input=input+new String(b,0,b.length);														
			}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
			return input;
		}	
		public static void main(String[] arg)
		{
//			new Console(); // create console with not reference

//			Polaczenie polaczenie=new Polaczenie();
			DebugConsole.consoleRun();

		}			

}

class DebugConsole
{
	public DebugConsole() {
	}

	public static void consoleRun()
	{
		boolean toClose=false;
		while (!toClose)
		{

			System.out.print("Prosze wpisac polecenie:");
			String comand = getInputFormConsole();
			Logger logger=Logger.getLogger("debugLogger");
			logger.setLevel(Level.ALL);

			switch (comand)
			{
				case "version":
					logInfo(logger,"Wiadomosc version");

					System.out.print("Prosze podac adres ip celu wiadomosci>");

					String ip=getInputFormConsole();
					if(isIpValid(ip))
					{
						Polaczenie polaczenie=new Polaczenie("version",ip);
						try {
							polaczenie.pol();
						} catch (IOException e) {
							System.out.println("Wysylanie wiadomosci nieudane");
						}
					}
					else
					{
						System.out.println("Bledny adres ip");
					}

					sleepTherad(); //logger needs some time to react

					break;
				case "close":
					logInfo(logger,"Zamykanie programu");
					toClose=true;
 					break;
				case "ipDNS":
					PeerDiscovery peerDiscovery=new PeerDiscovery();
					List<String>addressesListByDNS= null;
					try {
						addressesListByDNS = peerDiscovery.getAddressesListByDNS();
						addressesListByDNS.forEach(System.out::println);
					} catch (UnknownHostException e) {
						System.out.println("Nie udalo sie nawiazac polaczenia z DNS");
					}

					break;
				case "help":
					String helpInfo="close: zamykanie programu\n" +
							"version: wyslanie wiadomosci version\n" +
							"ipDNS: wypisanie adresow ip pochodzacych z wyszukiwania DNS\n";
					System.out.println(helpInfo);
					break;
				case "ping":
					logInfo(logger,"Wiadomosc ping");

					System.out.print("Prosze podac adres ip celu pinga>");

					ip=getInputFormConsole();
					if(isIpValid(ip))
					{
						Polaczenie polaczenie=new Polaczenie("ping",ip);
						try {
							polaczenie.pol();
						} catch (IOException e) {
							System.out.println("Wysylanie pinga");
						}
					}
					else
					{
						System.out.println("Bledny adres ip");
					}

					sleepTherad(); //logger needs some time to react

					break;
				default:
				System.out.println("No such command, please try again");
			}
		}

	}

	private static boolean isIpValid(String ip) {
		String pattern="(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		return ip.matches(pattern);
	}

	private static void sleepTherad() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String getInputFormConsole() {
		Scanner scanner=new Scanner(System.in);
		return scanner.nextLine();
	}
	private static void logInfo(Logger logger,String message)
	{
		logger.info(message);
		sleepTherad();
	}
}