package bit.chinjs1.welcometodunedin;

import android.graphics.drawable.Drawable;

/**
 * Created by chinjs1 on 1/04/2017.
 */

public class Places {

    String placeName;
    String placeLocation;
    Drawable placeImage;
    int imgID;


    public Places(String placeName, String placeLocation, Drawable placeImage, int imgID) {

        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.placeImage = placeImage;
        this.imgID = imgID;
    }

    @Override
    public String toString() {

        return placeName + " " + "\n" + placeLocation;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public Drawable getPlaceImage() {
        return placeImage;
    }

    public int getImgID() { return imgID; }

}
