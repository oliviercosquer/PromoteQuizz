/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Olivier
 */
public class PQChannelManager {
    
    private HashMap<UUID,PQPlayer> playerList;    
    private PQQuestionManager questionManager;
    private FileConfiguration config;
    //Quizz messages string
    private String startMsg;
    private String stopMsg;
    private String badAnswerMsg;
    private String goodAnswerMsg;
    private String alReadyInQuizzMsg;
    private String notInQuizzMsg;

    public PQChannelManager(PromoteQuizz plugin) {
        this.playerList = new HashMap<>();
        this.config = plugin.getConfig();
        this.questionManager = plugin.getQuestionManager();
        
        //Quizz messages loading
        this.startMsg = this.config.getString("quizzMessage.StartMsg");
        this.stopMsg = this.config.getString("quizzMessage.StopMsg");
        this.badAnswerMsg = this.config.getString("quizzMessage.BadAnswerMsg");
        this.goodAnswerMsg = this.config.getString("quizzMessage.GoodAnswerMsg");
        this.alReadyInQuizzMsg = this.config.getString("quizzMessage.AlReadyInQuizzMsg");
        this.notInQuizzMsg = this.config.getString("quizzMessage.NotInAQuizzMsg");
    }
    
    public boolean addPlayer(PQPlayer player){
        if(!this.playerExist(player.getPlayer())){
            this.playerList.put(player.getPlayer().getUniqueId(), player);
            return true;
        }
        
        return false;
    }
    
    public void answerCommand(int index, PQPlayer player) {
        if (this.playerExist(player.getPlayer())) {
            if (this.getQuestionManager().getQuestion(player.getCurrentQuestionIndex()).isRightAnswer(index)) {
                
                player.setCurrentQuestionIndex(player.getCurrentQuestionIndex() + 1);
                this.goodAnswerMsg(player);
                
                //If the player reach the last question
                if (player.getCurrentQuestionIndex() == this.getQuestionManager().getQuestionsCount()) {
                    //ToDo perform command
                    this.removePlayer(player);
                } else {
                    this.sendPlayerQuestion(player);
                }

            } else {
                player.setCurrentQuestionIndex(0);
                this.sendPlayerQuestion(player);
                this.badAnswerMsg(player);
            }
        } else {
            this.notInQuizzMsg(player);
        }
    }
    
    public PQPlayer getPlayer(Player player){
        return (PQPlayer)this.playerList.get(player.getUniqueId());
    }
    
    public void removePlayer(PQPlayer player){
        this.playerList.remove(player.getPlayer().getUniqueId());
    }
    
    public boolean playerExist(Player player){
        return this.playerList.containsKey(player.getUniqueId());
    }
    
    public void sendPlayerQuestion(PQPlayer player){
        player.getPlayer().sendMessage(this.questionManager.getQuestion(player.getCurrentQuestionIndex()).toString());
    }
    
    public PQQuestionManager getQuestionManager() {
        return questionManager;
    }
    
    public void msgStart(PQPlayer player){
        player.getPlayer().sendMessage(startMsg);
    }
    
    public void msgStop(PQPlayer player){
        player.getPlayer().sendMessage(this.stopMsg);
    }
    
    public void badAnswerMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.badAnswerMsg);
    }
    
    public void goodAnswerMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.goodAnswerMsg);
    }
    
    public void alReadyInQuizzMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.alReadyInQuizzMsg);
    }
    
    public void notInQuizzMsg(PQPlayer player){
        player.getPlayer().sendMessage(this.notInQuizzMsg);
    }
    
}
