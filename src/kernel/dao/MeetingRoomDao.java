package kernel.dao;

import kernel.entity.MeetingRoom;

import java.util.List;

/*
 * 项目名: Meeting_Manage_System
 * 文件名: MeetingRoomDao
 * 创建者: cos
 * 创建时间:2021/12/28 6:56
 * 描述: 会议室表DAO接口
 */
public interface MeetingRoomDao {
    /**
     * 添加会议室
     * @param meetingRoom 会议室
     */
    void addInfo(MeetingRoom meetingRoom);

    /**
     * 根据id删除会议室
     * @param roomID
     */
    boolean deleteInfo(int roomID);

    /**
     * 更新会议室
     * @param meetingRoom
     */
    void updateInfo(MeetingRoom meetingRoom);

    /**
     * 根据id查询会议室
     * @param roomID
     */
    MeetingRoom findByID(int roomID);

    /**
     * 返回所有会议室
     * @return List<MeetingRoom> 会议室信息列表
     */
    public List<MeetingRoom> getList();

    /**
     * 返回所有可用会议室
     * @return List<MeetingRoom> 会议室信息列表
     */
    public List<MeetingRoom> getAvailableList();
}
