package kernel.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import kernel.Main;
import kernel.NowUser;
import kernel.entity.*;
import kernel.service.EquipmentService;
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
import java.util.Map;

public class AdminViewController {
    UserService userService;
    EquipmentService eService;
    MeetingRoomService roomService;
    MeetingService meetingService;
    // 菜单栏组件
    @FXML
    private Label topBarUserNameLabel;    //用户名标签

    // 五个页面面板
    @FXML
    private BorderPane roomManagePane;              // 会议室管理面板  最开始进入这个面板

    @FXML
    private BorderPane generalUserManagePane;       // 用户管理面板

    @FXML
    private BorderPane adminManagePane;             // 管理员管理面板

    @FXML
    private BorderPane meetingManagePane;             // 会议管理面板

    @FXML
    private BorderPane equipmentManagePane;             // 设备管理面板

    @FXML
    private BorderPane searchAndStatisticPane;             // 查询及统计面板

    // 会议室管理界面组件
    @FXML
    private TableView<MeetingRoom> roomTable;  // 左侧表格

    @FXML
    private TableColumn<MeetingRoom, Integer> roomRidColumn;     // 会议室编号列

    @FXML
    private TableColumn<MeetingRoom, String> roomNameColumn;     // 会议室名称列

    @FXML
    private TableColumn<MeetingRoom, Double> roomPriceColumn;     // 会议室费用列

    @FXML
    private TableColumn<MeetingRoom, Boolean> roomTypeColumn;     // 会议室状态列

    @FXML
    private Button roomAddButton;                     // 添加会议室按钮

    @FXML
    private Button roomDeleteButton;                  // 删除会议室按钮

    @FXML
    private Button roomUpdateButton;                  // 更新会议室按钮

    @FXML
    private TextField rmIdField;                      // 会议室编号文本框

    @FXML
    private TextField rmAddressField;                   // 会议室地址文本框

    @FXML
    private TextField rmNameField;                      // 会议室名称文本框

    @FXML
    private ChoiceBox<String> rmTypeChoiceBox;       // 会议室状态选择盒子

    @FXML
    private TextField rmCapacityField;                   // 会议室最大可容纳人数文本框

    @FXML
    private TextField rmHourPriceField;                   // 会议室使用一小时费用

    @FXML
    private TextArea rmDesIntroArea;                     // 会议室描述文本域

    private ObservableList<MeetingRoom> rmManageData = FXCollections.observableArrayList();   // 数据库查询而得到的会议室信息

    // 用户管理面板
    @FXML
    private TableView<GeneralUser> generalUserTable;                    // 左侧普通用户表格

    @FXML
    private TableColumn<GeneralUser, Integer> generalUserIdColumn;      // 普通用户id列

    @FXML
    private TableColumn<GeneralUser, String> generalUserNameColumn;     // 普通用户名列

    @FXML
    private Button generalUserManageAddButton;                          // 添加普通用户按钮

    @FXML
    private Button generalUserManageDeleteButton;                       // 删除普通用户按钮

    @FXML
    private Button generalUserManageUpdateButton;                       // 更新普通用户按钮

    @FXML
    private TextField generalUserIdField;                       // 用户id文本框

    @FXML
    private TextField generalUserNameField;                     // 用户名文本框

    @FXML
    private TextField generalUserPassWordField;                 // 用户密码文本框

    @FXML
    private TextField generalUserRealNameField;                 // 用户真实姓名文本框

    @FXML
    private TextField generalUserIdCardField;                   // 用户身份证号文本框

    @FXML
    private TextField generalUserPhoneField;                    // 用户联系电话文本框

    @FXML
    private TextField generalUserAddressField;                  // 用户联系地址文本框

    private ObservableList<GeneralUser> generalUsersData = FXCollections.observableArrayList();   // 数据库查询而得到的用户信息

    // 管理员管理面板
    @FXML
    private TableView<Admin> adminTable;                            // 左侧管理员表格

    @FXML
    private TableColumn<Admin, Integer> adminIdColumn;              // 管理员id列

    @FXML
    private TableColumn<Admin, String> adminUserNameColumn;         // 管理员用户名列

    @FXML
    private Button adminManageAddButton;                            // 添加管理员按钮

    @FXML
    private Button adminManageDeleteButton;                         // 删除管理员按钮

    @FXML
    private Button adminManageUpdateButton;                         // 更新管理员按钮

    @FXML
    private TextField adminIdField;                                 // 管理员id文本框

    @FXML
    private TextField adminUserNameField;                           // 管理员用户名文本框

    @FXML
    private TextField adminPassWordField;                           // 管理员密码文本框

    private ObservableList<Admin> adminsData = FXCollections.observableArrayList();   // 数据库查询而得到的管理员信息

    // 会议信息管理界面组件
    @FXML
    private TableView<Meeting> meetTable;  // 左侧表格

    @FXML
    private TableColumn<Meeting, Integer> meetIDColumn;         // 会议编号列

    @FXML
    private TableColumn<Meeting, String> meetNameColumn;         // 会议名称列

    @FXML
    private TableColumn<Meeting, String> meetSartTimeColumn;           // 预计开始时间

    @FXML
    private TableColumn<Meeting, Double> meetPriceColumn;            // 预计费用列

    @FXML
    private Button meetAddButton;                            // 添加会议按钮

    @FXML
    private Button meetDeleteButton;                         // 删除会议按钮

    @FXML
    private Button meetUpdateButton;                         // 更新会议按钮

    @FXML
    private TextField meetIDField;                      // 会议编号文本框

    @FXML
    private TextField meetNameField;                      // 会议名称文本框

    @FXML
    private ComboBox<String> meetUserNameChoiceBox;       // 预定户名选择盒子

    @FXML
    private ComboBox<String> meetRoomNameChoiceBox;       // 所订会议室名称选择盒子

    @FXML
    private TextField meetStartTimeField;                   // 预计开始时间文本框

    @FXML
    private ComboBox<Integer> meetPartNumChoiceBox;        // 预计参会人数选择盒子

    @FXML
    private ComboBox<Integer> meetDurationChoiceBox;       // 预计持续时间选择盒子

    @FXML
    private TextField meetTotalPriceField;                    // 会议预计费用文本框

    @FXML
    private TextArea meetDesIntroArea;                    // 会议预计费用文本框

    private ObservableList<Meeting> meetingsdata = FXCollections.observableArrayList();   // 数据库查询而得到的会议表格信息

    // 设备管理界面组件
    @FXML
    private TableView<Equipment> equipmentTable;  // 左侧表格

    @FXML
    private TableColumn<Equipment, Integer> equipmentIDColumn;     // 设备编号列

    @FXML
    private TableColumn<Equipment, String> equipmentNameColumn;     // 设备名称列

    @FXML
    private TableColumn<Equipment, Boolean> equipmentTypeColumn;     // 设备状态列

    @FXML
    private Button equipmentAddButton;                     // 添加设备按钮

    @FXML
    private Button equipmentDeleteButton;                  // 删除设备按钮

    @FXML
    private Button equipmentUpdateButton;                  // 更新设备按钮

    @FXML
    private TextField equipmentIDField;                      // 设备编号文本框

    @FXML
    private TextField equipmentNameField;                      // 设备名称文本框

    @FXML
    private ChoiceBox<String> equipmentTypeChoiceBox;       // 设备状态选择盒子

    @FXML
    private TextField equipmentValueField;                   // 设备价值文本框

    @FXML
    private TextField equipmentMIDField;                      // 所属会议室编号文本框

    private ObservableList<Equipment> eqManageData = FXCollections.observableArrayList();   // 数据库查询而得到的设备信息

    // 查询与统计界面组件
    @FXML
    private Label mostBusyUserLabel;    //最常开会用户名标签

    @FXML
    private Label mostPopularRoomLabel;    //最常使用会议室名称标签

    @FXML
    private ComboBox<String> searchUserNameChoiceBox;           // 查询用户名选择盒子

    @FXML
    private ComboBox<String> searchRoomNameChoiceBox;       // 查询会议室名选择盒子

    @FXML
    private Button searchButton;                            // 查询按钮

    @FXML
    private TableView<Meeting> searchTable;  // 表格

    @FXML
    private TableColumn<Meeting, Integer> searchIDColumn;            // 会议编号列

    @FXML
    private TableColumn<Meeting, String> searchMeetNameColumn;        // 会议名称列

    @FXML
    private TableColumn<Meeting, Integer> searchRoomNameColumn;          // 会议室名称列 实体类中存的是int的rid，需重载该显示

    @FXML
    private TableColumn<Meeting, Integer> searchUserNameColumn;        // 预定用户名列 实体类中存的是int的uid，需重载该显示

    @FXML
    private TableColumn<Meeting, Integer> searchPartNumColumn;            // 会议编号列

    @FXML
    private TableColumn<Meeting, String> searchStartTimeColumn;        // 预定用户名列

    @FXML
    private TableColumn<Meeting, Integer> searchDurationColumn;            // 会议编号列

    @FXML
    private TableColumn<Meeting, Double> searchPriceColumn;          // 预计费用列

    @FXML
    private TableColumn<Meeting, String> searchDescriptionColumn;        // 预定用户名列

    private ObservableList<Meeting> searchMeetingsdata = FXCollections.observableArrayList();   // 数据库查询而得到的订单信息

    // 各选项盒子数据
    private ObservableList<String> meetingUserNameChoiceBoxdata = FXCollections.observableArrayList();  // 用户名选择
    private ObservableList<String> meetRoomNameChoiceBoxdata = FXCollections.observableArrayList();     // 会议室名称选择
    private ObservableList<String> searchUserNameChoiceBoxdata = FXCollections.observableArrayList();
    private ObservableList<String> searchRoomNameChoiceBoxdata = FXCollections.observableArrayList();

    private Main mainApp;               // 主程序

    public AdminViewController() { System.out.println("构造函数"); }

    public Main getMainApp() { return mainApp; }

    public void setMainApp(Main mainApp) { this.mainApp = mainApp; }

    /**
     * 初始化各控件，包括列表的数据绑定和函数监听,在load时会执行这些操作。
     * 这个时候mainApp还未被设置，所以是null，需要全局用户来设置用户名标签
     * 这个函数，会在构造函数结束后执行
     */
    @FXML
    private void initialize() {
        System.out.println("初始化页面…");
        userService = new UserService();
        eService = new EquipmentService();
        roomService = new MeetingRoomService();
        meetingService = new MeetingService();
        // 初始化表格
        // 会议室列表
        roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        roomRidColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().hourPriceProperty().asObject());
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<MeetingRoom, Boolean>("roomType"));
        roomTypeColumn.setCellFactory(col-> {
                    TableCell<MeetingRoom, Boolean> cell = new TableCell<MeetingRoom, Boolean>() {
                        @Override
                        public void updateItem(Boolean item, boolean empty) {
                            super.updateItem(item, empty);
                            this.setText(null);
                            this.setGraphic(null);
                            if (!empty) {
                                int rowIndex = this.getIndex();
                                boolean flag = roomTable.getItems().get(rowIndex).isRoomType();
                                this.setText((flag == true ? "启用中" : "禁用中"));
                            }
                        }
                    };
			return cell;
        });
        roomTable.getSelectionModel().selectedItemProperty().addListener(       // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> rmUpdateDetail(newValue))
        );
        initRoomManageTable();            // 初始化会议室管理界面的表格
        // 用户列表
        generalUserIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        generalUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        generalUserTable.getSelectionModel().selectedItemProperty().addListener(    // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> generalUserUpdateDetail(newValue))
        );
        initGeneralUserTable();            // 初始化用户管理界面的表格
        // 管理员列表
        adminIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        adminUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        adminTable.getSelectionModel().selectedItemProperty().addListener(          // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> adminUpdateDetail(newValue))
        );
        initAdminTable();            // 初始化管理员管理界面的表格
        // 会议列表
        meetIDColumn.setCellValueFactory(cellData -> cellData.getValue().meetIDProperty().asObject());
        meetNameColumn.setCellValueFactory(cellData -> cellData.getValue().meetNameProperty());
        meetSartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        meetPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        meetTable.getSelectionModel().selectedItemProperty().addListener(          // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> meetingUpdateDetail(newValue))
        );
        initMeetingTable();            // 初始化会议管理界面的表格

        // 会议室列表
        equipmentIDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        equipmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        equipmentTypeColumn.setCellValueFactory(new PropertyValueFactory<Equipment, Boolean>("status"));
        equipmentTypeColumn.setCellFactory(col-> {
            TableCell<Equipment, Boolean> cell = new TableCell<Equipment, Boolean>() {
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        boolean flag = equipmentTable.getItems().get(rowIndex).isStatus();
                        this.setText((flag == true ? "可用" : "已损坏"));
                    }
                }
            };
            return cell;
        });
        equipmentTable.getSelectionModel().selectedItemProperty().addListener(       // 监听表格点击，获取点击的表格项并更新右侧详细信息
                ((observable, oldValue, newValue) -> equipmentUpdateDetail(newValue))
        );
        initEquipmentTable();            // 初始化设备管理界面的表格
        // 查询列表
        searchIDColumn.setCellValueFactory(cellData -> cellData.getValue().meetIDProperty().asObject());
        searchMeetNameColumn.setCellValueFactory(cellData -> cellData.getValue().meetNameProperty());
        searchUserNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, Integer>("uid"));
        searchUserNameColumn.setCellFactory(col-> {
            TableCell<Meeting, Integer> cell = new TableCell<Meeting, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String userName = userService.getUserNameByID( searchTable.getItems().get(rowIndex).getUid() );
                        this.setText(userName);
                    }
                }
            };
            return cell;
        });
        searchRoomNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, Integer>("rid"));
        searchRoomNameColumn.setCellFactory(col-> {
            TableCell<Meeting, Integer> cell = new TableCell<Meeting, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String roomName = roomService.getRoomNameByID( searchTable.getItems().get(rowIndex).getRid() );
                        this.setText(roomName);
                    }
                }
            };
            return cell;
        });
        searchPartNumColumn.setCellValueFactory(cellData -> cellData.getValue().partNumProperty().asObject());
        searchStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        searchDurationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        searchPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        searchDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // 这个不用监听
        initSearchTable();            // 初始化订单管理界面的表格
        System.out.println("初始化表格完毕…");

        // 初始化选择盒子
        initRoomManageChoiceBox();        // 初始化会议室管理界面的选择盒子
        initMeetingManageChoiceBox();        // 初始化会议管理界面的选择盒子
        initEquipmentChoiceBox();        // 初始化设备管理界面的选择盒子
        initSearchManageChoiceBox();        // 初始化查询及统计界面的选择盒子

        // 初始化用户名标签
        initTopBarUserNameLabel();

        // 初始化查询与统计面板
        initSearchAndStatisticPane();
    }

    /**
     * 初始化菜单栏用户名标签
     */
    private void initTopBarUserNameLabel() { topBarUserNameLabel.setText(NowUser.getUsername()); }


    /**
     * 初始化查询与统计面板
     */
    private void initSearchAndStatisticPane() {
        initSearchTable();
        mostBusyUserLabel.setText(userService.findUserNameMostBusy());
        mostPopularRoomLabel.setText(roomService.findRoomNameMostPopular());
    }

    /**
     * 初始化用户管理界面左侧用户显示列表
     */
    private void initGeneralUserTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `generalUser`;";
        List<Object> params = new ArrayList<>();
        try {
            List<GeneralUser> generalUsers = dbUtils.findMoreProResult(sql, params, GeneralUser.class);
            generalUsersData.clear();
            generalUsersData.addAll(generalUsers);
            generalUserTable.setItems(generalUsersData);
            generalUserTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化报刊管理界面左侧报刊显示列表
     */
    public void initRoomManageTable() {
        System.out.println("initRoomManageTable……");
        MeetingRoomService roomService = new MeetingRoomService();
        List<MeetingRoom> rawData = roomService.getList();
        rmManageData.clear();               // 重要 不然会重复添加
        rmManageData.addAll(rawData);
        roomTable.setItems(rmManageData);
        roomTable.getSelectionModel().selectFirst();
    }

    /**
     * 初始化管理员管理界面左侧管理员显示列表
     */
    private void initAdminTable() {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "select * from `admin`;";
        List<Object> params = new ArrayList<>();
        try {
            List<Admin> admins = dbUtils.findMoreProResult(sql, params, Admin.class);
            adminsData.clear();
            adminsData.addAll(admins);
            adminTable.setItems(adminsData);
            adminTable.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化订单管理界面左侧订单显示列表
     */
    private void initMeetingTable() {
        List<Meeting> meetings = meetingService.getList();
        meetingsdata.clear();
        meetingsdata.addAll(meetings);
        meetTable.setItems(meetingsdata);
        meetTable.getSelectionModel().selectFirst();
    }

    /**
     * 初始化设备管理界面左侧设备显示列表
     */
    private void initEquipmentTable() {
        List<Equipment> equipments = eService.getList();
        eqManageData.clear();
        eqManageData.addAll(equipments);
        equipmentTable.setItems(eqManageData);
        equipmentTable.getSelectionModel().selectFirst();
    }

    /**
     * 初始化查询管理界面会议显示列表为全部会议
     */
    private void initSearchTable() {
        List<Meeting> meetings = meetingService.getList();
        searchMeetingsdata.clear();
        searchMeetingsdata.addAll(meetings);
        searchTable.setItems(searchMeetingsdata);
        searchTable.getSelectionModel().selectFirst();
    }

    /**
     * 初始化字符串形式的选择盒子，通过sql语句选择对应其数据库信息
     * @param sql 数据库选择语句
     * @param box 选择盒子
     * @param items 选择盒子的数据
     */
    private void initBox(String sql, ComboBox<String> box, ObservableList<String> items) {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        List<Object> params = new ArrayList<>();
        try {
            items.clear();
            List<Map<String, Object>> names = dbUtils.findModeResult(sql, params);
            for (Map<String, Object> name : names) {
                items.add(name.get("name").toString());
            }
            box.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化会议室管理界面选择盒子
     * 这种的就直接加了
     */
    private void initRoomManageChoiceBox() {
        // 初始化出版周期选择盒子
        ObservableList<String> choiceBox1 = FXCollections.observableArrayList();
        choiceBox1.add("禁用状态");
        choiceBox1.add("启用状态");
        rmTypeChoiceBox.setItems(choiceBox1);
    }

    /**
     * 初始会议管理界面选择盒子
     */
    private void initMeetingManageChoiceBox() {

        initBox("select userName as name from `generalUser`", meetUserNameChoiceBox, meetingUserNameChoiceBoxdata);  // 初始化用户名盒子为所有已有用户名
        initBox("select roomName as name from `meetingroom`", meetRoomNameChoiceBox, meetRoomNameChoiceBoxdata);                 // 初始化报刊名选择盒子为所有报刊名称
        // 初始化参会人数选择盒子
        ObservableList<Integer> partNums = FXCollections.observableArrayList();
        for (int i = 1; i <= 30; i++) {
            partNums.add(i*10);
        }
        meetPartNumChoiceBox.setItems(partNums);

        // 初始化持续时间选择盒子
        ObservableList<Integer> duration = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            duration.add(i);
        }
        meetDurationChoiceBox.setItems(duration);
    }

    /**
     * 初始化设备管理界面选择盒子
     * 这种的就直接加了
     */
    private void initEquipmentChoiceBox() {
        // 初始化出版周期选择盒子
        ObservableList<String> choiceBox1 = FXCollections.observableArrayList();
        choiceBox1.add("损坏状态");
        choiceBox1.add("可用状态");
        equipmentTypeChoiceBox.setItems(choiceBox1);
    }
    /**
     * 初始化查询及统计界面选择盒子
     */
    private void initSearchManageChoiceBox() {

        initBox("select userName as name from `generalUser`", searchUserNameChoiceBox, searchUserNameChoiceBoxdata);  // 初始化用户名盒子为所有已有用户名
        initBox("select roomName as name from `meetingroom`", searchRoomNameChoiceBox, searchRoomNameChoiceBoxdata);  // 初始化会议室选择盒子为所有会议室
    }

    /**
     * 更新会议室管理界面右侧会议室信息的展示
     * @param nowrm 当前会议室对象
     */
    public void rmUpdateDetail(MeetingRoom nowrm) {
        if(nowrm != null) {
            System.out.println("rmUpdate");
            rmIdField.setText(""+nowrm.getRoomID());
            rmAddressField.setText(nowrm.getRoomAddress());
            rmNameField.setText(nowrm.getRoomName());
            rmTypeChoiceBox.setValue((nowrm.isRoomType() == true ? "启用状态":"禁用状态"));
            rmCapacityField.setText(""+nowrm.getRoomCapacity());
            rmHourPriceField.setText(""+nowrm.getHourPrice());
            rmDesIntroArea.setText(nowrm.getRoomDescribe());
            System.out.println("会议室详细信息已被更新");
        }
    }

    /**
     * 更新用户管理界面右侧管理信息的展示
     * @param newValue 当前用户对象
     */
    private void generalUserUpdateDetail(GeneralUser newValue) {
        if(newValue != null) {
            generalUserIdField.setText(""+newValue.getId());
            generalUserNameField.setText(newValue.getUserName());
            generalUserPassWordField.setText(newValue.getPassWord());
            generalUserRealNameField.setText(newValue.getRealName());
            generalUserIdCardField.setText(newValue.getIdCard());
            generalUserPhoneField.setText(newValue.getPhone());
            generalUserAddressField.setText(newValue.getAddress());
            System.out.println("用户详细信息已被更新");
        }
    }

    /**
     * 更新管理员管理界面右侧管理信息的展示
     * @param newValue 当前管理员对象
     */
    private void adminUpdateDetail(Admin newValue) {
        if (newValue != null) {
            // 依次更新
            adminIdField.setText(""+newValue.getId());
            adminUserNameField.setText(newValue.getUserName());
            adminPassWordField.setText(newValue.getPassWord());
            System.out.println("管理员详细信息已被更新");
        }
    }

    /**
     * 更新会议管理界面右侧管理信息的展示
     * @param newValue 当前会议对象
     */
    private void meetingUpdateDetail(Meeting newValue) {
        if (newValue != null) {
            // 依次更新
            meetIDField.setText((""+newValue.getMeetID()));
            meetNameField.setText(newValue.getMeetName());
            String userName = userService.getUserNameByID(newValue.getUid());
            String roomName = roomService.getRoomNameByID(newValue.getRid());
//            System.out.println(userName);
//            System.out.println(roomName);
            meetUserNameChoiceBox.setValue(userName);
            meetRoomNameChoiceBox.setValue(roomName);
            meetStartTimeField.setText(newValue.getStartTime());
            meetPartNumChoiceBox.setValue(newValue.getPartNum());
            meetDurationChoiceBox.setValue(newValue.getDuration());
            meetTotalPriceField.setText((""+newValue.getPrice()));
            meetDesIntroArea.setText(newValue.getDescription());
            System.out.println("会议详细信息已被更新");
        }
    }

    /**
     * 更新设备管理界面右侧设备信息的展示
     * @param nowE 当前设备对象
     */
    public void equipmentUpdateDetail(Equipment nowE) {
        if(nowE != null) {
            System.out.println("EquipmentUpdate");
            equipmentIDField.setText(""+nowE.getId());
            equipmentNameField.setText(nowE.getName());
            equipmentTypeChoiceBox.setValue((nowE.isStatus() == true ? "可用状态":"损坏状态"));
            equipmentValueField.setText(""+nowE.getValue());
            equipmentMIDField.setText(""+nowE.getMid());
            System.out.println("设备详细信息已被更新");
        }
    }

    /**
     * 会议室管理界面句柄：检查合法性后以当前会议室信息添加新的会议室信息
     */
    @FXML
    private void handleNewRoom() {
        String errorMessage = CheckerUtil.meetingRoomSignUpCheck(rmIdField.getText(),
                rmAddressField.getText(),
                rmNameField.getText(),
                rmTypeChoiceBox.getValue(),
                rmCapacityField.getText(),
                rmHourPriceField.getText(),
                rmDesIntroArea.getText());
        if (errorMessage == null) {
            int rmid = Integer.parseInt(rmIdField.getText());
            String rmAddr= rmAddressField.getText();
            String rmName= rmNameField.getText();
            boolean rmType = rmTypeChoiceBox.getValue().equals("启用状态");
            int rmCapacity = Integer.parseInt(rmCapacityField.getText());
            double rmHourPrice = Double.parseDouble(rmHourPriceField.getText());
            String rmDes= rmDesIntroArea.getText();
            MeetingRoom meetingRoom = new MeetingRoom(rmid, rmAddr, rmName, rmType, rmCapacity, rmHourPrice, rmDes);
            roomService.addInfo(meetingRoom);  // 添加到数据库
            SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加会议室成功");
            initRoomManageTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 会议室管理界面：删除当前会议室信息
     */
    @FXML
    private void handleDeleteRoom() {
        // 获取到所选项
        int index = roomTable.getSelectionModel().getFocusedIndex();
        MeetingRoom room = roomTable.getSelectionModel().getSelectedItem();
        roomService.deleteInfo(room.getRoomID());
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除成功!");
        initRoomManageTable();
    }


    /**
     * 会议室管理界面：更新当前会议室信息
     */
    @FXML
    private void handleUpdateRoom() {
        // 获取旧会议室信息
        MeetingRoom oldInfo = roomTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = rmIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }
        int rmid = Integer.parseInt(foo);
        String rmAddr= rmAddressField.getText();
        String rmName= rmNameField.getText();
        boolean rmType = rmTypeChoiceBox.getValue().equals("启用状态");
        int rmCapacity = Integer.parseInt(rmCapacityField.getText());
        double rmHourPrice = Double.parseDouble(rmHourPriceField.getText());
        String rmDes= rmDesIntroArea.getText();
        MeetingRoom newInfo = new MeetingRoom(rmid, rmAddr, rmName, rmType, rmCapacity, rmHourPrice, rmDes);
        errorMessage = CheckerUtil.meetingRoomUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性
        // 更新数据库
        if (errorMessage == null) {
            roomService.updateInfo(newInfo);
            SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            initRoomManageTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 用户管理界面：检查合法性后添加新的用户信息
     */
    @FXML
    private void handleNewUser() {
        String errorMessage = CheckerUtil.generalUserManageSignUpCheck(generalUserIdField.getText(),
                generalUserNameField.getText(),
                generalUserPassWordField.getText(),
                generalUserRealNameField.getText(),
                generalUserIdCardField.getText(),
                generalUserPhoneField.getText(),
                generalUserAddressField.getText());
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "insert into `generalUser`(id, userName, passWord, realName, idCard, phone, address) " +
                    "values(?, ?, ?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(generalUserIdField.getText()));
            params.add(generalUserNameField.getText());
            params.add(generalUserPassWordField.getText());
            params.add(generalUserRealNameField.getText());
            params.add(generalUserIdCardField.getText());
            params.add(generalUserPhoneField.getText());
            params.add(generalUserAddressField.getText());

            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加成功");
                initGeneralUserTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 用户管理界面：删除当前用户信息
     */
    @FXML
    private void handleDeleteGeneralUser() {
        // 获取到所选项
        int index = generalUserTable.getSelectionModel().getFocusedIndex();
        GeneralUser generalUser = generalUserTable.getSelectionModel().getSelectedItem();
        // 进行删除操作
        if (generalUser != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `generalUser` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(generalUser.getId());

            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您确认要删除这个用户吗？一旦删除后无法恢复，该用户的订单也会被一并删掉！")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    generalUserTable.getItems().remove(index);  // 从左侧表格中移除
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除成功!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用户管理界面：更新当前用户信息
     */
    @FXML
    private void handleUpdateGeneralUser() {
        System.out.println("点击了更新按钮");
        // 获取旧用户信息
        GeneralUser oldInfo = generalUserTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = generalUserIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }

        GeneralUser newInfo = new GeneralUser(Integer.parseInt(generalUserIdField.getText()),
                generalUserNameField.getText(),
                generalUserPassWordField.getText(),
                generalUserRealNameField.getText(),
                generalUserIdCardField.getText(),
                generalUserPhoneField.getText(),
                generalUserAddressField.getText());
        errorMessage = CheckerUtil.generalUserUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性

        // 确认无误，更新数据库
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "update `generalUser` set userName = ?, passWord = ?, realName = ?, idCard = ?, phone = ?, address = ? " +
                    "where id = ?;";
            try {
                List<Object> params = new ArrayList<>();
                params.add(newInfo.getUserName());
                params.add(newInfo.getPassWord());
                params.add(newInfo.getRealName());
                params.add(newInfo.getIdCard());
                params.add(newInfo.getPhone());
                params.add(newInfo.getAddress());
                params.add(newInfo.getId());
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            } catch (SQLException e) {
                System.out.println("更新失败！");
                e.printStackTrace();
            }
            initGeneralUserTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 管理员管理界面：添加当前管理员信息
     */
    @FXML
    private void handleAddAdmin() {
        String errorMessage = CheckerUtil.adminSignUpCheck(adminIdField.getText(),
                adminUserNameField.getText(),
                adminPassWordField.getText() );
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "insert into `admin`(id, userName, passWord) " +
                    "values(?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(Integer.parseInt(adminIdField.getText()));
            params.add(adminUserNameField.getText());
            params.add(adminPassWordField.getText());
            try {
                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加成功");
                initAdminTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }

    /**
     * 管理员管理界面：删除当前管理员信息
     */
    @FXML
    private void handleDeleteAdmin() {
        // 获取到所选项
        int index = adminTable.getSelectionModel().getFocusedIndex();
        Admin admin = adminTable.getSelectionModel().getSelectedItem();
        // 进行删除操作
        if (admin != null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "delete from `admin` where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(admin.getId());
            try {
                if (SysHintUtil.comfirmHint(mainApp.getPrimaryStage(), "您真的要删除这个管理员吗？一旦删除后无法恢复！")) {
                    dbUtils.updateByPreparedStatement(sql, params);
                    adminTable.getItems().remove(index);  // 从左侧表格中移除
                    SysHintUtil.successHint(mainApp.getPrimaryStage(), "管理员删除成功!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 管理员管理界面：更新当前管理员信息
     */
    @FXML
    private void handleUpdateAdmin() {
        System.out.println("点击了更新按钮");
        // 获取旧管理员信息
        Admin oldInfo = adminTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = adminIdField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }

        Admin newInfo = new Admin(Integer.parseInt(adminIdField.getText()),
                adminUserNameField.getText(),
                adminPassWordField.getText());
        errorMessage = CheckerUtil.adminInfoUpdateCheck(oldInfo, newInfo); // 检查更新信息合法性

        // 确认无误，更新数据库
        if (errorMessage == null) {
            DbUtils dbUtils = new DbUtils();
            dbUtils.getConnection();
            String sql = "update `admin` set userName = ?, passWord = ? " +
                    "where id = ?;";
            try {
                List<Object> params = new ArrayList<>();

                params.add(newInfo.getUserName());
                params.add(newInfo.getPassWord());
                params.add(newInfo.getId());

                dbUtils.updateByPreparedStatement(sql, params);
                SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
            } catch (SQLException e) {
                System.out.println("更新失败！");
                e.printStackTrace();
            }
            initAdminTable();
        } else {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
        }
    }
    /**
     * 会议管理界面：添加当前会议信息
     */
    @FXML
    private void handleAddMeeting() {
        String errorMessage = CheckerUtil.meetingSignUpCheck(
                meetIDField.getText(),
                meetNameField.getText(),
                meetPartNumChoiceBox.getEditor().getText(),
                meetStartTimeField.getText(),
                meetTotalPriceField.getText()
        );
        if(errorMessage != null) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
            return;
        }
        // 寻找用户名对应的用户id
        int uid = userService.getIDByUserName(meetUserNameChoiceBox.getEditor().getText());
        if(uid == -1) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "该用户不存在！");
            return;
        }
        // 寻找会议室名对应的会议室id
        int rid = roomService.getIDByRoomName(meetRoomNameChoiceBox.getEditor().getText());
        if(rid == -1) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "该会议室不存在！");
            return;
        }
        int id = Integer.parseInt(meetIDField.getText());
        String mName = meetNameField.getText();
        int partNum = Integer.parseInt(meetPartNumChoiceBox.getEditor().getText());
        String startTime = meetStartTimeField.getText();
        int duration = Integer.parseInt(meetDurationChoiceBox.getEditor().getText());
        double price = Double.parseDouble(meetTotalPriceField.getText());
        String des = meetDesIntroArea.getText();
        Meeting meeting = new Meeting(id, mName, partNum, startTime, duration, price, des, uid, rid);
        meetingService.addInfo(meeting);
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "会议添加成功");
        initMeetingTable();
    }

    /**
     * 会议管理界面：删除当前会议信息
     */
    @FXML
    private void handleDeleteMeeting() {
        int index = meetTable.getSelectionModel().getFocusedIndex();
        Meeting meeting = meetTable.getSelectionModel().getSelectedItem();
        if(meetingService.deleteInfo(meeting.getMeetID()))
            SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除会议成功");
        else SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "删除会议失败");
        initMeetingTable();
    }

    /**
     * 会议管理界面：更新当前会议信息
     */
    @FXML
    private void handleUpdateMeeting() {
        Meeting oldInfo = meetTable.getSelectionModel().getSelectedItem();
        String errorMessage = CheckerUtil.meetingUpdateCheck(
                oldInfo,
                meetIDField.getText(),
                meetNameField.getText(),
                meetPartNumChoiceBox.getEditor().getText(),
                meetStartTimeField.getText(),
                meetTotalPriceField.getText()
        );

        if(errorMessage != null) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), errorMessage);
            return;
        }

        // 寻找用户名对应的用户id
        int uid = userService.getIDByUserName(meetUserNameChoiceBox.getEditor().getText());
        if(uid == -1) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "该用户不存在！");
            return;
        }
        // 寻找会议室名对应的会议室id
        int rid = roomService.getIDByRoomName(meetRoomNameChoiceBox.getEditor().getText());
        if(rid == -1) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "该会议室不存在！");
            return;
        }
        int id = Integer.parseInt(meetIDField.getText());
        String mName = meetNameField.getText();
        int partNum = Integer.parseInt(meetPartNumChoiceBox.getEditor().getText());
        String startTime = meetStartTimeField.getText();
        int duration = Integer.parseInt(meetDurationChoiceBox.getEditor().getText());
        double price = Double.parseDouble(meetTotalPriceField.getText());
        String des = meetDesIntroArea.getText();
        Meeting meeting = new Meeting(id, mName, partNum, startTime, duration, price, des, uid, rid);
        meetingService.updateInfo(meeting);
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "会议添加成功");
        initMeetingTable();
    }

    /**
     * 设备管理界面句柄：以当前设备信息添加新的设备信息
     */
    @FXML
    private void handleNewEquipment() {
        int id = Integer.parseInt(equipmentIDField.getText());
        String name = equipmentNameField.getText();
        boolean type = equipmentTypeChoiceBox.getValue().equals("可用状态");
        double value = Double.parseDouble(equipmentValueField.getText());
        int mid = Integer.parseInt(equipmentMIDField.getText());

        Equipment equipment = new Equipment(id, name, type, value, mid);
        eService.addInfo(equipment);  // 添加到数据库
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "添加设备成功");
        initEquipmentTable();
    }

    /**
     * 设备管理界面：删除当前设备信息
     */
    @FXML
    private void handleDeleteEquipment() {
        // 获取到所选项
        int index = equipmentTable.getSelectionModel().getFocusedIndex();
        Equipment equipment = equipmentTable.getSelectionModel().getSelectedItem();
        eService.deleteInfo(equipment.getId());
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "删除成功!");
        initEquipmentTable();
    }

    /**
     * 设备管理界面：更新当前设备信息
     */
    @FXML
    private void handleUpdateEquipment() {
        // 获取旧会议室信息
        Equipment oldInfo = equipmentTable.getSelectionModel().getSelectedItem();
        String errorMessage;
        // 将id信息转化为整型
        String foo = equipmentIDField.getText();
        if (foo == null || foo.length() == 0) {
            SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id为空");
            return;
        } else {
            try {
                Integer.parseInt(foo);
            } catch (Exception e) {
                SysHintUtil.errorInfoHint(mainApp.getPrimaryStage(), "id应只含有数字");
                return;
            }
        }
        int eid = Integer.parseInt(foo);
        String eName= equipmentNameField.getText();
        boolean eType = equipmentTypeChoiceBox.getValue().equals("可用状态");
        double evalue = Double.parseDouble(equipmentValueField.getText());
        int emid = Integer.parseInt(equipmentMIDField.getText());
        Equipment newInfo = new Equipment(eid, eName, eType, evalue, emid);
        eService.updateInfo(newInfo);
        SysHintUtil.successHint(mainApp.getPrimaryStage(), "更新成功");
        initEquipmentTable();
    }

    /**
     * 查询及统计界面：查询当前所选用户、会议室对应会议信息
     */
    @FXML
    private void handleSearchData() {
        // 获取所选项
        String uName = searchUserNameChoiceBox.getEditor().getText();
        String rName = searchRoomNameChoiceBox.getEditor().getText();
        //进行搜索的过滤
        ObservableList<Meeting> searchData = FXCollections.observableArrayList();
        if(rName.length() != 0 || uName.length() != 0){
            for (Meeting meeting : searchMeetingsdata) {        // 遍历所有会议数据
                if (rName.length() != 0 && !roomService.getRoomNameByID(meeting.getRid()).equals(rName)) {    // 若有选择会议室名称，则不是该会议室的会议不要
                    continue;
                }
                if (uName.length() != 0 && !userService.getUserNameByID(meeting.getUid()).equals(uName)) {        // 若有选择用户名，则不是该用户名的会议不要
                    continue;
                }
                searchData.add(meeting);
            }
        } else searchData = searchMeetingsdata;
        searchTable.setItems(searchData);       // 将过滤后的数据显示出来
    }

    /**
     * 跳转到会议室管理界面
     */
    @FXML
    private void jumpToRoomManagePane() {
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        meetingManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        equipmentManagePane.setVisible(false);
        initRoomManageTable();
        roomManagePane.setVisible(true);
    }

    /**
     * 跳转到用户管理界面
     */
    @FXML
    private void jumpToGeneralUserManagePane() {
        roomManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        meetingManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        equipmentManagePane.setVisible(false);
        initGeneralUserTable();
        generalUserManagePane.setVisible(true);
    }

    /**
     * 跳转到管理员管理界面
     */
    @FXML
    private void jumpToAdminManagePane() {
        roomManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        meetingManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        equipmentManagePane.setVisible(false);
        initAdminTable();
        adminManagePane.setVisible(true);
    }

    /**
     * 跳转到会议室管理界面
     */
    @FXML
    private void jumpToEquipmentManagePane() {
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        meetingManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        roomManagePane.setVisible(false);
        initRoomManageTable();
        equipmentManagePane.setVisible(true);
    }

    /**
     * 跳转到会议管理界面
     */
    @FXML
    private void jumpToMeetingManagePane() {
        roomManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        searchAndStatisticPane.setVisible(false);
        equipmentManagePane.setVisible(false);
        initMeetingTable();
        meetingManagePane.setVisible(true);
    }

    /**
     * 跳转到查询及统计界面
     */
    @FXML
    private void jumpToSearchAndStatisticPane() {
        roomManagePane.setVisible(false);
        generalUserManagePane.setVisible(false);
        adminManagePane.setVisible(false);
        meetingManagePane.setVisible(false);
        initSearchAndStatisticPane();
        searchAndStatisticPane.setVisible(true);
    }

    /**
     * 注销功能：管理员退出，返回登录界面
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
