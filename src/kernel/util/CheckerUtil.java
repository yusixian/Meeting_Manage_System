package kernel.util;

import kernel.entity.Admin;
import kernel.entity.GeneralUser;
import kernel.entity.Meeting;
import kernel.entity.MeetingRoom;
import kernel.service.MeetingRoomService;
import kernel.service.MeetingService;

import java.util.ArrayList;
import java.util.List;

public class CheckerUtil {
    /**
     * 检查普通用户基本信息是否过长或过短（不包括id）
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserInfoCheck(String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = null;
        //检查地址
        if (addr.length() > 60) {
            errorMessage = "地址长度过长";
        }

        // 检查电话
        if (pho.length() > 60) {
            errorMessage = "电话号码过长";
        }

        //检查身份证号
        // TODO: 改成用正则匹配身份证号
        if (idc.length() > 60) {
            errorMessage = "身份证号过长";
        }

        //检查真实姓名
        if (rname.length() > 60) {
            errorMessage = "真实姓名过长？这不是真实姓名吧";
        }

        //用户名是不是符合要求
        if (un.length() == 0) {
            errorMessage = "用户名为空";
        } else if (un.length() > 60) {
            errorMessage = "用户名过长";
        }

        //密码是不是符合要求
        if (pw.length() == 0) {
            errorMessage = "密码为空";
        } else if (pw.length() > 60) {
            errorMessage = "密码过长";
        }

        return errorMessage;
    }

    /**
     * 管理员用户界面接口：检查普通用户更新信息，看id与用户名是否被改动
     * @param oldInfo 旧的普通用户信息
     * @param newInfo 新的普通用户信息
     * @return errorMessage 错误提示语句
     */
    public static String generalUserUpdateCheck(GeneralUser oldInfo, GeneralUser newInfo) {
        // 先检查长度
        String errorMessage = generalUserInfoCheck(newInfo.getUserName(), newInfo.getPassWord(),
                newInfo.getRealName(), newInfo.getIdCard(), newInfo.getPhone(), newInfo.getAddress());

        // 若改动了id和用户名这两个不能重复的信息
        if (oldInfo.getId() != newInfo.getId()) {
            errorMessage = "您不能修改用户的ID";
        }
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        if (!oldInfo.getUserName().equals(newInfo.getUserName())) { // 若改动了用户名 检查是否重复
            try {
                String sql = "select * from `generalUser` where userName = ?";
                List<Object> params = new ArrayList<>();
                params.add(newInfo.getUserName());
                GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                if (sameUser != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                System.out.println("更新用户信息失败！");
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 普通用户更改个人信息界面接口：将新信息传入检查合法性并检查是否改动用户名及密码，若改动了则检查新的用户名是否已被占用
     * @param olduname 旧用户名
     * @param uname 新用户名
     * @param pw 新密码
     * @param rname 新真实姓名
     * @param idc 新用户身份证号
     * @param pho 新用户联系电话
     * @param addr 新用户地址
     * @return errorMessage 错误提示语句
     */
    public static String userUpdateCheck(String olduname,String uname, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserInfoCheck(uname, pw, rname, idc, pho, addr); //检查合法性
        //如果改动了用户名这个不能重复的信息
        if (!olduname.equals(uname)) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `generalUser` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(uname);
            try {
                GeneralUser same = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
                if (same != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 检查管理员用户更新信息，看id与用户名是否被合法（用户名是否被占用，id不可修改）
     * @param oldInfo 旧管理员信息
     * @param newInfo 新管理员信息
     * @return errorMessage 错误提示语句
     */
    public static String adminInfoUpdateCheck(Admin oldInfo, Admin newInfo) {
        String errorMessage = null;

        if (newInfo.getUserName() == null || newInfo.getUserName().length() == 0) {
            return errorMessage = "用户名为空";
        }

        if (newInfo.getPassWord() == null || newInfo.getPassWord().length() == 0) {
            return errorMessage = "密码为空";
        }

        // 若改动了id和用户名这两个不能重复的信息
        if (oldInfo.getId() != newInfo.getId()) {
            errorMessage = "您不能修改用户的ID";
        }

        if (!oldInfo.getUserName().equals(newInfo.getUserName())) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "select * from `admin` where userName = ?";
            List<Object> params = new ArrayList<>();
            params.add(newInfo.getUserName());
            try {
                Admin same = dbUtils.findSimpleProResult(sql, params, Admin.class);
                if (same != null) {
                    errorMessage = "该用户名已被占用";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * 用户管理界面检查：管理员注册合法性检查，检查合法性以及id、用户名是否重复
     * @param id 编号
     * @param uname 用户名
     * @param pass 密码
     * @return errorMessage 错误提示语句
     */
    public static String adminSignUpCheck(String id, String uname, String pass) {
        String errorMessage = null;
        // 检查id合法性
        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }
        // 检查用户名合法性
        if (uname == null || uname.length() == 0) {
            return errorMessage = "用户名为空";
        }

        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `admin` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(Integer.parseInt(id));
        try {
            Admin sameUser = dbUtils.findSimpleProResult(sql, params, Admin.class);
            if (sameUser != null) {
                errorMessage = "该Id已被占用";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = "select * from `admin` where userName = ?";
        params.clear();
        params.add(uname);

        try {
            Admin sameUser = dbUtils.findSimpleProResult(sql, params, Admin.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被占用";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pass.length() > 60) {
            errorMessage = "密码过长";
        } else if (pass.length() == 0) {
            errorMessage = "密码为空，请输入密码";
        }

        return errorMessage;
    }
    /**
     * 管理员注册普通用户，检查输入信息
     * @param id id
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String adminAddUserCheck(String id, String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = null;
        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }

        // 检查id是否已经被注册
        DbUtils dbUtils= new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(Integer.parseInt(id));
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该id已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 检查该用户是否已被注册
        sql = "select * from `generalUser` where userName = ?";
        params.clear();
        params.add(un);
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
     /**
     * 注册界面检查：普通用户注册信息，检查合法性
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserSignUpCheck(String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserInfoCheck(un, pw, rname, idc, pho, addr); // 检查基本信息合法性
        // 检查该用户是否已被注册
        DbUtils dbUtils= new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where userName = ?";
        List<Object> params = new ArrayList<>();
        params.add(un);
        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该用户名已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    /**
     * 用户管理注册界面检查：检查合法性 并在原来的基础上检查id是否被注册
     * @param id 用户编号
     * @param un 用户名
     * @param pw 密码
     * @param rname 真实姓名
     * @param idc 身份证号
     * @param pho 联系电话
     * @param addr 联系地址
     * @return errorMessage 错误提示语句
     */
    public static String generalUserManageSignUpCheck(String id, String un, String pw, String rname, String idc, String pho, String addr) {
        String errorMessage = generalUserSignUpCheck(un, pw, rname, idc, pho, addr); // 检查注册信息是否合法

        // 在原来的基础上在检查id是否已经被注册
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();

        if (id == null || id.length() == 0) {
            return errorMessage = "id为空";
        } else if (id.length() > 11) {
            return errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "id应只含有数字";
            }
        }

        params.add(Integer.parseInt(id));

        try {
            GeneralUser sameUser = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            if (sameUser != null) {
                errorMessage = "该Id已被注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    /**
     * 检查新增会议室的常规信息
     * @param roomID 会议室编号
     * @param roomAddress 会议室地址
     * @param roomName 会议室名称
     * @param roomType 会议室状态 0：禁用状态 1：启用状态
     * @param roomCapacity 会议室最大可容纳人数
     * @param hourPrice 使用一小时费用
     * @param roomDescribe 会议室描述
     * @return
     */
    public static String meetingRoomRegularCheck(String roomID, String roomAddress, String roomName,
                                                 String roomType, String roomCapacity, String hourPrice, String roomDescribe) {
        String errorMessage = null;
        System.out.println("进入会议室检查");
        if (roomID.length() > 11) {
            errorMessage = "id过长";
        }
        if (roomAddress.length() > 30) {
            errorMessage = "地址过长，30字以内";
        }
        // 检查常规项
        if (roomName.length() == 0) {
            errorMessage = "会议室名称为空";
        }
        if (roomType.length() == 0) {
            errorMessage = "会议室状态为空";
        }
        if (roomCapacity.length() == 0) {
            errorMessage = "会议室容量为空";
        }
        if (hourPrice.length() == 0) {
            errorMessage = "价格为空";
        }
        if (roomDescribe.length() > 80) {
            errorMessage = "描述过长";
        }
        return errorMessage;
    }
    /**
     * 检查新加会议室信息，检查常规信息是否合法，并检查ID是否重复.
     * @param roomID 会议室编号
     * @param roomAddress 会议室地址
     * @param roomName 会议室名称
     * @param roomType 会议室状态 0：禁用状态 1：启用状态
     * @param roomCapacity 会议室最大可容纳人数
     * @param hourPrice 使用一小时费用
     * @param roomDescribe 会议室描述
     * @return errorMessage 错误提示语句
     */
    public static String meetingRoomSignUpCheck(String roomID, String roomAddress, String roomName,
                                             String roomType, String roomCapacity, String hourPrice, String roomDescribe) {
        String errorMessage = meetingRoomRegularCheck(roomID, roomAddress,roomName,roomType,roomCapacity,hourPrice,roomDescribe);   // 先检查常规信息
        //检查ID是否重复
        MeetingRoomService roomService = new MeetingRoomService();
        try {
            if (roomID == null || roomID.length() == 0) {
                return errorMessage = "id为空";
            } else {
                try {
                    Integer.parseInt(roomID);
                } catch (Exception e) {
                    return errorMessage = "id应只含有数字";
                }
            }
            MeetingRoom meetingRoom = roomService.findByID( Integer.parseInt(roomID) );
            if(meetingRoom != null) errorMessage = "该id已被注册";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    /**
     * 检查更新会议室信息，检查常规信息是否合法，并且id不允许被修改
     * @param oldInfo 旧会议室信息信息
     * @param newInfo 新会议室信息信息
     * @return errorMessage 错误提示语句
     */
    public static String meetingRoomUpdateCheck(MeetingRoom oldInfo, MeetingRoom newInfo) {
        String errorMessage = meetingRoomRegularCheck(Integer.toString(newInfo.getRoomID()), newInfo.getRoomAddress(), newInfo.getRoomName(), Boolean.toString(newInfo.isRoomType()),
                Integer.toString(newInfo.getRoomCapacity()), Double.toString(newInfo.getHourPrice()), newInfo.getRoomDescribe());
        // id不允许被修改
        if (oldInfo.getRoomID() != newInfo.getRoomID()) {
            errorMessage = "您不能修改会议室的ID";
        }
        return errorMessage;
    }

    /**
     * 检查会议信息，检查id、用户名、会议名称名、总金额等
     * @param id 新会议的id
     * @param name 新会议名称
     * @param partNum 新会议预计参会人数
     * @param startTime 新会议预计开始时间
     * @param price 新会议总金额
     * @return errorMessage 错误提示语句
     */
    public static String meetingSignUpCheck(String id, String name, String partNum, String startTime, String price) {
        String errorMessage = null;
        // 检查ID
        if (id.length() == 0) {
            return errorMessage = "会议编号不能为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的编号过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                return errorMessage = "会议编号应只含有数字";
            }

            MeetingService mService = new MeetingService();
            Meeting meeting = mService.findByID( Integer.parseInt(id) );
            if(meeting != null) return errorMessage = "该编号已存在";
        }
        // 检查总金额
        if (price == null || price.length() == 0) {
            errorMessage = "会议预计金额为空";
        } else if(price.length() > 11) {
            return  errorMessage = "预计金额过大";
        } else {
            try {
                Double.parseDouble(price);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "预计金额只能包含数字";
            }
        }
        return errorMessage;
    }

    /**
     * 检查订单更新信息，检查id、用户名、报刊名、总金额
     * @param old 旧会议信息
     * @param id 新会议的id
     * @param name 新会议名称
     * @param partNum 新会议预计参会人数
     * @param startTime 新会议预计开始时间
     * @param price 新会议总金额
     * @return errorMessage 错误提示语句
     */
    public static String meetingUpdateCheck(Meeting old, String id, String name, String partNum, String startTime, String price) {
        String errorMessage = null;
        // 检查id
        if (id == null || id.length() == 0){
            return errorMessage = "id为空";
        } else if(id.length() > 11) {
            return  errorMessage = "您输入的id过长";
        } else {
            try {
                Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                return errorMessage = "id只能包含数字";
            }
            if (old.getMeetID() != Integer.parseInt(id)) {
                return errorMessage = "您不可以更改会议的ID,想要修改id可以考虑删除后新建会议";
            }
        }

        // 检查总金额
        if (price == null || price.length() == 0) {
            errorMessage = "会议预计金额为空";
        } else if(price.length() > 11) {
            return  errorMessage = "预计金额过大";
        } else {
            try {
                Double.parseDouble(price);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "预计金额只能包含数字";
            }
        }
        return errorMessage;
    }

}