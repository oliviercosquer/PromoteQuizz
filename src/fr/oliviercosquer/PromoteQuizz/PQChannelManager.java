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

import fr.oliviercosquer.PromoteQuizz.bukkit.PromoteQuizz;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Olivier
 */
public class PQChannelManager {
    
    private HashMap<String,PQPlayer> playerList;    
    private PQQuestionManager questionManager;
    private PQCommandManager commandManager;
    private FileConfiguration config;    
    
    //Quizz messages string
    private String startMsg;
    private String stopMsg;
    private String badAnswerMsg;
    private String goodAnswerMsg;
    private String alReadyInQuizzMsg;
    private String notInQuizzMsg;

    /**
     * 
     * @param plugin 
     */
    public PQChannelManager(PromoteQuizz plugin) {
        this.playerList = new HashMap<String,PQPlayer>();
        this.config = plugin.getConfig();
        this.questionManager = plugin.getQuestionManager();
        this.commandManager = plugin.getCommandManager();      
        
        //Quizz messages loading
        this.startMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.StartMsg"));
        this.stopMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.StopMsg"));
        this.badAnswerMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.BadAnswerMsg"));
        this.goodAnswerMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.GoodAnswerMsg"));
        this.alReadyInQuizzMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.AlReadyInQuizzMsg"));
        this.notInQuizzMsg = ChatColor.translateAlternateColorCodes('&', this.config.getString("quizzMessage.NotInAQuizzMsg"));
    }
    
    /**
     * 
     * @param player
     * @return true if the player was added or false if the player already exist
     */
    public boolean addPlayer(PQPlayer player){
        if(!this.playerExist(player.getPlayer())){
            this.playerList.put(player.getPlayer().getName(), player);
            return true;
        }
        
        return false;
    }
    
    /**
     * Check the answer of the question
     * @param index
     * @param player 
     */
    public void answerCommand(int index, PQPlayer player) {
        if (this.playerExist(player.getPlayer())) {
            if (this.questionManager.getQuestion(player.getCurrentQuestionIndex()).isRightAnswer(index+1)) {
                
                player.setCurrentQuestionIndex(player.getCurrentQuestionIndex() + 1);
                this.goodAnswerMsg(player);
                
                //If the player reach the last question
                if (player.getCurrentQuestionIndex() == this.questionManager.getQuestionsCount()) {
                    this.removePlayer(player);
                    this.executeCommand(player.getPlayer());
                } else {
                    this.sendPlayerQuestion(player);
                }

            } else {
                player.setCurrentQuestionIndex(0);
                this.badAnswerMsg(player);
                this.sendPlayerQuestion(player);
                
            }
        } else {
            this.notInQuizzMsg(player);
        }
    }
    
    /**
     * Execute all the commands defined in the config.yml
     * @param player 
     */
    public void executeCommand(Player player){
        String playerName = player.getName();
        String tmpCmd;
        
        for(String cmd : this.commandManager.getCommandLst()){
            tmpCmd = cmd.replace("{player}", playerName);
            player.getServer().dispatchCommand(player.getServer().getConsoleSender(), tmpCmd);
        }
    }
    
    /**
     * 
     * @param player
     * @return 
     */
    public PQPlayer getPlayer(Player player){
        return (PQPlayer)this.playerList.get(player.getName());
    }
    
    /**
     * 
     * @param player 
     */
    public void removePlayer(PQPlayer player){
        this.playerList.remove(player.getPlayer().getName());
    }
    
    /**
     * 
     * @param player
     * @return 
     */
    public boolean playerExist(Player player){
        return this.playerList.containsKey(player.getName());
    }
    
    /**
     * Ask a question to the player
     * @param player 
     */
    public void sendPlayerQuestion(PQPlayer player){
        player.getPlayer().sendMessage(this.questionManager.getQuestion(player.getCurrentQuestionIndex()).toString());
    }
    
    /**
     * 
     * @param player 
     */
    public void msgStart(PQPlayer player){
        player.getPlayer().sendMessage(startMsg);
    }
    
    /**
     * 
     * @param player 
     */
    public void msgStop(PQPlayer player){
        player.getPlayer().sendMessage(this.stopMsg);
    }
    
    /**
     * 
     * @param player 
     */
    public void badAnswerMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.badAnswerMsg);
    }
    
    /**
     * 
     * @param player 
     */
    public void goodAnswerMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.goodAnswerMsg);
    }
    
    /**
     * 
     * @param player 
     */
    public void alReadyInQuizzMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.alReadyInQuizzMsg);
    }
    
    /**
     * 
     * @param player 
     */
    public void notInQuizzMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.notInQuizzMsg);
    }   
    
}
