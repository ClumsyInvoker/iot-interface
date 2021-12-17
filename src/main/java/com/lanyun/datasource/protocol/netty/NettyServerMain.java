package com.lanyun.datasource.protocol.netty;


public class NettyServerMain {

    private int port;

    public NettyServerMain(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8088;
        }

        IotDeviceNettyServer server = new IotDeviceNettyServer(port);
        server.start();
    }


}
