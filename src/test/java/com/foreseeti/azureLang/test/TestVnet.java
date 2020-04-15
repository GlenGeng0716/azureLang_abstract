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

public class TestVnet extends azureLangTest{
    
private static class VnetWithinVnetModel {
    public final Vnet vnet = new Vnet("vnet");
    //source subnet part
    
    public final Subnet srcSubnet = new Subnet("srcSubnet");
    public final RouteTable srcRouteTable = new RouteTable("srcRouteTable");
    public final AddressSpace srcIPAddresses = new PrivateIPAddresses("srcIPAddresses");
    public final Instance srcInstance = new Instance("srcInstance");
    public final NetworkInterface srcNetworkInterface = new NetworkInterface("srcNetworkInterface");
    public final PrivateIPAddresses srcInstanceIPAddresses = new PrivateIPAddresses("srcInstanceIPAddresses");
    public final NetworkSecurityGroup srcNSG = new NetworkSecurityGroup("srcNSG");
    public final OutboundSecurityGroupRule srcNSGRule = new OutboundSecurityGroupRule("srcNSGRule");
    public final Route srcToDestRoute = new Route("srcToDestRoute");
    
    //destination subnet part
    public final Subnet destSubnet = new Subnet("destSubnet");
    
    public final AddressSpace destIPAddresses = new PrivateIPAddresses("destIPAddresses");
    public final Instance destInstance = new Instance("destInstance");
    public final PrivateIPAddresses destInstanceIPAddresses = new PrivateIPAddresses("destInstanceIPAddresses");
    public final NetworkInterface destNetworkInterface = new NetworkInterface("destNetworkInterface");
    public final NetworkSecurityGroup destNSG = new NetworkSecurityGroup("destNSG");
    public final InboundSecurityGroupRule destNSGRule = new InboundSecurityGroupRule("destInboundNSGRule");
    
    //3 most common types of service to connect to a virtual machine: SSH (Linux, TCP port 22), RDP (Windows, TCP port 389), HTTPS (TCP port 443)
    public final Application sshService = new Application("sshService");
    public final PortRange port22 = new PortRange("port22");
    public final Application rdpService = new Application("RDPService");
    public final PortRange port3389 = new PortRange("port3389");
    public final Application httpsService = new Application("httpsService");
    public final PortRange port443 = new PortRange("port443");
    
    //Model: A virtual network contains 2 subnets -- source and destination. The instance at the destiantion runs
    //services on 2 different ports. Access to the destination is controlled by the NSG.
    public VnetWithinVnetModel(boolean VMtype, boolean https, boolean featureNSGs, int srcNSGPort, int destNSGPort)
    {
        vnet.addSubnets(srcSubnet);
        vnet.addSubnets(destSubnet);
        //Associations in source subnet
        srcSubnet.addAddressSpaces(srcIPAddresses);
        srcSubnet.addNetworkInterfaces(srcNetworkInterface);
        srcIPAddresses.addSubAddressSpaces(srcInstanceIPAddresses);
        srcInstanceIPAddresses.addNetworkInterface(srcNetworkInterface);
        srcInstance.addNetworkInterfaces(srcNetworkInterface);
        
        srcSubnet.addRouteTable(srcRouteTable);
        srcRouteTable.addRoutes(srcToDestRoute);
        srcToDestRoute.addLocalSubnets(srcSubnet);
        srcToDestRoute.addLocalSubnets(destSubnet);
        
        //Associations in destination subnet
        destSubnet.addAddressSpaces(destIPAddresses);
        destSubnet.addNetworkInterfaces(destNetworkInterface);
        destIPAddresses.addSubAddressSpaces(destInstanceIPAddresses);
        destInstanceIPAddresses.addNetworkInterface(destNetworkInterface);
        destInstance.addNetworkInterfaces(destNetworkInterface);
        
        if (VMtype == true) {
            sshService.addPortRanges(port22);
            sshService.addNetworkInterfaces(destNetworkInterface);
        }
        else if (VMtype == false) {
            rdpService.addPortRanges(port3389);
            rdpService.addNetworkInterfaces(destNetworkInterface);
        }
        
        if (https == true) {
            httpsService.addPortRanges(port443);
            httpsService.addNetworkInterfaces(destNetworkInterface);
        }
        
        if (featureNSGs == true) {
            //source NSG
            srcNetworkInterface.addNetworkSecurityGroups(srcNSG);
            srcNSG.addContainedRules(srcNSGRule);
            srcNSGRule.addAddressSpace(srcIPAddresses);
            if (srcNSGPort == 22) {
                srcNSGRule.addPortRange(port22);
            }
            else if (srcNSGPort == 3389) {
                srcNSGRule.addPortRange(port3389);
            }
            else if (srcNSGPort == 443) {
                srcNSGRule.addPortRange(port443);
                
            }
            srcNSGRule.addDestIPAddresses(destIPAddresses);
            
            //destination NSG
            destNetworkInterface.addNetworkSecurityGroups(destNSG);
            destNSG.addContainedRules(destNSGRule);
            destNSGRule.addAddressSpace(destIPAddresses);
            if (destNSGPort == 22) {
                destNSGRule.addPortRange(port22);
            }
            else if (srcNSGPort == 3389) {
                destNSGRule.addPortRange(port3389);
            }
            else {
                destNSGRule.addPortRange(port443);
            }
            destNSGRule.addSourceIPAddresses(srcIPAddresses);
        }
    }
        
        private void assertApplicationReached(Application application, boolean nsgConnect, boolean routeConnect)
        {
           
            if (nsgConnect) {
                application.debugNSGConnect.assertCompromisedInstantaneously();
            }
            else {
                application.debugNSGConnect.assertUncompromised();
            }
            
            if (routeConnect) {
                application.debugRouteConnect.assertCompromisedInstantaneously();
            }
            else {
                application.debugRouteConnect.assertUncompromised();
            }
            if (nsgConnect == true && routeConnect == true)
            {
                application.networkRequestConnect.assertCompromisedInstantaneously();
            }
            else {
                application.networkRequestConnect.assertUncompromised();
            }
        }
        
        public void assertSSHReached(boolean nsgConnect, boolean routeConnect) {
            assertApplicationReached(sshService, nsgConnect, routeConnect);
        }
        
        public void assertRDPReached(boolean nsgConnect, boolean routeConnect) {
            assertApplicationReached(rdpService, nsgConnect, routeConnect);
        }
        
        public void assertHTTPSReached(boolean nsgConnect, boolean routeConnect) {
            assertApplicationReached(httpsService, nsgConnect, routeConnect);
        }
         
    }
    //Below are the test cases
    @Test
    @DisplayName("Test SSH service connect a Linux VM without Network Security group.")
    public void testSSHNoNetworkSecurityGroup()
    {
        var model = new VnetWithinVnetModel(true, false, false, 22, 22);
        
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        model.srcNetworkInterface.transmit.assertCompromisedInstantaneously();
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(false, true);
        model.assertHTTPSReached(false, false);
        model.assertRDPReached(false, false);
        
    }
    
    @Test
    @DisplayName("Test RDP service connect to a Windows VM without Network Security group.")
    public void testRDPNoNetworkSecurityGroup()
    {
        var model = new VnetWithinVnetModel(false, false, false, 3389, 3389);
        
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        model.srcNetworkInterface.transmit.assertCompromisedInstantaneously();
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(false, false);
        model.assertRDPReached(false, true);
        model.assertHTTPSReached(false, false);
    }
    
    @Test
    @DisplayName("Test SSH service connect to a Linux VM with Network Security group.")
    public void testSSHWithNetworkSecurityGroup()
    {
        var model = new VnetWithinVnetModel(true, false, true, 22, 22);
        
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        model.srcNetworkInterface.transmit.assertCompromisedInstantaneously();
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(true, true);
        model.assertHTTPSReached(false, false);
        model.assertRDPReached(false, false);
        
    }
    
    @Test
    @DisplayName("Test RDP service connect to a Windows VM with Network Security group.")
    public void testRDPWithNetworkSecurityGroup()
    {
        var model = new VnetWithinVnetModel(false, false, true, 3389, 3389);
        
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        model.srcNetworkInterface.transmit.assertCompromisedInstantaneously();
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(false, false);
        model.assertHTTPSReached(false, false);
        model.assertRDPReached(true, true);
    }

    @Test
    @DisplayName("Test Network Security Group inbound block.")
    public void testNSGInboundBlock()
    {
        var model = new VnetWithinVnetModel(true, true, true, 22, 443);
        // The port number of inbound and outbound network work security rule needs to be identical.
        //Otherwise the services will not be accessed enven if the route reaches.
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(false, true);
        model.assertHTTPSReached(false, true);
    }
    
    @Test
    @DisplayName("Test Network HTTPS services.")
    public void testHTTPSService()
    {
        var model = new VnetWithinVnetModel(true, true, true, 443, 443);
        
        var attacker = new Attacker();
        attacker.addAttackPoint(model.srcInstance.highPrivilegeAccess);
        attacker.attack();
        
        model.srcNetworkInterface.transmitRequest.assertCompromisedInstantaneously();
        model.assertSSHReached(false, true);
        model.assertHTTPSReached(true, true);
    }
}


