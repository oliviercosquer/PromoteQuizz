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
import fr.oliviercosquer.PromoteQuizz.PQCommandManager;
import fr.oliviercosquer.PromoteQuizz.PQQuestionManager;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Olivier
 */
public class PromoteQuizz extends JavaPlugin{
    
    private PQCommandExecutor commandExecutor;
    private PQChannelManager channelManager;
    private PQQuestionManager questionManager;
    private PQCommandManager commandManager;
    private PQServerListener listener;
        
    @Override
    public void onEnable(){      
        this.saveDefaultConfig();
        this.getLogger().log(Level.INFO, "Promote Quizz enabled!");
        
        this.questionManager = new PQQuestionManager(this.getConfig());
        this.commandManager = new PQCommandManager(this.getConfig());
        this.channelManager = new PQChannelManager(this);
        
        this.commandExecutor = new PQCommandExecutor(this);
        this.getCommand("qcm").setExecutor(this.commandExecutor);

        this.listener = new PQServerListener(this);
        
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    
    @Override
    public void onDisable(){
        this.getLogger().log(Level.INFO, "Promote Quizz disabled!");
    }

    public PQChannelManager getChannelManager() {
        return channelManager;
    }

    public PQQuestionManager getQuestionManager() {
        return questionManager;
    }

    public PQCommandManager getCommandManager() {
        return commandManager;
    }
    
}
