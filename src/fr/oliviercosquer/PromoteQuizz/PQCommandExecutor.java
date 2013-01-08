/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Olivier
 */
public class PQCommandExecutor implements CommandExecutor {

    public PromoteQuizz plugin;
    private PQChannelManager channelManager;

    public PQCommandExecutor(PromoteQuizz plugin) {
        this.channelManager = plugin.getChannelManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender instanceof Player) {
            Player tmpPlayer = (Player) sender;
            PQPlayer player;

            //If there are arguments, like start or answer
            if (args.length == 1) {

                //Try to retrieve the player or create a new one
                if (this.channelManager.playerExist(tmpPlayer)) {
                    player = this.channelManager.getPlayer(tmpPlayer);
                } else {
                    player = new PQPlayer(tmpPlayer, 0);
                }

                //If the questionIndex is numeric then it's an answer else a command
                String cmdName = args[0].toLowerCase();

                switch (cmdName) {
                    case "start":
                        this.startCommand(player);
                        break;
                    case "restart":
                        this.restartCommand(player);
                        break;
                    case "recall":
                        this.recallCommand(player);
                        break;
                    case "stop":
                        this.stopCommand(player);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    private void startCommand(PQPlayer player) {
        if (this.channelManager.addPlayer(player)) {
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
        } else {
            this.channelManager.notInQuizzMsg(player);
        }
    }

}
