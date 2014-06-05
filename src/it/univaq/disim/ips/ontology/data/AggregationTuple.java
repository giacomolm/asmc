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

package it.univaq.disim.ips.ontology.data;

import it.univaq.disim.ips.algebra.Merge;
import it.univaq.disim.ips.algebra.Prod;
import it.univaq.disim.ips.algebra.Trans;
import it.univaq.disim.ips.core.Ips;
import it.univaq.disim.ips.data.action.Action;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
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
    
    public Ips getIps(){
        int i;
        List<Trans> trans_list = new ArrayList<>();
        for(i=0; i<aggregation.size(); i++){
            State s0 = new State("s0"),
                  s1 = new State("s1"),
                  s2 = new State("s2");

            List<State> states = new ArrayList();
            states.add(s0);
            states.add(s1);
            states.add(s2);

            InputAction input = new InputAction(aggregation.get(i).getName());
            OutputAction output = new OutputAction("x"+i);

            Transition t0 = new Transition(s0, input, s1),
                       t1 = new Transition(s1, output, s2);

            trans_list.add(new Trans(new Long(0), t0, t1));
        }
         
        State prod_s0 = new State("t0"), 
              prod_s1 = new State("t1");
        
        OutputAction prod_output = new OutputAction("x"+i);
        
        Transition prod_transition = new Transition(prod_s0, prod_output, prod_s1);
        
        Prod prod = new Prod(new Long(1), prod_transition);
        
        Ips result = trans_list.get(0);
        
        List<State> merge_states = new ArrayList();
        List<Action> merge_inputs = new ArrayList();
        List<Transition> merge_transitions = new ArrayList();
        for(i=0; i<=aggregation.size(); i++){
            State merge_s0, merge_s1;
            merge_s0 = new State("u"+i);
            merge_s1 = new State("u"+(i+1));                  
            
            merge_states.add(merge_s0);
            merge_states.add(merge_s1);

            InputAction input = new InputAction("x"+i);
            if(i<aggregation.size()){
                trans_list.get(i).getOutputs().get(0).addEquivalent(input);
                input.addEquivalent(trans_list.get(i).getOutputs().get(0));
            }
            else{
                prod_output.addEquivalent(input);
                input.addEquivalent(prod_output);
            }
            
            merge_inputs.add(input);
            Transition t0 = new Transition(merge_s0, input, merge_s1);
            
            merge_transitions.add(t0);                       
        }
        
        State merge_s0, merge_s1;
        merge_s0 = new State("u"+i);
        merge_s1 = new State("u"+(i+1));                  

        merge_states.add(merge_s0);
        merge_states.add(merge_s1);

        OutputAction merge_output = new OutputAction(this.getAggregate().getName());
       
        Transition t0 = new Transition(merge_s0, merge_output, merge_s1);

        merge_transitions.add(t0);      
        
        Merge merge = new Merge(new Long(0), merge_states, merge_inputs, merge_output, merge_transitions);
        
        for(i=1; i< aggregation.size(); i++){
            result = result.composition(trans_list.get(i));
        }
        
        result = result.composition(prod);        
        
        result = result.composition(merge);
        
        result.setType("at");
        
        return result;
    }
    
    
}
