package ie.gmit.sw;

import java.util.*;

public class Query {
	
	private String qNum;
	private List<Document> documents = new ArrayList<Document>();
	
	public Query(String qNum){
		super();
		this.qNum = qNum;
		
	}
	
	public String getQNum() {
		return qNum;
	}

	public void setQNum(String qNum) {
		this.qNum = qNum;
	}
	
	public boolean add(Document item) {
		return documents.add(item);
	}
	
	public Iterator<Document> iterator() {
		return documents.iterator();
	}

}
