package cn.jj.chunqiu;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ss.android.common.applog.TeaAgent;
import com.ss.android.common.applog.TeaConfigBuilder;
import com.ss.android.common.lib.AppLogNewUtils;
import com.ss.android.common.lib.EventUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import cn.jj.jjgamesdk.ITKAPICallback;
import cn.jj.jjgamesdk.TKAPI;
import cn.jj.jjgamesdk.TKAPIConst;
import android.os.Bundle;
import android.view.View;

import cn.jj.analytics.JJAnalyticsSDK;
import cn.jj.chunqiu.R;

public class MainActivity extends UnityPlayerActivity {
    static {
        System.loadLibrary("unity");
        System.loadLibrary("hotupdate");
        HotUpdate.getInstance().StartHook();
    }

    private static MainActivity instance;

    private TKAPI tkapi = null;

    private int smsActionType = TKAPIConst.SMSACTION_LOGINVERIFY;
    private boolean isPasswordExist = false;
    private boolean isStart = true;//false;//true;
    private int figureID = 0;
    private  String goodsParam = "";//支付配置中的某个商品信息
    private boolean isTest = false;//false;//
    private String msgContents = "";//存放服务端推送的消息�??
    private  int modifyNicknameLevelID = -1;//修改昵称的权�??

    private String unReadMSGs = "";
    private String allMSGs = "";
    private String lobbyStartParam = "";//记录15311大厅启动游戏时传递的参数�??


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HotUpdate.getInstance().SetHotUpdatePath(this);
        super.onCreate(savedInstanceState);
        instance = this;
        Log.d("jjlog_onCreate",Thread.currentThread().getId()+"onCreate");
        //UnitySplashSDK.getInstance().SetSplash(mUnityPlayer);
        //bugly init
        CrashReport.initCrashReport(getApplicationContext(), "7b31bbeb20", true);
        //头条sdk初始化
        TeaAgent.init(TeaConfigBuilder.create(getApplicationContext()).setAppName("chunqiu").setChannel("toutiao").setAid(153986).createTeaConfig());
    }
    //开始 统计时长
    public void ttOnResume(){
        TeaAgent.onResume(this);
    }
    //挂起 统计时长
    public void ttOnPause(){
        TeaAgent.onPause(this);
    }
    //开始 统计时长
    @Override
    protected void onResume() {
        super.onResume();
        TeaAgent.onResume(this);
        Log.d("UnityPlayer","onResume");
    }
    //挂起 统计时长
    @Override
    protected void onPause() {
        super.onPause();
        TeaAgent.onPause(this);
        Log.d("UnityPlayer","onPause");
    }
    //设置用户唯一标识
    public void ttSetUserUniqueID(String id){
        TeaAgent.setUserUniqueID(id);
        Log.d("UnityPlayer","ttSetUserUniqueID");
    }

    /**
     * 注册
     * @param method 注册⽅式 mobile、 weixin、 qq等（必须上传）
     * @param is_success 是否成功（必须上传）
     */
    public void ttSetRegister(String method,boolean is_success){
        EventUtils.setRegister(method, is_success);
        Log.d("UnityPlayer","ttSetRegister");
    }

    /**
     *⽀付
     * @param content_type 内容类型
     * @param content_name 商品/内容名
     * @param content_id 商品ID/内容ID
     * @param content_num 商品数量
     * @param payment_channel ⽀付渠道名 如⽀付宝、微信等
     * @param currency 真实货币类型
     * @param is_success 是否成功（必须上传）
     * @param currency_amount 本次⽀付的真实货币的⾦额（必须上传，单位：元）
     */
    public void ttSetPurchase(String content_type,String content_name,String content_id,int content_num,String payment_channel,
                              String currency,boolean is_success, int currency_amount)
    {
        EventUtils.setPurchase( content_type, content_name, content_id, content_num, payment_channel,
                currency, is_success,  currency_amount);
    }

    public void ttOnEvent(String eventid,JSONObject jparams){
        AppLogNewUtils.onEventV3( eventid, jparams);
    }

    //设置闪屏
    public void SetSplash(){
        UnitySplashSDK.getInstance().SetSplash(mUnityPlayer);
    }
    //隐藏闪屏
    public void HideSplash(){
        Log.d("UnityPlayer","Android_HideSplash");
        UnitySplashSDK.getInstance().onHideSplash();
    }

    /**anlaytics part start***/
    //初始化jjgame 统计sdk
    public void initJJAnalyticsSDK(int appId,String appChanel){
        JJAnalyticsSDK.init(getApplicationContext(), appId,appChanel);
    }
    //设置jjgame统计sdk mode
    public void setAnlayticsLogEnabled(boolean value){
        JJAnalyticsSDK.setLogEnabled(value);
    }
    //统计用户登录
    public void userLogin(String userID)
    {
        //JJAnalyticsSDK.setLogEnabled(true);
        JJAnalyticsSDK.userLogin(userID);
    }
    //统计用户登出
    public void userLogout()
    {
        JJAnalyticsSDK.userLogout();
    }
    //统计客户端版本升级开�?
    public void onUpgradeStart(){
        JJAnalyticsSDK.onUpgradeStart();
    }
    //统计客户端版本升级结�?
    public void onUpgradeEnd(int arg1,int arg2){
        JJAnalyticsSDK.onUpgradeEnd(arg1,arg2);
    }

    //统计到达登录页面
    public void enterLoginPage() {
        JJAnalyticsSDK.enterLoginPage();
    }
    //统计到达游戏页面
    public void enterGamePage(int loadTime) {
        JJAnalyticsSDK.enterGamePage(loadTime);
    }
    //统计自定义事�?
    public void event(String eventId)
    {
        JJAnalyticsSDK.event(getApplicationContext(), eventId);
    }
    //统计自定义事�?
    public void event(String eventId,String eventValue)
    {
        JJAnalyticsSDK.event(getApplicationContext(), eventId,eventValue);
    }
    //统计
    public void onJJResume() {
        JJAnalyticsSDK.onResume(this);
    }
    //统计
    public void onJJPause() {
        JJAnalyticsSDK.onPause(this);
    }
    //统计 设置上传间隔
    public void setSendDataInterval(int interval){
        JJAnalyticsSDK.setSendDataInterval(interval);
    }
    //设置是否只在wifi下上�?
    public void setSendDataOnlyWifi(boolean onlyWifi){
        JJAnalyticsSDK.setSendDataOnlyWifi(onlyWifi);
    }
    /**anlaytics part end***/

    //在设置模式时 获取 tkapi 在ui线程中
    public void setDebugMode(boolean isDebug){
        Log.d("jjlog_setDebugMode",Thread.currentThread().getId()+"setDebugMode");
        isTest = isDebug;
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Log.d("jjlog_runOnUiThread",Thread.currentThread().getId()+"runOnUiThread");
                tkapi = TKAPI.getInstance(getApplicationContext());
            }});

    }
    //获取大厅登陆启动参数
    public String jjLobbyStartParam(){
        lobbyStartParam = getIntent().getStringExtra("param");
        return lobbyStartParam;
    }

    //初始化sdk
    public int jjInit(int APPID){
        Log.d("jjlog_jjInit",Thread.currentThread().getId()+"jjInit");
        if(isTest) {
            tkapi.openTestMode();
            Log.d("openTestMode","openTestMode");
        }
        tkapi.init(APPID);
        log("init");
        tkapi.setITKAPICallback(itkCallback);
        log("setITKAPICallback pid:"+Thread.currentThread().getId());
        int ret = tkapi.open();//客户端连接成�??
        return ret;
    }


    public int open(){
        int ret = tkapi.open();//客户端连接成�??
        return ret;
    }

    //sdk 开启状态判断
    public boolean isOpen(){
        boolean ret = tkapi.isOpen();
        return ret;
    }
    //大厅跳转授权登录
    public int authUser(String authParam){
        int ret = tkapi.authUser(authParam);;
        return ret;
    }
    //自动登录
    public int autoLogin(){
        int ret = tkapi.autoLogin();
        return ret;
    }
    //登出
    public int jjlogout(){
        log("logout_btn click");
        int ret = tkapi.logout();
        return ret;
    }
    //游客登录
    public int jjloginWithGuestAccount(){
        log("unreglogin_btn click");
        int ret = tkapi.loginWithGuestAccount();
        return ret;
    }
    //通用登录 /用户名密码登�?? smscode 设备认证
    public int jjloginWithLoginName(String loginName, String password, String smsCode){
        log("generalLogin_btn click");
        int ret = tkapi.loginWithLoginName(loginName, password, smsCode);
        return ret;
    }

    //手机�?? 验证码登�??
    public int jjloginWithPhoneNumberAndSMSCode(String phoneNumber, String smsCode){
        log("phoneNumlogin_btn click");
        int ret = tkapi.loginWithPhoneNumberAndSMSCode(phoneNumber, smsCode);
        return ret;
    }
    /**qq 登录**/
    public int loginWithQQ(String appID_qq, int productID){
        log("qq_login_btn click");
        int ret = tkapi.loginWithQQ(appID_qq, productID);
        return ret;
    }
    //微信登录
    public int loginWithWeChat(String appID_wx, int productID){
        //infoShow("微信登录�??要真实包名和签名");
        int ret = tkapi.loginWithWeChat(appID_wx, productID);
        return ret;
    }

    //登录名列表登�??
    public int jjloginWithHistoryLoginName(String loginName){
        int ret = tkapi.loginWithHistoryLoginName(loginName);
        return ret;
    }
    //注册
    public int jjregister(String phoneNumber, String password, String smsCode, String nickname){
        log("register_btn click");
        int ret = tkapi.register(phoneNumber, password, smsCode, nickname);
        return ret;
    }
    //游客账号升级
    public int upgradeGuestAccount(String phoneNumber, String password, String smsCode, String nickname){
        int ret = tkapi.upgradeGuestAccount(phoneNumber, password,smsCode, nickname);
        return ret;
    }

    //当前是登录�?�返回true，否则返回false�??
    public boolean isCurrentLogined(){
        boolean ret = tkapi.isCurrentLogined();
        return ret;
    }
    //获取appKey对应的App的SSO request token
    public int getSSORequestToken(String appKey){
        int ret = tkapi.getSSORequestToken(appKey);
        return ret;
    }
    //获取合作方App的OID request token
    public int  getOIDRequestToken(){
        int ret = tkapi.getOIDRequestToken();
        return ret;
    }

    //修改密码
    public int modifyPassword(String oldPwd, String newPwd){
        int ret = tkapi.modifyPassword(oldPwd, newPwd);
        return ret;
    }
    //重置密码
    public int resetPassword(String loginName, String smsCode, String password){
        int ret = tkapi.resetPassword(loginName, smsCode, password);
        return ret;
    }
    //查询安保手机
    public int  queryBoundPhoneNumber(){
        int ret = tkapi.queryBoundPhoneNumber();
        return ret;
    }
    //通过登录名查询安保手�??
    public int queryBoundPhoneNumber(String loginName){
        int ret = tkapi.queryBoundPhoneNumber(loginName);
        return ret;
    }
    //绑定安保手机
    public int bindPhoneNumber(String phoneNumber, String smsCode, String password){
        int ret = tkapi.bindPhoneNumber(phoneNumber, smsCode, password);
        return ret;
    }
    //获取推荐昵称列表
    public int getRecommendedNicknames(){
        int ret = tkapi.getRecommendedNicknames();
        return ret;
    }
    //修订昵称
    public int emendNickname(String nickname){
        int ret = tkapi.emendNickname(nickname);
        return ret;
    }

    //查询昵称修改条件
    public int queryModifyNicknamePermission(){
        int ret = tkapi.queryModifyNicknamePermission();
        return ret;
    }

    //修改昵称
    public int modifyNickname(String nickname, int levelID, int type){
        int	ret = tkapi.modifyNickname(nickname, levelID, type);
        return ret;
    }
    //查询昵称是否可用
    public int queryNicknameAvailable(String nickname){
        int ret = tkapi.queryNicknameAvailable(nickname);
        return ret;
    }
    //查询密码是否存在
    public int isPasswordExist(){
        int ret = tkapi.isPasswordExist();
        return ret;
    }
    //查询手机号占用情�??
    public int isPhoneNumberOccupied(String phoneNumber, boolean isRegister){
        int ret = tkapi.isPhoneNumberOccupied(phoneNumber, isRegister);
        return ret;
    }
    //查询用户信息
    public String getUserInfo(){
        String userInfo = tkapi.getUserInfo();
        return userInfo;
    }
    //获取�??近登录名列表
    public String getRecentUserInfoList(){
        String recentUserInfoList = tkapi.getRecentUserInfoList();
        return recentUserInfoList;
    }
    //获取头像URL地址
    public String buildHeadImgUrl(int figureID){
        String headImgUrl = tkapi.buildHeadImgUrl(figureID);
        return headImgUrl;
    }
    //修改头像ID
    public int modifyFigureID(int figureID){
        int ret = tkapi.modifyFigureID(figureID);
        return ret;
    }
    //查询实名认证�??�??
    public int checkIfNeedRealID(int bizSrc){
        int ret = tkapi.checkIfNeedRealID(bizSrc);
        return ret;
    }

    //获取真实身份信息
    public int getRealIDInfo(int idCardType)
    {
        int ret = tkapi.getRealIDInfo(idCardType);
        return ret;
    }

    //设置真实身份信息
    public int setRealIDInfo(int idCardType, String idCard, String realName, int bizSrc){
        int ret = tkapi.setRealIDInfo(idCardType, idCard, realName,bizSrc);
        return ret;
    }
    //通过登录名获取验证码
    public int getSMSCodeWithLoginName(String loginName, int actionType){
        int ret = tkapi.getSMSCodeWithLoginName(loginName, actionType);
        return ret;
    }
    //通过手机号获取验证码
    public int getSMSCodeWithPhoneNumber(String phoneNumber, int actionType){
        int ret = tkapi.getSMSCodeWithPhoneNumber(phoneNumber, actionType);
        return ret;
    }
    //�?处最近登录名
    public int removeUserFromRecentList(String loginName){
        int ret =tkapi.removeUserFromRecentList(loginName);
        return ret;
    }
    ///获取商品列表
    public int getGoodsList(){
        int ret = tkapi.getGoodsList();
        return ret;
    }
    ///查询商品详细信息
    public String queryGoodsDetails(String goodsParam){
        String ret = tkapi.queryGoodsDetails(goodsParam);
        return ret;
    }
    ///打开支付选择界面
    public int selectPayMethod(final String goodsParam){
        int	ret = tkapi.selectPayMethod(goodsParam);
        Log.d("jjlog_selectPayMethod",Thread.currentThread().getId()+"selectPayMethod");
        return ret;
    }
    ///下单
    //@SuppressWarnings("null")
    public int payOrder(final String orderParam){
        Log.d("jjlog_payOrder1",Thread.currentThread().getId()+"payOrder1");
        //final int[] aRets = null;
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Log.d("jjlog_payOrder2",Thread.currentThread().getId()+"payOrder2");
                // TODO Auto-generated method stub
                tkapi.payOrder(orderParam, instance);
            }
        });
        return 0;
    }
    ///打开客服界面
    public int openSubmissionPage(final String gameName,final  String serverName, final String nickName){
        int ret = tkapi.openSubmissionPage(gameName, serverName, nickName);
        return ret;
    }
    ///打开兑奖界面
    public int openRewardPage(){
        int ret = tkapi.openRewardPage();
        return ret;
    }

/*	public boolean checkRecordAudioPermission(){
	    PackageManager pm = getPackageManager();
	    boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "cn.jj.chunqiu"));
	    return permission;
	}
	*/
    /******call back begain*****/

    private ITKAPICallback itkCallback = new ITKAPICallback() {
        @Override
        public void onConnectSuccess() {
            AndroidSendMessageToUnity("onConnectSuccess","");
        }

        @Override
        public void onConnectFailed() {
            log("onConnectFailed");
            AndroidSendMessageToUnity("onConnectFailed","");
        }

        @Override
        public void onDisconnect() {
            log("onDisconnect");
            AndroidSendMessageToUnity("onDisconnect","");
        }

        @Override
        public void onMsgResp(int type, int errCode, String result) {
            String logInfo = "onMsgResp type:"+type+", errCode:"+errCode+"\nresult:"+result;
            log(logInfo);
            Log.d("jjlog_onMsgResp",Thread.currentThread().getId()+"onMsgResp");
            UnityPlayer.UnitySendMessage("JJGameSDK", "onMsgResp", type + ";" + errCode+ ";" +result);
        }
    };

    /****** call back end****/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void log(String content){
        //Log.e("mainActivity", content);
    }

    //AndroidSendMessageToUnity
    //ReceiveObject
    //ReceiverMethod
    private void AndroidSendMessageToUnity(String receiverMethod,String str){
        String ReceiveObject="JJGameSDK";
        if(receiverMethod ==""){
            receiverMethod="Receive";
        }
        UnityPlayer.UnitySendMessage(ReceiveObject, receiverMethod, str);
    }

    public int queryEvtInfoByTag(String param){
        return tkapi.queryEvtInfoByTag(param);
    }

    public int modifyEvtInfoByTag(String param){
        return tkapi.modifyEvtInfoByTag(param);
    }


    public void clickAction(View view) {
    }
}
