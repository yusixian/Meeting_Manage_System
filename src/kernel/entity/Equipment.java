package kernel.entity;

import javafx.beans.property.*;

import java.io.Serializable;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: Equipment
 * 创建者: cos
 * 创建时间:2021/12/28 5:54
 * 描述: 设备实体类
 */
public class Equipment implements Serializable {
    private IntegerProperty id;         // 设备编号
    private StringProperty name;        // 设备名称
    private BooleanProperty status;     // 设备状态
    private DoubleProperty value;       // 设备价值
    private IntegerProperty mid;        // 所属会议室编号
    public Equipment(){}
    public Equipment(int id, String name, boolean status, double value, int mid) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.status = new SimpleBooleanProperty(status);
        this.value = new SimpleDoubleProperty(value);
        this.mid = new SimpleIntegerProperty(mid);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isStatus() {
        return status.get();
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set(status);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public int getMid() {
        return mid.get();
    }

    public IntegerProperty midProperty() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid.set(mid);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", value=" + value +
                ", mid=" + mid +
                '}';
    }
}
