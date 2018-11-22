package repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class Repository<T> {
	
	final Class<T> typeParameterClass;
	private Cluster cluster;
	private Session session;
	private Mapper<T> mapper;
	
	public Repository(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
		this.cluster = Cluster.builder()
				.addContactPoint("127.0.0.1")
		        .build();
		this.session = cluster.connect();
		MappingManager mappingManager = new MappingManager(this.session);
		this.mapper = mappingManager.mapper(this.typeParameterClass);
	}
	
	public T getOne(T entity) {
		return this.mapper.get(entity);
	}
	
	public void save(T entity) {
		this.mapper.save(entity);
	}
}
