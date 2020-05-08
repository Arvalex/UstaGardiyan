package me.Speretta.UstaGardiyan;

import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.server.v1_15_R1.EntityGuardian;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftGuardian;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class Gardiyan
{
  public static ArrayList<UUID> yasaklaniyor = new ArrayList<UUID>();
  public static void gardiyanCagir(final Player p, final Double sure, final String sebep) {
    if (!p.hasPermission("UstaGardiyan.yetkili")) {
      p.teleport(p.getWorld().getSpawnLocation());
      yasaklaniyor.add(p.getUniqueId());
    } 
    final ArrayList<EntityGuardian> _gardiyanlar = new ArrayList<EntityGuardian>();
    final ArrayList<Location> daire = getCircle(p.getLocation().add(0.0D, 4.0D, 0.0D), 4.0D, 120);
    final EntityPlayer ep = ((CraftPlayer)p).getHandle();
    for (int i = 0; i < 4; i++) {
      Guardian s = (Guardian)p.getWorld().spawn((Location)daire.get(i * 30), Guardian.class);
      s.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(999.0D);
      s.setHealth(999.0D);
      EntityGuardian entity = (EntityGuardian)((CraftGuardian)s).getHandle();
      entity.setSilent(true);
      try {
        entity.setGoalTarget(ep, EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
      } catch (Exception exception) {}

      
      entity.setNoGravity(true);
      _gardiyanlar.add(entity);
    } 

    
    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
          public void run() {
            int[] i = { 0, 30, 60, 90,0};
            (new BukkitRunnable()
              {
                public void run() {
                  for (int j = 0; j < 4; j++) {
                    _gardiyanlar.get(j).setLocation(daire.get(i[j]).getX(), daire.get(i[j]).getY(), daire.get(i[j]).getZ(), _gardiyanlar.get(j).getBukkitYaw(), _gardiyanlar.get(j).pitch);
                    
                    _gardiyanlar.get(j).setGoalTarget(ep, EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
                    
                    i[j] = i[j] + 2;
                    if (i[j] == 120) {
                      i[j] = 0;
                      i[4] = i[4] + 1;
                      if (i[4] > 8) {
                        ArrayList<Location> tnt = Gardiyan.getCircle(p.getLocation(), 1.5D, 180);
                        World w = p.getWorld();
                        if (!p.hasPermission("UstatGardiyan.yetkili")) {
                          String s = "";
                          for (Object yazi : Komut.yasakmesaj) {
                            if (String.valueOf(yazi).contains("{0}")) {
                              s = String.valueOf(s) + String.valueOf(yazi).replace("{0}", Util.hesapla(sure)) + "\n"; continue;
                            } 
                            if (String.valueOf(yazi).contains("{1}")) {
                              s = String.valueOf(s) + String.valueOf(yazi).replace("{1}", sebep) + "\n"; continue;
                            } 
                            s = String.valueOf(s) + String.valueOf(yazi) + "\n";
                          } 
                          
                          p.kickPlayer(s);
                        } 
                        int b = 0;
                        for (Location tn : tnt) {
                          if (b % 10 == 0) {
                            w.createExplosion(tn, 0.0F, false, false);
                          }
                          if (b % 2 == 0) {
                            w.spawnParticle(Particle.EXPLOSION_HUGE, tn.add(0.0D, 0.5D, 0.0D), 1);
                          }
                          w.spawnParticle(Particle.EXPLOSION_HUGE, tn, 1);
                          b++;
                        } 

                        
                        for (EntityGuardian u : _gardiyanlar) {
                          u.setLocation(0.0D, -100.0D, 0.0D, 0.0F, 0.0F);
                        }
                        Gardiyan.yasaklaniyor.remove(p.getUniqueId());
                        cancel();
                      } 
                    } 
                  } 
                }
              }).runTaskTimer(Main.getInstance(), 0L, 1L);
          }
        },40L);
  }




  
  public static ArrayList<Location> getCircle(Location center, double radius, int amount) {
    World world = center.getWorld();
    double increment = 6.283185307179586D / amount;
    ArrayList<Location> locations = new ArrayList<Location>();
    for (int i = 0; i < amount; i++) {
      
      double angle = i * increment;
      double x = center.getX() + radius * Math.cos(angle);
      double z = center.getZ() + radius * Math.sin(angle);
      locations.add(new Location(world, x, center.getY(), z));
    } 
    return locations;
  }
}
