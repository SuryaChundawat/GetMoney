package advice_natio.project.com.getmoney.scrap;

import advice_natio.project.com.getmoney.R;

/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "Content for ";

      /*  switch (menuItemId) {
            case R.id.tab_recent:
                message += "recents";
                break;
            case R.id.tab_favorites:
                message += "favorites";
                break;
            case R.id.tab_nearby:
                message += "nearby";
                break;
            case R.id.tab_friends:
                message += "friends";
                break;
            case R.id.tab_food:
                message += "food";
                break;
        }*/

        if (isReselection) {
            message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
