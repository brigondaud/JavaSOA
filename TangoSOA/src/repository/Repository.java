package repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import resources.RESTResource;

public class Repository<T> {
	
	final Class<T> typeParameterClass;
	private Cluster cluster;
	private Session session;
	private Mapper<T> mapper;
	
	public Repository(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
		String dbAddr = System.getenv("dbAddr") == null ? "127.0.0.1" : System.getenv("dbAddr");
		this.cluster = Cluster.builder()
				.addContactPoint(dbAddr)
		        .build();
		this.session = cluster.connect();
		this.createKeySpace("ks", "SimpleStrategy", 1);
		this.createTables();
		MappingManager mappingManager = new MappingManager(this.session);
		this.mapper = mappingManager.mapper(this.typeParameterClass);
	}
	
	public T getOne(RESTResource entity) {
		return this.mapper.get(entity.getKey());
	}
	
	public void save(T entity) {
		this.mapper.save(entity);
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
	
	private void createTables() {
		session.execute("CREATE TABLE IF NOT EXISTS ks.receipts(\n" +
		        "        id int,\n" +
				"        products int,\n" +
		        "        PRIMARY KEY (id)\n" +
		        "    )");
		session.execute("CREATE TABLE IF NOT EXISTS ks.products(\n" +
		        "        name varchar,\n" +
				"        price int,\n" +
		        "        PRIMARY KEY (name)\n" +
		        "    )");
	}
}
