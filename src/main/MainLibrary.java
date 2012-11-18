package main;

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
	}

}
