package consumptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import productions.Producer;

class ProdSchema {
	private Map<String, HashMap<String, Double>> prodTree = new HashMap<String, HashMap<String, Double>>();
	
	void addProdTree(String prodId, String consId, Double consQt) {
		if(!prodTree.containsKey(prodId)) {
			HashMap<String, Double> consMap= new HashMap<String, Double>();
			consMap.put(consId,  consQt);
			prodTree.put(prodId, consMap);
		}else{
			HashMap<String, Double> consMap = prodTree.get(prodId);	
			if(consMap.containsKey(consId)) {
				consMap.replace(consId, consMap.get(consId) + consQt);
			}else {
				consMap.put(consId, consQt);
			}
			prodTree.replace(prodId, consMap);
		}
	}
	
	
	void addProdTree(String prodID, HashMap<String, Double> consIDQt, double multiplier) {
		for(String consID : consIDQt.keySet()) {
			addProdTree(prodID, consID, consIDQt.get(consID)*multiplier);
		}
	}
	
	void addProdTree(String prodID, HashMap<String, Double> consIDQt) {
		addProdTree(prodID, consIDQt, 1);
	}
	
	boolean isIDProduced(String itemID) {
		return prodTree.containsKey(itemID);
	}
	
	/**
	 * Gets set of used parts for ID's production
	 * 
	 * @param prodID produced part ID
	 * @return set of parts used for ID's production
	 */
	Set<String> getUsedParts(String prodID){
		if(!isIDProduced(prodID))return null;
		return prodTree.get(prodID).keySet();
	}
	
	HashMap<String, Double> getConsIDQt(String prodID){
		if(prodTree.containsKey(prodID)) {
			return prodTree.get(prodID);
		}else {
			if(prodID.equals(new String("0515403"))) {
				System.out.printf("vypisuji spotøebu 0515403");
			}
			HashMap<String, Double> emptyMap = new HashMap<String, Double>();
			emptyMap.put("no production", 0.00);
			return emptyMap;
		}
			
	}


	public ProdSchema setSchemaPerPiece(Producer prod) {
		ProdSchema psPP = new ProdSchema();
		int processedIDCount = 0;
		int totalIDCount = prod.getProducts().size();
		for(String prodID : prod.getProducts()) {
			System.out.printf("Preparing schema per piece ID %s; %d/%d\n", prodID, processedIDCount++, totalIDCount);
			psPP.addProdTree(prodID, this.getConsIDQt(prodID), 1/prod.getProducedQuantity(prodID));
		}
		return psPP;
	}
	
	/**
	 * 
	 * @return all products produced in production schema
	 */
	Set<String> getProducts(){
		return prodTree.keySet();
	}





}
