package eu.avalonya.api.utils;

import java.util.HashMap;

public class ConfigFilesManager
{

    public static HashMap<String, CustomConfigFile> configFiles = new HashMap<>();

    public static CustomConfigFile getFile(String name)
    {
        return configFiles.get(name);
    }

    public static void putFile(String key, CustomConfigFile customFile)
    {
        configFiles.put(key, customFile);
    }

}
