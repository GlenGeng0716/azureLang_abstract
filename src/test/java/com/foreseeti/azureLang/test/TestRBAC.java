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

public class TestRBAC extends azureLangTest {
    private static class ReadSubscriptionModel {
        // A simple azure account login model
        
        public final Reader reader = new Reader("reader");
        public final ADUser aduser = new ADUser("aduser");
        public final Subscription subscription = new Subscription("subscription");
        
        public ReadSubscriptionModel(){
            aduser.addPrincipalReader(reader);
            reader.addScopes(subscription);
        }
    }
    
    @Test
    @DisplayName("Test Reader role of RBAC affect on the subscription.")
    public void testReaderRBAConSubscription() {
        var model = new ReadSubscriptionModel();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.aduser.assume);
        attacker.attack();
        
        model.subscription.read.assertCompromisedInstantaneously();
        model.subscription.write.assertUncompromised();
    }
    
   
    
    }


