package kernel.entity;

import javafx.beans.property.*;

import java.io.Serializable;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: Meeting
 * 创建者: cos
 * 创建时间:2021/12/28 5:44
 * 描述: 会议表实体类
 */
public class Meeting implements Serializable {
    private IntegerProperty meetID;         // 会议编号
    private StringProperty meetName;        // 会议名称
    private IntegerProperty partNum;        // 预计参会人数
    private StringProperty startTime;       // 预计开始时间
    private IntegerProperty duration;       // 持续时间
    private DoubleProperty price;           // 预计费用
    private StringProperty description;     // 会议说明
    private IntegerProperty uid;            // 预定用户编号
    private IntegerProperty rid;            // 使用的会议室id
    public Meeting(){}
    public Meeting(int meetID, String meetName, int partNum, String startTime, int duration,
                   double price, String description, int uid, int rid) {
        this.meetID = new SimpleIntegerProperty(meetID);
        this.meetName = new SimpleStringProperty(meetName);
        this.partNum = new SimpleIntegerProperty(partNum);
        this.startTime = new SimpleStringProperty(startTime);
        this.duration = new SimpleIntegerProperty(duration);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.uid = new SimpleIntegerProperty(uid);
        this.rid = new SimpleIntegerProperty(rid);
    }

    public int getMeetID() {
        return meetID.get();
    }

    public IntegerProperty meetIDProperty() {
        return meetID;
    }

    public void setMeetID(int meetID) {
        this.meetID.set(meetID);
    }

    public String getMeetName() {
        return meetName.get();
    }

    public StringProperty meetNameProperty() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName.set(meetName);
    }

    public int getPartNum() {
        return partNum.get();
    }

    public IntegerProperty partNumProperty() {
        return partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum.set(partNum);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getUid() {
        return uid.get();
    }

    public IntegerProperty uidProperty() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid.set(uid);
    }

    public int getRid() {
        return rid.get();
    }

    public IntegerProperty ridProperty() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid.set(rid);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetID=" + meetID +
                ", meetName=" + meetName +
                ", partNum=" + partNum +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", price=" + price +
                ", description=" + description +
                ", uid=" + uid +
                ", rid=" + rid +
                '}';
    }
}
