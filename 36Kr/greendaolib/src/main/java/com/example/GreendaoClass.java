package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreendaoClass {
    public static void main(String[] args) {
        // 数据库图表 (数据库的框架)
        // 两个参数:数据库的版本号;自动生成代码的包名
        Schema schema = new Schema(1, "com.zhuxiaoming.kr36.database");
        addNote(schema);
        // 自动生成代码
        // 两个参数:图表对象;自动生成的代码路径
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法就是为我的数据库里添加我所需的内容
     *
     * @param schema
     */
    public static void addNote(Schema schema) {
        // 添加表名
        Entity entity = schema.addEntity("LoginBean");
        // 加入id
        // 并且id自增
        entity.addIdProperty().autoincrement().primaryKey();
        // 添加类的属性,根据属性生成相应表中的字段
        entity.addStringProperty("user");
        entity.addStringProperty("password");
        // 添加表名
        Entity entity1 = schema.addEntity("FavoriteBean");
        // 加入id
        // 并且id自增
        entity1.addIdProperty().autoincrement().primaryKey();
        // 添加类的属性,根据属性生成相应表中的字段
        entity1.addStringProperty("feedId");
        entity1.addStringProperty("imgUrl");
        entity1.addStringProperty("author");
        entity1.addStringProperty("title");
        entity1.addStringProperty("type");
        entity1.addLongProperty("time");


    }
}

