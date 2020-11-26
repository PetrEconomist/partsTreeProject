package controller;

import consumptions.Consumer;
import fileReader.ReadFile;
import fileWriter.WriteFile;
import productions.ProdRatios;
import productions.Producer;



/**
 * Class controller is responsible for management data about production and consumption and it's processing
 * @author Petr Potociar
 *
 */
public class Controller {

	//consumer is responsible for storage of data concerning consumption and it's processing
	private Consumer cons;
	//producer  is responsible for storage of data concerning production and it's processing
	private Producer prod;
	//prodRatios provides data about production ratios
	private ProdRatios prodRatios;

	

	/**
	 * Creates controller from data about consumptions and productions
	 * 
	 * @param consFileName file with consumptions, incl. extension. 
	 * @param prodFileName file with consumptions, incl. extension.
	 */
	public Controller(String consFileName, String prodFileName) {
		this(consFileName, prodFileName, null);
	}
	
	public Controller(String consFileName, String prodFileName, String purchasesFile) {
		prod = new Producer(prodFileName);
		cons = new Consumer(consFileName, prod);
		prodRatios = new ProdRatios(prod, purchasesFile);
	}
	
	/**
	 * Dilutes consumption.
	 * By dissolution is meant breakage of produced items into materials used for the production.
	 * If semi-production is used, it is also broken into materials, therefore only raw materials
	 * are present in final diluted prodution tree. 
	 * The diluted production is per production of one piece (if measurement is not in pieces but in meter, 
	 * kg or other quantity, it is calculated per one unit)
	 */
	public void diluteConsumption() {
		cons.diluteConsumption(prodRatios);
	}
		
		
	/**
	 * Saves diluted production per piece into the file
	 * Lists only parts given in input.
	 * @param fileNameOutput name of the file for output
	 * @param fileNameInput name of file with products to be listed in output
	 */
	public void loadDilCon(String fileNameOutput, String fileNameInput) {
		String[] products = ReadFile.getFileAsList(fileNameInput).toArray(new String[0]);
		for(String prodID : products) {
			System.out.printf("Saving diluted production of %s into %s", prodID, fileNameOutput);
			WriteFile.writeIntoFile(fileNameOutput, cons.getDilConPP(prodID));
		}
	}
	
	/**
	 * Saves diluted production per piece into the file.
	 * Lists all produced parts.
	 * @param fileNameOutput name of the file for output
	 */
	public void storeDilCon(String fileNameOutput) {
		String[] products = prod.getProducts().toArray(new String[0]);
		for(String prodID : products) {
			System.out.printf("Saving diluted production of %s into %s", prodID, fileNameOutput);
			WriteFile.writeIntoFile(fileNameOutput, cons.getDilConPP(prodID));
		}
	}
	
	
	/**
	 * For given product creates diluted production tree, per piece (unit), in text form
	 * @param prodID product ID whose production tree will be created
	 * @return text with production tree, ";" as separator, line separated
	 */
	public String[] getDilConPP(String prodID) {
		return cons.getDilConPP(prodID);
	}
	
	

}
