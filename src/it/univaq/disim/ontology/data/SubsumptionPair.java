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

package it.univaq.disim.ontology.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giacomo
 */
public class SubsumptionPair {
    
    private List<Concept> pair;
  
    /**
    * Create a sumbsumption pair, regarding the position of the parameters.
    * @param c1
    * @param c2 
    */
    public SubsumptionPair(Concept c1, Concept c2){
        pair = new ArrayList();
        pair.add(c1);
        pair.add(c2);
    }
    
    public List<Concept> getPair() {
        return pair;
    }

    public void setPair(Concept c1, Concept c2) {
        pair.set(0, c1);
        pair.set(1, c2);
    }
}
