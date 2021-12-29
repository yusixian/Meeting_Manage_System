package kernel.service;


import kernel.dao.EquipmentDao;
import kernel.dao.Impl.EquipmentDaoImpl;
import kernel.entity.Equipment;

import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: EquipmentService
 * 创建者: cos
 * 创建时间:2021/12/28 19:32
 * 描述: 设备相关功能
 */public class EquipmentService {
    EquipmentDao equipmentDao = new EquipmentDaoImpl();
    public EquipmentService() {}
    /**
     * 添加一条设备信息
     * @param equipment 设备信息
     */
    public void addInfo(Equipment equipment) {
        System.out.println("添加设备信息：" + equipment.toString());
        equipmentDao.addInfo(equipment);
        System.out.println("添加后所有设备信息如下");
        printAllInfo();
    }

    /**
     * 根据id删除设备信息
     * @param equipmentID
     */
    public boolean deleteInfo(int equipmentID) {
        System.out.println("---------删除id为"+equipmentID+ "的设备信息---------");
        boolean flag = equipmentDao.deleteInfo(equipmentID);
        System.out.println("删除成功与否：" + flag);
        System.out.println("删除后所有设备信息如下");
        printAllInfo();
        return flag;
    }

    /**
     * 更新设备信息
     * @param equipment 设备信息
     */
    public void updateInfo(Equipment equipment) {
        System.out.println("---------更新id为" + equipment.getId() + "的设备信息---------");
        equipmentDao.updateInfo(equipment);
        System.out.println("更新后所有设备信息如下");
        printAllInfo();
    }

    /**
     * 根据id查找设备信息
     * @param ID
     * @return Equipment
     */
    public Equipment findByID(int ID) {
        System.out.println("---------查找id为"+ID+ "的设备信息---------");
        Equipment equipment = equipmentDao.findByID(ID);
        System.out.println("查找到的设备信息:" + equipment);
        return equipment;
    }

    /**
     * 返回所有设备信息
     */
    public List<Equipment> getList() {
        List<Equipment> list = equipmentDao.getList();
        return list;
    }

    /**
     * 显示所有设备信息
     */
    public void printAllInfo() {
        List<Equipment> list = equipmentDao.getList();
        System.out.println("-----------以下为所有设备信息-----------");
        for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Equipment equipment1 = new Equipment(88, "美的空调", true, 1500, 1);
        Equipment equipment2 = new Equipment(99, "联想电脑", true, 3400, 1);
        EquipmentService eService = new EquipmentService();
//        eService.printAllInfo();
//        eService.addInfo(equipment1);
//        eService.addInfo(equipment2);
//        eService.deleteInfo(99);
//        eService.addInfo(equipment2);
//        equipment2.setName("华硕电脑");
//        eService.updateInfo(equipment2);

        eService.findByID(88);
        eService.findByID(70);
    }
}
