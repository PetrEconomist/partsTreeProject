package consumptions;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fileReader.ReadFile;
import productions.ProdRatios;
import productions.Producer;

/**
 * Class Consumer is responsible for storage of data concerning consumption and it's processing
 * @author Petr
 *
 */
public class Consumer {
	//production schema, which items were used for products' manufacture
	private ProdSchema prodSchema;
	//production schema, which items were used for products' manufacture, per piece (unit)
	private ProdSchema prodSchemaPP;
	//production schema, which raw materials were used for products' manufacture, per piece (unit)
	private ProdSchema dilutedProdSchema;

	 
	
	/**
	 * Reads input from file and calculates consumptions in schemes and usage map
	 * @param fileName file with consumptions
	 * @param prod Producer used for calculation of production per unit (piece)
	 */
	public Consumer(String fileName, Producer prod) {
		prodSchema = new ProdSchema();
		//fills usage map and prodSchema with data
		processInput(fileName);
		//calculates production schema per piece (unit)
		System.out.printf("Consumer started creation of schema per piece\n");
		prodSchemaPP = prodSchema.setSchemaPerPiece(prod);
	}
	
	
	/**
	 * The procedure loads data from file and stores it
	 * 
	 * @param fileName name of the file with data regarding consumption
	 *		<p>File's layout: Data are line-separated. 
	 *		Line layout: "produced(ID);consumed(ID);consumption(quantity)"</p>
	 * 
	 * @return count of loaded lines
	 */
	private int processInput(String fileName) {
		LinkedList<String> input = ReadFile.getFileAsList(fileName);
		int loadedLines = 0;
		for(String s : input) {
			int sepPos1 = s.indexOf(";");
			int sepPos2 = s.indexOf(";", sepPos1+1);
			String prodId = s.substring(0,sepPos1);
			String consId = s.substring(sepPos1+1,sepPos2);
			Double consQt = Double.parseDouble(s.substring(sepPos2+1));
			addConsumption(prodId, consId, consQt);
			loadedLines++;
			System.out.printf("Consumer loaded line %d\n", loadedLines);
		}
		return loadedLines;
	}
	
	/**
	 * Adds consumption into usage map and production schema
	 * @param prodId product's ID
	 * @param consId consumed part's ID
	 * @param consQt quantity in units of consumed part
	 */
	private void addConsumption(String prodId, String consId, Double consQt) {
		prodSchema.addProdTree(prodId, consId, consQt);
	}
	
	/**
	 * Dilutes consumption of all known production steps (and all products involved)
	 */
	public void diluteConsumption(ProdRatios prodRatios) {
		dilutedProdSchema = new ProdSchema();
		//counters for status info
		int counterDiluted = 0; 
		int countItems = prodSchema.getProducts().size();
		//loop over products
		for(String itemID : prodSchema.getProducts()){
			System.out.printf("Diluting %s, item %d/%d\n", itemID, counterDiluted++, countItems);
			try{
				diluteID(itemID, prodRatios.getProdRatio(itemID));
			}catch(Exception E){
				//running in cycle
			}	
		}
	}
	
	public void diluteCOnsumption() {
		diluteConsumption(null);
	}
	
	/**
	 * Dilutes consumption of given ID and stores it in dilutedProdSchema
	 *  
	 * @param partID ID to be diluted
	 * 
	 */
	private void diluteID(String itemID) throws Exception{
		diluteID(itemID, 1);
	}
	
	/**
	 * Dilutes consumption of given ID and stores it in dilutedProdSchema
	 *  
	 * @param partID ID to be diluted
	 * @param prodRatio ratio of production to intake
	 * 
	 */
	private void diluteID(String itemID, double prodRatio) throws Exception{
		if(dilutedProdSchema.isIDProduced(itemID))return; //already finished branch (already diluted)
		//not raw material
		if(prodSchemaPP.isIDProduced(itemID)) {
			//processoring parts to be diluted
			Set<String> predecessorIDs = prodSchemaPP.getUsedParts(itemID);
			for(String predecessor : predecessorIDs) {
				System.out.printf("Diluting predecessor %s of product %s\n", predecessor, itemID);
				if(predecessor==itemID) { //part is its own predecessor
					throw new Exception("Cycle in prodution " + itemID);
				}
				diluteID(predecessor); //recursion
				//diluted production schema of predecessor (created above)
				HashMap<String, Double> dilutedConsTreePredecessor = dilutedProdSchema.getConsIDQt(predecessor);
				//consumed quantity of predecessor in the production of item
				Double consQtPredor = prodSchemaPP.getConsIDQt(itemID).get(predecessor) * prodRatio;
				dilutedProdSchema.addProdTree(itemID, dilutedConsTreePredecessor, consQtPredor);
			}
			if(prodRatio!=0) {
				//add production of itself
				//represents purchased part of item intake
				dilutedProdSchema.addProdTree(itemID, itemID, 1-prodRatio);
			}
		}else{ //not produced -> raw material
			//fictitious production from itself (in production schema)
			dilutedProdSchema.addProdTree(itemID, itemID, 1.0);
		}
	}
	
	/**
	 * Gets diluted consumption map per piece (unit)
	 * @param prodID product's ID
	 * @return map with diluted materials used for one production of one unit
	 */
	private HashMap<String, Double> getDilConsPP(String prodID){
		return dilutedProdSchema.getConsIDQt(prodID);
	}
	
	/**
	 * Creates production schema per piece for given product ID in the text form
	 * 
	 * @param prodID product ID
	 * @return production schema per piece in text form
	 */
	public String[] getDilConPP(String prodID) {
		//temporary output storage
		List<String> output = new LinkedList<String>();
		//map of consumed IDs and their quantity
		HashMap<String, Double> consPPIDQt = getDilConsPP(prodID);
		//store production schema in formatted text form
		DecimalFormat formatter = new DecimalFormat("###.##");
		for(String consID : consPPIDQt.keySet()) {
			output.add(prodID + ";" + consID + ";" + formatter.format(consPPIDQt.get(consID)));
		}
		//convert temporary output list to array
		return output.toArray(new String[0]);
	}
		
		
}
