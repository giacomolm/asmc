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
public class Prod extends Ips{
    
    public Prod(Long id) {
        super(id);
    }

    public Prod(Long id, Transition transition) {
        super(id);
        this.addState(transition.getSource());
        this.addState(transition.getTarget());
        try{
            this.addOutput((OutputAction) transition.getAction());
        }
        catch(ClassCastException cce){
            System.out.println("Your transition must contains an output action");
            cce.printStackTrace();
        }
        this.addTransition(transition);
    }
    
    public Prod(Long id, State s1, State s2, OutputAction output, Transition transition) {
        super(id);
        
        this.addState(s1);
        this.addState(s2);
        
        this.addOutput(output);
        
        this.addTransition(transition);
    } 
}
