package resources;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Transient;

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

    public RESTResource() {}
	
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
	 * Get the relative path to the resource.
	 * 
	 * @return The relative path to the resource.
	 */
	@Transient
	public abstract String path();
}
