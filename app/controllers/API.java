/*
 * Copyright (c) 2013, Samuel Loup. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import play.mvc.*;
import models.*;
import values.AutoCompleteValue;

@With(Secure.class)
public class API extends Controller {

    
    //AUTOCOMPLETER
    public static void autocompleteAccount(final String term) {
        
        final List<AutoCompleteValue> response = new ArrayList<AutoCompleteValue>();
        
        String that = term.toUpperCase() + "%";
        List<Account> accounts = Account.find("UPPER(title) like ?",that).fetch();
        
        
        //Try by classification
        if(accounts.isEmpty()&&term.matches("\\d+"))
        {
            accounts = Account.find("classification like ?",that).fetch();
        }
        
        for (Account a : accounts)
        {
            response.add(new AutoCompleteValue(a.id.toString(), a.title + "(" + a.classification + ")" ));
        }
   
        renderJSON(response);
    }
}
