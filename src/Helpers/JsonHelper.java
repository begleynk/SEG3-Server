package Helpers;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.*;
import com.github.julman99.gsonfire.GsonFireBuilder;
import com.github.julman99.gsonfire.TypeSelector;
import com.google.gson.*;
//import com.sun.xml.internal.rngom.parse.host.Base;
//import sun.jvm.hotspot.ci.ciReceiverTypeData;

/**
 * Created by Niklas Begley on 13/02/2014.
 *
 * A singleton JSON helper class.
 *
 * Gson-fire used to properly pass question subclasses.
 * https://github.com/julman99/gson-fire
 *
 */
public class JsonHelper {

    private static Gson gson = null;

    protected JsonHelper()
    {

    }

    public static Gson getInstance()
    {
        if (gson == null)
        {
            gson = createInstance();
        }

        return gson;
    }

    private static Gson createInstance()
    {
        GsonFireBuilder builder = new GsonFireBuilder().registerTypeSelector(Question.class, new TypeSelector<Question>() {
            @Override
            public Class<? extends Question> getClassForElement(JsonElement readElement) {
                int kind = readElement.getAsJsonObject().get("type").getAsInt();

                if (kind == 0) {
                    return RangeQuestion.class;
                } else if (kind == 1) {
                    return SelectManyQuestion.class;
                } else if (kind == 2) {
                    return YesNoQuestion.class;
                } else if (kind == 3) {
                    return TextQuestion.class;
                } else if (kind == 4) {
                    return SelectOneQuestion.class;
                } else if (kind == 5) {
                    return RankQuestion.class;
                } else {
                    return null;
                }
            }
        });
        return builder.createGson();
    }
}
