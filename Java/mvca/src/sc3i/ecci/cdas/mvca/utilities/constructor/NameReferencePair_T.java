package sc3i.ecci.cdas.mvca.utilities.constructor;


public class NameReferencePair_T {

	private String name;

	private Object object;

	public NameReferencePair_T(String nm, Object o) {
		this.name = nm;
		this.object = o;
	}

	public String getName() {
		return name;
	}

	public Object getObject() {
		return object;
	}

}
