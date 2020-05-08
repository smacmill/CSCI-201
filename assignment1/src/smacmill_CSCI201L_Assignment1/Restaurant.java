
package smacmill_CSCI201L_Assignment1;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    
    public void addData(Datum newDatum) {
    	this.data.add(newDatum);
    }
    
    
}
