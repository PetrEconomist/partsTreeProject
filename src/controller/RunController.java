package controller;

import java.util.Arrays;

import consoleInput.consoleReader;

public class RunController {

	/**
	 * From files with productions and consumptions in project folder
	 * creates database with diluted production schema.
	 * By diluted production schema is meant schema, where all produced parts
	 * are diluted into materials used for their production. This means these
	 * semi-finished products are not present directly in the final product, 
	 * but are present in final product only as raw materials. 
	 * 
	 */
	/**This is the main class, which controls the whole process*/
	
	private static String consFile;
	private static String prodFile;
	private static String purchFile;
	private static String outputFile;
	
	public static void main(String[] args) {
		
		setFileNames(args);
		
		//creates controller, which handles productions and consumptions data
		Controller ctg = new Controller(consFile, prodFile, purchFile);
		//dilutes consumptions
		ctg.diluteConsumption();
		//store diluted consumptions in txt file
		ctg.storeDilCon(outputFile);
		
		System.out.printf("Successfuly completed");
		
	}
	
	private static void setFileNames(String[] args) {
		System.out.printf("loaded %d arguments\n", args.length);
		if(args.length==0) {
			String testResponse = consoleReader.getStringFromConsole("Is this WJ21Q2 test (y/n)?");
			if(testResponse.equals(new String("y"))) {
				args = new String[1];
				args[0] = "consumptionsWJ21Q2.txt;productionsWJ21Q2.txt;purchasesWJ21Q2.txt;outputWJ21Q2.txt";
				args=args[0].split(";");
				System.out.printf("%s\n", Arrays.toString(args));
			}
		}
		if(args.length==1){
			 System.out.printf("delimiting using ; separator\n");
				args=args[0].split(";");
			 System.out.printf("%s\n", Arrays.toString(args));
		}
		
		if(args.length==0) {
			consFile = consoleReader.getStringFromConsole("Enter name of file with consumptions");
			prodFile = consoleReader.getStringFromConsole("Enter name of file with productions");
			purchFile = consoleReader.getStringFromConsole("Enter name of file with purchases");
			outputFile = consoleReader.getStringFromConsole("Enter name of file for output");
		}else{
			consFile = args[0];
			prodFile = args[1];
			purchFile = args[2];
			outputFile = args[3];
		}
	}

}
