package advice_natio.project.com.getmoney.BaseActivity;

/**
 * Created by Surya Chundawat on 11/9/2017.
 */

public class NetworkUrl {

    public static final String OTP = "https://2factor.in/API/V1/";
    public static final String API ="c943a173-a90b-11e7-94da-0200cd936042";
    public static final String OTPVERIFY="https://2factor.in/API/V1/{api_key}/SMS/VERIFY/{session_id}/{otp_entered_by_user}";
    public static final String REGISTRATION ="https://www.cashjet.co/IRRestService/SetIR_BasicDetails";
    public static final String DASHBOARD_URL="https://www.cashjet.co/IRRestService/GetIR_PointsDetails";
    public static final String URL_CHECKIR_ID="https://www.cashjet.co/IRRestService/Check_IR_Exists";
    public static final String URL_PERSONAL = "https://www.cashjet.co/IRRestService/SetIR_PersonalDetails";
    public static  final String URL_NOMINEEDETAIS="https://www.cashjet.co/IRRestService/SetIR_NomineeDetails";
    public static final String URL_BANKDETILS="https://www.cashjet.co/IRRestService/SetIR_BankDetails";
    public static final String URL_GETBASICDETAILS = "https://www.cashjet.co/IRRestService/GetIR_BasicDetails";
    public static final String URL_ENCASHPOINTS="https://www.cashjet.co/IRRestService/GetIR_EncashDetails";
    public static final String URL_SETBANKDETAILS = "https://www.cashjet.co/IRRestService/SetIR_BankDetails";


}
