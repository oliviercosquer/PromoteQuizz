/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Olivier
 */
public class PQServerListener implements Listener {

    public PromoteQuizz plugin;
    private PQChannelManager channelManager;

    public PQServerListener(PromoteQuizz plugin) {
        this.plugin = plugin;
        this.channelManager = plugin.getChannelManager();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {        
        PQPlayer player = this.channelManager.getPlayer(event.getPlayer());
        int answer;
        //Checking if sender is in quizz
        if (this.channelManager.playerExist(event.getPlayer())) {  
            char cAnswer = event.getMessage().toCharArray()[0];
            if(Character.isDigit(cAnswer) && event.getMessage().length() == 1){
                answer = Integer.parseInt(Character.toString(cAnswer));
            }else{
                answer = -1;
            }
            
            event.setCancelled(true);
            this.channelManager.answerCommand(answer, player);
        } else {

            //Remove all player in quizz from recipients
            for (Player tmpPlayer : event.getRecipients()) {
                if (this.channelManager.playerExist(tmpPlayer) && player != tmpPlayer) {
                    event.getRecipients().remove(tmpPlayer);
                }
            }
        }
    }
}
