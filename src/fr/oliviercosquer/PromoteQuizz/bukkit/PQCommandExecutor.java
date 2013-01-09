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
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author Olivier
 */
public class PQCommandExecutor implements CommandExecutor {

    public PromoteQuizz plugin;
    private PQChannelManager channelManager;
    private Permission permission;

    public PQCommandExecutor(PromoteQuizz plugin) {
        this.channelManager = plugin.getChannelManager();
        RegisteredServiceProvider<Permission> permProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        this.permission = permProvider.getProvider();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender instanceof Player) {
            Player tmpPlayer = (Player) sender;
            PQPlayer player;

            //If there are arguments, like start or answer
            if (args.length == 1) {
                if(this.permission.has(tmpPlayer, "promoteQuizz.qcm")){
                    //Try to retrieve the player or create a new one
                    if (this.channelManager.playerExist(tmpPlayer)) {
                        player = this.channelManager.getPlayer(tmpPlayer);
                    } else {
                        player = new PQPlayer(tmpPlayer, 0);
                    }

                    //If the questionIndex is numeric then it's an answer else a command
                    String cmdName = args[0].toLowerCase();

                    if (cmdName.equals("start")) {
                        this.startCommand(player);
                        return true;
                    } else if (cmdName.equals("restart")) {
                        this.restartCommand(player);
                        return true;
                    } else if (cmdName.equals("recall")) {
                        this.recallCommand(player);
                        return true;
                    } else if (cmdName.equals("stop")) {
                        this.stopCommand(player);
                        return true;
                    }
                    return false;
                }else{
                    sender.sendMessage(ChatColor.RED+"permissions insuffisantes!");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void startCommand(PQPlayer player) {
        if (this.channelManager.addPlayer(player)) {
            this.channelManager.msgStart(player);
            this.channelManager.sendPlayerQuestion(player);
        } else {
            this.channelManager.alReadyInQuizzMsg(player);
        }
    }

    private void recallCommand(PQPlayer player) {
        if (this.channelManager.playerExist(player.getPlayer())) {
            this.channelManager.sendPlayerQuestion(player);
        } else {
            this.channelManager.notInQuizzMsg(player);
        }
    }

    private void restartCommand(PQPlayer player) {
        if (this.channelManager.playerExist(player.getPlayer())) {
            player.setCurrentQuestionIndex(0);
            this.channelManager.sendPlayerQuestion(player);
        } else {
            this.channelManager.notInQuizzMsg(player);
        }
    }

    private void stopCommand(PQPlayer player) {
        if (this.channelManager.playerExist(player.getPlayer())) {
            this.channelManager.removePlayer(player);
            this.channelManager.msgStop(player);
        } else {
            this.channelManager.notInQuizzMsg(player);
        }
    }

}
