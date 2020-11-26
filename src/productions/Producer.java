package productions;

import java.util.LinkedList;
import java.util.Set;

import fileReader.ReadFile;

/**
 * Processes data concerning productions
 * @author Petr Potociar
 *
 */
public class Producer {
	
	//produced volumes
	private ProdVolumes prodVol;
	
	/**
	 * Create producer based on production database in the file
	 * @param fileName database with IDs and produced units.
	 */
	public Producer(String fileName) {
		prodVol = new ProdVolumes();
		processInput(fileName);
	}
	
	/**
	 * The procedure loads data from file and stores it
	 * 
	 * @param fileName name of the file with data regarding consumption
	 *		<p>File's layout: Data are line-separated. 
	 *		Line layout: "produced(ID);production(quantity)". </p>
	 * 
	 * @return count of loaded lines
	 */
	private int processInput(String fileName) {
		LinkedList<String> input = ReadFile.getFileAsList(fileName);
		for(String s : input) {
			int sepPos1 = s.indexOf(";");
			String prodId = s.substring(0,sepPos1);
			System.out.printf("producer loading production %s\n", prodId);
			Double prodQt = Double.parseDouble(s.substring(sepPos1+1));
			addProduction(prodId, prodQt);
		}
		return 1;
	}
	
	/**
	 * Get produced quantity in ID's units.
	 * @param prodId produced ID
	 * @return produced quantity in ID's units
	 */
	public double getProducedQuantity(String prodId) {
		return prodVol.getProdQt(prodId);
	}
	
	/**
	 * Add produced quantity of product
	 * @param prodId Product's ID
	 * @param prodQt produced quantity
	 */
	private void addProduction(String prodId, Double prodQt) {
		prodVol.addProdVolumes(prodId, prodQt);
	}
	
	/**
	 * Get all produced items
	 * @return Produced IDs
	 */
	public Set<String> getProducts(){
		return prodVol.getProducts();
	}
}
