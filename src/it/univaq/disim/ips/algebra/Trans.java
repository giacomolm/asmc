/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.disim.ips.algebra;

import it.univaq.disim.ips.core.Ips;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;

/**
 *
 * @author Giacomo
 */
public class Trans extends Ips{

    public Trans(Long id) {
        super(id);
    }
    
    public Trans(Long id, Transition t1, Transition t2){
        super(id);
        
        this.addState(t1.getSource());
        this.addState(t1.getTarget());
        this.addState(t1.getTarget());
        
        try{
            this.addInput((InputAction)t1.getAction());
            this.addOutput((OutputAction)t2.getAction());
        }
        catch(ClassCastException cce){
            System.out.println("Your transitions must contain an input and an output Action");
            cce.printStackTrace();
        }
        
        this.addTransition(t1);
        this.addTransition(t2);
        
    }
    public Trans(Long id, State s1, State s2, State s3, InputAction input, OutputAction output, Transition t1, Transition t2){
        super(id);
        
        this.addState(s1);
        this.addState(s2);
        this.addState(s3);
        
        this.addInput(input);
        this.addOutput(output);
        
        this.addTransition(t1);
        this.addTransition(t2);
        
    }
    
}
