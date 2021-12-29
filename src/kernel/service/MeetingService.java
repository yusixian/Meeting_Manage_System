package kernel.service;


import kernel.dao.Impl.MeetingDaoImpl;
import kernel.dao.MeetingDao;
import kernel.entity.Meeting;
import kernel.util.DbUtils;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: MeetingService
 * 创建者: cos
 * 创建时间:2021/12/28 8:02
 * 描述: 会议相关功能
 */
public class MeetingService {

    MeetingDao meetingDao = new MeetingDaoImpl();
    public MeetingService() {}
    /**
     * 添加一条会议信息
     * @param meeting 会议信息
     */
    public void addInfo(Meeting meeting) {
        System.out.println("添加会议信息：" + meeting.toString());
        meetingDao.addInfo(meeting);
        System.out.println("添加后所有会议信息如下");
        printAllInfo();
    }

    /**
     * 添加会议信息 无id
     * @param meeting 会议信息
     */
    public void addInfoWithoutID(Meeting meeting) {
        System.out.println("添加会议信息：" + meeting.toString());
        meetingDao.insertInfo(meeting);
        System.out.println("添加后所有会议信息如下");
        printAllInfo();
    }
    /**
     * 根据id删除会议信息
     * @param ID
     */
    public boolean deleteInfo(int ID) {
        System.out.println("---------删除id为"+ID+ "的会议信息---------");
        boolean flag = meetingDao.deleteInfo(ID);
        System.out.println("删除成功与否：" + flag);
        System.out.println("删除后所有会议信息如下");
        printAllInfo();
        return flag;
    }

    /**
     * 更新会议信息
     * @param meeting 会议信息
     */
    public void updateInfo(Meeting meeting) {
        System.out.println("---------更新id为" + meeting.getMeetID() + "的会议信息---------");
        meetingDao.updateInfo(meeting);
        System.out.println("更新后所有会议信息如下");
        printAllInfo();
    }

    /**
     * 根据id查找会议信息
     * @param ID
     * @return Meeting
     */
    public Meeting findByID(int ID) {
        System.out.println("---------查找id为"+ID+ "的会议信息---------");
        Meeting meeting = meetingDao.findByID(ID);
        System.out.println("查找到的会议信息:" + meeting);
        return meeting;
    }

    /**
     * 根据用户id查找其所有会议信息
     * @param uid
     * @return Meeting
     */
    public List<Meeting> getAllMeetingByUID(int uid) {
        List<Meeting> list = new ArrayList<>();
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        try {
            String sql = "select * from `meeting` where uid = ?";
            List<Object> params = new ArrayList<>();
            params.add(uid);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int partNum = resultSet.getInt("partNum");
                Timestamp timestamp = resultSet.getTimestamp("startTime");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
                String startTime= df.format(timestamp);
                int duration = resultSet.getInt("duration");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int rid = resultSet.getInt("rid");
                Meeting meeting = new Meeting(id, name, partNum, startTime, duration, price, description, uid, rid);
                list.add(meeting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 返回所有会议室信息
     */
    public List<Meeting> getList() {
        List<Meeting> list = meetingDao.getList();
        return list;
    }


    /**
     * 显示所有会议信息
     */
    public void printAllInfo() {
        List<Meeting> list = meetingDao.getList();
        System.out.println("-----------以下为所有会议信息-----------");
        for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Meeting meeting1 = new Meeting(111, "讨论会议", 50, "2021-07-21 16:00:00", 2, 0, "今天希娜小姐吃什么", 1, 2);
        Meeting meeting2 = new Meeting(222, "战略会议", 20, "2021-02-13 17:00:00", 3, 0, "原来是这样解决的吗", 1, 3);
        MeetingService mService = new MeetingService();
//        mService.printAllInfo();
//        mService.addInfo(meeting1);
//        mService.deleteInfo(1);
//
//        mService.addInfo(meeting2);
        mService.addInfoWithoutID(meeting1);
//        meeting2.setDescription("Change");
//        mService.updateInfo(meeting2);
//        mService.findByID(77);
//        mService.findByID(3);
    }

}
