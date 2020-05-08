package me.Speretta.UstaGardiyan;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  private static Main instance;
  
  public static Main getInstance() { return instance; }
  
  public Komut komut;
  public Gardiyan gardiyan;
  public Veri veri;
  public Olay olay;
  public Util util;
  
  public void onEnable() {
    instance = this;
    this.komut = new Komut();
    this.gardiyan = new Gardiyan();
    this.veri = new Veri();
    this.olay = new Olay();
    this.util = new Util();
    Bukkit.getPluginManager().registerEvents(this.olay, this);
    getCommand("ug").setExecutor(this.komut);
    getCommand("ugc").setExecutor(this.komut);
    saveDefaultConfig();
  }
  
  public void onDisable() {}
}
