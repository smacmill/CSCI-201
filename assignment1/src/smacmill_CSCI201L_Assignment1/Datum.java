
package smacmill_CSCI201L_Assignment1;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("menu")
    @Expose
    private List<String> menu = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }
    
    public double calcDist(double yourLat, double yourLong) {
		double d = 3963.0 * Math.acos((Math.sin(yourLat) * Math.sin(latitude)) + (Math.cos(yourLat) * Math.cos(latitude)) * Math.cos(longitude - yourLong));
		return d;
	}

    public String printDistLoc() {
    	return getName() + ", located " + String.format("%.1f", calcDist(Main.userLat, Main.userLong)) + " miles away at " + getAddress() + "\n";
    }

}
