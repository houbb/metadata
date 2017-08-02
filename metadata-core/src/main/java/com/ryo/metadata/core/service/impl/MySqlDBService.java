package com.ryo.metadata.core.service.impl;

import com.ryo.metadata.core.dal.DBMapper;
import com.ryo.metadata.core.dal.JdbcMapper;
import com.ryo.metadata.core.dal.impl.MySqlDBMapper;
import com.ryo.metadata.core.dal.impl.MySqlJdbcMapper;

/**
 * Created by bbhou on 2017/8/1.
 */
public class MySqlDBService extends AbstractDBService {

    private static DBMapper dbMapper = null;

    private static JdbcMapper jdbcMapper = null;

    @Override
    protected DBMapper getDbMapper() {
        if(null == dbMapper) {
            dbMapper = new MySqlDBMapper();
        }
        return dbMapper;
    }

    @Override
    protected JdbcMapper getJdbcMapper() {
        if(null == jdbcMapper) {
            jdbcMapper = new MySqlJdbcMapper();
        }
        return jdbcMapper;
    }

}