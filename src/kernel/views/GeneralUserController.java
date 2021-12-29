package kernel.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import kernel.Main;
import kernel.NowUser;
import kernel.entity.GeneralUser;
import kernel.entity.Meeting;
import kernel.entity.MeetingRoom;
import kernel.service.MeetingRoomService;
import kernel.service.MeetingService;
import kernel.service.UserService;
import kernel.util.CheckerUtil;
import kernel.util.DbUtils;
import kernel.util.SysHintUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralUserController {
    MeetingRoomService roomService;
    MeetingService meetService;
    UserService userService;
    // 菜单栏组件
    @FXML
    private Label userNameLabel;    //用户名标签

    // 三个页面面板
    @FXML
    private BorderPane scheduleMeetingPane;             // 预定会议面包

    @FXML
    private BorderPane personalInfoPane;                // 个人信息修改面板

    @FXML
    private BorderPane myMeetingPane;                   // 我的会议面板

    // 预定会议界面组件
    @FXML
    private TableView<MeetingRoom> roomsTable;  // 左侧表格

    @FXML
    private TableColumn<MeetingRoom, String> roomAddressColumn;     // 会议室地址列

    @FXML
    private TableColumn<MeetingRoom, String> roomNameColumn;     // 会议室名称列

    @FXML
    private TableColumn<MeetingRoom, Double> roomHourPriceColumn;    // 会议室一小时费用列

    @FXML
    private TableColumn<MeetingRoom, Integer> roomCapacityColumn;    // 会议室可容纳人数列

    @FXML
    private ChoiceBox<Integer> durationChoiceBox;       // 持续时间数选择盒子

    @FXML
    private Label totalPriceLabel;                  // 总金额标签

    @FXML
    private TextField meetNameTextField;          // 会议名称文本框

    @FXML
    private TextField meetPartNumField;          // 预计参会人数文本框

    @FXML
    private TextField meetStartTimeField;          // 预计参会人数文本框

    @FXML
    private TextArea meetDesIntroTextArea;          // 会议简介文本域

    @FXML
    private Button confirmScheduleButton;                   // 确认订阅按钮

    @FXML
    private Label roomIDLabel;                        // 会议室编号标签

    @FXML
    private Label roomNameLabel;                      // 会议室名称标签

    @FXML
    private Label roomAddressLabel;                    // 会议室地址标签

    @FXML
    private Label roomHourPriceLabel;                     // 一小时费用标签

    @FXML
    private Label roomCapacityLabel;                     // 分类标签

    @FXML
    private Label roomDesIntroLabel;                     // 内容简介标签

    // 个人信息修改面板组件
    @FXML
    private TextField userNameField;                // 用户名文本框

    @FXML
    private TextField passWordField;                // 密码文本框

    @FXML
    private TextField realNameField;                // 真实姓名文本框

    @FXML
    private TextField idCardField;                  // 身份证号文本框

    @FXML
    private TextField phoneField;                   // 联系电话文本框

    @FXML
    private TextField addressField;                 // 联系地址文本框

    @FXML
    private Button confirmChangeButton;                   // 确认修改按钮

    // 我的会议面板组件
    @FXML
    private TableView<Meeting> myMeetingTable;              // 会议表

    @FXML
    private TableColumn<Meeting, Integer> myMeetIDColumn;           // 会议编号列

    @FXML
    private TableColumn<Meeting, String> myMeetNameColumn; // 会议名称列

    @FXML
    private TableColumn<Meeting, String> myMeetStartTimeColumn; // 会议开始时间列

    @FXML
    private TableColumn<Meeting, Integer> myMeetDurationColumn;  // 会议持续时间列

    @FXML
    private TableColumn<Meeting, Integer> myMeetPartNumColumn;   // 会议参与人数列

    @FXML
    private TableColumn<Meeting, Double> myMeetPriceColumn;  // 会议总费用列

    @FXML
    private Label myMeetNameLabel;                     //会议详情-会议名称标签

    @FXML
    private Label myMeetPartNumLabel;                     //会议详情-预计参会人数标签

    @FXML
    private Label myMeetStartTimeLabel;                     //会议详情-开始时间标签

    @FXML
    private Label myMeetDesIntroLabel;                     //会议详情-会议说明标签

    @FXML
    private Label myMeetRoomAddressLabel;                     //会议详情-会议室地址标签

    @FXML
    private Label myMeetRoomNameLabel;                     //会议详情-会议室地址标签

    @FXML
    private Label myMeetDurationLabel;                     //会议详情-持续时间标签

    @FXML
    private Label myMeetPriceLabel;                     //会议详情-费用合计标签
    @FXML
    private Main mainApp;               // 主程序

    private ObservableList<MeetingRoom> roomData = FXCollections.observableArrayList();   // 数据库查询而得到的会议室信息

    private ObservableList<Meeting> meetingData = FXCollections.observableArrayList();          // 数据库查询而得到的订单


    public GeneralUserController() { }

    public Main getMainApp() { return mainApp; }

    public void setMainApp(Main mainApp) { this.mainApp = mainApp; }

    /**
     * 初始化各控件，包括列表的数据绑定和函数监听,在load时会执行这些操作。
     * 这个时候mainApp还未被设置，所以是null，需要全局用户来设置用户名标签
     * 这个函数，会在构造函数结束后执行
     */
    @FXML
    private void initialize() {
        roomService = new MeetingRoomService();
        meetService = new MeetingService();
        userService = new UserService();
        roomAddressColumn.setCellValueFactory(cellData -> cellData.getValue().roomAddressProperty());
        roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        roomHourPriceColumn.setCellValueFactory(cellData -> cellData.getValue().hourPriceProperty().asObject());
        roomCapacityColumn.setCellValueFactory(cellData -> cellData.getValue().roomCapacityProperty().asObject());

        myMeetIDColumn.setCellValueFactory(cellData -> cellData.getValue().meetIDProperty().asObject());
        myMeetNameColumn.setCellValueFactory(cellData -> cellData.getValue().meetNameProperty());
        myMeetStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        myMeetDurationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        myMeetPartNumColumn.setCellValueFactory(cellData -> cellData.getValue().partNumProperty().asObject());
        myMeetPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        initTable();
        initMyMeetingPane();
        initChoiceBox();
        /*
         *==================================================
         *                 !!important!!
         *       监听器要放到后面，避免未初始化的监听
         *      使用lambda表达式简化操作：https://blog.csdn.net/phcgld1314/article/details/78028995
         *==================================================
         */
        // 监听表格点击，获取点击的表格项并更新右侧详细信息
        roomsTable.getSelectionModel().selectedItemProperty().addListener(

                ((observable, oldValue, newValue) -> updateDetail(newValue))
        );
        // 监听表格点击，获取点击的表格项并更新下方详细信息
        myMeetingTable.getSelectionModel().selectedItemProperty().addListener(

                ((observable, oldValue, newValue) -> updateMyMeetingDetail(newValue))
        );
        // 监听持续时间选择盒子，获取持续时间并更新总金额
        durationChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateTotalPrice()
        );
        roomsTable.getSelectionModel().selectFirst();
        myMeetingTable.getSelectionModel().selectFirst();
        /*
         *==================================================
         *                 !!important!!
         *     这里mainApp还没有载入，所以不能使用，一直为null
         *==================================================
         */
        initUserNameLabel();
    }


    /**
     * 初始化预定会议界面会议室显示表格
     */
    public void initTable() {
        List<MeetingRoom> rawData = roomService.getAvailableList();
        roomData.addAll(rawData);
        roomsTable.setItems(roomData);
    }

    private void initMyMeetingPane() {
        List<Meeting> myMeetings = meetService.getAllMeetingByUID(NowUser.getId());
        meetingData.addAll(myMeetings);
        myMeetingTable.setItems(meetingData);
    }
    /**
     * 初始化选择盒子
     */
    private void initChoiceBox() {
        // 初始化订阅周期选择盒子
        ObservableList<Integer> choiceBox1 = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            choiceBox1.add(i-1, i);
        }
        durationChoiceBox.setItems(choiceBox1);
        durationChoiceBox.setValue(1);
    }


    /**
     * 初始化用户名标签
     */
    private void initUserNameLabel() { userNameLabel.setText(NowUser.getUsername()); }


    /**
     * 初始化个人信息修改面板
     */
    private void initPersonInfoPane() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser` where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(NowUser.getId());
        try {
            // 找到这个用户
            GeneralUser userNow = dbUtils.findSimpleProResult(sql, params, GeneralUser.class);
            // 设置显示对应的原始数据
            userNameField.setText(userNow.getUserName());
            passWordField.setText(userNow.getPassWord());
            realNameField.setText(userNow.getRealName());
            idCardField.setText(userNow.getIdCard());
            phoneField.setText(userNow.getPhone());
            addressField.setText(userNow.getAddress());
        } catch (Exception e) {
            System.out.println("初始化用户信息失败！");
            e.printStackTrace();
        }
    }


    /**
     * 更新右侧详细信息的展示界面
     * @param newValue 当前会议室对象
     */
    public void updateDetail(MeetingRoom newValue) {
        // 依次更新
        if (newValue != null) {
            roomIDLabel.setText("会议室编号：" + newValue.getRoomID());
            roomAddressLabel.setText("会议室地址：" + newValue.getRoomAddress());
            roomNameLabel.setText("会议室名称：" + newValue.getRoomName());
            roomHourPriceLabel.setText("一小时费用：" + newValue.getHourPrice());
            roomCapacityLabel.setText("可容纳人数：" + newValue.getRoomCapacity());
            roomDesIntroLabel.setText("会议室描述：" + newValue.getRoomDescribe());
            updateTotalPrice();
            System.out.println("详细信息已被更新");
        }
    }

    private void updateMyMeetingDetail(Meeting newValue) {
        // 依次更新
        if (newValue != null) {
            myMeetNameLabel.setText(newValue.getMeetName());
            myMeetPartNumLabel.setText(""+newValue.getPartNum());
            myMeetStartTimeLabel.setText(newValue.getStartTime());
            myMeetDesIntroLabel.setText(newValue.getDescription());
            myMeetRoomAddressLabel.setText( roomService.getRoomAddressByID(newValue.getRid()) );
            myMeetRoomNameLabel.setText( roomService.getRoomNameByID(newValue.getRid()) );
            myMeetDurationLabel.setText(""+newValue.getDuration());
            myMeetPriceLabel.setText(""+newValue.getPrice());
            System.out.println("详细信息已被更新");
        }
    }

    /**
     * 更新所选会议预计总费用
     */
    private void updateTotalPrice() {
        int duration = durationChoiceBox.getValue();
        double hourPrice = roomsTable.getSelectionModel().getSelectedItem().getHourPrice();
        totalPriceLabel.setText("预计费用：" +  duration*hourPrice + "￥");
        System.out.println("预计费用已更新");
    }

    /**
     * 跳转到预定会议界面
     * 由于是一开始就初始化好了的，所以不需再次初始化。
     */
    @FXML
    private void jumpToSubscriptionPane() {
        myMeetingPane.setVisible(false);
        personalInfoPane.setVisible(false);
        scheduleMeetingPane.setVisible(true);
    }

    /**
     * 跳转到个人信息修改界面
     */
    @FXML
    private void jumpToPersonInfoPane() {
        scheduleMeetingPane.setVisible(false);
        myMeetingPane.setVisible(false);
        initPersonInfoPane();
        personalInfoPane.setVisible(true);
    }

    /**
     * 跳转到我的会议界面
     */
    @FXML
    private void jumpToMyMeetingPane() {
        System.out.println("进入我的会议界面");
        personalInfoPane.setVisible(false);
        scheduleMeetingPane.setVisible(false);
        initMyMeetingPane();
        myMeetingPane.setVisible(true);
    }

    /**
     * 确认预定功能：用户确认预定会议总金额，向数据库提交订单信息。
     */
    @FXML
    void handleConfirm() {
        // 弹出确认提示，如用户选择取消则返回
        if(!SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "确认您选择的会议室信息是否正确，会议预定后无法更改！")) {
            return;
        }
        MeetingRoom room = roomsTable.getSelectionModel().getSelectedItem();
        String mName = meetNameTextField.getText();
        int partNum = Integer.parseInt(meetPartNumField.getText());
        String startTime = meetStartTimeField.getText();
        int duration = durationChoiceBox.getValue();
        double hourPrice = roomsTable.getSelectionModel().getSelectedItem().getHourPrice();
        double price = duration*hourPrice;
        String des = meetDesIntroTextArea.getText();
        int uid = NowUser.getId();
        int rid = room.getRoomID();
        Meeting meeting = new Meeting(0, mName, partNum, startTime, duration, price, des, uid, rid);    //id随便给 自动生成
        meetService.addInfoWithoutID(meeting);
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "预定会议成功");
    }

    /**
     * 跳转修改个人信息界面之后，点击确认修改按钮后进入
     * 向数据库提交改变个人信息的表单
     */
    @FXML
    private void handleChange() {
        // 检查输入合法性
        String errorMessage = CheckerUtil.userUpdateCheck(NowUser.getUsername(),userNameField.getText(), passWordField.getText(),
                realNameField.getText(), idCardField.getText(), phoneField.getText(), addressField.getText());
        if(errorMessage != null) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
            return;
        }
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "update `generalUser` set userName = ?, passWord = ?, realName = ?, " +
                "idCard = ?, phone = ?, address = ? where id = ?";
        List<Object> params = new ArrayList<>();

        // 添加更改后的新值
        params.add(userNameField.getText());
        params.add(passWordField.getText());
        params.add(realNameField.getText());
        params.add(idCardField.getText());
        params.add(phoneField.getText());
        params.add(addressField.getText());
        params.add(NowUser.getId());
        try {
            dbUtils.updateByPreparedStatement(sql, params);
            SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            NowUser.setUsername(userNameField.getText());
            initUserNameLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        /**
     * 注销功能：用户退出，返回登录界面
     */
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/LoginWindows.fxml"));
            Parent loginView = loader.load();
            LoginViewController loginViewController = loader.getController();
            mainApp.getPrimaryStage().setScene(new Scene(loginView, 1253, 600));
            mainApp.getPrimaryStage().show();
            loginViewController.setMainApp(mainApp);
        } catch (IOException e) {
            System.out.println("退出失败！");
            e.printStackTrace();
        }
    }

}
