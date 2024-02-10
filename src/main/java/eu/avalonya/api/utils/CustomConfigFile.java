package eu.avalonya.api.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfigFile
{
        private final String filename;
        private  File file;
        private FileConfiguration customFile;
        private final JavaPlugin javaInstance;

        public CustomConfigFile(JavaPlugin javaInstance, String filename, String key)
        {
            this.filename = filename;
            this.javaInstance = javaInstance;
            this.setup();
            ConfigFilesManager.putFile(key, this);
        }
        public void setup()
        {
            file = new File(this.javaInstance.getDataFolder(), this.filename);

            if (!file.exists())
            {
                this.javaInstance.saveResource(filename, false);
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
