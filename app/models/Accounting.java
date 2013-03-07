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

package models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

@Entity
public class Accounting extends Model{

    public Date periodFrom;
    public Date periodTo;

    @OneToMany(mappedBy="accounting")
    public List<Account> accounts;
    
    @OneToMany(mappedBy="accounting")
    public List<LogBook> logBook;
    
    
    @Override
    public String toString()
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(this.periodFrom);
        int from = calendar.get(Calendar.YEAR);
        return "Plan comptable " + from;
    }
}
