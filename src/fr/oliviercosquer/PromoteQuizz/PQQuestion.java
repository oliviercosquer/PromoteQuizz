/*
Copyright 2013 - Olivier Cosquer - http://www.olivier-cosquer.com

 This file is part of PromoteQuizz.

    PromoteQuizz is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PromoteQuizz is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PromoteQuizz.  If not, see <http://www.gnu.org/licenses/>.
*/

package fr.oliviercosquer.PromoteQuizz;

import java.util.ArrayList;
import org.bukkit.ChatColor;

/**
 *
 * @author Olivier
 */
public class PQQuestion {
    
    private String question;
    private int rightAnswer;    
    private String alphabet = "abcdefghijklmnopqrstuvwxz";

    /**
     * 
     * @param question
     * @param rightAnswer
     * @param answerList 
     */
    public PQQuestion(String question, int rightAnswer, ArrayList<String> answerList,ChatColor questionColor,ChatColor answerColor) {
        this.question = questionColor+question+answerColor;
        this.rightAnswer = rightAnswer;
        
        //Create the question
        for(Integer i = 0; i < answerList.size(); i++){
            Integer j = i + 1;
            this.question += "\n"+alphabet.toUpperCase().charAt(i) +"- "+answerList.get(i);
            
        }
    }    

    /**
     * Check if the answer is right
     * @param answerIndex
     * @return 
     */
    public boolean isRightAnswer(int answerIndex){
        return this.rightAnswer == answerIndex;
    }

    @Override
    public String toString() {
        return question;
    }
    
    
}
