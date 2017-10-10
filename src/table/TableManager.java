package table;
/**
 *
 * @author Vince
 */

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 *
 * @author 311015
 */
public class TableManager {
    
    HashMap<Integer,Table> managerMap;
    HashMap<String,String> mainRoster;
    Table table;
    static int tableID;
    
    public TableManager(LinkedHashMap<String,String> roster){
        managerMap = new HashMap<Integer,Table>();
        this.mainRoster = roster;
        tableID = 0;
    }
    
    
    public void addTable(){
        table = new Table(tableID,mainRoster);
        managerMap.put(tableID,table);
        tableID++;
    }
    
    public Table getTable(int ID){
        Table tb = managerMap.get(ID);
        return tb;
    }
    
}