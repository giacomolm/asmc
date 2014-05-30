/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.disim.ips.ontology;

import it.univaq.disim.ips.ontology.ProtocolOntology;
import it.univaq.disim.ips.ontology.data.AggregationTuple;
import it.univaq.disim.ips.ontology.data.Concept;
import it.univaq.disim.ips.ontology.data.SubsumptionPair;
import java.util.List;
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
public class ProtocolOntologyTest {
    
    public ProtocolOntologyTest() {
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
     * Test of getConcepts method, of class ProtocolOntology.
     */
    @Test
    public void testSubsumptionPairToIps() {
        System.out.println("Testing subsumption pait conversion to Ips");
        ProtocolOntology po = new ProtocolOntology();
        Concept place_order = new Concept("placeOrder"), 
                close_order = new Concept("closeOrder");
        
        po.addConcept(place_order);
        po.addConcept(close_order);
        
        SubsumptionPair sp = new SubsumptionPair(place_order, close_order);        
        
        po.addSubsumptionPair(sp);        
        
        System.out.println(sp.getIps());
        int expResult = 2;
        assertEquals(expResult, sp.getIps().getTransitions().size());
        
    }
}
