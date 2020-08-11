package cn.shuangbofu.nocturne.dao;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.biezhi.anima.Anima;

/**
 * Created by shuangbofu on 2020/7/29 上午11:00
 */
public class LoadDb {
    public static void load(String url, String username, String password) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setInitialSize(5);
        datasource.setMaxActive(5);
        datasource.setMinIdle(2);
        datasource.setMaxWait(6000);
        Anima.open(datasource);
    }
}
