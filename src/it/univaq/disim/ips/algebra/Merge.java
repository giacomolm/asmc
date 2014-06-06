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
import it.univaq.disim.ips.data.state.BinaryState;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
import java.util.ArrayList;
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
        
        this.setTransitions(getTransitionsProduct(new ArrayList(), new BinaryState(new State("s"+(inputs.size())), new State("t0"),inputs.size(), 0), 0));
        this.getTransitions().add(new Transition(new BinaryState(new State("s"+(inputs.size())), new State("t0"),inputs.size(), 0), 
                                          output,new BinaryState(new State("s"+(inputs.size()+1)), new State("t0"),inputs.size()+1, 0)));
                
    }
    
    //merge inputs don't follow a specific order, so we have to product them
    public List<Transition> getTransitionsProduct(List<Action> visited, State final_state, int jump){
        List<Transition> result = new ArrayList();
        int i=0, cont=0;
        
        for(i=0; i<this.getInputs().size(); i++){
            
            Action action = this.getInputs().get(i);
            
            if(!visited.contains(action)){
                
                State s1, s2;                
                
                if(visited.size() == this.getInputs().size()-1){
                    //we have to insert only the last actions
                    s1 = new BinaryState(new State("s"+visited.size()), new State("t"+jump), visited.size(), jump);
                    s2 = final_state;
                }
                else if(visited.size()==0){
                    s1 = new BinaryState(new State("s0"), new State("t0"), 0, 0);
                    s2 = new BinaryState(new State("s"+(visited.size()+1)), new State("t"+cont), visited.size()+1, cont);
                }
                else{
                    
                    s1 = new BinaryState(new State("s"+visited.size()), new State("t"+jump), visited.size(), jump);
                    s2 = new BinaryState(new State("s"+(visited.size()+1)), new State("t"+(((visited.size()+1)*jump)+cont)), visited.size()+1, (((visited.size()+1)*jump)+cont));
                }
                
                Transition t = new Transition(s1, action, s2);
                result.add(t);
                cont++;
                
                visited.add(action);
                result.addAll(getTransitionsProduct(visited, final_state, (jump*visited.size())+(cont-1) ));
                visited.remove(action);
                
            }
        }
        
        return result;
    }
}
