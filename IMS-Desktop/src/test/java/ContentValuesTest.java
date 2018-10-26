import static org.junit.Assert.*;

import org.junit.Test;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.JSONExportException;
import io.amecodelabs.ims.models.utils.JSONImportException;

public class ContentValuesTest {
	final String provider_json = "{\"company\":\"Acme Inc\",\"Products\":[{\"name\":\"Peras\",\"price\":1000},{\"name\":\"Mango\",\"price\":[\"$0.5\",\"$0.6\",\"$0.8\"]},{\"name\":\"Manzanas\",\"price\":[2.5,2.0,3.6]}]}";
	
	final String products_json = "[{\"name\":\"Peras\",\"price\":1000},{\"name\":\"Mango\",\"price\":[\"$0.5\",\"$0.6\",\"$0.8\"]},{\"name\":\"Manzanas\",\"price\":[2.5,2.0,3.6]}]";
	
	final String product1_json = "{\"name\":\"Peras\",\"price\":1000}";
	final String product2_json = "{\"name\":\"Mango\",\"price\":[\"$0.5\",\"$0.6\",\"$0.8\"]}";
	final String product3_json = "{\"name\":\"Manzanas\",\"price\":[2.5,2.0,3.6]}";
	
	@Test
	public void testExport() {
		String expectedResult = this.provider_json;
		
		ContentValues provider = ContentValues.newInstanceEmpy("Provider");
        provider.put("company", "Acme Inc");
        
        ContentValues product1 = ContentValues.newInstanceEmpy("Product");
        product1.put("name", "Peras");
        product1.put("price", 1000);
        
        ContentValues product2 = ContentValues.newInstanceEmpy("Product");
        product2.put("name", "Mango");
        product2.put("price", new String[]{"$0.5", "$0.6", "$0.8"});
        
        ContentValues product3 = ContentValues.newInstanceEmpy("Product");
        product3.put("name", "Manzanas");
        product3.put("price", new double[]{2.5, 2, 3.6});
        
        provider.put("Products", product1, product2, product3);
        
        try {
        	String result = provider.exportJSON();
			
			assertEquals(expectedResult, result);
		} catch (JSONExportException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testImport() {
		String expectedResult = this.provider_json;
		try {
			ContentValues provider = ContentValues.newInstanceOfImportJSON("Provider", this.provider_json);
			
			assertEquals(expectedResult, provider.exportJSON());
		} catch (JSONImportException | JSONExportException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetValues() {
		try {
			ContentValues provider = ContentValues.newInstanceOfImportJSON("Provider", this.provider_json);
			
			String company = provider.getValue("company");
			ContentValues[] products = provider.getArrayContentValues("Products");
			
			assertArrayEquals(new String[] {"Acme Inc", product1_json, product2_json, product3_json}, 
					new String[] {company, products[0].exportJSON(), products[1].exportJSON(), products[2].exportJSON()});
			
		} catch (JSONImportException | JSONExportException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testImportArrayJson() {
		try {
			ContentValues[] products = ContentValues.newInstanceOfImportArrayJSON("Products", this.products_json);
			
			assertArrayEquals(new String[] {product1_json, product2_json, product3_json}, 
					new String[] {products[0].exportJSON(), products[1].exportJSON(), products[2].exportJSON()});
		} catch (JSONImportException | JSONExportException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
