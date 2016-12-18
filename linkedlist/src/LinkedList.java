class Node {
	
	public String _id;
	public Node _next;
	
	public Node(String id) {
		this(id, null);
	}
	
	public Node(String id, Node n) {
		_id = id;
		_next = n;
	}
}


public class LinkedList {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Node top = new Node("top");
		Node next = new Node("0");
		
		top._next = next;
		for (int i=1; i<=9 ; i++) {
			Node current = add(Integer.toString(i), next);
			next = current;
//			Node current = new Node(Integer.toString(i));
//			next._next = current;
//			next = next._next;
		}
		
		for (Node p = top._next; p != null; p = p._next) {
			System.out.println("Node: " + p._id);
		}
	}
	
	static Node add(String s, Node previous) {
		
		Node current = new Node(s);
		previous._next = current;
		previous = previous._next;
		return previous;
	}
}
