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

package it.univaq.disim.asmc;

import it.univaq.disim.ips.core.Ips;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.StartState;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
import it.univaq.disim.ontology.ProtocolOntology;
import it.univaq.disim.ontology.data.AggregationTuple;
import it.univaq.disim.ontology.data.Concept;
import it.univaq.disim.ontology.data.SubsumptionPair;

/**
 * Automatic Synthesis of Modular Connector via Composition of modular mediation
 * pattern. 
 * @author Giacomo Lamonaco
 */
public class ASMC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // defining an excerpt the moon client interaction protocol
        Ips moonClient = new Ips(new Long(0));
        //defining moon client states
        State mcstate0 = new State("s0");
        State mcstate1 = new State("s1");
        State mcstate2 = new State("s2");
        State mcstate3 = new State("s3");
        //and  its starting state
        StartState mcstart = new StartState(mcstate0);
        // defining moon client actions
        InputAction closeOrder = new InputAction("closeOrder");
        OutputAction login = new OutputAction("login");
        OutputAction createOrder = new OutputAction("createOrder");
        //defining moon client transitions
        Transition mct0 = new Transition(mcstate0, login, mcstate1);
        Transition mct1 = new Transition(mcstate1, createOrder, mcstate2);
        Transition mct2 = new Transition(mcstate2, closeOrder, mcstate3);
        
        //adding all the state elements to the ips
        moonClient.addState(mcstate0);
        moonClient.addState(mcstate1);
        moonClient.addState(mcstate2);
        moonClient.addState(mcstate3);
        
        moonClient.setStart(mcstart);
        
        //adding previous defined actions to moon client IPS
        moonClient.addInput(closeOrder);
        moonClient.addOutput(login);
        moonClient.addOutput(createOrder);
        
        //adding transition
        moonClient.addTransition(mct0);
        moonClient.addTransition(mct1);
        moonClient.addTransition(mct2);
        
        //Definition of Blue Service interaction protocol
        Ips blueService = new Ips(new Long(1));
        
        State bsstate0 = new State("t0");
        State bsstate1 = new State("t1");
        State bsstate2 = new State("t2");
        
        StartState bsstart = new StartState(bsstate0);
        
        InputAction startOrder = new InputAction("startOrder");
        OutputAction placeOrder = new OutputAction("placeOrder");
        
        Transition bst0 = new Transition(bsstate0, startOrder, bsstate1);
        Transition bst1 = new Transition(bsstate1, placeOrder, bsstate2);
        
        blueService.addState(bsstate0);
        blueService.addState(bsstate1);
        blueService.addState(bsstate2);
        
        blueService.setStart(bsstart);
        
        blueService.addInput(startOrder);
        blueService.addOutput(placeOrder);
        
        blueService.addTransition(bst0);
        blueService.addTransition(bst1);
        
        //defining the Semantic Web Service protocol ontolgy
        ProtocolOntology po = new ProtocolOntology();
        Concept place_order = new Concept("placeOrder"), 
                close_order = new Concept("closeOrder");
        
        po.addConcept(place_order);
        po.addConcept(close_order);
        
        po.addSubsumptionPair(new SubsumptionPair(place_order, close_order));
        
        Concept start_order = new Concept("startOrder"),
                create_order = new Concept("createOrder"),
                login_concept = new Concept("login");
        
        AggregationTuple at = new AggregationTuple(start_order);
        at.addAggregationConcept(start_order);
        at.addAggregationConcept(login_concept);
        
        po.addAggregationTuple(at);
        
    }
    
}
