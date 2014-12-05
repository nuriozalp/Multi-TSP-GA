
package mtsp;


public class UAV {
    int id;
    int x;
    int y;
    UAVType type;
    int maxSpeed;

    public UAV(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
        public UAV(int id, int x, int y, int type) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = UAVType.values()[type];
        maxSpeed=Utils.getUAVTypeMaxSpeed().get(this.type);
    }
    
}
