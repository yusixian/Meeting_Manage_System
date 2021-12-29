package kernel.dao;

import kernel.entity.Equipment;

import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: EquipmentDao
 * 创建者: cos
 * 创建时间:2021/12/28 18:58
 * 描述: 设备表DAO接口
 */
public interface EquipmentDao {
    /**
     * 添加设备
     * @param equipment 设备
     */
    void addInfo(Equipment equipment);

    /**
     * 根据id删除设备
     * @param equipmentID
     */
    boolean deleteInfo(int equipmentID);

    /**
     * 更新设备
     * @param equipment
     */
    void updateInfo(Equipment equipment);

    /**
     * 根据id查询设备
     * @param equipmentID
     */
    Equipment findByID(int equipmentID);

    /**
     * 返回所有设备
     * @return List<Equipment> 设备列表
     */
    public List<Equipment> getList();
}
