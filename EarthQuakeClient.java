
import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    
    /*
    * QuakeEntry
    *
    * @pram double magMin -> Specifyマグニチュード
    */
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
        // 指定されたマグニチュード以上のものをanswer arraylistないに入れていく
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        //TODO
        for (QuakeEntry qe : quakeData) {
            
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }
        return answer;              
    }
    /**
     * 
     * @param quakeData -> ArrayList型の地震データ
     * @param distMax -> fromからの距離
     * @param from -> 指定したローケーション
     * @return ArraList
     */
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {
        // returnで返す用
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        // distMax以下であればanswerにドンドン追加していく
        for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }

    /**
     * This method should return an ArrayList of type QuakeEntry of all the earthquakes from quakeData whose depth is between minDepth and maxDepth, exclusive. (Do not include quakes with depth exactly minDepth or maxDepth
     * 
     * @param quakeData -> quakeData from xml
     * @param minDepth -> Minimum value of earthquake
     * @param MaxDepth -> Maximum value of earthquake
     * @return -> 
     */    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, Double minDepth,
                                Double MaxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            double currentDepth = qe.getDepth();
            if(currentDepth > minDepth && currentDepth < MaxDepth){
                answer.add(qe);
            }
        }
        return answer;
    }
            
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                              qe.getMagnitude(),
                              qe.getInfo());
        }
        
    }
    
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        // リストに1つ1つ読み込まれたのがArrayList型として格納される
        ArrayList<QuakeEntry> list = parser.read(source);
        // arraylist.size => 全体の要素数
        System.out.println("read data for " + list.size() + " quakes");
       /*
       for (QuakeEntry qe : list) {
           if (qe.getMagnitude() > 5.0) {
               System.out.println(qe);
           }
       }
       */
        
       /*
       * listBigには指定していた listBig に 全リストから指定のマグニチュードが格納される.
       *  
       */
        ArrayList<QuakeEntry> listBig = filterByMagnitude(list,5.0);
        
        for(QuakeEntry qe: listBig){
            System.out.println(qe);
        }
        
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("# quakes read: " + list.size());

        //Durham, NC
        //Location city = new Location(35.988, -78.907);
        //Bridgeport, CA
        Location city = new Location(38.17, -118.82);
        ArrayList<QuakeEntry> close = filterByDistanceFrom(list, 1000 * 1000, city);
        for (int k = 0; k < close.size(); k++) {
            QuakeEntry entry = close.get(k);
            double distanceInMeters = city.distanceTo(entry.getLocation());
            System.out.println(distanceInMeters / 1000 + " " + entry.getInfo());
        }
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "nov20quakedatasmall.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        // filterByDepthを呼び出す
        ArrayList<QuakeEntry> bwDepthList = filterByDepth(list, -10000.0, -5000.0);
        System.out.println("Find quakes with depth between -10000.0 and -5000.0" );
        for (QuakeEntry qe : bwDepthList) {
            System.out.println(qe);
         }
        }
}
