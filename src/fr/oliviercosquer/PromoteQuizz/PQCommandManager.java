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

import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Olivier
 */
public class PQCommandManager {
    
    private ArrayList<String> commandList;
    private FileConfiguration config;
    
    public PQCommandManager(FileConfiguration config){
        this.config = config;
        this.commandList = new ArrayList<String>();
        this.initCommands();
    }
    
    private void initCommands(){        
        for(String cmd : this.config.getStringList("Commands")){
            this.commandList.add(cmd);
        }
    }
    
    public ArrayList<String> getCommandLst(){
        return this.commandList;
    }
}
