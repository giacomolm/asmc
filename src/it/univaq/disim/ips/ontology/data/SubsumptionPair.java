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

import it.univaq.disim.ips.algebra.Cons;
import it.univaq.disim.ips.algebra.Split;
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
    
    public Ips getIps(){
        State s0 = new State("s0"),
              s1 = new State("s1"),
              s2 = new State("s2"),
              s3 = new State("s3");
        
        List<State> states = new ArrayList();
        states.add(s0);
        states.add(s1);
        states.add(s2);
        states.add(s3);
        
        InputAction input = new InputAction(pair.get(0).getName());
        OutputAction output1 = new OutputAction(pair.get(1).getName()),
                     output2 = new OutputAction("x2");
        
        List<Action> output_list = new ArrayList();
        output_list.add(output1);
        output_list.add(output2);
        
        Transition t0 = new Transition(s0, input, s1),
                   t1 = new Transition(s1, output1, s2),
                   t2 = new Transition(s2, output2, s3);
        
        List<Transition> transitions_list = new ArrayList();
        transitions_list.add(t0);
        transitions_list.add(t1);
        transitions_list.add(t2);
        
        Split split = new Split(new Long('0'), states, input, output_list, transitions_list);
        
        State cons_s0 = new State("t0"), 
              cons_s1 = new State("t1");
        
        InputAction cons_input = new InputAction("x2");
        
        Transition cons_transition = new Transition(cons_s0, cons_input, cons_s1);
        
        Cons cons = new Cons(new Long(1), cons_transition);
        
        //setting the equivalent actions
        output2.addEquivalent(cons_input);
        cons_input.addEquivalent(output2);
        
        Ips result = split.composition(cons);
        result.setType("sp");
        
        return result;
    }
}
