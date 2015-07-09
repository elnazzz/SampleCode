package se.elnazhonar.sample.data.orm;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
import se.elnazhonar.sample.data.model.Food;


public class FoodDaoGenerator {

    private final static int VERSION = 1;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(VERSION, "se.test.data.orm");
        createDB(schema);
        new DaoGenerator().generateAll(schema, "../");

    }


    private static void createDB(Schema schema) {

        //Food table
        Entity food = schema.addEntity(Food.class.getSimpleName());
        food.addIdProperty();
        food.addLongProperty("categoryid");
        food.addDoubleProperty("fiber");
        food.addStringProperty("headimage");
        food.addDoubleProperty("pcsingram");
        food.addStringProperty("brand");
        food.addDoubleProperty("unsaturatedfat");
        food.addDoubleProperty("fat");
        food.addIntProperty("servingcategory");
        food.addIntProperty("typeofmeasurement");
        food.addDoubleProperty("protein");
        food.addIntProperty("defaultserving");
        food.addDoubleProperty("mlingram");
        food.addLongProperty("foodid");
        food.addDoubleProperty("saturatedfat");
        food.addStringProperty("category");
        food.addBooleanProperty("verified");
        food.addStringProperty("title");
        food.addStringProperty("pcstext");
        food.addDoubleProperty("sodium");
        food.addDoubleProperty("carbohydrates");
        food.addIntProperty("showOnlySameType");
        food.addLongProperty("calories");
        food.addIntProperty("serving_version");
        food.addDoubleProperty("sugar");
        food.addIntProperty("source");
        food.addLongProperty("measurementid");
        food.addDoubleProperty("cholesterol");
        food.addDoubleProperty("gramsperserving");
        food.addIntProperty("showmeasurement");
        food.addDoubleProperty("potassium");
    }
}
