package com.stackroute.keepnote.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport 
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * 
 * */
@Configuration
@ComponentScan(basePackages = "com.stackroute.keepnote")
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationContextConfig {

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(
				"jdbc:mysql://localhost:3306/keepnote_dbtest?createDatabaseIfNotExist=true&verifyServerCertificate=false&useSSL=false&requiredSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("Welcome1");

		// Hobbes configuration
		/*
		 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		 * dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
		 * System.getenv("MYSQL_DATABASE") +
		 * "?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		 * dataSource.setUsername(System.getenv("MYSQL_USER"));
		 * dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
		 */
		return dataSource;
	}

	/*
	 * Use this configuration while submitting solution in hobbes.
	 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	 * dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
	 * System.getenv("MYSQL_DATABASE")
	 * +"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
	 * dataSource.setUsername(System.getenv("MYSQL_USER"));
	 * dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
	 */

	/*
	 * create a getter for Hibernate properties here we have to mention 1. show_sql
	 * 2. Dialect 3. hbm2ddl
	 */

	private Properties getHibernateProperties() {
		Properties props = new Properties();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.put("hibernate.hbm2ddl.auto", "update");
		return props;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		;
		builder.addProperties(getHibernateProperties());
		builder.scanPackages("com.stackroute.keepnote.model");
		return builder.buildSessionFactory();
	}

	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles
	 * transaction in Spring. The application that uses single hibernate session
	 * factory for database transaction has good choice to use
	 * HibernateTransactionManager. HibernateTransactionManager can work with plain
	 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
	 * ensures data integrity.
	 */
	@Bean
	public HibernateTransactionManager getTransaction(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
		return txManager;
	}

}
