/*
 * Copyright 2019 Foreseeti AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.foreseeti.azurelang_abstract.test;

import core.Asset;
import core.Attacker;
import core.AttackStep;
import core.Defense;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCore extends azureLangTest {
    private static class AccountLoginModel {
        // A simple azure account login model
        
        public final Account azureAccount = new Account("azureAccount");
        public final Password password = new Password("password");
        public final Subscription subscription = new Subscription("subscription");
        
        public AccountLoginModel(){
            azureAccount.addPassword(password);
            azureAccount.addSubscription(subscription);
            
        }
    }
    
    @Test
    @DisplayName("Test login behaviour with a known password attached account.")
    public void testwithPassword() {
        var model = new AccountLoginModel();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.password.access);
        attacker.addAttackPoint(model.azureAccount.getaccount);
        attacker.attack();
        
        model.subscription.access.assertCompromisedInstantaneously();
    }
    
    @Test
    @DisplayName("Test login behaviour without any password attached.")
    public void testwithNoPassword() {
        var model = new AccountLoginModel();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.azureAccount.getaccount);
        
        attacker.attack();
        model.subscription.access.assertUncompromised();
    }
    
   
    
    }


