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
        // A simple RBAC model, reader role affect on subscription
        
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
    
    private static class ReadSubscriptionModelwithAction{
        // A simple RBAC model, reader role affect on subscription using action 'SubscriptionRead'
        
        public final Reader reader = new Reader("reader");
        public final ADUser aduser = new ADUser("aduser");
        public final Subscription subscription = new Subscription("subscription");
        public final SubscriptionRead subscriptionread = new SubscriptionRead("subscriptionread");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        
        public ReadSubscriptionModelwithAction(){
            aduser.addPrincipalReader(reader);
            reader.addExecutees(subscriptionread);
            subscriptionread.addScopes(subscription);
            subscription.addResourcegroup(resourcegroup);
        }
    }
    
    @Test
    @DisplayName("Test Reader role of RBAC affect on the subscription using the action 'SubscriptionRead'")
    public void testReaderRBAConSubscriptionwithAction() {
        var model = new ReadSubscriptionModelwithAction();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.aduser.assume);
        attacker.attack();
        
        model.subscription.read.assertCompromisedInstantaneously();
        model.subscription.write.assertUncompromised();
    }
    
    @Test
    @DisplayName("Test the Reader Role can affect on the resource group scope from a higher scope, subscription.")
    public void testReaderRBAConResourceGroupfromSubscription() {
        var model = new ReadSubscriptionModelwithAction();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.aduser.assume);
        attacker.attack();
        
        model.resourcegroup.read.assertCompromisedInstantaneously();
        model.resourcegroup.write.assertUncompromised();
        
        
    }
    
    private static class ReadResourceGroupwithAction {
        public final Reader reader = new Reader("reader");
        public final ADUser aduser = new ADUser("aduser");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        
        
        public final ResourceGroupRead resourcegroupread = new ResourceGroupRead("resourcegroupread");
        public final AzureServiceResource azureserviceresource = new AzureServiceResource("azureserviceresource");
        
        public ReadResourceGroupwithAction() {
            aduser.addPrincipalReader(reader);
            reader.addExecutees(resourcegroupread);
            resourcegroupread.addScopes(resourcegroup);
            resourcegroup.addAzureResources(azureserviceresource);
        }
    }
    
    @Test
    @DisplayName("Test the Read Role of RBAC affect on the Resource Group scope. In other words, the AD user has been distributed a reader role of a resource group.")
    public void testReaderRBAConResourceGroupwithAction() {
        var model = new ReadResourceGroupwithAction();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.aduser.assume);
        attacker.attack();
        
        model.resourcegroup.read.assertCompromisedInstantaneously();
        model.resourcegroup.write.assertUncompromised();
        model.azureserviceresource.read.assertCompromisedInstantaneously();
        model.azureserviceresource.write.assertUncompromised();
    }
}


