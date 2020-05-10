package me.Speretta.UstaGardiyan;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class Olay
  implements Listener
{
  @EventHandler
  public void onJoin(PlayerLoginEvent e) {
    if (Veri.ymlCek().isSet(e.getPlayer().getName())) {
      if (Veri.ymlCek().getDouble(String.valueOf(e.getPlayer().getName()) + ".sure") - System.currentTimeMillis() <= 0.0D || e.getPlayer().hasPermission("UstaGardiyan.yetkili")) {
        Veri.ymlCek().set(e.getPlayer().getName(), null);
        Gardiyan.yasaklaniyor.remove(e.getPlayer().getUniqueId());
        Veri.ymlKaydet();
      } else {
        String sebep = Veri.ymlCek().getString(String.valueOf(e.getPlayer().getName()) + ".sebep");
        Double sure = Double.valueOf(Veri.ymlCek().getDouble(String.valueOf(e.getPlayer().getName()) + ".sure"));
        String s = "";
        for (Object yazi : Komut.yasakmesaj) {
          if (String.valueOf(yazi).contains("{0}")) {
            s = String.valueOf(s) + String.valueOf(yazi).replace("{0}", Util.hesapla(Double.valueOf(sure.doubleValue() - System.currentTimeMillis()))) + "\n"; continue;
          } 
          if (String.valueOf(yazi).contains("{1}")) {
            s = String.valueOf(s) + String.valueOf(yazi).replace("{1}", sebep) + "\n"; continue;
          } 
          s = String.valueOf(s) + String.valueOf(yazi) + "\n";
        } 



        
        e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        e.setKickMessage(s);
      } 
    }
  }





  
  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    if (Gardiyan.yasaklaniyor.contains(e.getPlayer().getUniqueId()) && (
      e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (Gardiyan.yasaklaniyor.contains(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPickUp(EntityPickupItemEvent e) {
    if (e.getEntity() instanceof org.bukkit.entity.Player && 
      Gardiyan.yasaklaniyor.contains(e.getEntity().getUniqueId())) {
      e.setCancelled(true);
    }
  }

  
  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    if (e.getPlayer() instanceof org.bukkit.entity.Player && 
      Gardiyan.yasaklaniyor.contains(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
    }
  }


  
  @EventHandler
  public void onDamagge(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof org.bukkit.entity.Player && 
      Gardiyan.yasaklaniyor.contains(e.getDamager().getUniqueId())) {
      e.setCancelled(true);
    }
  }


  
  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (e.getEntity() instanceof org.bukkit.entity.Player && 
      Gardiyan.yasaklaniyor.contains(e.getEntity().getUniqueId())) {
      e.setCancelled(true);
    }
  }

  
  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent e) {
    if (Gardiyan.yasaklaniyor.contains(e.getPlayer().getUniqueId()))
      e.setCancelled(true); 
  }
}
