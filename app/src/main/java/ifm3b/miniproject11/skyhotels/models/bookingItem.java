package ifm3b.miniproject11.skyhotels.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class bookingItem implements Parcelable {
    private int id;
    private int guestId;
    private int roomId;
    private String startDate;
    private String endDate;
    private String status;
    private String code;

    public bookingItem(int id, int guestId, int roomId, String startDate, String endDate, String status,String code) {
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.code = code;
    }

    protected bookingItem(Parcel in){
        id = in.readInt();
        guestId = in.readInt();
        roomId = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        status = in.readString();
        code = in.readString();

    }

    public static final Creator<bookingItem> CREATOR = new Creator<bookingItem>() {
        @Override
        public bookingItem createFromParcel(Parcel in) {
            return new bookingItem(in);
        }

        @Override
        public bookingItem[] newArray(int size) {
            return new bookingItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(guestId);
        dest.writeInt(roomId);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(status);
        dest.writeString(code);
    }
}
