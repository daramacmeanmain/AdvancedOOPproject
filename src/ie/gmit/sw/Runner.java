package ie.gmit.sw;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.query.*;
import com.db4o.ta.*;

import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

import java.util.*;

import static java.lang.System.*;

public class Runner{
	
	private ObjectContainer db = null;
	//private List<Query> queries = new ArrayList<Query>();
	
	public Runner(){
		init();
		
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport()); //Real lazy. Saves all the config commented out below
		config.common().add(new TransparentPersistenceSupport()); //Lazier still. Saves all the config commented out below
		config.common().updateDepth(7); //Propagate updates
		
		//Use the XTea lib for encryption. The basic Db4O container only has a Caesar cypher... Dicas quod non est ita!
		config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));
		
		db = Db4oEmbedded.openFile(config, "q.data");
		
		showDocs();
		addDocument("1", new Document(Document.docTitle, Document.docText));
	}
	
	private void showDocs(){
		//An ObjectSet is a specialised List for storing results
		ObjectSet<Query> queries = db.query(Query.class);
		for (Query query : queries) {
			out.println("[Customer] " + query.getQNum() + "\t ***Database ObjID: " + db.ext().getID(query));

			//Removing objects from the database is as easy as adding them
			//db.delete(customer);
			db.commit();
		}
	}
	
	private void addDocument(final String qNum, Document item){
		ObjectSet<Query> result = db.query(new Predicate<Query>() {
			private static final long serialVersionUID = 777L;

			public boolean match(Query query) {
		        return query.getQNum().equals(qNum);
		    }	
		});
		
		if (result.hasNext()) {
			Query q = result.next();
			//out.println(q.size());
			
			q.add(item);
			db.store(q);
			db.commit();
			
			Iterator<Document> i = q.iterator();
			while (i.hasNext()) {
				Document doc = i.next();
				out.println("\t[Document]" + doc.getDocTitle());
			}
			//out.println(o.size());
		} else {
			out.println("[Error]");
		}
		
	}
	
	public void init(){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner( System.in );
		System.out.println("Title: ");
		String docTitleInput = scanner.nextLine();
		Document.docTitle = docTitleInput;
		
		System.out.println("Content: ");
		String docTextInput = scanner.nextLine();
		Document.docText = docTextInput;
	}
	
	public static void main(String[] args) {
		new Runner();
	}
}
