package me.Speretta.UstaGardiyan;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;








public class Veri
{
  Main plugin = Main.getInstance(); private static FileConfiguration maincfg; public Veri() {
    if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdir(); 
    mainfile = new File(this.plugin.getDataFolder(), "Yasaklananlar.yml");
    if (!mainfile.exists())
      try { mainfile.createNewFile(); }
      catch (IOException e)
      { Bukkit.getServer().getConsoleSender().sendMessage("[UstatGardiyan] Config oluşturulurken hata oluştu!"); }
       
    maincfg = YamlConfiguration.loadConfiguration(mainfile);
  }
  public static File mainfile;
  public static FileConfiguration ymlCek() { return maincfg; }

  
  public static void ymlKaydet() {
    try {
      maincfg.save(mainfile.getPath());
    } catch (IOException e) {
      Bukkit.getServer().getConsoleSender().sendMessage("[UstatGardiyan] Config kaydedilirken hata oluştu!");
    } 
  }
}
