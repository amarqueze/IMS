package io.amecodelabs.ims;

import io.amecodelabs.ims.view.base.BuildWindowDirector;
import io.amecodelabs.ims.view.context.ApplicationContext;
import io.amecodelabs.ims.view.context.Session;
import io.amecodelabs.ims.view.loginstage.LoginBuildable;
import io.amecodelabs.ims.view.mainstage.MainBuildable;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		ApplicationContext context = ApplicationContext.getInstance();
		context.addAttribute("host-services", getHostServices());
		
		BuildWindowDirector
			.getDirector()
				.prepare("login", new LoginBuildable())
				.prepare("main", new MainBuildable());
	}

	@Override
	public void start(Stage stage) throws Exception {
		if ( Session.isSession() ) 
			BuildWindowDirector.getDirector().create("main");
		else
			BuildWindowDirector.getDirector().create("login");
	}

}
