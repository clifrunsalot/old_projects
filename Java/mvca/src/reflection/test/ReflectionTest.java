package reflection.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sc3i.ecci.cdas.mvca.utilities.constructor.Constructor_T;
import sc3i.ecci.cdas.mvca.utilities.constructor.NameReferencePair_T;

public class ReflectionTest {

	public ReflectionTest(String file) {
		List listOfObjs = this.read(file);
		List pairs = this.process(listOfObjs);
		System.out.println("There are: " + pairs.size() + " in the list");
		this.printNames(pairs);
	}
	
	public void printNames(List listOfObjs){
		Iterator iter = listOfObjs.iterator();
		
		System.out.println("Printing names in list");
		
		while(iter.hasNext()){
			NameReferencePair_T pair = (NameReferencePair_T)iter.next();
			String id = (String)pair.getName();
			System.out.println(id);
		}
	}

	public static void main(String[] args) {
		String fileName = "D:\\Documents and Settings\\clif\\workspace\\mvca\\src\\reflection\\test\\DTOs.txt";
		ReflectionTest test = new ReflectionTest(fileName);

	}

	public List process(List list) {
		List<NameReferencePair_T> dtoNameRef = new LinkedList<NameReferencePair_T>();
		Constructor_T ctr = new Constructor_T();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			String clsName = (String) iter.next();
			Object obj = ctr.createInstance(clsName);
			NameReferencePair_T pair = new NameReferencePair_T(obj.getClass()
					.getName(), obj);
			dtoNameRef.add(pair);
		}
		return dtoNameRef;
	}

	public List read(String file) {
		List<String> objList = new LinkedList<String>();
		File f = new File(file);
		try {
			BufferedReader b = new BufferedReader(new FileReader(f));
			String s;
			while ((s = b.readLine()) != null) {
				objList.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objList;
	}

}
