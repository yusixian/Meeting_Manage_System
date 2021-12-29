package kernel.service;

import kernel.util.DbUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: UserService
 * 创建者: cos
 * 创建时间:2021/12/29 0:04
 * 描述: User相关功能
 */
public class UserService {
    /**
     * 根据用户id获取用户姓名
     * @param id
     * @return userName
     */
    public String getUserNameByID(int id) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String userName = null;
        try {
            String sql = "select userName from `generalUser` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(id);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                userName = resultSet.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    /**
     * 根据用户名获取用户id
     * @param userName
     * @return id
     */
    public int getIDByUserName(String userName) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        int id = -1;
        try {
            String sql = "select id from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(userName);
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 查询开会最多的用户名
     * @return userName
     */
    public String findUserNameMostBusy() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String userName = null;
        try {
            String sql = "select userName from `view_userMeetingView` as t where t.meetingCount = (select max(t1.meetingCount) from `view_userMeetingView` as t1);";
            List<Object> params = new ArrayList<>();
            ResultSet resultSet = dbUtils.executeQuery(sql, params);
            while (resultSet.next()) {
                userName = resultSet.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        String uName = userService.getUserNameByID(1);
        System.out.println(uName);
        System.out.println(userService.getIDByUserName("ysx"));
    }
}
