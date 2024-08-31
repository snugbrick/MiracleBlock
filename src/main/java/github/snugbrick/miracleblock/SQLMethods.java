package github.snugbrick.miracleblock;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public enum SQLMethods {
    TABLE("table"),
    QUERY("query"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");


    final String code;

    final SQLManager sqlManager = MiracleBlock.getSqlManager();

    SQLMethods(String code) {
        this.code = code;
    }

    public String runTasks(String tableName, String... args) throws SQLException {
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
        TableCreateBuilder tcb = sqlManager.createTable(tableName);

        for (String columnName : args) {
            tcb.addColumn(columnName, "VARCHAR(32) NOT NULL");
        }
        tcb.addAutoIncrementColumn("id")
                .build().executeAsync();

        MiracleBlock.getInstance().getLogger().info("table创建完毕");

        return "true";
    }

    /**
     * 参数位置错一下就会出现奇妙的结果哦 >:)
     *
     * @param tableName 指定表
     * @param args      三个参数 第一个是被查找的列 第二个是你根据哪个列来进行查找
     *                  第三个是你根据第二参数指定列中的值来获取行以定位
     * @return 被定位的值
     */
    private String executeQuery(String tableName, String[] args) {
        if (args.length == 3) {
            String callBack = sqlManager.createQuery()
                    .inTable(tableName)
                    .selectColumns(args[0])
                    .addCondition(args[1], args[2])
                    .orderBy("id", false)
                    .setLimit(1)
                    //.setPageLimit(0, 5) //分页
                    .build().execute(query -> {
                        ResultSet result = query.getResultSet();
                        return result.next() ? result.getString(args[0]) : null;
                    }, (exception, action) -> {
                        MiracleBlock.getInstance().getLogger().info("Query执行错误");
                    });

            MiracleBlock.getInstance().getLogger().info("Query执行完毕 " + callBack);
            return callBack;
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      两个参数 第一个是被插值列，第二个是插值本身
     */
    private String executeInsert(String tableName, String[] args) throws SQLException {
        ResultSet columns = sqlManager.getConnection().getMetaData().getColumns(null, null, tableName, null);
        int columnCount = 0;
        while (columns.next()) {
            columnCount++;
        }
        if (args.length <= 2 * (columnCount - 1)) {
            List<String> column = new ArrayList<>();
            List<String> content = new ArrayList<>();
            for (int i = 0; i < 2 * (columnCount - 1); i += 2) {
                column.add(args[i]);//0 2 5
                MiracleBlock.getInstance().getLogger().info(args[i]);
                content.add(args[i + 1]);//1 3 6
                MiracleBlock.getInstance().getLogger().info(args[i + 1]);
            }
            //两个列表不行 一个列表一个数组就能行 还得麻烦我压数组 :/
            String[] commitContent = new String[3];
            for (int i = 0; i < columnCount - 1; i++) {
                commitContent[i] = content.get(i);
            }
            sqlManager.createInsert(tableName)
                    .setColumnNames(column)
                    .setParams(commitContent)
                    .executeAsync();//非查询不需要build
            MiracleBlock.getInstance().getLogger().info("Insert执行完毕");
            return "true";
        }
        return "你插入的参数太少了，也有可能是因为你第一次创建表";
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

            MiracleBlock.getInstance().getLogger().info("Update执行完毕");
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

            MiracleBlock.getInstance().getLogger().info("Delete执行完毕");
            return "true";
        }
        return "false";
    }
}
