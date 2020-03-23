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
    @DisplayName("Test the Reader Role of RBAC affect on the Resource Group scope. In other words, the AD user has been distributed a reader role of a resource group.")
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
    
    private static class ContributorRoleModel{
        //Below defines the scopes in the model
        public final Contributor contributor = new Contributor("contributor");
        public final ADUser aduser = new ADUser("aduser");
        public final Subscription subscription = new Subscription("subscription");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        public final AzureServiceResource azureserviceresource = new AzureServiceResource("azureserviceresource");
        //Actions in this model are defined below
        public final SubscriptionRead subscriptionread = new SubscriptionRead("subscriptionread");
        public final SubscriptionWrite subscriptionwrite = new SubscriptionWrite("subscriptionwrite");
        public final CancelSubscription cancelsubscription  = new CancelSubscription("cancelsubscription");
        
        public final ResourceGroupRead resourcegroupread = new ResourceGroupRead("resourcegroupread");
        public final ResourceGroupWrite resourcegroupwrite = new ResourceGroupWrite("resourcegroupwrite");
        public final ResourceGroupDelete resourcegroupdelete = new ResourceGroupDelete("resourcegroupdelete");
        
        public final AzureServiceRead azureserviceread = new AzureServiceRead("azureserviceread");
        public final AzureServiceWrite azureservicewrite = new AzureServiceWrite("azureservicewrite");
        public final AzureServiceDelete azureservicedelete = new AzureServiceDelete("azureservicedelete");
        
        public ContributorRoleModel() {
                   aduser.addPrincipalContributor(contributor);
                   contributor.addExecutees(subscriptionread);
                   contributor.addExecutees(subscriptionwrite);
                   contributor.addExecutees(cancelsubscription);
                   subscriptionread.addScopes(subscription);
                   subscriptionwrite.addScopes(subscription);
                   cancelsubscription.addScopes(subscription);
                   subscription.addResourcegroup(resourcegroup);
                   resourcegroup.addAzureResources(azureserviceresource);
               }

    }
    
    @Test
           @DisplayName("Test contributor role model")
           public void testContributorRBAC(){
               var model = new ContributorRoleModel();
               
               Attacker attacker = new Attacker();
               attacker.addAttackPoint(model.aduser.assume);
               attacker.attack();
               
               model.subscription.write.assertCompromisedInstantaneously();
               model.resourcegroup.write.assertCompromisedInstantaneously();
               model.azureserviceresource.write.assertCompromisedInstantaneously();
               model.subscription.read.assertCompromisedInstantaneously();
               model.resourcegroup.read.assertCompromisedInstantaneously();
               model.azureserviceresource.read.assertCompromisedInstantaneously();
               model.subscription.cancel.assertCompromisedInstantaneously();
               model.resourcegroup.delete.assertCompromisedInstantaneously();
               model.azureserviceresource.delete.assertCompromisedInstantaneously();
               //A contributor can not granct access to either an AD user or an existed AD user.
               model.aduser.createLoginProfile.assertUncompromised();
               model.aduser.LoginProfileUpdated.assertUncompromised();
               model.aduser.LoginProfileDeleted.assertUncompromised();

           }
    
    private static class OwnerRoleModel{
        //Below defines the scopes in the model
        public final Owner owner = new Owner("owner");
        public final ADUser aduser1 = new ADUser("aduser1");
        public final ADUser aduser2 = new ADUser("aduser2");
        public final Subscription subscription = new Subscription("subscription");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        public final AzureServiceResource azureserviceresource = new AzureServiceResource("azureserviceresource");
        //Actions in this model are defined below
        public final SubscriptionRead subscriptionread = new SubscriptionRead("subscriptionread");
        public final SubscriptionWrite subscriptionwrite = new SubscriptionWrite("subscriptionwrite");
        public final CancelSubscription cancelsubscription  = new CancelSubscription("cancelsubscription");
        
        public final ResourceGroupRead resourcegroupread = new ResourceGroupRead("resourcegroupread");
        public final ResourceGroupWrite resourcegroupwrite = new ResourceGroupWrite("resourcegroupwrite");
        public final ResourceGroupDelete resourcegroupdelete = new ResourceGroupDelete("resourcegroupdelete");
        
        public final AzureServiceRead azureserviceread = new AzureServiceRead("azureserviceread");
        public final AzureServiceWrite azureservicewrite = new AzureServiceWrite("azureservicewrite");
        public final AzureServiceDelete azureservicedelete = new AzureServiceDelete("azureservicedelete");
        
        public final AddLoginProfile addloginprofile = new AddLoginProfile("addloginprofile");
        public final UpdateLoginProfile updateloginprofile = new UpdateLoginProfile("updateloginprofile");
        public final DeleteLoginProfile deleteloginprofile = new DeleteLoginProfile("deleteloginprofile");
        
        public OwnerRoleModel() {
                   aduser1.addPrincipalOwner(owner);
                   owner.addExecutees(subscriptionread);
                   owner.addExecutees(subscriptionwrite);
                   owner.addExecutees(cancelsubscription);
                   subscriptionread.addScopes(subscription);
                   subscriptionwrite.addScopes(subscription);
                   cancelsubscription.addScopes(subscription);
                   subscription.addResourcegroup(resourcegroup);
                   resourcegroup.addAzureResources(azureserviceresource);
            
                   owner.addExecutees(addloginprofile);
                   owner.addExecutees(updateloginprofile);
                   owner.addExecutees(deleteloginprofile);
                   addloginprofile.addResources(aduser2);
                   updateloginprofile.addResources(aduser2);
                   deleteloginprofile.addResources(aduser2);
            
               }

    }
    
    @Test
           @DisplayName("Test owner role model")
           public void testOwnerRBAC(){
               var model = new OwnerRoleModel();
               
               Attacker attacker = new Attacker();
               attacker.addAttackPoint(model.aduser1.assume);
               attacker.attack();
               
               
               model.subscription.write.assertCompromisedInstantaneously();
               model.resourcegroup.write.assertCompromisedInstantaneously();
               model.azureserviceresource.write.assertCompromisedInstantaneously();
               model.subscription.read.assertCompromisedInstantaneously();
               model.resourcegroup.read.assertCompromisedInstantaneously();
               model.azureserviceresource.read.assertCompromisedInstantaneously();
               model.subscription.cancel.assertCompromisedInstantaneously();
               model.resourcegroup.delete.assertCompromisedInstantaneously();
               model.azureserviceresource.delete.assertCompromisedInstantaneously();
               //An contributor can granct access to either an AD user or an existed AD user.
               model.owner.owneraccess.assertCompromisedInstantaneously();
               model.aduser2.attemptAddLoginProfile.assertCompromisedInstantaneously();
          //     model.aduser2.LoginProfileUpdated.assertCompromisedInstantaneously();
          //xs     model.aduser2.LoginProfileDeleted.assertCompromisedInstantaneously();
           }
}
