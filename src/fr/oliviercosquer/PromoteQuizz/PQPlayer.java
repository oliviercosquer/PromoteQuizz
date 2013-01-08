/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import org.bukkit.entity.Player;

/**
 *
 * @author Olivier
 */
public class PQPlayer {
    
    private Player player;
    private int currentQuestionIndex;

    public PQPlayer(Player player, int currentQuestionIndex) {
        this.player = player;
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }   
    
    public Player getPlayer() {
        return player;
    }  
    
}
