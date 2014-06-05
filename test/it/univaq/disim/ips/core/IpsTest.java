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
     * Test of composition method, of class Ips.
     
    @Test
    public void testWrap() {
        System.out.println("Wrapping ");
        
        // defining an excerpt the blue service interaction protocol
        Ips blue_service = new Ips(new Long(2));
        //defining moon client states
        State bs_state0 = new State("s0");
        State bs_state1 = new State("s1");
        State bs_state2 = new State("s2");
        //and  its starting state
        StartState bs_start = new StartState(bs_state0);
        // defining moon client actions
        InputAction start_order = new InputAction("startOrder");
        OutputAction place_order = new OutputAction("placeOrder");
        //defining moon client transitions
        Transition bs_t0 = new Transition(bs_state0, start_order, bs_state1);
        Transition bs_t1 = new Transition(bs_state1, place_order, bs_state2);
        
        //adding all the state elements to the ips
        blue_service.addState(bs_state0);
        blue_service.addState(bs_state1);
        blue_service.addState(bs_state2);
        
        blue_service.setStart(bs_start);
        
        //adding previous defined actions to moon client IPS
        blue_service.addInput(start_order);
        blue_service.addOutput(place_order);
        
        //adding transition
        blue_service.addTransition(bs_t0);
        blue_service.addTransition(bs_t1);
        
        //Defining the aggregation tuple        
        AggregationTuple at = new AggregationTuple(new Concept("startOrder"));
        at.addAggregationConcept(new Concept("createOrder"));
        at.addAggregationConcept(new Concept("login"));       
                
        Ips at_ips = at.getIps();
        
        //setting equivalent actions
        start_order.addEquivalent(at_ips.getActionByName("startOrder"));
        at_ips.getActionByName("startOrder").addEquivalent(start_order);
        
        Ips result = at_ips.wrap(blue_service);
        
        //definining the subsumption pair
        SubsumptionPair sp = new SubsumptionPair(new Concept("placeOrder"), new Concept("closeOrder"));                         
        
        Ips sp_ips = sp.getIps();
        
        //defining the place order equivalent actions
        place_order.addEquivalent(sp_ips.getActionByName("placeOrder"));
        sp_ips.getActionByName("placeOrder").addEquivalent(place_order);
        
        result = sp_ips.wrap(result);
        
        System.out.println(result);
        
        //assertEquals(expResult, result.getTransitions().size());
    }*/

    
}
