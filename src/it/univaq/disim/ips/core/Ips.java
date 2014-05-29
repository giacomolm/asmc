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
package it.univaq.disim.ips.core;

import it.univaq.disim.ips.data.action.Action;
import it.univaq.disim.ips.data.action.HiddenAction;
import it.univaq.disim.ips.data.action.InputAction;
import it.univaq.disim.ips.data.action.OutputAction;
import it.univaq.disim.ips.data.state.BinaryState;
import it.univaq.disim.ips.data.state.State;
import it.univaq.disim.ips.data.transition.Transition;
import java.util.ArrayList;
import java.util.List;
import sun.rmi.transport.ObjectTable;

/**
 * Interaction Protocol System
 * @author Giacomo
 */
public class Ips {
    
    private Long id;
    
    private List<State> states;
    
    private State start;
   
    private List<Action> inputs, outputs, hiddens;
    
    private List<Transition> transitions;

    public Ips(){
        this.states = new ArrayList();
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
        this.hiddens = new ArrayList();
        this.transitions = new ArrayList();
    }
    public Ips(Long id) {
        this.id = id;
        this.states = new ArrayList();
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
        this.hiddens = new ArrayList();
        this.transitions = new ArrayList();
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
    
    public void addState(State state){
        this.states.add(state);
    }

    public State getStart() {
        return start;
    }

    public void setStart(State start) {
        this.start = start;
    }

    public List<Action> getInputs() {
        return inputs;
    }

    public void setInputs(List<Action> inputs) {
        this.inputs = inputs;
    }
    
    public void addInput(InputAction input){
        this.inputs.add(input);
    }

    public List<Action> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Action> outputs) {
        this.outputs = outputs;
    }
    
    public void addOutput(OutputAction output){
        this.outputs.add(output);
    }
    
    public List<Action> getHiddens() {
        return hiddens;
    }
    
    public void AddHidden(HiddenAction hidden){
        this.hiddens.add(hidden);
    }

    public void setHiddens(List<Action> hiddens) {
        this.hiddens = hiddens;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public void addTransition(Transition transition){
        this.transitions.add(transition);
    }

    /**
     * Two actions are common between two protocol P and R if one of them appears
     * as input action in P and as output action in R, and viceversa.
     * @param ips1 first specification containing input and output actions 
     * @param ips2 secondo specification containing input and output actions
     * @return list of common action
     */
    public static List<Action> common(Ips ips1, Ips ips2){
        List<Action> result = new ArrayList();
        List<Action> ips1_inaction = ips1.getInputs(), ips2_outaction = ips2.getOutputs(),
                     ips1_outaction = ips1.getOutputs(), ips2_inaction = ips2.getInputs();
        int i,j;
        for(i=0; i<ips1_inaction.size(); i++){
            for(j=0; j<ips2_outaction.size(); j++){
                if(ips1_inaction.get(i).equivalent(ips2_outaction.get(j))) 
                     result.add(ips1_inaction.get(i));
            }
        }
        
        for(i=0; i<ips1_outaction.size(); i++){
            for(j=0; j<ips2_inaction.size(); j++){
                if(ips1_outaction.get(i).equivalent(ips2_inaction.get(j))) 
                     result.add(ips1_outaction.get(i));
            }
        }
        return result;
    }
    /**
     *
     * @return 
     */
    public static boolean checkInconsistency(Ips ips1, Ips ips2, State s1, State s2){

        List<Transition> tl1 = ips1.getTransitions(), 
                         tl2 = ips2.getTransitions();
        int i, j;
        List<Action> common_actions = common(ips1, ips2); //getting the common actions
        for(i=0; i<tl1.size(); i++){
            if(tl1.get(i).getSource().equals(s1)){
                //getting the corresponding action
                Action action = tl1.get(i).getAction();
                //if the action is an instance of InputAction, and is contained in common_actions
                //we have to check if the other Ips can generate this action
                if((action instanceof InputAction)&&(common_actions.contains(action))){
                    for(j=0; j<tl2.size(); j++){
                        //we're right transition containing as source state the state s2
                        if(tl2.get(j).getSource().equals(s2)){
                                
                            if(tl2.get(j).getAction() instanceof OutputAction){
                                //if the action are equal, we have found the equivalent output action
                                if(tl2.get(j).getAction().equals(action)){
                                    return false;
                                }
                                else return checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget());
                            }
                            else{
                                //we have to continue the searching of the output equivalent action,
                                //avoiding the presence of other common input action
                                if(!common_actions.contains(tl2.get(j).getAction())) 
                                    return checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget());
                                else return true;
                            } 

                        }
                    }
                }
                else //the immediate action after the state s1 is an output action, so we have to find potential input action (of the same meaning) 
                    if((action instanceof OutputAction)&&(common_actions.contains(action))){
                    for(j=0; j<tl2.size(); j++){
                        if(tl2.get(j).getSource().equals(s2)){
                            
                            if(tl2.get(j).getAction() instanceof InputAction){
                                //if the action are equivalent, we have an inconsistency, as explained
                                if(tl2.get(j).getAction().equals(action)){
                                    return true;
                                }
                                else return false;
                            }
                            else //if the action is an output, we have to contine the exploration
                                return checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget());
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Product between two IPS. 
     * @param ips1
     * @param ips2
     * @return new Ips containing the product the two input
     */
    public static Ips product(Ips ips1, Ips ips2){
        Ips result = new Ips();
        List<State> states;
        List<Action> in_actions = new ArrayList(),
                     out_actions = new ArrayList(),
                     hidden_actions = new ArrayList();
        List<Transition> transitions = new ArrayList();
        int i,j;
        
        //Cartesian product of the two set of states
        states = BinaryState.cartesianProduct(ips1.getStates(), ips2.getStates());
        //calculating the common actions
        List<Action> common_actions = common(ips1, ips2);
        
        //adding all the input actions of the ips1, and removing the common ones
        in_actions.addAll(ips1.getInputs());
        in_actions.removeAll(common_actions);
        //adding all the input actions of the ips2, and removing the common ones
        in_actions.addAll(ips2.getInputs());
        in_actions.removeAll(common_actions);
        
        //adding all the output actions of the ips1, and removing the common ones
        out_actions.addAll(ips1.getOutputs());
        out_actions.removeAll(common_actions);
        //adding all the input actions of the ips2, and removing the common ones
        out_actions.addAll(ips2.getOutputs());
        out_actions.removeAll(common_actions);
        
        //adding the hidden actions to the resulting ips
        hidden_actions.addAll(ips1.getHiddens());
        hidden_actions.addAll(ips2.getHiddens());
        //and we have to add all the common actions
        hidden_actions.addAll(common_actions);
        
        //Setting all the transition of the new IPS
        for(i=0; i<ips1.getTransitions().size(); i++){
            //getting the i-th transition of ips1
            Transition t1 = ips1.getTransitions().get(i);
            //if exists the equivalent action in the j-th transaction, then mark this variable to true
            boolean action_equivalent = false;
            
            for(j=0; j<ips2.getTransitions().size(); j++){
                //getting the j-th transition of ips2
                Transition t2 = ips2.getTransitions().get(j);     
                //if both the action are equivalent, then i've to create the new transition
                if(t1.getAction().equivalent(t2.getAction())){
                    //creating the new states
                    BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                target = new BinaryState(t1.getTarget(), t2.getTarget());
                    
                    //creating the new transition and adding it to the collection
                    transitions.add(new Transition(source, t1.getAction(), target));
                             
                    //setting the action_equivalent to true, as we've found it
                    action_equivalent = true;
                     
                }
                else if(t1.getAction().equals(t2.getAction())&& //if the two actions are equals and
                        common_actions.contains(t1.getAction())){//the t1 action is common
                            //creating the new states
                            BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                        target = new BinaryState(t1.getTarget(), t2.getTarget());

                            //creating the new transition and adding it to the collection
                            transitions.add(new Transition(source, t1.getAction(), target));
                }
            }
            
            if((!action_equivalent) && //if there isn't any transition in ips2 with the equivalent action and
               (!common_actions.contains(t1.getAction()))){//the action in t1 is not in common
                //we have to define two new transitions                 
                if(i<ips2.getTransitions().size()){
                    //getting the j-th transition of ips2
                    Transition t2 = ips2.getTransitions().get(i);
                    
                    //for the  transition in ips1 create e new binary transition
                    BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                target = new BinaryState(t1.getTarget(), t2.getSource());
                    //creating the new transition and adding it to the collection
                    transitions.add(new Transition(source, t1.getAction(), target));
                    
                    source = new BinaryState(t1.getTarget(), t2.getSource());
                    target = new BinaryState(t1.getTarget(), t2.getTarget());
                    //creating the new transition and adding it to the collection
                    transitions.add(new Transition(source, t2.getAction(), target));
                    
                }
            }
        }
        
        //now we need to discover the actions in ips2 don't having en equivalent action in ips1
        for(j=0; j<ips2.getTransitions().size(); j++){
            //getting the i-th transition of ips1
            Transition t1 = ips2.getTransitions().get(j);
            //if exists the equivalent action in the j-th transaction, then mark this variable to true
            boolean action_equivalent = false;
            for(i=0; i<ips1.getTransitions().size(); i++){
                //getting the i-th transitio in ips1
                Transition t2 = ips1.getTransitions().get(i);
                //we're interested to equivalent actions and their existence
                if(t1.getAction().equivalent(t2.getAction())){
                    //an equivalent action is found in ips1
                    action_equivalent = true;
                }
            }
            
            if(!action_equivalent && //if doesn't exist an equivalent action
              (!common_actions.contains(t1.getAction()))){//the action in t1 is not in common
                
                    if(j<ips1.getTransitions().size()){
                        //getting the j-th transition of ips2
                        Transition t2 = ips1.getTransitions().get(j);
                        //for each transition in ips1 create e new binary transition
                        BinaryState source = new BinaryState(t2.getSource(), t1.getSource()),
                                    target = new BinaryState(t2.getSource(), t1.getTarget());
                        //creating the new transition and adding it to the collection
                        transitions.add(new Transition(source, t1.getAction(), target));
                        
                        source = new BinaryState(t2.getSource(), t1.getTarget());
                        target = new BinaryState(t2.getTarget(), t1.getTarget());
                        //creating the new transition and adding it to the collection
                        transitions.add(new Transition(source, t2.getAction(), target));
                    }
            }
        }
        
        //Building the resulting IPS
        result.setStates(states);
        result.setInputs(in_actions);
        result.setOutputs(out_actions);
        result.setHiddens(hidden_actions);
        result.setTransitions(transitions);
        return result;
    }
    
    /**
     * Composition between two IPS. 
     * @param ips1
     * @param ips2
     * @return new Ips containing the product the two input
     */
    public static Ips composition(Ips ips1, Ips ips2){
        Ips result = new Ips();
        List<State> states;
        List<Action> in_actions = new ArrayList(),
                     out_actions = new ArrayList(),
                     hidden_actions = new ArrayList();
        List<Transition> transitions = new ArrayList();
        int i,j;
        
        //Cartesian product of the two set of states
        states = BinaryState.cartesianProduct(ips1.getStates(), ips2.getStates());
        //calculating the common actions
        List<Action> common_actions = common(ips1, ips2);
        
        //adding all the input actions of the ips1, and removing the common ones
        in_actions.addAll(ips1.getInputs());
        in_actions.removeAll(common_actions);
        //adding all the input actions of the ips2, and removing the common ones
        in_actions.addAll(ips2.getInputs());
        in_actions.removeAll(common_actions);
        
        //adding all the output actions of the ips1, and removing the common ones
        out_actions.addAll(ips1.getOutputs());
        out_actions.removeAll(common_actions);
        //adding all the input actions of the ips2, and removing the common ones
        out_actions.addAll(ips2.getOutputs());
        out_actions.removeAll(common_actions);
        
        //adding the hidden actions to the resulting ips
        hidden_actions.addAll(ips1.getHiddens());
        hidden_actions.addAll(ips2.getHiddens());
        //and we have to add all the common actions
        hidden_actions.addAll(common_actions);
        
        //Setting all the transition of the new IPS
        for(i=0; i<ips1.getTransitions().size(); i++){
            //getting the i-th transition of ips1
            Transition t1 = ips1.getTransitions().get(i);
            //if exists the equivalent action in the j-th transaction, then mark this variable to true
            boolean action_equivalent = false;
            
            for(j=0; j<ips2.getTransitions().size(); j++){
                //getting the j-th transition of ips2
                Transition t2 = ips2.getTransitions().get(j);     
                //if both the action are equivalent, then i've to create the new transition
                if(t1.getAction().equivalent(t2.getAction())){
                    //creating the new states
                    BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                target = new BinaryState(t1.getTarget(), t2.getTarget());
                    
                    //creating the new transition and adding it to the collection
                    transitions.add(new Transition(source, t1.getAction(), target));
                             
                    //setting the action_equivalent to true, as we've found it
                    action_equivalent = true;
                     
                }
                else if(t1.getAction().equals(t2.getAction())&& //if the two actions are equals and
                        common_actions.contains(t1.getAction())){//the t1 action is common
                            //creating the new states
                            BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                        target = new BinaryState(t1.getTarget(), t2.getTarget());

                            //creating the new transition and adding it to the collection
                            transitions.add(new Transition(source, t1.getAction(), target));
                }
            }
            
            if((!action_equivalent) && //if there isn't any transition in ips2 with the equivalent action and
               (!common_actions.contains(t1.getAction()))){//the action in t1 is not in common
                //we have to define two new transitions                 
                if(i<ips2.getTransitions().size()){
                    //getting the j-th transition of ips2
                    Transition t2 = ips2.getTransitions().get(i);
                    
                    //for the  transition in ips1 create e new binary transition
                    BinaryState source = new BinaryState(t1.getSource(), t2.getSource()),
                                target = new BinaryState(t1.getTarget(), t2.getSource());                    
                    
                    //check the consistency
                    if(!checkInconsistency(ips1, ips2, t1.getTarget(), t2.getSource())){  
                        //creating the new transition and adding it to the collection
                        transitions.add(new Transition(source, t1.getAction(), target));
                        
                        source = new BinaryState(t1.getTarget(), t2.getSource());
                        target = new BinaryState(t1.getTarget(), t2.getTarget());
                        //creating the new transition and adding it to the collection
                        transitions.add(new Transition(source, t2.getAction(), target));
                    }
                }
            }
        }
        
        //now we need to discover the actions in ips2 don't having en equivalent action in ips1
        for(j=0; j<ips2.getTransitions().size(); j++){
            //getting the i-th transition of ips1
            Transition t1 = ips2.getTransitions().get(j);
            //if exists the equivalent action in the j-th transaction, then mark this variable to true
            boolean action_equivalent = false;
            for(i=0; i<ips1.getTransitions().size(); i++){
                //getting the i-th transitio in ips1
                Transition t2 = ips1.getTransitions().get(i);
                //we're interested to equivalent actions and their existence
                if(t1.getAction().equivalent(t2.getAction())){
                    //an equivalent action is found in ips1
                    action_equivalent = true;
                }
            }
            
            if(!action_equivalent && //if doesn't exist an equivalent action
              (!common_actions.contains(t1.getAction()))){//the action in t1 is not in common
                
                    if(j<ips1.getTransitions().size()){
                        //getting the j-th transition of ips2
                        Transition t2 = ips1.getTransitions().get(j);
                        //for each transition in ips1 create e new binary transition
                        BinaryState source = new BinaryState(t2.getSource(), t1.getSource()),
                                    target = new BinaryState(t2.getSource(), t1.getTarget());
                        
                        //check the consistency
                        if(!checkInconsistency(ips2, ips1, t1.getTarget(), t2.getSource())){
                            //creating the new transition and adding it to the collection
                            transitions.add(new Transition(source, t1.getAction(), target));
                            
                            source = new BinaryState(t2.getSource(), t1.getTarget());
                            target = new BinaryState(t2.getTarget(), t1.getTarget());
                            //creating the new transition and adding it to the collection
                            transitions.add(new Transition(source, t2.getAction(), target));
                        }
                    }
            }
        }
        
        //Building the resulting IPS
        result.setStates(states);
        result.setInputs(in_actions);
        result.setOutputs(out_actions);
        result.setHiddens(hidden_actions);
        result.setTransitions(transitions);
        return result;
    }
}
