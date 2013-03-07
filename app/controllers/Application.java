package controllers;


import java.util.Date;
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

    public static void my() {
        render();
    }
    
    public static void accounts() {
        User user = User.find("login", Security.connected()).first();   
        LogBook logBook = user.userCurrentLogBook;
        List<Account> accounts = logBook.accounting.accounts;
        render(accounts);
    }
    public static void journal() {
        
        User user = User.find("login", Security.connected()).first();
           
        LogBook logBook = user.userCurrentLogBook;
        List<LogBook> availableLogBook = LogBook.find("id not in (?)", user.userCurrentLogBook.id).fetch();
        
        render(logBook,availableLogBook);
    }
    
    public static void newLogBookEntry(Long debAccountId, Long creAccountId, String label, int amount) {
        
        User currentUser = User.find("login", Security.connected()).first();
        
        LogBookEntry newEntry = new LogBookEntry();
        
        newEntry.loggedAt = new Date();
        newEntry.debitedAccount = Account.findById(debAccountId); 
        newEntry.creditedAccount = Account.findById(creAccountId);
        newEntry.debitedAmount = amount;
        newEntry.creditedAmount = amount;
        newEntry.logBook = currentUser.userCurrentLogBook;
        newEntry.loggedBy = currentUser;
        newEntry.label = label;
        
        newEntry.save();
        
        Application.journal();
    }
    
    public static void changeLogBook(Long logBookId)
    {
        User currentUser = User.find("login", Security.connected()).first();
        currentUser.userCurrentLogBook = LogBook.findById(logBookId);
        currentUser.save();
        
        Application.journal();
    }
}