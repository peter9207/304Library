package main;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import ui.MainFrame;
/**
 * 
 * @author peter9207
 *
 * main class the liburary is started from, probably shouldnt have any more code that it already does,
 * since all it should do is start up the window
 * 
 */
public class MainLibrary {
	public static DatabaseHandler databaseHandler;
	public static OracleConnection con;
	public static void main(String [] args){
		JFrame frame = new MainFrame();
		frame.pack();
		frame.setVisible(true);
		con = new OracleConnection();
		databaseHandler = new DatabaseHandler();
		//asdasd
		System.out.print(TimeUnit.MILLISECONDS.convert(2*7 ,TimeUnit.DAYS));
		System.out.println("\n");
		System.out.print(TimeUnit.MILLISECONDS.convert(12*7 ,TimeUnit.DAYS));
		System.out.println("\n");
		System.out.print(TimeUnit.MILLISECONDS.convert(6*7 ,TimeUnit.DAYS));
		System.out.println("\n");
	}

}
