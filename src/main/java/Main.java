import com.facebook.ads.sdk.*;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final String ACCESS_TOKEN = "EAAJnO7WZBGQABAC98audmbjSlAefsKvNSh2YnN7IZAk7d6PcNZA1o0C0BLKVW4hWxwBFktjxrbiBGzj1uKHqvTNPtcDwJzFUNoScwTnlV7RoZAIYh6yBtZC7aaey4BF0E88OXZBMwGnq4xZCqWEe1w4w8BXopcUhrghBjLbbHORL5xuiHk3ROSF0vZAoROZATMP7ITOnFzEruVTiFDO5cW2Ci";
    public static final String ACCOUNT_ID = "531562877199464";
    public static final String APP_SECRET = "9bbd865c2c3b0394e0ac14eb494db9fc";

    public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET);

    public static void main(String[] args) {
        try {
            File imageFile = new File("src/main/resources/bjerget.jpg");
            System.out.println(imageFile.getAbsolutePath());
            AdAccount account = new AdAccount(ACCOUNT_ID, context);
            BatchRequest batch = new BatchRequest(context);
            Campaign campaign = account.createCampaign()
                    .setName("Salgsprisberegner Java Test (RPB)")
                    .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
                    .setSpecialAdCategories("NONE")
                    .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                    .execute();
            System.out.println(campaign);
            String campaignId = campaign.getId();
            ArrayList<TargetingGeoLocationZip> zips = new ArrayList();
            TargetingGeoLocationZip zip1 = new TargetingGeoLocationZip();
            zip1.setFieldKey("DK:4760");
            zips.add(zip1);
            TargetingGeoLocationZip zip2 = new TargetingGeoLocationZip();
            zip2.setFieldKey("DK:4700");
            zips.add(zip2);
            AdSet adSet = account.createAdSet()
                    .setName("Java 4760 & 4700 test")
                    .setCampaignId(campaignId)
                    .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
                    .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                    .setDailyBudget(10000L)
                    .setBidAmount(100L)
                    .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_IMPRESSIONS)
                    .setTargeting(
                            new Targeting()
                                    .setFieldAgeMin(25L)
                                    .setFieldGeoLocations(
                                            new TargetingGeoLocation()
                                                    .setFieldZips(zips)))
                    .execute();
            System.out.println(adSet);
            AdImage image = account.createAdImage()
                    .addUploadFile("file", imageFile)
                    .execute();
            System.out.println(image);
            AdCreative adCreative = account.createAdCreative()
                    .setName("Java Annonce Test")
                    .setObjectStorySpec(
                            new AdCreativeObjectStorySpec()
                                    .setFieldLinkData(
                                            new AdCreativeLinkData()
                                                    .setFieldName("Beregn din pris HER")
                                                    .setFieldImageHash(image.getFieldHash())
                                                    .setFieldLink("https://realmaeglerne-roskilde.beregner.net/")
                                                    .setFieldMessage("Overvejer du at sælge din bolig?\n" +
                                                            "Beregn salgsprisen på din bolig ved, at svare på et par enkelte spørgsmål - det tager kun 2 minutter!")
                                    )
                                    .setFieldPageId("369597546494244")
                    )
                    .execute();
            System.out.println(adCreative);
            Ad ad = account.createAd()
                    .setName("Java test ANNONCE")
                    .setAdsetId(adSet.getId())
                    .setCreative(
                            new AdCreative()
                                    .setFieldId(adCreative.getFieldId())
                    )
                    .setStatus(Ad.EnumStatus.VALUE_PAUSED)
                    .execute();
            System.out.println(ad);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}