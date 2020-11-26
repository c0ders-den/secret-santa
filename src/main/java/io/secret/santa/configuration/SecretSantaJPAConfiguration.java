/* 
 * Copyright (C) 2020-present  Arpan Mukhopadhyay. All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.secret.santa.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"io.secret.santa.repository"})
public class SecretSantaJPAConfiguration {

	@Autowired
	private SecretSantaApplicationInitConfiguration config;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(getDatabase());
		vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("io.secret.santa.db.models");
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties(getDialect()));
		return em;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		populateDataSource(ds);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private void populateDataSource(DriverManagerDataSource ds) {
		if (null == ds) return;
		String url = null;
		switch (config.getDBType()) {
		case hsql:
			ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
			url = "jdbc:hsqldb:file:" + config.getDBPath(); 
			break;
		case postgres:
			ds.setDriverClassName("org.postgresql.Driver");
			url = "jdbc:postgresql://" + config.getDBHost() + "/" + config.getDBName();
			ds.setSchema("public");
			break;
		case mysql:
			ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
			url = "jdbc:mysql://" + config.getDBHost() + "/" + config.getDBName();
			ds.setSchema(config.getDBName());
			break;
		case mssql:
			ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			url = "jdbc:sqlserver://" + config.getDBHost() + ";databaseName=" + config.getDBName();
			break;
		default:
			break;
		}
		ds.setUrl(url);
		ds.setUsername(config.getDBUsername());
		ds.setPassword(config.getDBPassword());
	}
	
	private Properties additionalProperties(final String dialect) {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.current_session_context_class", "thread");
		properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}
	
	private String getDialect() {
		String dialect = null;
		switch (config.getDBType()) {
		case hsql:
			dialect = "org.hibernate.dialect.HSQLDialect";
			break;
		case postgres:
			dialect = "org.hibernate.dialect.PostgreSQL95Dialect";
			break;
		case mysql:
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect";
			break;
		case mssql:
			dialect = "org.hibernate.dialect.SQLServerDialect";
			break;	
		case derby:
			dialect = "org.hibernate.dialect.DerbyTenSevenDialect";
		default:
			break;
		}
		return dialect;
	}
	
	private Database getDatabase() {
		Database db = null;
		switch (config.getDBType()) {
		case hsql:
			db = Database.HSQL;
			break;
		case postgres:
			db = Database.POSTGRESQL;
			break;
		case mysql:
			db = Database.MYSQL;
			break;
		case mssql:
			db = Database.SQL_SERVER;
			break;
		case derby:
			db = Database.DERBY;
		default:
			break;
		}
		return db;
	}
}
