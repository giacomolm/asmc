/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package it.univaq.disim.ips.data.state;

import com.sun.jmx.snmp.SnmpString;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giacomo
 */
public class BinaryState extends State{
    
    List<State> states;
    public BinaryState(State s1, State s2) {
        states = new ArrayList();
        states.add(s1);
        states.add(s2);
        setName(s1.getName()+","+s2.getName());
    }
    
    
    
    
    public static List<State> cartesianProduct(List<State> l1, List<State> l2){
        List<State> result = new ArrayList();
        int i,j;
        
        for(i=0; i<l1.size(); i++){
            for(j=0; j<l2.size(); j++){
                result.add(new BinaryState(l1.get(i), l2.get(j)));
            }
        }
        
        return result;
    }

    @Override
    public String toString() {
        return "("+this.states.get(0).getName()+","+this.states.get(1).getName()+")";
    }
    
    
}
