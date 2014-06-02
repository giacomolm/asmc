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
import java.util.Iterator;
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

    protected void setInputs(List<Action> inputs) {
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
                if(ips1_inaction.get(i).equivalent(ips2_outaction.get(j))){ 
                     result.add(ips1_inaction.get(i));
                     result.add(ips2_outaction.get(j));
                }
            }
        }
        
        for(i=0; i<ips1_outaction.size(); i++){
            for(j=0; j<ips2_inaction.size(); j++){
                if(ips1_outaction.get(i).equivalent(ips2_inaction.get(j))){
                     result.add(ips1_outaction.get(i));
                     result.add(ips2_inaction.get(j));
                }
            }
        }
        return result;
    }
    /**
     *
     * @return 
     */
    public static Transition checkInconsistency(Ips ips1, Ips ips2, State s1, State s2, boolean lookahead){

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
                                
                                if(tl2.get(j).getAction().equivalent(action)){
                                    return tl2.get(j);
                                }
                                else return null;//checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget());
                            }
                            else if(lookahead){
                                //we have to continue the searching of the output equivalent action,
                                //avoiding the presence of other common input action
                                if(!common_actions.contains(tl2.get(j).getAction())) 
                                    return checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget(),true);
                                else return null;
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
                                
                                if(tl2.get(j).getAction().equivalent(action)){
                                    return tl2.get(j);
                                }
                                else return null;
                            }
                            else //if the action is an output, we have to contine the exploration
                                return null; //checkInconsistency(ips1, ips2, s1, tl2.get(j).getTarget(),true);
                        }
                    }
                }
            }
        }
        Transition t = new Transition(new State("fake0"), null, new State("fake1"));
        return t;// (new Transition(new State("fake0"), null, new State("fake1")));
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
    
    private List<Transition> getComposedTransition(Ips ips1, Ips ips2, State start1, State start2, int new_state1, int new_state2, List<Action> ips1_action, List<Action> ips2_action, List<String> existing){
        //Setting all the transition of the new IPS
        int i,j=0; //j will contain the ips2 most advanced transition index
        List<Action> common_actions = common(ips1, ips2);
        List<Transition> transitions = new ArrayList();
        
        for(i=0; i<ips1.getTransitions().size(); i++){            
                        
            //finding the starting state of the current branch
            if(ips1.getTransitions().get(i).getSource().equals(start1)){
                
                Transition t1 = ips1.getTransitions().get(i);                               
                                
                if(!ips1_action.contains(t1.getAction())){  
                    
                    //if the action is common between the two IPS
                    if(common_actions.contains(t1.getAction())){
                        Transition equivalent_t = checkInconsistency(ips1, ips2, start1, start2, false);
                        
                        if(equivalent_t!=null && t1.getAction() instanceof OutputAction){                                                                                   
                            
                            if(start2.equals(equivalent_t.getSource())){
                                ips1_action.add(t1.getAction());
                                ips2_action.add(equivalent_t.getAction());
                                
                                transitions.addAll(this.getComposedTransition(ips1, ips2, t1.getTarget(), equivalent_t.getTarget(), new_state1, new_state2, ips1_action , ips2_action, existing));                                
                                ips1_action.remove(t1.getAction());
                                ips2_action.remove(equivalent_t.getAction());
                            }
                        }
                    }
                    else{                       
                        State source = new BinaryState(new State("s"+new_state1), new State("t"+new_state2)),
                              target = new BinaryState(new State("s"+(new_state1+1)), new State("t"+new_state2));                                             
                        
                        if(checkInconsistency(ips1, ips2, t1.getTarget(), start2, true)!=null){                        
                            //creating the new transition and trying to add it to the collection
                            Transition new_t = new Transition(source, t1.getAction(), target);

                            boolean found = false;
                            for(j=0; j<existing.size(); j++){
                                if(existing.get(j).equals(new_t.toString())){
                                    found = true;
                                }
                            }

                            if(!found){                        
                                transitions.add(new_t);
                                existing.add(new_t.toString());

                                ips1_action.add(t1.getAction());

                                transitions.addAll(this.getComposedTransition(ips1, ips2, t1.getTarget(), start2, new_state1+1, new_state2, ips1_action , ips2_action, existing));
                                ips1_action.remove(t1.getAction());
                            }
                        }
                    }
                }
            }
        }
        
        for(j=0; j<ips2.getTransitions().size(); j++){
            //finding the starting state of the current branch
            if(ips2.getTransitions().get(j).getSource().equals(start2)){
                
                Transition t1 = ips2.getTransitions().get(j);                  
               
                if(!ips2_action.contains(t1.getAction())){
                    
                    if(common_actions.contains(t1.getAction())){
                        Transition equivalent_t = checkInconsistency(ips2, ips1, start2, start1,false);           
                        
                        if(equivalent_t!=null && t1.getAction() instanceof OutputAction){
                            
                            if(start1.equals(equivalent_t.getSource())){   
                                ips1_action.add(equivalent_t.getAction());
                                ips2_action.add(t1.getAction());
                                transitions.addAll(this.getComposedTransition(ips1, ips2, equivalent_t.getTarget(), t1.getTarget(), new_state1, new_state2, ips1_action , ips2_action, existing));
                                ips1_action.remove(equivalent_t.getAction());
                                ips2_action.remove(t1.getAction());
                            }
                        }
                    }
                    else{                       
                        
                        State source = new BinaryState(new State("s"+new_state1), new State("t"+new_state2)),
                              target = new BinaryState(new State("s"+new_state1), new State("t"+(new_state2+1)));                                            
                        
                        if(checkInconsistency(ips2, ips1, t1.getTarget(), start1, true)!=null){
                            Transition new_t = new Transition(source, t1.getAction(), target);
                            boolean found = false;
                            for(i=0; i<existing.size(); i++){
                                if(existing.get(i).equals(new_t.toString())){
                                    found = true;
                                }
                            }

                            if(!found){
                                transitions.add(new_t);                        
                                existing.add(new_t.toString());

                                ips2_action.add(t1.getAction());                        
                                transitions.addAll(this.getComposedTransition(ips1, ips2, start1, t1.getTarget(), new_state1, new_state2+1, ips1_action , ips2_action, existing));
                                ips2_action.remove(t1.getAction());
                            }
                        }
                    }
                }
            }
        }
       
        return transitions;
    }
    
    /**
     * Composition between two IPS. 
     * @param ips1
     * @param ips2
     * @return new Ips containing the product the two input
     */
    public Ips composition(Ips ips){
        Ips result = new Ips();
        List<State> states;
        List<Action> in_actions = new ArrayList(),
                     out_actions = new ArrayList(),
                     hidden_actions = new ArrayList();
        List<Transition> transitions = new ArrayList();
        int i,j;
        
        //Cartesian product of the two set of states
        states = BinaryState.cartesianProduct(this.getStates(), ips.getStates());
        //calculating the common actions
        List<Action> common_actions = common(this, ips);
        
        //adding all the input actions of the this, and removing the common ones
        in_actions.addAll(this.getInputs());
        in_actions.removeAll(common_actions);
        //adding all the input actions of the ips, and removing the common ones
        in_actions.addAll(ips.getInputs());
        in_actions.removeAll(common_actions);
        
        //adding all the output actions of the this, and removing the common ones
        out_actions.addAll(this.getOutputs());
        out_actions.removeAll(common_actions);
        //adding all the input actions of the ips, and removing the common ones
        out_actions.addAll(ips.getOutputs());
        out_actions.removeAll(common_actions);
        
        //adding the hidden actions to the resulting ips
        hidden_actions.addAll(this.getHiddens());
        hidden_actions.addAll(ips.getHiddens());
        //and we have to add all the common actions
        hidden_actions.addAll(common_actions);
        
        transitions = getComposedTransition(this, ips, this.getTransitions().get(0).getSource(), ips.getTransitions().get(0).getSource(), 0,0, new ArrayList(), new ArrayList(), new ArrayList());
        
        //Building the resulting IPS
        result.setStates(states);
        result.setInputs(in_actions);
        result.setOutputs(out_actions);
        result.setHiddens(hidden_actions);
        result.setTransitions(transitions);
        return result;
    }

    @Override
    public String toString() {
        return  "States: "+this.states+"\n"+
                "Input Actions: "+this.inputs+"\n"+
                "Output Actions: "+this.outputs+"\n"+
                "Hidden Actions: "+this.hiddens+"\n"+
                "Transitions: "+this.transitions+"\n";
    }
    
    
}
