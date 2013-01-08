/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.oliviercosquer.PromoteQuizz;

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
    private PQServerListener listener;
        
    @Override
    public void onEnable(){      
        this.saveDefaultConfig();
        this.getLogger().log(Level.INFO, "Promote Quizz enabled!");
        
        this.questionManager = new PQQuestionManager(this.getConfig());
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
    
}
