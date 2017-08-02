package com.ryo.metadata.core.dal.impl;

import com.ryo.metadata.core.dal.JdbcMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bbhou on 2017/8/1.
 */
public abstract class AbstractJdbcMapper implements JdbcMapper {

    private static final Logger LOGGER = LogManager.getLogger(AbstractJdbcMapper.class);

    /**
     * 获取数据库连接
     * @return
     */
    protected abstract Connection getConnection();

    @Override
    public ResultSet query(String querySql) {
        ResultSet rs = null;
        Connection connection = getConnection();
        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(querySql);
        } catch (Exception e) {
            LOGGER.error("query meet ex: "+e, e);
        }
        return rs;
    }

    @Override
    public void execute(String sql) throws SQLException {
        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            LOGGER.error("execute meet ex: "+e, e);
        }
    }

    @Override
    public void executeTransaction(List<String> stringList) throws SQLException {
        LOGGER.info("executeTransaction with sql: "+ stringList);
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            for(String sql : stringList) {
                statement.addBatch(sql);
                LOGGER.debug(sql);
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error("query meet ex: "+e, e);
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public DatabaseMetaData metaData() throws SQLException {
        return getConnection().getMetaData();
    }
}
