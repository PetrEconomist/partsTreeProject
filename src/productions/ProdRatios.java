package productions;

import java.util.HashMap;
import java.util.Set;

public class ProdRatios {
	//production purchase ratio of produced item
	// ratio: production/(production+purchases)
	//ratio 0 = only purchased material
	//ratio 0.25 = 25% of item stock increase was from production
	//ratio 1 = only produced item
	private HashMap<String, Double> prodRatios;
	
		
	/**
	 * Constructs production rated based on producer and data about purchases 
	 * @param prod
	 * @param purchasesFile
	 */
	public ProdRatios(Producer prod, String purchasesFile){
		prodRatios = new HashMap<String, Double>();
		setProdRatios(prod, new Purchaser(purchasesFile));
	}
	
	/**
	 * Sets production rations
	 * @param prod
	 * @param purch
	 */
	private void setProdRatios(Producer prod, Purchaser purch) {
		
		// ratio: production/(production+purchases)
		Set<String> purchIDs = purch.getPurchasedIDs();
		//counters for status console
		int counter = 1;
		int totalCount = purchIDs.size();
		
		for(String id : purchIDs) {
			System.out.printf("calculation production ratios item %d/%d\n", counter++, totalCount);
			double prodQt = prod.getProducedQuantity(id);
			double ratio = prodQt / (prodQt + purch.getPurQuantity(id)); 
			prodRatios.put(id, ratio);
		}
	}
	
	/**
	 * 
	 * @param prodID produced item's ID
	 * @return production ratio (production / (production + purchases))
	 */
	public double getProdRatio(String prodID) {
		return prodRatios.get(prodID);
	}
	
}
