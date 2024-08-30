package github.snugbrick.miracleblock;

public class SqlSettingGetter {
    public static String driver = MiracleBlock.getInstance().getConfig().getString("miracle_block.datasource.driver");
    public static String url = MiracleBlock.getInstance().getConfig().getString("miracle_block.datasource.url");
    public static String username = MiracleBlock.getInstance().getConfig().getString("miracle_block.datasource.username");
    public static String password = MiracleBlock.getInstance().getConfig().getString("miracle_block.datasource.password");
}
