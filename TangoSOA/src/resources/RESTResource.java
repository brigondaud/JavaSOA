package resources;

import com.datastax.driver.mapping.annotations.PartitionKey;

/**
 * A resource that can be queries to the REST web service.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public abstract class RESTResource<Key> {
	
	/**
	 * The key of the resource (must be unique for insertion).
	 */
	protected Key key;
	
	/**
	 * Initiates the resource with its resourcePath
	 * 
	 * @param key The key of the resource.
	 */
	public RESTResource(Key key) {
		this.key = key;
	}
	
	/**
	 * Getter for the resource key.
	 * 
	 * @return The resource's key.
	 */
	@PartitionKey
	public Key getKey() {
		return this.key;
	}
	
	/**
	 * Getter for the path to the resource.
	 * 
	 * @return The resource's path.
	 */
	public abstract String getResource();
	
	/**
	 * Gets the full path to the resource.
	 * 
	 * @return The path to the resource.
	 */
	public String getResourcePath() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://localhost:8080/TangoSOA/rest");
		sb.append(getResource());
		return sb.toString();
	}
}
