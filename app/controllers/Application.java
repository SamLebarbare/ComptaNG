package controllers;


import java.util.List;
import play.mvc.*;
import models.*;

@With(Secure.class)
public class Application extends Controller {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("login", Security.connected()).first();
            renderArgs.put("user", user.login);
        }
    }
    
    
    public static void index() {
        render();
    }

    public static void journal() {
        
        User user = User.find("login", Security.connected()).first();
           
        LogBook logBook = user.userCurrentLogBook;
        
        
        render(logBook);
    }
}