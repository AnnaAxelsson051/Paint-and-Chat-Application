package netty;

import client.Client;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static final ChannelGroup channels = new DefaultChannelGroup(new DefaultEventExecutor());

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //Send all connected clients to incoming connection.
        for (Channel channel : channels)
            incoming.writeAndFlush("[SERVER] - " + remoteAddress(channel) + " has joined\r\n");

        channels.add(incoming);
        channels.writeAndFlush("[SERVER] - " + remoteAddress(incoming) + " has joined\r\n");
//        System.out.println("Client " + incoming.remoteAddress() + " has joined\r\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[SERVER] - " + remoteAddress(incoming) + " has left\r\n");
        channels.remove(incoming);
//        System.out.println("Client " + incoming.remoteAddress() + " has left\r\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + remoteAddress(incoming) + "] " + s + "\r\n");
            } else {
                channel.writeAndFlush("[you] " + s + "\r\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
//        System.out.println("Error for incoming.remoteAddress()," + cause.getMessage() +"\r\n");
        ctx.close();
    }

    private String remoteAddress(Channel remoteChannel){
        return remoteChannel.remoteAddress().toString().substring(1);
    }
}