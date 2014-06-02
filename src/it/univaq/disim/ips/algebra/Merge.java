/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.univaq.disim.ips.algebra;

import it.univaq.disim.ips.core.Ips;
import it.univaq.disim.ips.data.action.Action;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
import java.util.List;

/**
 *
 * @author Giacomo
 */
public class Merge extends Ips{
    
    public Merge(Long id) {
        super(id);
    }
    
    public Merge(Long  id, List<State> states, List<Action> inputs, OutputAction output, List<Transition> transitions){
        super(id);
        
        this.setStates(states);
        
        this.setInputs(inputs);
        this.addOutput(output);
        
        this.setTransitions(transitions);
    }
}
