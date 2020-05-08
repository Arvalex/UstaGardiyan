package me.Speretta.UstaGardiyan;

import java.text.DecimalFormat;

public class Util {
  private static DecimalFormat df2 = new DecimalFormat("#");
  public static String hesapla(Double d) {
    String s = "";
    
    double saniye = d.doubleValue() / 1000.0D;
    double dakika = saniye / 60.0D;
    saniye %= 60.0D;
    double saat = dakika / 60.0D;
    dakika %= 60.0D;
    double gun = saat / 24.0D;
    saat %= 24.0D;
    gun %= 24.0D;
    if (gun > 0.9D) {
      s = String.valueOf(s) + df2.format(Math.floor(gun)) + " g�n";
    }
    if (saat > 0.9D) {
      if (s.equalsIgnoreCase("")) {
        s = String.valueOf(s) + df2.format(Math.floor(saat)) + " saat";
      } else {
        s = String.valueOf(s) + " " + df2.format(Math.floor(saat)) + " saat";
      } 
    }
    
    if (dakika > 0.9D) {
      if (s.equalsIgnoreCase("")) {
        s = String.valueOf(s) + df2.format(Math.floor(dakika)) + " dakika";
      } else {
        s = String.valueOf(s) + " " + df2.format(Math.floor(dakika)) + " dakika";
      } 
    }
    if (saniye > 0.9D) {
      if (s.equalsIgnoreCase("")) {
        s = String.valueOf(s) + df2.format(Math.floor(saniye)) + " saniye";
      } else {
        s = String.valueOf(s) + " " + df2.format(Math.floor(saniye)) + " saniye";
      } 
    }
    return s;
  }
}
