package github.snugbrick.miracleblock;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public enum SqlMethods {
    TABLE("table"),
    QUERY("query"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");


    final String code;

    final SQLManager sqlManager = MiracleBlock.getSqlManager();

    SqlMethods(String code) {
        this.code = code;
    }

    public String runTasks(String tableName, String... args) {
        switch (code) {
            case ("table"):
                return executeTable(tableName, args);
            case ("query"):
                return executeQuery(tableName, args);
            case ("insert"):
                return executeInsert(tableName, args);
            case ("update"):
                return executeUpdate(tableName, args);
            case ("delete"):
                return executeDelete(tableName, args);
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      生成的所有列
     */
    private String executeTable(String tableName, String[] args) {
        TableCreateBuilder createTable = sqlManager.createTable(tableName)
                .addAutoIncrementColumn("id", true);
        for (String columnName : args) {
            createTable.addColumn(columnName);
        }
        return "true";
    }

    /**
     * 参数位置错一下就会出现奇妙的结果哦 >:)
     *
     * @param tableName 指定表
     * @param args      三个参数 第一个是你想查找的列 第二个是你根据哪个列来进行查找 第三个是你根据第二参数指定的列的哪一行来查找
     * @return 定位的结果
     */
    private String executeQuery(String tableName, String[] args) {
        if (args.length == 3) {
            QueryAction queryAction = sqlManager.createQuery()
                    .inTable(tableName)
                    .selectColumns(args[0])
                    .addCondition(args[1], args[2])
                    .orderBy("id", false)
                    //LIMIT 0, 5    LIMIT实现的分页，CRUD人基操了（
                    .setPageLimit(0, 5)
                    .build();

            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
            queryAction.executeAsync(successQuery -> {

                ResultSet resultSet1 = successQuery.getResultSet();
                if (resultSet1.next()) {
                    concurrentHashMap.put("id", String.valueOf(resultSet1.getInt("id")));
                    concurrentHashMap.put(args[0], resultSet1.getString(args[0]));
                }
            });
            return concurrentHashMap.get(args[0]);
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      两个参数 第一个是被插值列，第二个是插值本身
     */
    private String executeInsert(String tableName, String[] args) {
        if (args.length == 2) {

            sqlManager.createInsert(tableName)
                    .setColumnNames(args[0])
                    .setParams(args[1])
                    .executeAsync();//非查询不需要build

            return "true";
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      四个参数 第一个参数被更改列 第二参数被更改值 第三参数目标更改列 第四参数目标更改值
     */
    private String executeUpdate(String tableName, String[] args) {
        if (args.length == 4) {

            LinkedHashMap<String, Object> infoMap = new LinkedHashMap<>();
            for (String arg : args) {
                if (arg.equals(args[0]) || arg.equals(args[1])) continue;
                infoMap.put(arg, arg);
            }

            sqlManager.createUpdate(tableName)
                    .setColumnValues(infoMap)                       //实现字段映射与数据
                    .setConditions(args[0] + " = '" + args[1] + "'")      //where条件，String类型的数据记得像这样用 ' 单引号包裹住，这种单纯的拼接方法不如传入键值对的那个方便
                    .build()                                        //需要build得到SQLAction再execute | executeAsync
                    .executeAsync((success) -> {
                    }, (exception, sqlAction) -> {
                        MiracleBlock.getInstance().getLogger().warning(exception.getMessage());
                    });

            return "true";
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      两个参数 第一个指定删除列 第二个指定删除列中的删除值
     */
    private String executeDelete(String tableName, String[] args) {
        if (args.length == 2) {
            sqlManager.createDelete(tableName)
                    .setConditions(args[0] + " = '" + args[1] + "'")
                    .build()
                    .execute(null);//同步执行，异常回调为null
            return "true";
        }
        return "false";
    }
}
