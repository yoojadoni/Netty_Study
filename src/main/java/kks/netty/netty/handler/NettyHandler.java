package kks.netty.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyHandler extends ChannelInboundHandlerAdapter {
    private int DATA_LENGTH = 2048;
    private ByteBuf buff;


    // 핸들러가 생성될 때 호출되는 메소드
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buff = ctx.alloc().buffer(DATA_LENGTH);
    }

    // 핸들러가 제거될 때 호출되는 메소드
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String cliernIp = inetSocketAddress.getAddress().getHostAddress();
        log.info("[CLIENT 연결종료] -> : {}", cliernIp) ;

        buff = null;
    }

    // 클라이언트와 연결되어 트래픽을 생성할 준비가 되었을 때 호출되는 메소드
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String cliernIp = inetSocketAddress.getAddress().getHostAddress();
        log.info("[CLIENT 연결] -> : {}", cliernIp) ;

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();

        String receive = (String) msg;
        //데이터 보내기
        ctx.writeAndFlush("수신성공");
        /*final ChannelFuture f = ctx.writeAndFlush(buff);
        f.addListener(ChannelFutureListener.CLOSE);*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        ctx.close();
        log.info("[exceptionCaught] -> : {}", cause.toString()) ;

    }

}
