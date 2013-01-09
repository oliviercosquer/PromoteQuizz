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

import org.bukkit.entity.Player;

/**
 *
 * @author Olivier
 */
public class PQPlayer {
    
    private Player player;
    private int currentQuestionIndex;

    /**
     * 
     * @param player
     * @param currentQuestionIndex 
     */
    public PQPlayer(Player player, int currentQuestionIndex) {
        this.player = player;
        this.currentQuestionIndex = currentQuestionIndex;
    }
    
    /**
     * Give the player question's index
     * @return 
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
    
    /**
     * Change the question index of the player
     * @param currentQuestionIndex 
     */
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }   
    
    /**
     * 
     * @return org.bukkit.entity.Player
     */
    public Player getPlayer() {
        return this.player;        
    }  
    
}
