package advice_natio.project.com.getmoney.Https;

import org.json.JSONObject;

/**
 * Created by Chari on 7/18/2017.
 */

public interface ApiResponse {

    void OnSucess(JSONObject response, int id);

    void OnFailed(int error, int id);


}
