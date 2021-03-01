package hqsc.ray.log.feign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.dto.CommonLog;
import hqsc.ray.core.kafka.channel.LogChannel;
import hqsc.ray.core.log.feign.ICommonLogProvider;

/**
 * 消息生产者调用
 * @author pangu
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "调用消息生产者")
public class CommonLogProvider implements ICommonLogProvider {

    private final LogChannel logChannel;

    @Override
    @PostMapping("/provider/common-log/send")
    @ApiOperation(value = "发送普通消息", notes = "发送普通消息")
    public Result<?> sendCommonLog(@RequestBody CommonLog commonLog) {
        boolean flag = logChannel.sendLogMessage().send(MessageBuilder.withPayload(commonLog).build());
        if (flag) {
            return Result.success("操作成功");
        }
        return Result.fail("操作失败");
    }
}
