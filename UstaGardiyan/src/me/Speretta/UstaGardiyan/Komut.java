package me.Speretta.UstaGardiyan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class Komut
  implements CommandExecutor
{
  static Main plugin;
  private static String[] yazilar = new String[16];
  public static List<?> yasakmesaj = new ArrayList<>();
  private final File configfile;
  
  public Komut() {
    plugin = Main.getInstance();
    this.configfile = new File(plugin.getDataFolder(), "config.yml");
    yazilariAta();
  }
  public void yazilariAta() {
    yazilar[0] = Main.getInstance().getConfig().getString("yazilar.sebep");
    yazilar[1] = Main.getInstance().getConfig().getString("yazilar.yetkiyok");
    yazilar[2] = Main.getInstance().getConfig().getString("yazilar.komuthatirlatma");
    yazilar[3] = Main.getInstance().getConfig().getString("yazilar.komut-oyuncu-0-yerine");
    yazilar[4] = Main.getInstance().getConfig().getString("yazilar.komut-sure-1-yerine");
    yazilar[5] = Main.getInstance().getConfig().getString("yazilar.yasak-kaldirildi");
    yazilar[6] = Main.getInstance().getConfig().getString("yazilar.yasak-yok");
    yazilar[7] = Main.getInstance().getConfig().getString("yazilar.yasaklandi");
    yazilar[8] = Main.getInstance().getConfig().getString("yazilar.dis-yasaklandi");
    yazilar[9] = Main.getInstance().getConfig().getString("yazilar.dis-yasaklandi");
    yazilar[10] = Main.getInstance().getConfig().getString("yazilar.sayi-hatali");
    yazilar[11] = Main.getInstance().getConfig().getString("yazilar.tip-hatali");
    yazilar[12] = Main.getInstance().getConfig().getString("yazilar.ugc");
    yazilar[13] = Main.getInstance().getConfig().getString("yazilar.ugc-reload");
    yazilar[14] = Main.getInstance().getConfig().getString("yazilar.ugc-restart");
    yazilar[15] = Main.getInstance().getConfig().getString("yazilar.ugc-plugin");
    yasakmesaj = Main.getInstance().getConfig().getList("yazilar.yasak-mesaj");
  }


  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("ugc")) {
      if (sender.hasPermission("UstaGardiyan.komut")) {
        if (args.length > 0) {
          if (args[0].equalsIgnoreCase("restart")) {
            this.configfile.delete();
            Main.getInstance().saveDefaultConfig();
            yazilariAta();
            sender.sendMessage(yazilar[14]);
            return true;
          }  if (args[0].equalsIgnoreCase("reload")) {
            Main.getInstance().reloadConfig();
            yazilariAta();
            sender.sendMessage(yazilar[13]);
            return true;
          }  if (args[0].equalsIgnoreCase("plugin")) {
            Main.getInstance().getPluginLoader().disablePlugin(plugin);
            Main.getInstance().getPluginLoader().enablePlugin(plugin);
            sender.sendMessage(yazilar[15]);
            return true;
          } 
        } else {
          sender.sendMessage(yazilar[12]);
          return true;
        } 
      } else {
        sender.sendMessage(yazilar[1]);
        return true;
      } 
    }

    
    if (cmd.getName().equalsIgnoreCase("ug")) {
      if (sender.hasPermission("UstatGardiyan.komut")) {
        String suretipi = "";
        String sebep = yazilar[0];
        double sure = 0.0D;
        if (args.length < 1) {
          sender.sendMessage(yazilar[2].replace("{0}", yazilar[3]).replace("{1}", yazilar[4]));
          return true;
        }  if (args.length == 1) {
          if (Veri.ymlCek().isSet(args[0])) {
            Veri.ymlCek().set(args[0], null);
            Veri.ymlKaydet();
            sender.sendMessage(yazilar[5].replace("{0}", args[0]));
            return true;
          } 
          sender.sendMessage(yazilar[6].replace("{0}", args[0]));
          
          sender.sendMessage(yazilar[2].replace("{0}", args[0]).replace("{1}", yazilar[4]));
          return true;
        }  if (args.length > 1) {
          if (args[1].endsWith("sn") || args[1].endsWith("dk") || args[1].endsWith("sa") || args[1].endsWith("gün")) {
            suretipi = args[1].substring(args[1].length() - 2);
            if (args[1].endsWith("g�n")) {
              suretipi = args[1].substring(args[1].length() - 3);
            }
            
            try {
              sure = Double.parseDouble(args[1].replace(suretipi, ""));
              if (args.length > 2) {
                sebep = "";
                for (int i = 2; i < args.length; i++) {
                  sebep = String.valueOf(sebep) + args[i] + " ";
                }
              }
            
            } catch (NumberFormatException e) {
              sender.sendMessage(yazilar[10]);
              sender.sendMessage(yazilar[2].replace("{0}", args[0]).replace("{1}", args[1]));
              return true;
            }
          
          } else if (args.length > 2) {
            if (args[2].equalsIgnoreCase("sn") || args[2].equalsIgnoreCase("dk") || args[2].equalsIgnoreCase("sa") || args[2].equalsIgnoreCase("gün")) {
              suretipi = args[2];
              try {
                sure = Double.parseDouble(args[1]);
                if (args.length > 3) {
                  sebep = "";
                  for (int i = 3; i < args.length; i++) {
                    sebep = String.valueOf(sebep) + args[i] + " ";
                  }
                }
              
              } catch (NumberFormatException e) {
                sender.sendMessage(yazilar[10]);
                sender.sendMessage(yazilar[2].replace("{0}", args[0]).replace("{1}", args[1]));
                return true;
              } 
            } else {
              sender.sendMessage(yazilar[11]);
              sender.sendMessage(yazilar[2].replace("{0}", args[0]).replace("{1}", args[1]));
              return true;
            } 
          } else {
            sender.sendMessage(yazilar[11]);
            sender.sendMessage(yazilar[2].replace("{0}", args[0]).replace("{1}", args[1]));
            return true;
          } 
        }
        
        if (suretipi.contains("sn")) {
          sure *= 1000.0D;
        } else if (suretipi.contains("dk")) {
          sure *= 60000.0D;
        } else if (suretipi.contains("sa")) {
          sure *= 3600000.0D;
        } else if (suretipi.contains("gün")) {
          sure *= 8.64E7D;
        } 
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (args[0].equalsIgnoreCase(p.getName())) {
            if (sure > 0.0D) {
              Double suan = Double.valueOf(System.currentTimeMillis());
              if (!p.hasPermission("UstatGardiyan.yetkili")) {
                Veri.ymlCek().set(String.valueOf(p.getName()) + ".sure", Double.valueOf(sure + suan.doubleValue()));
                Veri.ymlCek().set(String.valueOf(p.getName()) + ".sebep", sebep);
                Veri.ymlKaydet();
              } 
              Gardiyan.gardiyanCagir(p, Double.valueOf(sure), sebep);
              
              return true;
            }  if (Veri.ymlCek().isSet(p.getName())) {
              Veri.ymlCek().set(p.getName(), null);
              Veri.ymlKaydet();
              return true;
            } 
          } 
        }  
        for (OfflinePlayer p: Bukkit.getOfflinePlayers() ) 
        {
          if (args[0].equalsIgnoreCase(p.getName())) {
            Double suan = Double.valueOf(System.currentTimeMillis());
            Veri.ymlCek().set(String.valueOf(p.getName()) + ".sure", Double.valueOf(sure + suan.doubleValue()));
            Veri.ymlCek().set(String.valueOf(p.getName()) + ".sebep", sebep);
            Veri.ymlKaydet();
            sender.sendMessage(yazilar[8].replace("{0}", args[0]));
            return true;
          }  
          }
      
      } else {
        sender.sendMessage(yazilar[1]);
        return true;
      } 
    }
    
    return true;
  }
}
