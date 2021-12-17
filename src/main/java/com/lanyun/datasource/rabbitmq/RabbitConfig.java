package com.lanyun.datasource.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

/**
 * RabbitMQ配置
 */
@Configuration
public class RabbitConfig {

    /**
     * 读取配置文件
     */
    @Primary
    @Bean(name = "deviceProperties")
    @ConfigurationProperties(prefix = "spring.rabbitmq.device")
    public RabbitProperties deviceMqProperties() {
        return new RabbitProperties();
    }
    /**
     * 根据配置文件创建MQ连接工厂
     */
    @Primary
    @Bean(name = "deviceMqConnectionFactory")
    public ConnectionFactory deviceMqConnectionFactory() {
        return getConnectionFactory(deviceMqProperties());
    }
    /**
     * 从工厂获取 rabbitTemplate
     */
    @Bean(name = "deviceMqTemplate")
    public AmqpTemplate deviceRabbitTemplate() {
        return new RabbitTemplate(deviceMqConnectionFactory());
    }
    /**
     * 创建rabbitMq监听工厂
     */
    @Bean(name = "deviceMqContainerFactory")
    public RabbitListenerContainerFactory deviceMqContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, deviceMqConnectionFactory());
        return factory;
    }
    /**
     *
     */
    @Bean(name = "deviceAmqpAdmin")
    public AmqpAdmin deviceAmqpAdmin() {
        return new RabbitAdmin(deviceMqConnectionFactory());
    }

    @Bean(name = "oceanMqProperties")
    @ConfigurationProperties(prefix = "spring.rabbitmq.ocean")
    public RabbitProperties oceanMqProperties() {
        return new RabbitProperties();
    }

    @Bean(name = "oceanMqConnectionFactory")
    public ConnectionFactory oceanMqConnectionFactory() {
        return getConnectionFactory(oceanMqProperties());
    }

    @Bean(name = "oceanMqTemplate")
    public AmqpTemplate oceanRabbitTemplate() {
        return new RabbitTemplate(oceanMqConnectionFactory());
    }

    @Bean(name = "oceanMqContainerFactory")
    public RabbitListenerContainerFactory oceanMqContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, oceanMqConnectionFactory());
        return factory;
    }

    @Bean(name = "oceanAmqpAdmin")
    public AmqpAdmin oceanAmqpAdmin() {
        return new RabbitAdmin(oceanMqConnectionFactory());
    }

    @Bean(name = "wasteMqProperties")
    @ConfigurationProperties(prefix = "spring.rabbitmq.waste")
    public RabbitProperties wasteMqProperties() {
        return new RabbitProperties();
    }
    
    @Bean(name = "wasteMqConnectionFactory")
    public ConnectionFactory wasteMqConnectionFactory() {
        return getConnectionFactory(wasteMqProperties());
    }

    @Bean(name = "wasteMqTemplate")
    public AmqpTemplate wasteRabbitTemplate() {
        return new RabbitTemplate(wasteMqConnectionFactory());
    }

    @Bean(name = "wasteMqContainerFactory")
    public RabbitListenerContainerFactory wasteMqContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, wasteMqConnectionFactory());
        return factory;
    }

    @Bean(name = "wasteAmqpAdmin")
    public AmqpAdmin wasteAmqpAdmin() {
        return new RabbitAdmin(wasteMqConnectionFactory());
    }

    private ConnectionFactory getConnectionFactory(RabbitProperties properties) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(properties.getAddresses());
        factory.setPort(properties.getPort());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setVirtualHost(StringUtils.isEmpty(properties.getVirtualHost()) ? "/" : properties.getVirtualHost());
        return factory;
    }
}
