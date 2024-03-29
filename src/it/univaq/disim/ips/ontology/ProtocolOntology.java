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

package it.univaq.disim.ips.ontology;

import it.univaq.disim.ips.ontology.data.AggregationTuple;
import it.univaq.disim.ips.ontology.data.Concept;
import it.univaq.disim.ips.ontology.data.SubsumptionPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giacomo
 */
public class ProtocolOntology {
    
    private List<Concept> concepts;
    
    final private List<SubsumptionPair> subsumptionPairs;
    
    final private List<AggregationTuple> aggregationTuples;

    public ProtocolOntology() {
        concepts = new ArrayList();
        subsumptionPairs = new ArrayList();
        aggregationTuples = new ArrayList();
    }

    public ProtocolOntology(List<Concept> concepts) {
        this.concepts = concepts;
        subsumptionPairs = new ArrayList();
        aggregationTuples = new ArrayList();
    }
    
    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }
    
    public void addConcept(Concept concept){
        this.concepts.add(concept);
    }

    public List<SubsumptionPair> getSubsumptionPairs() {
        return subsumptionPairs;
    }

    public void addSubsumptionPair(SubsumptionPair subsumptionPair) {
        subsumptionPairs.add(subsumptionPair);
    }

    public List<AggregationTuple> getAggregationTuples() {
        return aggregationTuples;
    }

    public void addAggregationTuple(AggregationTuple aggregationTuple) {
        aggregationTuples.add(aggregationTuple);
    }

}
