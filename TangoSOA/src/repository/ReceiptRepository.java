package repository;

import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import resources.Receipt;

public class ReceiptRepository {
	
	private Cluster cluster;
	private Session session;
	private Mapper<Receipt> mapper;
	
	public ReceiptRepository() {
		String dbAddr = System.getenv("dbAddr") == null ? "127.0.0.1" : System.getenv("dbAddr");
		this.cluster = Cluster.builder()
				.addContactPoint(dbAddr)
		        .build();
		this.session = cluster.connect();
		this.createKeySpace("ks", "SimpleStrategy", 1);
		this.createProductType();
		this.createReceiptsTable();
		MappingManager mappingManager = new MappingManager(this.session);
		this.mapper = mappingManager.mapper(Receipt.class);
	}
	
	/**
	 * Get a Receipt given it's ID
	 * 
	 * @param id The ID of the receipt to retrieve
	 * @return The receipt fetched, or null if it doesn't exist
	 */
	public Receipt getOne(int id) {
		return this.mapper.get(id);
	}
	
	/**
	 * Get all receipts in the database
	 * 
	 * @return A list containing all the mapped Receipt objects
	 */
	public List<Receipt> getAll() {
		ResultSet results = this.session.execute("SELECT * FROM ks.receipts");
		Result<Receipt> receipts = this.mapper.map(results);
		return receipts.all();
	}
	
	/**
	 * Save a Receipt object in the database
	 * 
	 * @param entity The Receipt to save
	 */
	public void save(Receipt entity) {
		this.mapper.save(entity);
	}
	
	public void deleteOne(int id) {
		this.mapper.delete(id);
	}
	
	public void deleteAll() {
		this.session.execute("TRUNCATE ks.receipts");
	}
	
	private void createKeySpace(String keyspaceName, String replicationStrategy, int replicationFactor) {
		StringBuilder sb =
				new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
				      .append(keyspaceName).append(" WITH replication = {")
				      .append("'class':'").append(replicationStrategy)
				      .append("','replication_factor':").append(replicationFactor)
				      .append("};");
				         
		String query = sb.toString();
		this.session.execute(query);
	}
	
	/**
	 * Creates the User Defined Type "product" in the database
	 */
	private void createProductType() {
		session.execute("CREATE TYPE IF NOT EXISTS ks.product(\n" +
		        "        name varchar,\n" +
				"        price int,\n" +
				"        quantity int\n" +
		        "    )");
	}
	
	/**
	 * Creates the Receipt Table in the database
	 */
	private void createReceiptsTable() {
		session.execute("CREATE TABLE IF NOT EXISTS ks.receipts(\n" +
		        "        id int,\n" +
				"        products frozen<list<product>>,\n" +
		        "        PRIMARY KEY (id)\n" +
		        "    )");
	}
}
