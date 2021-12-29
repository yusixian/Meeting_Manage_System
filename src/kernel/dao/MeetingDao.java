package kernel.dao;

import kernel.entity.Meeting;

import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: Meeting
 * 创建者: cos
 * 创建时间:2021/12/28 7:44
 * 描述: 会议表DAO接口
 */
public interface MeetingDao {
    /**
     * 添加会议
     * @param meeting 会议
     */
    void addInfo(Meeting meeting);

    /**
     * 添加会议 自动生成id
     * @param meeting 会议
     */
    void insertInfo(Meeting meeting);

    /**
     * 根据id删除会议
     * @param roomID
     */
    boolean deleteInfo(int roomID);

    /**
     * 更新会议
     * @param meeting
     */
    void updateInfo(Meeting meeting);

    /**
     * 根据id查询会议
     * @param meetingID
     */
    Meeting findByID(int meetingID);

    /**
     * 返回所有会议
     * @return List<Meeting> 会议信息列表
     */
    public List<Meeting> getList();
}
