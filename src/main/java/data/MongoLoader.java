package data;

import java.util.List;

import eu.socialsensor.framework.client.dao.ItemDAO;
import eu.socialsensor.framework.client.dao.impl.ItemDAOImpl;
import eu.socialsensor.framework.common.domain.Item;

public class MongoLoader {

	public static void main(String[] args) {
		String host = args[0];
		String dbName = args[1];
		String collection = args[2];
	
		MongoLoader mongoLoader = new MongoLoader(host, dbName, collection);
		mongoLoader.printTest();
		
	}
	
	protected ItemDAO itemDao = null;
	
	public MongoLoader(String host, String dbName, String collection) {
		itemDao = new ItemDAOImpl(host, dbName, collection);
	}
	
	public void printTest(){
		List<Item> items = itemDao.getLatestItems(100);
		for (int i = 0; i < items.size(); i++){
			System.out.println(items.get(i).getId() + ": " + items.get(i).getTitle());
		}
	}
}
