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

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

@Entity
public class Account extends Model{
    
    
    public String title;
    public String classification;
    
    @ManyToOne
    public Accounting accounting;
    
    @OneToMany(mappedBy="creditedAccount")
    public List<LogBookEntry> creditLogBookEntries;
    
    @OneToMany(mappedBy="debitedAccount")
    public List<LogBookEntry> debitLogBookEntries;
    
    @Override
    public String toString()
    {
        return this.title + " (" + this.classification + ")";
    }
}
