package productions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ProdVolumes {

	private Map<String, Double> prodQtMap = new HashMap<String, Double>();

	/**
	 * Stores information production and volumes
	 * @param prodId product's ID
	 * @param prodQt produced quantity
	 */
	void addProdVolumes(String prodId, Double prodQt) {
		//if no information about ID's production already, add 
		if(!prodQtMap.containsKey(prodId)) {
			prodQtMap.put(prodId, prodQt);
		}else { //already exists, add quantity
			prodQtMap.replace(prodId, prodQt + prodQtMap.get(prodId));
		}
	}
	
	/**
	 * Get ID's produced units. 
	 * @param prodId product's ID
	 * @return produced quantity (in product's units)
	 */
	double getProdQt(String prodId) {
		return prodQtMap.get(prodId);
		
	}

	/**
	 * Get all produced IDs
	 * @return all produced IDs
	 */
	public Set<String> getProducts() {
		return prodQtMap.keySet();
	}
}
