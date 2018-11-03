package io.amecodelabs.ims.view.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import io.amecodelabs.ims.models.utils.ContentValues;

public class Session implements Serializable {
	private static final long serialVersionUID = -8719475938868694268L;
	private static boolean isSession;
	private static Session session;
	
	private ContentValues user;
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
	
	public static Session createNewSessionLocal(ContentValues user, String token) {
		session = createNewSession(user, token);
		
		String homeUser = System.getProperty("user.home");
		try {
			ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(homeUser + "/t.tmp"));
			ObjectOutputStream.writeObject(session);
			ObjectOutputStream.close();
			isSession = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}
	
	public static Session createNewSession(ContentValues user, String token) {
		return session = new Session(user, token);
	}
	
	public static void deleteLocalSession() {
		String homeUser = System.getProperty("user.home");
		File fichero = new File(homeUser + "/t.tmp");
		fichero.delete();
	}
	
	private Session(ContentValues user, String token) {
		this.user = user;
		this.token = token;
	}
	
	public String getPassword() {
		return user.getValueString("password");
	}

	public String getEmail() {
		return user.getValueString("email");
	}
	
	public boolean isManagerProduct() {
		return user.getContentValues("privileges")
				.getContentValues("product")
				.getValueBoolean("create");
	}
	
	public boolean isManagerUsers() {
		return user.getContentValues("privileges")
				.getContentValues("user")
				.getValueBoolean("create");
	}
	
	public boolean isManagerProviders() {
		return user.getContentValues("privileges")
				.getContentValues("provider")
				.getValueBoolean("create");
	}
	
	public String getToken() {
		return token;
	}
	
}
