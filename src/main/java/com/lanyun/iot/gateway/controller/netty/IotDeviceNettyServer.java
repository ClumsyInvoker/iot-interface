package com.lanyun.iot.gateway.controller.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Netty服务器
 */
@Slf4j
@Component
public class IotDeviceNettyServer implements InitializingBean, DisposableBean {

    @Value("${netty.server.enable}")
    private boolean enable = false;

    private Integer bossThread;
    private Integer workerThread;
    //
    private int lengthFieldLen = 2; //长度字段默认2字节

    private int idleTimeoutSecodes = 10 * 60; //默认十分钟超时，断开连接

    //
    private String bindIp;

    @Value("${netty.server.port}")
    private Integer port;
    //
    private volatile AtomicBoolean started = new AtomicBoolean(false);


    private ChannelFuture serverSocketChannelFuture;

    private Thread startThread;

    /**
     * 关闭时使用
     */
    private EventLoopGroup boss;

    private EventLoopGroup worker;

    /////////////////////////////////////////////////////
    //constructor
    //////////////////////////////////////////////////
    public IotDeviceNettyServer() {
    }

    public IotDeviceNettyServer(Integer port) {
        this.port = port;
    }

    public IotDeviceNettyServer(String bindIp, Integer port) {
        this.bindIp = bindIp;
        this.port = port;
    }

    public IotDeviceNettyServer(Integer bossThread, Integer workerThread, String bindIp, Integer port) {
        this.bossThread = bossThread;
        this.workerThread = workerThread;
        this.bindIp = bindIp;
        this.port = port;
    }

    public IotDeviceNettyServer(Integer bossThread, Integer workerThread, int lengthFieldLen,
                                int idleTimeoutSecodes, String bindIp, Integer port) {
        this.bossThread = bossThread;
        this.workerThread = workerThread;
        this.lengthFieldLen = lengthFieldLen;
        this.idleTimeoutSecodes = idleTimeoutSecodes;
        this.bindIp = bindIp;
        this.port = port;
    }

    /**
     * 启动服务
     */
    public void start() {
        log.info("启动NettyServer相关");
        log.info(this.getClass().getSimpleName() + " will start at ip:" + bindIp + " ,port:" + port);
        if (!started.get() && started.compareAndSet(false, true)) {
            if (port == null) {
                throw new IllegalStateException(this.getClass().getSimpleName() + " param port is not initialized");
            }

            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("netty server port must between 0 and 65535, actually is " + port);
            }

            //因为是长链接，不会频繁的断开连接，接受连接的线程设为2即可
            if (bossThread == null) {
                bossThread = 2;
            }
            //处理数据的线程数默认为CPU + 1
            if (workerThread == null) {
                workerThread = Runtime.getRuntime().availableProcessors() + 1;
            }

            try {
                doStart();
            } catch (Exception e) {
                log.error(this.getClass().getSimpleName() + "start failed!", e);
                started.set(false);
            }
        } else {
            log.warn(this.getClass().getSimpleName() + " start failed for it is already started");
        }
    }

    private void doStart() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThread);
        //
        boss = bossGroup;
        worker = workerGroup;
        //
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new IotDeviceServerHandlerInitializer(lengthFieldLen, idleTimeoutSecodes))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            // 绑定端口，开始接收进来的连接
            ChannelFuture f;
            if (bindIp != null) {
                f = b.bind(bindIp, port);
            } else {
                f = b.bind(port);
            }
            //
            f.sync();
            //
            log.info(this.getClass().getSimpleName() + " started");
            //保存下来，关闭连接时使用
            this.serverSocketChannelFuture = f;

            f.channel().closeFuture().sync();

        } finally {
            log.info("netty threadPool will shutdownGracefully in finally block");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 关闭服务
     */
    public void stop() {
        log.info(this.getClass().getSimpleName() + " stopping...");
        if (started.get() && started.compareAndSet(true, false)) {
            try {
                if (serverSocketChannelFuture != null)
                    serverSocketChannelFuture.channel().close().await(200); //等待关闭200ms
            } catch (Exception e) {
                log.error("Exception occurs while close channel future", e);
            }

            log.info("netty threadPool will shutdownGracefully in stop method");
            //
            if (boss != null)
                boss.shutdownGracefully();
            if (worker != null)
                worker.shutdownGracefully();
            //
            log.info(this.getClass().getSimpleName() + " stopped");
        } else {
            log.warn(this.getClass().getSimpleName() + " stop failed for it is already stopped");
        }
    }

    /////////////////////////////////

    public void setBossThread(Integer bossThread) {
        this.bossThread = bossThread;
    }

    public void setWorkerThrad(Integer workerThread) {
        this.workerThread = workerThread;
    }

    public void setLengthFieldLen(int lengthFieldLen) {
        this.lengthFieldLen = lengthFieldLen;
    }

    public void setIdleTimeoutSecodes(int idleTimeoutSecodes) {
        this.idleTimeoutSecodes = idleTimeoutSecodes;
    }

    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (enable) {
            if (startThread == null) {
                startThread = new Thread(this::start);
            }
            startThread.start();
        }
    }
}
