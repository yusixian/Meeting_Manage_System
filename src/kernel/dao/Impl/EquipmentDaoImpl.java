package kernel.dao.Impl;

import kernel.dao.EquipmentDao;
import kernel.entity.Equipment;
import kernel.util.DbUtils;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System.iml
 * 文件名: EquipmentDaoImpl
 * 创建者: cos
 * 创建时间:2021/12/28 19:00
 * 描述: 设备表DAO接口实现类
 */
public class EquipmentDaoImpl implements EquipmentDao {

    /**
     * 添加设备
     * @param equipment 设备
     */
    @Override
    public void addInfo(Equipment equipment) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_equipmentAdd(?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(equipment.getId());
            params.add(equipment.getName());
            params.add((equipment.isStatus()?(short)(1):(short)(0)));
            params.add(equipment.getValue());
            params.add(equipment.getMid());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("插入设备信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("插入设备信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除设备
     * @param equipmentID
     */
    @Override
    public boolean deleteInfo(int equipmentID) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_equipmentDelete(?)}";
            List<Object> params = new ArrayList<>();
            params.add(equipmentID);
            int result = dbUtils.executeCallableQuery(sql, params);
            if(result == 1)
                return true;
            else return false;
        } catch (Exception e) {
            System.out.println("删除设备信息失败！");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新设备
     * @param equipment
     */
    @Override
    public void updateInfo(Equipment equipment) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_equipmentUpdate(?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(equipment.getId());
            params.add(equipment.getName());
            params.add((equipment.isStatus()?(short)(1):(short)(0)));
            params.add(equipment.getValue());
            params.add(equipment.getMid());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("更新设备信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("更新设备信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询设备
     * @param equipmentID
     */
    @Override
    public Equipment findByID(int equipmentID) {
        // 调用存储过程
        DbUtils dbUtils = new DbUtils();
        Equipment equipment = null;
        try {
            dbUtils.getConnection();
            String sql = "{call p_equipmentFindByID(?)}";
            List<Object> params = new ArrayList<>();
            params.add(equipmentID);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                boolean status = resultSet.getBoolean("status");
                double value = resultSet.getDouble("value");
                int mid = resultSet.getInt("mid");
                equipment = new Equipment(equipmentID, name, status, value, mid);            }
            return equipment;
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return equipment;
    }

    /**
     * 返回所有设备
     * @return List<Equipment> 设备列表
     */
    @Override
    public List<Equipment> getList() {
        // 调用存储过程
        List<Equipment> list = new ArrayList<>();
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_equipmentAll()}";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                boolean status = resultSet.getBoolean("status");
                double value = resultSet.getDouble("value");
                int mid = resultSet.getInt("mid");
                Equipment equipment = new Equipment(id, name, status, value, mid);
                list.add(equipment);
            }
            if (list.isEmpty())
                System.out.println("数据库中不存在设备");
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return list;
    }
}
