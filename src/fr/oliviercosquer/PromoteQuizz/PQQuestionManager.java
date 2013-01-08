/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Olivier
 */
public class PQQuestionManager {
    
    private ArrayList<PQQuestion> questionsList;
    private FileConfiguration config;

    public PQQuestionManager(FileConfiguration config) {
        this.config = config;
        this.questionsList = new ArrayList<>();
        this.initQuestion();
    }
    
    public PQQuestion getQuestion(int currentIndex){
        if(currentIndex <= this.questionsList.size()){            
            return this.questionsList.get(currentIndex);
        }
        return null;
    }
    
    public Integer getQuestionsCount(){
        return this.questionsList.size();
    }
    
    private void initQuestion(){
        ConfigurationSection qList = this.config.getConfigurationSection("questions");
        
        //For each question
        for(String questionKey : qList.getKeys(false)){
            ConfigurationSection qSection = this.config.getConfigurationSection("questions."+questionKey);
            
            String question = qSection.getString("question");
            int rightAnswer = qSection.getInt("rightAnswer");
            ArrayList<String> answerList = new ArrayList<>();
            
            //For each answer in the question
            for(String answerKey : qSection.getConfigurationSection("answers").getKeys(false)){
                answerList.add(qSection.getString("answers."+answerKey));
            }
            
            //Create new question object
            this.questionsList.add(new PQQuestion(question,rightAnswer,answerList));
        }
    }
}
