package im.hxmeet.Xfile.module.config.event;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SystemConfigModifyHandler implements ISystemConfigModifyHandler {

    @Resource
    private List<ISystemConfigModifyHandler> handlers;

    /*遍历注入的 handlers 列表，首先通过 filter 方法筛选出符合条件的处理器（即那些 matches 方法返回 true 的处理器），
    然后通过 forEach 方法调用每个符合条件的处理器的 modify 方法，执行具体的修改操作。*/
    public void modify(SystemConfig originalSystemConfig, SystemConfig newSystemConfig) {
        handlers.stream()
                .filter(handler -> handler.matches(originalSystemConfig.getName()))
                .forEach(handler -> handler.modify(originalSystemConfig, newSystemConfig));
    }

    @Override
    public boolean matches(String name) {
        return true;
    }

}

