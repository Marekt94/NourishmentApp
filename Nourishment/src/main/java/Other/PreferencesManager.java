/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author Marek
 */
public class PreferencesManager {
    public static void clearPreferences(Class classType){
        Preferences pref = Preferences.userRoot().node(classType.getName());
        try {
            pref.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(PreferencesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> loadOmittedColumns(String [] prefixes, Class classType, List<String> list){
      if (list == null){return null;}
      list.clear();
      String prefix = createPrefix(prefixes);
      Preferences pref = Preferences.userRoot().node(classType.getName());
      Integer count = pref.getInt(prefix + "." + "count", 0);
      if (count == 0){
        return null;
      }
      for (Integer i = 0; i < count; i++) {
        String pom = pref.get(prefix + "." + i.toString(), ""); 
        list.add(pom);
      }
      return list;
    }
    
    public static void saveOmittedColumns(String [] prefixes, Class classType, List<String> list){
        Preferences pref = null;
        Integer count;
        pref = Preferences.userRoot().node(classType.getName());
        pref.putInt(createPrefix(prefixes) + "." + "count", list.size());
        for (Integer i = 0; i < list.size(); i++) {
            pref.put(createPrefix(prefixes) + "." + i.toString(), list.get(i));
        }
    }
    
    private static String createPrefix(String [] prefixes){
        String prefix = "";
        prefix = prefixes[prefixes.length - 1];
        for (int i = prefixes.length - 2; i >= 0; --i) {
            prefix = prefixes[i] + "." + prefix;
        }     
        return prefix;
    }
    
}
