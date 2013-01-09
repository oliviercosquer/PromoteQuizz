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

package fr.oliviercosquer.PromoteQuizz.bukkit;

import fr.oliviercosquer.PromoteQuizz.PQChannelManager;
import fr.oliviercosquer.PromoteQuizz.PQPlayer;
import java.util.Iterator;
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
    private String alphabet = "abcdefghijklmnopqrstuvwxz";

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
            
            answer = alphabet.indexOf(event.getMessage().toLowerCase().toCharArray()[0]);
            
            event.setCancelled(true);
            this.channelManager.answerCommand(answer, player);
        } else {
            for (Iterator<Player> it = event.getRecipients().iterator(); it.hasNext();) {
                Player tmpPlayer = it.next();
                if (this.channelManager.playerExist(tmpPlayer) && player != tmpPlayer) {
                    it.remove();
                }
            }
        }
    }
}
