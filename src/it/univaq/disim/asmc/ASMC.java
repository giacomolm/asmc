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
import it.univaq.disim.ips.ontology.ProtocolOntology;
import it.univaq.disim.ips.ontology.data.AggregationTuple;
import it.univaq.disim.ips.ontology.data.Concept;
import it.univaq.disim.ips.ontology.data.SubsumptionPair;

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
        State mcstate4 = new State("s4");
        State mcstate5 = new State("s5");
        State mcstate6 = new State("s6");
        State mcstate7 = new State("s7");
        State mcstate8 = new State("s8");

        //and  its starting state
        StartState mcstart = new StartState(mcstate0);
        // defining moon client actions
        OutputAction login = new OutputAction("login");
        OutputAction createOrder = new OutputAction("createOrder");
        OutputAction selectItem = new OutputAction("selectItem");
        OutputAction selectItemQuantity = new OutputAction("selectItemQuantity");
        InputAction closeOrder = new InputAction("closeOrder");
        InputAction confirmItem = new InputAction("confirmItem");
        InputAction payThirdyPart = new InputAction("payThirdyPart");
        InputAction close = new InputAction("close");
        //defining moon client transitions
        Transition mct0 = new Transition(mcstate0, login, mcstate1);
        Transition mct1 = new Transition(mcstate1, createOrder, mcstate2);
        Transition mct2 = new Transition(mcstate2, selectItem, mcstate3);
        Transition mct3 = new Transition(mcstate3, selectItemQuantity, mcstate4);
        Transition mct4 = new Transition(mcstate4, closeOrder, mcstate5);
        Transition mct5 = new Transition(mcstate5, confirmItem, mcstate6);
        Transition mct6 = new Transition(mcstate6, payThirdyPart, mcstate7);
        Transition mct7 = new Transition(mcstate7, close, mcstate8);
        
        //adding all the state elements to the ips
        moonClient.addState(mcstate0);
        moonClient.addState(mcstate1);
        moonClient.addState(mcstate2);
        moonClient.addState(mcstate3);
        moonClient.addState(mcstate4);
        moonClient.addState(mcstate5);
        moonClient.addState(mcstate6);
        moonClient.addState(mcstate7);
        moonClient.addState(mcstate8);
        
        moonClient.setStart(mcstart);
        
        //adding previous defined actions to moon client IPS
        moonClient.addOutput(login);
        moonClient.addOutput(createOrder);
        moonClient.addOutput(selectItem);
        moonClient.addOutput(selectItemQuantity);
        moonClient.addInput(closeOrder);
        moonClient.addInput(confirmItem);
        moonClient.addInput(payThirdyPart);
        moonClient.addInput(close);
        
        //adding transition
        moonClient.addTransition(mct0);
        moonClient.addTransition(mct1);
        moonClient.addTransition(mct2);
        moonClient.addTransition(mct3);
        moonClient.addTransition(mct4);
        moonClient.addTransition(mct5);
        moonClient.addTransition(mct6);
        moonClient.addTransition(mct7);
        
        //Definition of Blue Service interaction protocol
        Ips blueService = new Ips(new Long(1));
        
        State bsstate0 = new State("t0");
        State bsstate1 = new State("t1");
        State bsstate2 = new State("t2");
        State bsstate3 = new State("t3");
        State bsstate4 = new State("t4");
        State bsstate5 = new State("t5");
        
        StartState bsstart = new StartState(bsstate0);
        
        InputAction startOrder = new InputAction("startOrder");
        InputAction addItemToOrder = new InputAction("addItemToOrder");
        OutputAction getConfirmation= new OutputAction("getConfirmation");
        OutputAction placeOrder = new OutputAction("placeOrder");
        InputAction quit = new InputAction("quit");
        
        
        Transition bst0 = new Transition(bsstate0, startOrder, bsstate1);
        Transition bst1 = new Transition(bsstate1, addItemToOrder, bsstate2);
        Transition bst2 = new Transition(bsstate2, addItemToOrder, bsstate2);
        Transition bst3 = new Transition(bsstate2, getConfirmation, bsstate3);
        Transition bst4 = new Transition(bsstate3, getConfirmation, bsstate3);
        Transition bst5 = new Transition(bsstate3, placeOrder, bsstate4);
        Transition bst6 = new Transition(bsstate4, quit, bsstate5);
        
        blueService.addState(bsstate0);
        blueService.addState(bsstate1);
        blueService.addState(bsstate2);
        blueService.addState(bsstate3);
        blueService.addState(bsstate4);
        blueService.addState(bsstate5);
        
        blueService.setStart(bsstart);
        
        blueService.addInput(startOrder);
        blueService.addInput(addItemToOrder);
        blueService.addOutput(getConfirmation);
        blueService.addOutput(placeOrder);
        blueService.addInput(quit);
        
        blueService.addTransition(bst0);
        blueService.addTransition(bst1);
        blueService.addTransition(bst2);
        blueService.addTransition(bst3);
        blueService.addTransition(bst4);
        blueService.addTransition(bst5);
        blueService.addTransition(bst6);
        
        //defining the Semantic Web Service protocol ontolgy
        ProtocolOntology po = new ProtocolOntology();
                
        //definining the subsumption pair
        SubsumptionPair sp1 = new SubsumptionPair(new Concept("placeOrder"), new Concept("closeOrder")),
                        sp2 = new SubsumptionPair(new Concept("getConfirmation"), new Concept("confirmItem"));

        //Defining the aggregation tuple  
        AggregationTuple at1 = new AggregationTuple(new Concept("addItemToOrder")),
                         at2 = new AggregationTuple(new Concept("quit")),
                         at3 = new AggregationTuple(new Concept("startOrder"));        
        
        at1.addAggregationConcept(new Concept("selectItem"));
        at1.addAggregationConcept(new Concept("selectItemQuantity"));        
        
        at2.addAggregationConcept(new Concept("close"));
        
        at3.addAggregationConcept(new Concept("createOrder"));
        at3.addAggregationConcept(new Concept("login"));
        
        po.addSubsumptionPair(sp1);       
        po.addSubsumptionPair(sp2);
        
        po.addAggregationTuple(at1);
        po.addAggregationTuple(at2);
        po.addAggregationTuple(at3);
        
        System.out.println(blueService.align(po));
        //System.out.println(at1.getIps());*/
        
    }
    
}
