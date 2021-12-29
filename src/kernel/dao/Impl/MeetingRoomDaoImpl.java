package kernel.dao.Impl;

import kernel.dao.MeetingRoomDao;
import kernel.entity.MeetingRoom;
import kernel.util.DbUtils;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System.iml
 * 文件名: MeetingRoomDaoImpl
 * 创建者: cos
 * 创建时间:2021/12/28 7:02
 * 描述: 会议室表DAO接口实现类
 */
public class MeetingRoomDaoImpl implements MeetingRoomDao {
    /**
     * 添加会议室
     * @param meetingRoom 会议室
     */
    @Override
    public void addInfo(MeetingRoom meetingRoom) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingRoomAdd(?, ?, ?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(meetingRoom.getRoomID());
            params.add(meetingRoom.getRoomAddress());
            params.add(meetingRoom.getRoomName());
            params.add((meetingRoom.isRoomType()?(short)(1):(short)(0)));
            params.add(meetingRoom.getRoomCapacity());
            params.add(meetingRoom.getHourPrice());
            params.add(meetingRoom.getRoomDescribe());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("插入会议室信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("插入会议室信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除会议室
     * @param roomID
     */
    @Override
    public boolean deleteInfo(int roomID) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingRoomDelete(?)}";
            List<Object> params = new ArrayList<>();
            params.add(roomID);
            int result = dbUtils.executeCallableQuery(sql, params);
            if(result == 1)
                return true;
            else return false;
        } catch (Exception e) {
            System.out.println("删除会议室信息失败！");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新会议室
     * @param meetingRoom
     */
    @Override
    public void updateInfo(MeetingRoom meetingRoom) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingRoomUpdate(?, ?, ?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(meetingRoom.getRoomID());
            params.add(meetingRoom.getRoomAddress());
            params.add(meetingRoom.getRoomName());
            params.add((meetingRoom.isRoomType()?(short)(1):(short)(0)));
            params.add(meetingRoom.getRoomCapacity());
            params.add(meetingRoom.getHourPrice());
            params.add(meetingRoom.getRoomDescribe());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("更新会议室信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("更新会议室信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询会议室
     * @param roomID
     */
    @Override
    public MeetingRoom findByID(int roomID) {
        // 调用存储过程
        DbUtils dbUtils = new DbUtils();
        MeetingRoom meetingRoom = null;
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingRoomFindByID(?)}";
            List<Object> params = new ArrayList<>();
            params.add(roomID);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                String roomAddress = resultSet.getString("roomAddress");
                String roomName = resultSet.getString("roomName");
                boolean roomType = resultSet.getBoolean("roomType");
                int roomCapacity = resultSet.getInt("roomCapacity");
                double hourPrice = resultSet.getDouble("hourPrice");
                String roomDescribe = resultSet.getString("roomDescribe");
                meetingRoom = new MeetingRoom(roomID, roomAddress, roomName, roomType, roomCapacity, hourPrice, roomDescribe);
            }
            return meetingRoom;
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return meetingRoom;
    }


    /**
     * 返回所有会议室
     * @return List<MeetingRoom> 会议室信息列表
     */
    @Override
    public List<MeetingRoom> getList() {
        // 调用存储过程
        List<MeetingRoom> list = new ArrayList<>();
        DbUtils dbUtils = new DbUtils();
        MeetingRoom resultObject = null;
        try {

            dbUtils.getConnection();
            String sql = "{call p_meetingRoomAll()}";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            ResultSetMetaData metaData = resultSet.getMetaData();
//            System.out.println(metaData);
            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String roomAddress = resultSet.getString("roomAddress");
                String roomName = resultSet.getString("roomName");
                boolean roomType = resultSet.getBoolean("roomType");
                int roomCapacity = resultSet.getInt("roomCapacity");
                double hourPrice = resultSet.getDouble("hourPrice");
                String roomDescribe = resultSet.getString("roomDescribe");
                MeetingRoom meetingRoom = new MeetingRoom(roomID, roomAddress, roomName, roomType, roomCapacity, hourPrice, roomDescribe);
                list.add(meetingRoom);
            }
            if (list.isEmpty())
                System.out.println("数据库中不存在会议室");
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MeetingRoom> getAvailableList() {
        // 调用存储过程
        List<MeetingRoom> list = new ArrayList<>();
        DbUtils dbUtils = new DbUtils();
        MeetingRoom resultObject = null;
        try {

            dbUtils.getConnection();
            String sql = "{call p_meetingRoomAvailableAll()}";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            ResultSetMetaData metaData = resultSet.getMetaData();
//            System.out.println(metaData);
            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String roomAddress = resultSet.getString("roomAddress");
                String roomName = resultSet.getString("roomName");
                boolean roomType = resultSet.getBoolean("roomType");
                int roomCapacity = resultSet.getInt("roomCapacity");
                double hourPrice = resultSet.getDouble("hourPrice");
                String roomDescribe = resultSet.getString("roomDescribe");
                MeetingRoom meetingRoom = new MeetingRoom(roomID, roomAddress, roomName, roomType, roomCapacity, hourPrice, roomDescribe);
                list.add(meetingRoom);
            }
            if (list.isEmpty())
                System.out.println("数据库中不存在会议室");
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return list;
    }
}
