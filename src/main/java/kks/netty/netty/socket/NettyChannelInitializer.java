package kks.netty.netty.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import kks.netty.netty.handler.NettyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyHandler nettyHandler;
    private static final int TIMEOUT_SECONDS = 15;

    // 클라이언트 소켓 채널이 생성될 때 호출
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        // 뒤이어 처리할 디코더 및 핸들러 추가
//        pipeline.addLast(testDecoder);

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        // Byte To String
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // String To Byte
        pipeline.addLast(new StringEncoder(Charset.forName("EUC-KR")));
        //타임아웃 설정 15초
        pipeline.addLast(new IdleStateHandler(TIMEOUT_SECONDS, 0, 0, TimeUnit.SECONDS));
        // 수행되는 로직
        pipeline.addLast(nettyHandler);

    }
}
