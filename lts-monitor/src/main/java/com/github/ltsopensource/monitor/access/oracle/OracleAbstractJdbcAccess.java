package com.github.ltsopensource.monitor.access.oracle;

import com.github.ltsopensource.core.cluster.Config;
import com.github.ltsopensource.core.json.JSON;
import com.github.ltsopensource.store.jdbc.JdbcAbstractAccess;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Oracle jdbc抽象访问类
 * Created by zhangjianjun on 2017/5/23.
 */
public abstract class OracleAbstractJdbcAccess extends JdbcAbstractAccess {
    public OracleAbstractJdbcAccess(Config config) {
        super(config);
        if(!tableExists(getTableName())){
            createOracleTable(readSqlFile("sql/oracle/" + getTableName() + ".sql", getTableName()), getTableName());
        }
    }

    /**
     * 判断oracle表是否存在
     * @param tableName 表名
     * @return 存在返回true
     */
    private boolean tableExists(String tableName) {
        BigDecimal num = null;
        try {
            num = getSqlTemplate().queryForValue("select count(*)  from user_tables where table_name = ?", tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("tablename:"+tableName+", num:"+ JSON.toJSONString(num));
        return num != null && num.intValue() > 0;
    }

    protected abstract String getTableName();

}
