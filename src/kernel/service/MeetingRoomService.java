package kernel.service;

import kernel.dao.Impl.MeetingRoomDaoImpl;
import kernel.dao.MeetingRoomDao;
import kernel.entity.MeetingRoom;
import kernel.util.DbUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: MeetingRoomService
 * 创建者: cos
 * 创建时间:2021/12/28 7:29
 * 描述: 会议室相关功能
 */
public class MeetingRoomService {
    MeetingRoomDao meetingRoomDao = new MeetingRoomDaoImpl();
    public MeetingRoomService() {}
    /**
     * 添加一条会议室信息
     * @param meetingRoom 会议室信息
     */
    public void addInfo(MeetingRoom meetingRoom) {
        System.out.println("添加会议室信息：" + meetingRoom.toString());
        meetingRoomDao.addInfo(meetingRoom);
        System.out.println("添加后所有会议室信息如下");
        printAllInfo();
    }

    /**
     * 根据id删除会议室信息
     * @param roomID
     */
    public boolean deleteInfo(int roomID) {
        System.out.println("---------删除id为"+roomID+ "的会议室信息---------");
        boolean flag = meetingRoomDao.deleteInfo(roomID);
        System.out.println("删除成功与否：" + flag);
        System.out.println("删除后所有会议室信息如下");
        printAllInfo();
        return flag;
    }

    /**
     * 更新会议室信息
     * @param meetingRoom 会议室信息
     */
    public void updateInfo(MeetingRoom meetingRoom) {
        System.out.println("---------更新id为" + meetingRoom.getRoomID() + "的会议室信息---------");
        meetingRoomDao.updateInfo(meetingRoom);
        System.out.println("更新后所有会议室信息如下");
        printAllInfo();
    }

    /**
     * 根据id查找会议室信息
     * @param roomID
     * @return MeetingRoom
     */
    public MeetingRoom findByID(int roomID) {
        System.out.println("---------查找id为"+roomID+ "的会议室信息---------");
        MeetingRoom meetingRoom = meetingRoomDao.findByID(roomID);
        System.out.println("查找到的会议室信息:" + meetingRoom);
        return meetingRoom;
    }

    /**
     * 根据会议室id获取会议室名称
     * @param id
     * @return roomName
     */
    public String getRoomNameByID(int id) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String roomName = null;
        try {
            String sql = "select roomName from `meetingRoom` where roomID = ?";
            List<Object> params = new ArrayList<>();
            params.add(id);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                roomName = resultSet.getString("roomName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomName;
    }
    /**
     * 根据会议室id获取会议室地址
     * @param id
     * @return roomName
     */
    public String getRoomAddressByID(int id) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String roomName = null;
        try {
            String sql = "select roomAddress from `meetingRoom` where roomID = ?";
            List<Object> params = new ArrayList<>();
            params.add(id);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                roomName = resultSet.getString("roomAddress");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomName;
    }
    /**
     * 根据会议室名获取会议室id
     * @param roomName
     * @return id 无则返回-1
     */
    public int getIDByRoomName(String roomName) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        int id = -1;
        try {
            String sql = "select roomID from `meetingRoom` where roomName = ?";
            List<Object> params = new ArrayList<>();
            params.add(roomName);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                id = resultSet.getInt("roomID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 查询最常使用的会议室
     * @return roomName
     */
    public String findRoomNameMostPopular() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String roomName = null;
        try {
            String sql = "select roomName from `view_roomMeetingView` as t where t.meetingCount = (select max(t1.meetingCount) from `view_roomMeetingView` as t1) order by roomName desc ;";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                roomName = resultSet.getString("roomName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomName;
    }

    /**
     * 返回所有会议室信息
     */
    public List<MeetingRoom> getList() {
        List<MeetingRoom> list = meetingRoomDao.getList();
        return list;
    }

    /**
     * 返回所有可用会议室信息
     */
    public List<MeetingRoom> getAvailableList() {
        List<MeetingRoom> list = meetingRoomDao.getAvailableList();
        return list;
    }
    /**
     * 显示所有会议室信息
     */
    public void printAllInfo() {
        List<MeetingRoom> list = meetingRoomDao.getList();
        System.out.println("-----------以下为所有会议室信息-----------");
        for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MeetingRoom meetingRoom1 = new MeetingRoom(111, "3号楼3634", "语音实验室", false, 70, 30, "可举行各种会议");
        MeetingRoom meetingRoom2 = new MeetingRoom(222, "test2", "sss", true, 30, 10, "test");
        MeetingRoomService mService = new MeetingRoomService();
//        mService.printAllInfo();
//        mService.addInfo(meetingRoom1);
//        mService.deleteInfo(111);
//
//        mService.addInfo(meetingRoom1);
//        mService.addInfo(meetingRoom2);
//        meetingRoom2.setRoomDescribe("Change");
//        mService.updateInfo(meetingRoom2);
        mService.findByID(888);
        System.out.println(mService.getRoomNameByID(2));
        System.out.println(mService.getIDByRoomName("行政会议室"));
    }
}
