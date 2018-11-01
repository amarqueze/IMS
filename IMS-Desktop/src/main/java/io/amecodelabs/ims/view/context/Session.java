package io.amecodelabs.ims.view.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Session implements Serializable {
	private static final long serialVersionUID = -8719475938868694268L;
	private static boolean isSession;
	private static Session session;
	
	private String email;
	private String password;
	private String token;
	
	static {
		String homeUser = System.getProperty("user.home");
		ObjectInputStream objectInputStream = null;
	   try {
			objectInputStream = new ObjectInputStream(new FileInputStream(homeUser + "/t.tmp"));
			session = (Session) objectInputStream.readObject();
			objectInputStream.close();
			isSession = true;
	   } catch (IOException | ClassNotFoundException e) {
		   isSession = false;
	   } 
	}
	
	public static boolean isSession() {
		return isSession;
	}
	
	public static Session getSession() {
		return session;
	}
	
	public static Session createNewSessionLocal(String email, String password, String token) {
		session = new Session(email, password, token);
		
		String homeUser = System.getProperty("user.home");
		try {
			ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(homeUser + "/t.tmp"));
			ObjectOutputStream.writeObject(session);
			ObjectOutputStream.close();
			isSession = true;
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return session;
	}
	
	public static Session createNewSession(String email, String password, String token) {
		return session = new Session(email, password, token);
	}
	
	public static void deleteLocalSession() {
		String homeUser = System.getProperty("user.home");
		File fichero = new File(homeUser + "/t.tmp");
		fichero.delete();
	}
	
	private Session(String email, String password, String token) {
		this.email = email;
		this.password = password;
		this.token = token;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	public String getToken() {
		return token;
	}
	
}
