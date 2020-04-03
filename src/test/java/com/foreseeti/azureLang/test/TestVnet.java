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
    public final Vnet net = new Vnet("vnet");
    public final Subnet srcSubnet = new Subnet("srcSubnet");
    public final RouteTable srcRouteTable = new RouteTable("srcRouteTable");
    public final AddressSpace srcAddressSpace = new PrivateIPAddresses("srcAddressSpace");
    
}
    
}


