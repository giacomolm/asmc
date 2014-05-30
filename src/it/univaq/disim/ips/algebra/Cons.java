/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.disim.ips.algebra;

import it.univaq.disim.ips.core.Ips;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;

/**
 *
 * @author Giacomo
 */
public class Cons extends Ips{

    public Cons(Long id) {
        super(id);
    }

    public Cons(Long id, Transition transition) {
        super(id);
        this.addState(transition.getSource());
        this.addState(transition.getTarget());
        try{
            this.addInput((InputAction) transition.getAction());
        }
        catch(ClassCastException cce){
            System.out.println("You transition must contains an input action");
            cce.printStackTrace();
        }
        this.addTransition(transition);
    }
    
    public Cons(Long id, State s1, State s2, InputAction input, Transition transition) {
        super(id);
        
        this.addState(s1);
        this.addState(s2);
        
        this.addInput(input);
        
        this.addTransition(transition);
    }
    
}
