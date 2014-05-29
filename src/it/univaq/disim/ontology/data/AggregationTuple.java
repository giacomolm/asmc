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
public class AggregationTuple {
    
    private Concept aggregate;
    
    private List<Concept> aggregation;

    public AggregationTuple() {
        aggregation = new ArrayList();
    }

    public AggregationTuple(Concept aggregate) {
        this.aggregate = aggregate;
        aggregation = new ArrayList();
    }

    public AggregationTuple(Concept aggregate, List<Concept> aggregation) {
        this.aggregate = aggregate;
        this.aggregation = aggregation;
    }

    public Concept getAggregate() {
        return aggregate;
    }

    public void setAggregate(Concept aggregate) {
        this.aggregate = aggregate;
    }

    public List<Concept> getAggregation() {
        return aggregation;
    }

    public void setAggregation(List<Concept> aggregation) {
        this.aggregation = aggregation;
    }
    
    public void addAggregationConcept(Concept concept){
        this.aggregation.add(concept);
    }
    
    
    
    
}
