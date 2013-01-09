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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Olivier
 */
public class PQQuestionManager {
    
    private ArrayList<PQQuestion> questionsList;
    private FileConfiguration config;

    /**
     * 
     * @param config 
     */
    public PQQuestionManager(FileConfiguration config) {
        this.config = config;
        this.questionsList = new ArrayList<PQQuestion>();
        this.initQuestion();
    }
    
    /**
     * Get a question by index
     * @param currentIndex
     * @return 
     */
    public PQQuestion getQuestion(int currentIndex){
        if(currentIndex <= this.questionsList.size()){            
            return this.questionsList.get(currentIndex);
        }
        return null;
    }
    
    /**
     * Return the amount of questions in the quizz
     * @return 
     */
    public Integer getQuestionsCount(){
        return this.questionsList.size();
    }
    
    /**
     * Load the question in the config.yml
     */
    private void initQuestion(){
        ConfigurationSection qList = this.config.getConfigurationSection("questions");
        
        //For each question
        for(String questionKey : qList.getKeys(false)){
            ConfigurationSection qSection = this.config.getConfigurationSection("questions."+questionKey);
            
            String question = qSection.getString("question");
            int rightAnswer = qSection.getInt("rightAnswer");
            ArrayList<String> answerList = new ArrayList<String>();
            
            //For each answer in the question
            for(String anwser : qSection.getStringList("answers")){
                answerList.add(anwser);
            }
                        
            //Create new question object
            this.questionsList.add(new PQQuestion(question,rightAnswer,answerList));
        }
    }
}
