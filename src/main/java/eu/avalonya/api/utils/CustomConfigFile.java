package eu.avalonya.api.utils;

import eu.avalonya.api.AvalonyaAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfigFile
{
        private String filename;
        private  File file;
        private FileConfiguration customFile;

        public CustomConfigFile(String filename)
        {
            this.filename = filename;
            this.setup();
            ConfigFilesManager.putFile("sql", this);
        }
        public void setup()
        {
            file = new File(AvalonyaAPI.getInstance().getDataFolder(), this.filename);

            if (!file.exists())
            {
                AvalonyaAPI.getInstance().saveResource(filename, false);
            }
            this.customFile = YamlConfiguration.loadConfiguration(file);
        }

        public FileConfiguration get()
        {
            return this.customFile;
        }
        public  void save()
        {
            try
            {
                this.customFile.save(file);
            }
            catch (IOException e){
                System.out.println("Couldn't save file");
            }
        }

        public void reload()
        {
            this.customFile = YamlConfiguration.loadConfiguration(this.file);
        }

}
