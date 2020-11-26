package consoleInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class consoleReader {
	
	public static String getStringFromConsole(String message) {
		System.out.printf("%s\n", message);
		//Enter data using BufferReader 
        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
        //Reading data using readLine
        String input = "";
		try {
			input = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return input;
	}
	

}
