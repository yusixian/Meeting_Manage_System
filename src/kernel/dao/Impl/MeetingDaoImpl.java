package kernel.dao.Impl;

import kernel.dao.MeetingDao;
import kernel.entity.Meeting;
import kernel.util.DbUtils;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System.iml
 * 文件名: MeetingDaoImpl
 * 创建者: cos
 * 创建时间:2021/12/28 7:46
 * 描述: 会议表DAO接口实现类
 */
public class MeetingDaoImpl implements MeetingDao {
    /**
     * 添加会议
     * @param meeting 会议
     */
    @Override
    public void addInfo(Meeting meeting) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingAdd(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(meeting.getMeetID());
            params.add(meeting.getMeetName());
            params.add(meeting.getPartNum());
            params.add(meeting.getStartTime());
            params.add(meeting.getDuration());
            params.add(meeting.getPrice());
            params.add(meeting.getDescription());
            params.add(meeting.getUid());
            params.add(meeting.getRid());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("插入会议信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("插入会议信息失败！");
            e.printStackTrace();
        }
    }

    @Override
    public void insertInfo(Meeting meeting) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "insert into `meeting` (name, partNum, startTime, duration, price, description, uid, rid) values(?, ?, ?, ?, ?, ?, ?, ?);";
            List<Object> params = new ArrayList<>();
            params.add(meeting.getMeetName());
            params.add(meeting.getPartNum());
            params.add(meeting.getStartTime());
            params.add(meeting.getDuration());
            params.add(meeting.getPrice());
            params.add(meeting.getDescription());
            params.add(meeting.getUid());
            params.add(meeting.getRid());
            dbUtils.updateByPreparedStatement(sql, params);
            System.out.println("插入会议信息成功！");
        } catch (Exception e) {
            System.out.println("插入会议信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除会议
     * @param roomID
     */
    @Override
    public boolean deleteInfo(int roomID) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingDelete(?)}";
            List<Object> params = new ArrayList<>();
            params.add(roomID);
            int result = dbUtils.executeCallableQuery(sql, params);
            if(result == 1)
                return true;
            else return false;
        } catch (Exception e) {
            System.out.println("删除会议信息失败！");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新会议
     * @param meeting
     */
    @Override
    public void updateInfo(Meeting meeting) {
        CallableStatement proc = null;//执行sql存储过程的接口
        DbUtils dbUtils = new DbUtils();
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingUpdate(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            List<Object> params = new ArrayList<>();
            params.add(meeting.getMeetID());
            params.add(meeting.getMeetName());
            params.add(meeting.getPartNum());
            params.add(meeting.getStartTime());
            params.add(meeting.getDuration());
            params.add(meeting.getPrice());
            params.add(meeting.getDescription());
            params.add(meeting.getUid());
            params.add(meeting.getRid());
            int result = dbUtils.executeCallableQuery(sql, params);
            System.out.println("更新会议信息成功！");
            System.out.println("影响的行数：" + result);
        } catch (Exception e) {
            System.out.println("更新会议信息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询会议室
     * @param meetingID
     */
    @Override
    public Meeting findByID(int meetingID) {
        // 调用存储过程
        DbUtils dbUtils = new DbUtils();
        Meeting meeting = null;
        try {
            dbUtils.getConnection();
            String sql = "{call p_meetingFindByID(?)}";
            List<Object> params = new ArrayList<>();
            params.add(meetingID);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int partNum = resultSet.getInt("partNum");
                Timestamp timestamp = resultSet.getTimestamp("startTime");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
                String startTime= df.format(timestamp);
                int duration = resultSet.getInt("duration");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int uid = resultSet.getInt("uid");
                int rid = resultSet.getInt("rid");
                meeting = new Meeting(meetingID, name, partNum, startTime, duration, price, description, uid, rid);
            }
            return meeting;
        } catch (Exception e) {
            System.out.println("查找失败！");
            e.printStackTrace();
        }
        return meeting;
    }

    /**
     * 返回所有会议
     * @return List<Meeting> 会议信息列表
     */
    @Override
    public List<Meeting> getList() {

        // 调用存储过程
        List<Meeting> list = new ArrayList<>();
        DbUtils dbUtils = new DbUtils();
        Meeting resultObject = null;
        try {

            dbUtils.getConnection();
            String sql = "{call p_meetingAll()}";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            ResultSetMetaData metaData = resultSet.getMetaData();
//            System.out.println(metaData);
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
                int uid = resultSet.getInt("uid");
                int rid = resultSet.getInt("rid");
                Meeting meeting = new Meeting(id, name, partNum, startTime, duration, price, description, uid, rid);
                list.add(meeting);
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
