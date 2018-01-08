package ie.gmit.sw;

import java.util.Scanner;

public class Document {
	private String docNum;
	public static String docTitle;
	public static String docText;
	
	
	
	public Document(String docTitle, String docText){
		super();
		this.docTitle = docTitle;
		this.docText = docText;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		docTitle = docTitle;
	}
}


