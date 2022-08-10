package ifm3b.miniproject11.skyhotels.models;

import android.os.Parcel;
import android.os.Parcelable;

public class roomItem implements Parcelable{
    private int id;
    private String room_num;
    private String description;
    private String name;
    private String amenities;
    private int capacity;
    private double rate;
    private String status;
    private String imgUrl;


    public roomItem(int id, String room_num, String name, String description, double rate, String status, int capacity) {
        this.id = id;
        this.room_num = room_num;
        this.description = description;
        this.name = name;
        this.amenities = amenities;
        this.rate = rate;
        this.capacity = capacity;
        this.status = status;
    }

    protected roomItem(Parcel in) {
        id = in.readInt();
        room_num = in.readString();
        name = in.readString();
        description = in.readString();
        amenities = in.readString();
        rate = in.readDouble();
        status = in.readString();
        capacity = in.readInt();
    }

    public static final Creator<roomItem> CREATOR = new Creator<roomItem>() {
        @Override
        public roomItem createFromParcel(Parcel in) {
            return new roomItem(in);
        }

        @Override
        public roomItem[] newArray(int size) {
            return new roomItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomnum() {
        return room_num;
    }

    public void setRoomnum(String room_num) {
        this.room_num = room_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    public String getType() {
        return name;
    }

    public void setType(String type) {
        this.name = type;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(room_num);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(amenities);
        dest.writeDouble(rate);
        dest.writeString(status);
        dest.writeInt(capacity);
    }
}
