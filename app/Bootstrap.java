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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import models.*;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;


@OnApplicationStart
public class Bootstrap extends Job<Void> {

    @Override
    public void doJob() {
        // Check if the database is empty
        if (Accounting.count() == 0) 
        {

            System.out.println("Initial Setup");
            
            Fixtures.loadModels("accounts.yml");
            
            /*Accounting setupAccounting = new Accounting();
            setupAccounting.periodFrom = new Date();
            
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(setupAccounting.periodFrom);
            calendar.add(Calendar.YEAR, 1);
            
            setupAccounting.periodTo = new Date(calendar.getTime().getTime());
                            
            setupAccounting.save();
            
            
            LogBook setupLogBook = new LogBook();
            setupLogBook.accounting = Accounting.all().first();
            setupLogBook.save();
            
            User admin = new User();
            admin.isAdmin = true;
            admin.login = "admin";
            admin.password = "admin";
            admin.userCurrentLogBook = setupLogBook;
            admin.save();*/
            
        }


    }
}



