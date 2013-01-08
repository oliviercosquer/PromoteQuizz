/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import java.util.ArrayList;

/**
 *
 * @author Olivier
 */
public class PQQuestion {
    
    private String question;
    private int rightAnswer;

    public PQQuestion(String question, int rightAnswer, ArrayList<String> answerList) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        
        //Create the question
        for(Integer i = 0; i < answerList.size(); i++){
            Integer j = i + 1;
            this.question += "\n"+j.toString()+"- "+answerList.get(i);
            
        }
    }    

    public boolean isRightAnswer(int answerIndex){
        return this.rightAnswer == answerIndex;
    }

    @Override
    public String toString() {
        return question;
    }
    
    
}
