package ifm3b.miniproject11.skyhotels.models;

import android.os.Parcel;
import android.os.Parcelable;

public class reservation implements Parcelable {
    private String Name;
    private String RoomNum;
    private int id;
    private int guestId;
    private int roomId;
    private String startDate;
    private String endDate;
    private String status;

    public reservation(String name, String roomNum, int id, int guestId, int roomId, String startDate, String endDate, String status) {
        Name = name;
        RoomNum = roomNum;
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoomNum() {
        return RoomNum;
    }

    public void setRoomNum(String roomNum) {
        RoomNum = roomNum;
    }

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

    protected reservation(Parcel in) {
        Name = in.readString();
        RoomNum = in.readString();
        id = in.readInt();
        guestId = in.readInt();
        roomId = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        status = in.readString();
    }

    public static final Creator<reservation> CREATOR = new Creator<reservation>() {
        @Override
        public reservation createFromParcel(Parcel in) {
            return new reservation(in);
        }

        @Override
        public reservation[] newArray(int size) {
            return new reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(RoomNum);
        dest.writeInt(id);
        dest.writeInt(guestId);
        dest.writeInt(roomId);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(status);
    }
}
