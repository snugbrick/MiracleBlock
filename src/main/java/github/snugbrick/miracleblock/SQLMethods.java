package github.snugbrick.miracleblock;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;
import cc.carm.lib.easysql.api.builder.TableQueryBuilder;

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

    /**
     * {@code TABLE}: tableName-指定表 args-生成的所有列<br>
     * {@code QUERY}: tableName-指定表 args-三个参数 第一个是被查找的列 第二个是你根据哪个列来进行查找 第三个是你根据第二参数指定列中的值来获取行以定位<br>
     * {@code INSERT}: tableName-指定表 args-2a个参数 第一个是被插值列，第二个是插值本身<br>
     * {@code UPDATE}: tableName-指定表 第一个参数(x)指定定位被更改列值对数量，第二个参数(y)指定更改结果列值对数量，之后的2x的参数为被更改的列值对用于定位，之后的2y的参数用于将值修改为指定列值对<br>
     * {@code DELETE}: tableName-指定表 第1个列值对删除列 第2个指定删除列中的删除值 以此类推<br>
     *
     * @param tableName 指定表
     * @param args      参数
     * @return 包含结果的列表
     * @throws SQLException 数据库错误
     */
    public List<String> runTasks(String tableName, String... args) throws SQLException {
        List<String> backValue = new ArrayList<>();
        switch (code) {
            case ("table"):
                backValue.add(executeTable(tableName, args));
                return backValue;
            case ("query"):
                return executeQuery(tableName, args);
            case ("insert"):
                backValue.add(executeInsert(tableName, args));
                return backValue;
            case ("update"):
                backValue.add(executeUpdate(tableName, args));
                return backValue;
            case ("delete"):
                backValue.add(executeDelete(tableName, args));
                return backValue;
        }
        backValue.add("false");
        return backValue;
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

        MiracleBlock.getInstance().getLogger().info(tableName + " table创建完毕");

        return "true";
    }

    /**
     * 参数位置错一下就会出现奇妙的结果哦 >:)
     *
     * @param tableName 指定表
     * @param args      参数列表 第一个是被查找的列 第二、三参数是第一对条件列和值
     *                  第四、五参数是第二对条件列和值 (可选) 如果没有提供第二对条件则仅使用第一对
     * @return 被定位的值
     */
    private List<String> executeQuery(String tableName, String... args) {
        if (args.length >= 1 && args.length <= 5) {
            TableQueryBuilder tqb = sqlManager.createQuery()
                    .inTable(tableName)
                    .selectColumns(args[0]);

            // 如果提供了第一对条件
            if (args.length >= 3 && args[1] != null && args[2] != null) {
                tqb.addCondition(args[1], args[2]);
            }

            // 如果提供了第二对条件
            if (args.length == 5 && args[3] != null && args[4] != null) {
                tqb.addCondition(args[3], args[4]);
            }

            List<String> callBack = tqb.build().execute(query -> {
                ResultSet result = query.getResultSet();
                List<String> results = new ArrayList<>();
                while (result.next()) {
                    results.add(result.getString(args[0]));
                }
                return results;
            }, (exception, action) -> {
                MiracleBlock.getInstance().getLogger().info("Query执行错误: " + exception.getMessage());
            });

            return callBack;
        }
        return null;
    }

    /**
     * @param tableName 指定表
     * @param args      偶数倍个参数 第一个是被插值列，第二个是插值本身
     */
    private String executeInsert(String tableName, String[] args) throws SQLException {
        ResultSet columns = sqlManager.getConnection().getMetaData().getColumns(null, null, tableName, null);
        int columnCount = 0;
        while (columns.next()) {
            columnCount++;//5
        }
        if (args.length <= 2 * (columnCount - 1)) {
            List<String> column = new ArrayList<>();
            List<String> content = new ArrayList<>();
            for (int i = 0; i < args.length; i += 2) {
                column.add(args[i]);
                content.add(args[i + 1]);
            }
            //两个列表不行 一个列表一个数组就能行 还得麻烦我压数组 :/
            String[] commitContent = new String[content.size()];
            for (int i = 0; i < content.size(); i++) {
                commitContent[i] = content.get(i);
            }
            sqlManager.createInsert(tableName)
                    .setColumnNames(column)
                    .setParams(commitContent)
                    .execute();//非查询不需要build
            return "true";
        }
        return "你插入的参数太少了，也有可能是因为你第一次创建表";
    }

    /**
     * @param tableName 指定表
     * @param args      第一个参数(x)指定定位被更改列值对数量，之后的2x的参数为被更改的列值对用于定位，之后的2y的参数用于将值修改为指定列值对
     */
    private String executeUpdate(String tableName, String[] args) {//8 ->3对实际参数
        if (args.length >= 4) {
            int aimColumnCounter = Integer.parseInt(args[0]);//2

            //存储更改结果列值对
            LinkedHashMap<String, Object> infoMap = new LinkedHashMap<>();
            LinkedHashMap<String, Object> aimMap = new LinkedHashMap<>();
            int counter = 0;
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    continue;
                }
                if (counter < aimColumnCounter) {
                    aimMap.put(args[i], args[i + 1]);
                    i++;
                    counter++;
                } else {
                    infoMap.put(args[i], args[i + 1]);
                    i++;
                }
            }

            sqlManager.createUpdate(tableName)
                    .setColumnValues(infoMap)                       //实现字段映射与数据
                    .setConditions(aimMap)      //WHERE条件，String类型的数据记得像这样用 ' 单引号包裹住，这种单纯的拼接方法不如传入键值对的那个方便
                    .build()                                        //需要build得到SQLAction再execute | executeAsync
                    .executeAsync((success) -> {
                    }, (exception, sqlAction) -> {
                        MiracleBlock.getInstance().getLogger().warning("Update执行异常");
                    });

            return "true";
        }
        return "false";
    }

    /**
     * @param tableName 指定表
     * @param args      第1个列值对删除列 第2个指定删除列中的删除值 以此类推
     */
    private String executeDelete(String tableName, String[] args) {//4
        if (args.length % 2 == 0) {
            LinkedHashMap<String, Object> aimMap = new LinkedHashMap<>();
            for (int i = 0; i < args.length; i += 2) {
                aimMap.put(args[i], args[i + 1]);
            }
            sqlManager.createDelete(tableName)
                    .setConditions(aimMap)
                    .build()
                    .execute(null);//同步执行，异常回调为null

            return "true";
        }
        return "false";
    }
}
