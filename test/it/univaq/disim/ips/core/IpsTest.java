/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.disim.ips.core;

import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.StartState;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
import it.univaq.disim.ips.ontology.ProtocolOntology;
import it.univaq.disim.ips.ontology.data.AggregationTuple;
import it.univaq.disim.ips.ontology.data.Concept;
import it.univaq.disim.ips.ontology.data.SubsumptionPair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Giacomo
 */
public class IpsTest {
    
    public IpsTest() {
        //System.out.println("Tests on IPS are referred to slide #5 of IT-ICSE2013-13052013");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of product method, of class Ips.
     */
    @Test
    public void testProduct() {
        System.out.println("Product");
        // defining an excerpt the moon client interaction protocol
        Ips ips1 = new Ips(new Long(0));
        //defining moon client states
        State ips1state0 = new State("s0");
        State ips1state1 = new State("s1");
        State ips1state2 = new State("s2");
        State ips1state3 = new State("s3");
        //and  its starting state
        StartState ips1start = new StartState(ips1state0);
        // defining moon client actions
        InputAction a1 = new InputAction("a");
        InputAction c = new InputAction("c");
        OutputAction ap1 = new OutputAction("a'");
        //defining moon client transitions
        Transition ips1t0 = new Transition(ips1state0, a1, ips1state1);
        Transition ips1t1 = new Transition(ips1state1, c, ips1state2);
        Transition ips1t2 = new Transition(ips1state2, ap1, ips1state3);
        
        //adding all the state elements to the ips
        ips1.addState(ips1state0);
        ips1.addState(ips1state1);
        ips1.addState(ips1state2);
        ips1.addState(ips1state3);
        
        ips1.setStart(ips1start);
        
        //adding previous defined actions to moon client IPS
        ips1.addInput(a1);
        ips1.addInput(c);
        ips1.addOutput(ap1);
        
        //adding transition
        ips1.addTransition(ips1t0);
        ips1.addTransition(ips1t1);
        ips1.addTransition(ips1t2);
        
        //Definition of Blue Service interaction protocol
        Ips ips2 = new Ips(new Long(1));
        
        State ips2state0 = new State("t0");
        State ips2state1 = new State("t1");
        State ips2state2 = new State("t2");
        State ips2state3 = new State("t3");
        
        StartState ips2start = new StartState(ips2state0);
        
        OutputAction ap2 = new OutputAction("a");
        OutputAction b = new OutputAction("b");
        InputAction a2 = new InputAction("a'");
        
        Transition bst0 = new Transition(ips2state0, ap2, ips2state1);
        Transition bst1 = new Transition(ips2state1, b, ips2state2);
        Transition bst2 = new Transition(ips2state2, a2, ips2state3);
        
        ips2.addState(ips2state0);
        ips2.addState(ips2state1);
        ips2.addState(ips2state2);
        
        ips2.setStart(ips2start);
        
        ips2.addOutput(ap2);
        ips2.addOutput(b);
        ips2.addInput(a2);
        
        ips2.addTransition(bst0);
        ips2.addTransition(bst1);
        ips2.addTransition(bst2);
        
        a1.addEquivalent(ap2);
        ap2.addEquivalent(a1);
        
        a2.addEquivalent(ap1);
        ap1.addEquivalent(a2);
        
        int expResult = 6;
        Ips result = Ips.product(ips1, ips2);
        
        System.out.println(result.getTransitions());
        
        assertEquals(expResult, result.getTransitions().size());
    }
    
    /**
     * Test of composition method, of class Ips.
     */
    @Test
    public void testComposition() {
        System.out.println("Composition");
        // defining an excerpt the moon client interaction protocol
        Ips ips1 = new Ips(new Long(0));
        //defining moon client states
        State ips1state0 = new State("s0");
        State ips1state1 = new State("s1");
        State ips1state2 = new State("s2");
        State ips1state3 = new State("s3");
        //and  its starting state
        StartState ips1start = new StartState(ips1state0);
        // defining moon client actions
        InputAction a1 = new InputAction("a");
        InputAction c = new InputAction("c");
        OutputAction ap1 = new OutputAction("a'");
        //defining moon client transitions
        Transition ips1t0 = new Transition(ips1state0, a1, ips1state1);
        Transition ips1t1 = new Transition(ips1state1, c, ips1state2);
        Transition ips1t2 = new Transition(ips1state2, ap1, ips1state3);
        
        //adding all the state elements to the ips
        ips1.addState(ips1state0);
        ips1.addState(ips1state1);
        ips1.addState(ips1state2);
        ips1.addState(ips1state3);
        
        ips1.setStart(ips1start);
        
        //adding previous defined actions to moon client IPS
        ips1.addInput(a1);
        ips1.addInput(c);
        ips1.addOutput(ap1);
        
        //adding transition
        ips1.addTransition(ips1t0);
        ips1.addTransition(ips1t1);
        ips1.addTransition(ips1t2);
        
        //Definition of Blue Service interaction protocol
        Ips ips2 = new Ips(new Long(1));
        
        State ips2state0 = new State("t0");
        State ips2state1 = new State("t1");
        State ips2state2 = new State("t2");
        State ips2state3 = new State("t3");
        
        StartState ips2start = new StartState(ips2state0);
        
        OutputAction ap2 = new OutputAction("a'");
        OutputAction b = new OutputAction("b");
        InputAction a2 = new InputAction("a");
        
        Transition bst0 = new Transition(ips2state0, ap2, ips2state1);
        Transition bst1 = new Transition(ips2state1, b, ips2state2);
        Transition bst2 = new Transition(ips2state2, a2, ips2state3);
        
        ips2.addState(ips2state0);
        ips2.addState(ips2state1);
        ips2.addState(ips2state2);
        
        ips2.setStart(ips2start);
        
        ips2.addOutput(ap2);
        ips2.addOutput(b);
        ips2.addInput(a2);
        
        ips2.addTransition(bst0);
        ips2.addTransition(bst1);
        ips2.addTransition(bst2);
        
        a1.addEquivalent(ap2);
        ap2.addEquivalent(a1);
        
        a2.addEquivalent(ap1);
        ap1.addEquivalent(a2);
        
        int expResult = 2;
        Ips result = ips1.composition(ips2);
        
        System.out.println(Ips.common(ips1, ips2));
        System.out.println(result.getTransitions());
        
        assertEquals(expResult, result.getTransitions().size());
    }
    
    /**
     * Test of align method, of class Ips.
    */
    @Test
    public void testAlign() {
        System.out.println("Wrapping ");
        
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
        
        int result = blueService.align(po).getTransitions().size();    
        
        assertEquals(14, result);
    }

    
}
