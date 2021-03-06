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

import models.*;

public class Security extends Secure.Security {

    static void onDisconnected() {
        Application.index();
    }

    static boolean authenticate(String username, String password) {
        
        if(User.find("login = ? and password = ?",username,password).first()!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
        
        
    }
    
    static boolean check(String profile) {
        if ("admin".equals(profile)) {
            return User.find("login", connected()).<User>first().isAdmin;
        }
        return false;
    }

}