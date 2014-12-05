package mtsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    static public int taskId = 0;
    static public int uav_id = 0;
    public static int maxTaskAssignment = 15;
    static public int totalTask = 80;
    static public int totalUAV = 20;
    static public int maxITeration = 2500;
    static public int maxTaskDurationTime = 100;
    static Map<UAVType, Integer> airtime = new HashMap<UAVType, Integer>();
    static Map<UAVType, Integer> uavMaxSpeed = new HashMap<UAVType, Integer>();
    private static ArrayList<UAV> uavList = new ArrayList<UAV>();

    public static ArrayList<UAV> getUavList() {
        return uavList;
    }

    public static void addUav(UAV uav) {
        uavList.add(uav);
    }

    public static void setUavList(ArrayList uavList) {
        uavList = uavList;
    }

    public static int numberOfUAVs() {
        return uavList.size();
    }

    public static Map<UAVType, Integer> getUAVTypeAirtime() {
        if (airtime.isEmpty()) {
            airtime.put(UAVType.miniUAV, 60);
            airtime.put(UAVType.tacticUAV, 120);
            airtime.put(UAVType.strategicUAV, 180);
            airtime.put(UAVType.operatifUAV, 240);
        }
        return airtime;
    }

    public static Map<UAVType, Integer> getUAVTypeMaxSpeed() {
        if (uavMaxSpeed.isEmpty()) {
            uavMaxSpeed.put(UAVType.miniUAV, 50);
            uavMaxSpeed.put(UAVType.tacticUAV, 100);
            uavMaxSpeed.put(UAVType.strategicUAV, 150);
            uavMaxSpeed.put(UAVType.operatifUAV, 250);
        }
        return uavMaxSpeed;
    }
}
