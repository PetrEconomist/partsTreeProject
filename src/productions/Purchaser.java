package productions;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import fileReader.ReadFile;
import fileWriter.WriteFile;

/**
 * Class for storage and processing of data about purchases
 * @author Petr
 *
 */
public class Purchaser {
	//map with IDs and purchased units
	private HashMap<String, Double> purchQt = new HashMap<String, Double>();
	
	/**
	 * Creates purchaser from input file 
	 * @param inputFile
	 */
	Purchaser(String inputFile) {
		LinkedList<String> input = ReadFile.getFileAsList(inputFile);
		for(String s : input) {
			int sepPos1 = s.indexOf(";");
			String id = s.substring(0, sepPos1);
			Double quantity = 0.0;
			try {
				quantity = Double.parseDouble(s.substring(sepPos1+1));
			}catch(Exception e) {
				String[] errorMessage = new String[1];
				errorMessage[0] = "Filed to set quantity for input " + s + " from input file " + inputFile + 
						" line " + input.indexOf(s) + " ended with error " + e;
				WriteFile.writeIntoFile("errorLog.txt", errorMessage);
			}
			addItem(id, quantity);
		}
				
	}

	/**
	 * Puts item and purchased quantity into HashMap
	 * @param id
	 * @param quantity
	 */
	private void addItem(String id, Double quantity) {
		//already in purchases -> add purchased quantity
		if(purchQt.containsKey(id)) {
			purchQt.replace(id, purchQt.get(id) + quantity);
		}else { //not existed -> put new id
			purchQt.put(id, quantity);
		}
	}
	
	/**
	 * Get purchased quantity in ID's units
	 * @param ID purchased item ID
	 * @return purchased volume in ID's units
	 */
	double getPurQuantity(String ID) {
		if(purchQt.containsKey(ID)){
			return purchQt.get(ID);
		}else return 0;
	}
	
	/**
	 * Gets all purchased items
	 * @return purchased items' ID
	 */
	Set<String> getPurchasedIDs(){
		return purchQt.keySet();
	}
	
}
