package kostiv.testingdb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

/**
 * Authentication module configuration. Contains all beans used by module for proper running.
 * 
 * @author Vasyl.Kostiv
 *
 */
@Configuration
@PropertySource({"application.properties"})
public class AppSpringConfig {
	

	@Autowired
	private Environment env;
	
	public static String DB_DRIVER_CLASSNAME = "database.driverClassName";
	public static String DB_URL = "database.url";
	public static String DB_USERNAME = "database.username";
	public static String DB_PASSWORD = "database.password";

	private static final String HB_DIALECT = "hibernate.dialect";

	
	private static Logger LOGGER = LoggerFactory.getLogger(AppSpringConfig.class);


	/**
	 * EntityManagerFactory provider for dependent DAOs. Injects DataSource bean as necessary part. 
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean
	@Autowired
	@Qualifier("dataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties hibernateProperties, JpaDialect jpaDialect) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setJpaProperties(hibernateProperties);
		em.setDataSource(dataSource);
		em.setPackagesToScan("kostiv.testingdb.data");
		//em.setMappingResources("mapping.xml");
		em.setJpaDialect(jpaDialect);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		return em;
	}

	@Bean
	public Properties hibernateProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", env.getProperty(HB_DIALECT));
		props.put("hibernate.show_sql", true);
		return props;
	}
	
	/**
	 * Provides {@link DataSource} instance for pooled database access
	 * 
	 * @return
	 */
	@Bean(name="dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty(DB_DRIVER_CLASSNAME, "org.postgresql.Driver"));
		dataSource.setUrl(env.getProperty(DB_URL));
		dataSource.setUsername(env.getProperty(DB_USERNAME));
		dataSource.setPassword(env.getProperty(DB_PASSWORD));
		return dataSource;
	}

	/**
	 * Provides TransactionManager based on existing {@link EntityManagerFactory} 
	 * 
	 * @param emf
	 * @return
	 */
	@Bean
	@Autowired
	@Qualifier("dataSource")
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf, JpaDialect jpaDialect, DataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		transactionManager.setDataSource(dataSource);
		transactionManager.setJpaDialect(jpaDialect);

		return transactionManager;
	}
	
	@Bean
	public JpaDialect jpaDialect() {
		return new HibernateJpaDialect();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
