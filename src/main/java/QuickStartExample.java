import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.APIException;

public class QuickStartExample {

    public static final String ACCESS_TOKEN = "EAAJnO7WZBGQABAC98audmbjSlAefsKvNSh2YnN7IZAk7d6PcNZA1o0C0BLKVW4hWxwBFktjxrbiBGzj1uKHqvTNPtcDwJzFUNoScwTnlV7RoZAIYh6yBtZC7aaey4BF0E88OXZBMwGnq4xZCqWEe1w4w8BXopcUhrghBjLbbHORL5xuiHk3ROSF0vZAoROZATMP7ITOnFzEruVTiFDO5cW2Ci";
    public static final String ACCOUNT_ID = "531562877199464";
    public static final String APP_SECRET = "9bbd865c2c3b0394e0ac14eb494db9fc";

    public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET);
    public static void main(String[] args) {
        try {
            AdAccount account = new AdAccount(ACCOUNT_ID, context);
            Campaign campaign = account.createCampaign()
                    .setName("Java SDK Test Campaign")
                    .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
                    .setSpendCap("1000000")
                    .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                    .setSpecialAdCategories("NONE")
                    .execute();
            System.out.println(campaign.fetch());
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}