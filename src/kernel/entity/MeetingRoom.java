package kernel.entity;

import javafx.beans.property.*;

import java.io.Serializable;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: MeetingRoom
 * 创建者: cos
 * 创建时间:2021/12/28 5:23
 * 描述: 会议室实体类
 */
public class MeetingRoom implements Serializable {
    private IntegerProperty roomID;         // 会议室编号
    private StringProperty roomAddress;     // 会议室地址
    private StringProperty roomName;        // 会议室名称
    private BooleanProperty roomType;       // 会议室状态 0：禁用状态 1：启用状态
    private IntegerProperty roomCapacity;   // 会议室最大可容纳人数
    private DoubleProperty hourPrice;       // 使用一小时费用
    private StringProperty roomDescribe;        // 会议室描述
    public MeetingRoom(){}
    public MeetingRoom(int roomID, String roomAddress, String roomName, boolean roomType,
                       int roomCapacity, double hourPrice, String roomDescribe) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomAddress = new SimpleStringProperty(roomAddress);
        this.roomName = new SimpleStringProperty(roomName);
        this.roomType = new SimpleBooleanProperty(roomType);
        this.roomCapacity = new SimpleIntegerProperty(roomCapacity);
        this.hourPrice = new SimpleDoubleProperty(hourPrice);
        this.roomDescribe = new SimpleStringProperty(roomDescribe);
    }

    public int getRoomID() {
        return roomID.get();
    }

    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }

    public String getRoomAddress() {
        return roomAddress.get();
    }

    public StringProperty roomAddressProperty() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress.set(roomAddress);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public boolean isRoomType() {
        return roomType.get();
    }

    public BooleanProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(boolean roomType) {
        this.roomType.set(roomType);
    }

    public int getRoomCapacity() {
        return roomCapacity.get();
    }

    public IntegerProperty roomCapacityProperty() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity.set(roomCapacity);
    }

    public double getHourPrice() {
        return hourPrice.get();
    }

    public DoubleProperty hourPriceProperty() {
        return hourPrice;
    }

    public void setHourPrice(double hourPrice) {
        this.hourPrice.set(hourPrice);
    }

    public String getRoomDescribe() {
        return roomDescribe.get();
    }

    public StringProperty roomDescribeProperty() {
        return roomDescribe;
    }

    public void setRoomDescribe(String roomDescribe) {
        this.roomDescribe.set(roomDescribe);
    }

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "roomID=" + roomID +
                ", roomAddress=" + roomAddress +
                ", roomName=" + roomName +
                ", roomType=" + roomType +
                ", roomCapacity=" + roomCapacity +
                ", hourPrice=" + hourPrice +
                ", roomDescribe=" + roomDescribe +
                '}';
    }
}
