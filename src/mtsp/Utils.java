/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsp;

import java.util.ArrayList;

/**
 *
 * @author nuri
 */
public class Utils {

    static public int task_id = 0;
    static public int uav_id = 0;

    // Holds our cities
    private static ArrayList uavList = new ArrayList<UAV>();

    public static ArrayList getUavList() {
        return uavList;
    }

    public static void addUav(UAV uav) {
        uavList.add(uav);
    }

    public static void setUavList(ArrayList uavList) {
        uavList = uavList;
    }

    // Get the number of destination cities

    public static int numberOfUAVs() {
        // return uavList.size();
        return uavList.size();
    }
}
