package com.parent.xtgl.utils.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/*获取mysql数据库下表和列的信息*/
@Component
public class TableUtils {
    public final static String sqlForTable_all = "select TABLE_NAME from information_schema.tables where TABLE_SCHEMA = 'qianxp'";
    public final static String sqlForTableAndColumns = "select TABLE_NAME,COLUMN_NAME from information_schema.columns where TABLE_SCHEMA = 'qianxp'";
    public final static String sqlForTableAndId="select TABLE_NAME,COLUMN_NAME from information_schema.columns where TABLE_SCHEMA = 'qianxp' AND COLUMN_KEY = 'PRI'  ";
    public final static String sqlForGetColumnsByTable = "select COLUMN_NAME from information_schema.columns where TABLE_SCHEMA = 'qianxp' AND TABLE_NAME = ?";
    public final static String sqlForGetIdColumnByTable = "select COLUMN_NAME from information_schema.columns where TABLE_SCHEMA = 'qianxp' AND COLUMN_KEY = 'PRI' AND TABLE_NAME = ?";
    public final static String sqlForCheckTableExists = "select count(1) from information_schema.tables where TABLE_SCHEMA = 'qianxp' AND TABLE_NAME = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*获取全部的表名*/
    public List<String> getAllTables(){
        List<String> list = jdbcTemplate.queryForList(sqlForTable_all,String.class);
        return list;
    }

    /*获取全部表名和列名*/
    public List<Map<String,Object>> getAllTablesAndColumns(){
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sqlForTableAndColumns);
        return list;
    }
    /*获取全部表面和主键名*/
    public List<Map<String,Object>> getAllTablesAndId(){
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sqlForTableAndId);
        return list;
    }
    /*获取表名下的所有列名*/
    public List<String> getColumnsByTable(String table){
        List<String> list = jdbcTemplate.queryForList(sqlForGetColumnsByTable,new String[]{table},String.class);
        return list;
    }
    /*通过表名获得主键列*/
    public String getIdColumnByTable(String table){
        String idColumn = jdbcTemplate.queryForObject(sqlForGetIdColumnByTable,String.class,table);
        return idColumn;
    }
    /*判断表是否存在*/
    public boolean isExistTable(String tableName){
        Integer count = jdbcTemplate.queryForObject(sqlForCheckTableExists,Integer.class,tableName);
        if(count > 0){return true;}
        return false;
    }
    /*查询表中的所有数据*/
    public List<Map<String,Object>> getTableData(String tableName){
        List<Map<String,Object>> tableList = jdbcTemplate.queryForList("select * from "+tableName);
        return tableList;
     }
}
