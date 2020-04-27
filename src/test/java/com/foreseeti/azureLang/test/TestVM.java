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

public class TestVM extends azureLangTest {
    private static class VMOwnerModel1 {
        
        //Define a AD user has the owner role of a virutal machine
        public final VirtualMachine vm = new LinuxVM("vm");
        public final ADUser user = new ADUser("user");
        public final Owner vmowner = new Owner("vmowner");
        public final Subscription subscription = new Subscription("subscription");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        
        public final RunVirtualMachine runVM = new RunVirtualMachine("runvm");
        
        public VMOwnerModel1() {
            user.addPrincipalOwner(vmowner);
            vmowner.addExecutees(runVM);
            runVM.addResources(vm);
        }
    }
    
    @Test
        @DisplayName("Test an owner role directly have effect on virtual machine.")
    public void testVMOwnerModel1() {
        var model = new VMOwnerModel1();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.user.assume);
        attacker.attack();
        
        model.subscription.write.assertUncompromised();
        model.subscription.read.assertUncompromised();
        model.subscription.cancel.assertUncompromised();
        model.vm.connect.assertCompromisedInstantaneously();
    }
    
    private static class VMOwnerModel2 {
        public final VirtualMachine vm = new LinuxVM("vm");
        public final ADUser user = new ADUser("user");
        public final Owner subscriptionowner = new Owner("subscriptionowner");
        public final Subscription subscription = new Subscription("subscription");
        public final ResourceGroup vmresourcegroup = new ResourceGroup("vmresourcegroup");
        
        public VMOwnerModel2() {
            user.addPrincipalOwner(subscriptionowner);
            subscriptionowner.addScopes(subscription);
            subscription.addResourcegroup(vmresourcegroup);
            vmresourcegroup.addResource(vm);
        }
        
    }
    
    @Test
    @DisplayName("Susbcription owner can connect to a virtual machine.")
    public void testVMOwnerModel2() {
        var model = new VMOwnerModel2();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.user.assume);
        attacker.attack();
        
        model.subscriptionowner.owneraccess.assertCompromisedInstantaneously();
        model.subscription.access.assertCompromisedInstantaneously();
        model.vmresourcegroup.access.assertCompromisedInstantaneously();
        model.vm.connect.assertCompromisedInstantaneously();
    }
    
    private static class VMReaderModel1 {
        
        //Define a AD user has the owner role of a virutal machine
        public final VirtualMachine vm = new LinuxVM("vm");
        public final ADUser user = new ADUser("user");
        public final Reader vmreader = new Reader("vmreader");
        public final Subscription subscription = new Subscription("subscription");
        public final ResourceGroup resourcegroup = new ResourceGroup("resourcegroup");
        
        public final RunVirtualMachine runVM = new RunVirtualMachine("runvm");
        public final TerminateVirtualMachine terminatevm = new TerminateVirtualMachine("terminatevm");
        
        public VMReaderModel1() {
            user.addPrincipalReader(vmreader);
            vmreader.addExecutees(runVM);
            vmreader.addExecutees(terminatevm);
            runVM.addResources(vm);
        }
    }
    
    @Test
    @DisplayName("VM reader cannot terminate a VM.")
    public void testVMReaderModel1() {
        var model = new VMReaderModel1();
        
        Attacker attacker = new Attacker();
        attacker.addAttackPoint(model.user.assume);
        attacker.attack();
        
        model.vm.connect.assertUncompromised();
        model.vm.terminate.assertUncompromised();
    }
}


