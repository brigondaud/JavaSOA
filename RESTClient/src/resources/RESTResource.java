package resources;

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
	public Key getKey() {
		return this.key;
	}
	
	/**
	 * Getter for the path to the resource.
	 * 
	 * @return The resource's path.
	 */
	public abstract String getResourcePath();
}
