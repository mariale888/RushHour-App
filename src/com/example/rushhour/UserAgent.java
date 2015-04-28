import java.sql.Timestamp;
import java.util.UUID;

public class UserAgent {

    UUID ID;
    float src_lat;
    float src_long;
    float dest_lat;
    float dest_long;
    Timestamp time_window_start;
    Timestamp time_window_end;

    UserAgent()
    {
        ID = UUID.randomUUID();
    }

    float [] get_source_location(){
        float [] src_location =  {src_lat, src_long};
        return src_location;
    }

    float [] get_dest_location(){
        float [] dest_location =  {dest_lat, dest_long};
        return dest_location;
    }

    Timestamp[] get_time_window(){
        Timestamp [] time_window = {time_window_start,time_window_end};
        return time_window;
    }

    void set_time_window(Timestamp ts, Timestamp te){
        time_window_start = ts;
        time_window_end = te;
    }

    void set_src_location(float src_location[]){
        src_lat = src_location[0];
        src_long = src_location[1];
    }

    void set_dest_location(float dest_location[]){
        dest_lat = dest_location[0];
        dest_long = dest_location[1];
    }

    //Example usage
    public static void main(String[] args) {
    UserAgent u = new UserAgent();
        float [] loc1 = {40.43532f, -79.92160f};
        float [] loc2 = {40.44367f, -79.94362f};
        Timestamp t1 = new Timestamp(2015,5,30,12,23,39,99);
        Timestamp t2 = new Timestamp(2015,5,30,15,28,40,12);

        u.set_src_location(loc1);
        u.set_dest_location(loc2);
        u.set_time_window(t1,t2);

        System.out.println(u.ID + " " +  u.src_lat + " " + u.src_long + " " +  u.dest_lat + " " +  u.dest_long);
    }
}
