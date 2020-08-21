package com.mylife.test;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @descirption : Mybatis-Plus代码生成器
 * @author : wyh
 * @date : 2020/8/21 16:06
 */
public class CodeGenerator {
	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();
		//全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("/Users/wyh/java/");
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false);//是否在xml中添加二级缓存配置
		gc.setBaseResultMap(true);
		gc.setAuthor("wyh");
		mpg.setGlobalConfig(gc);

		//数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setUrl("jdbc:mysql://localhost:3306/mylife?characterEncoding=utf8");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456");
		mpg.setDataSource(dsc);

		//策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[] { "t_user"}); // 需要生成的表
		mpg.setStrategy(strategy);


		//包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.mylife");
		pc.setXml("mapper");
		mpg.setPackageInfo(pc);

		//注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
			}
		};
		mpg.setCfg(cfg);

		// 执行生成
		mpg.execute();

		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}
}