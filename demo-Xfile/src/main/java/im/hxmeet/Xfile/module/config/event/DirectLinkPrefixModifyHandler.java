package im.hxmeet.Xfile.module.config.event;


import im.hxmeet.Xfile.core.service.DynamicControllerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DirectLinkPrefixModifyHandler implements ISystemConfigModifyHandler {

    @Resource
    private DynamicControllerManager dynamicControllerManager;

    @Override
    public void modify(SystemConfig originalSystemConfig, SystemConfig newSystemConfig) {
        dynamicControllerManager.changeDirectLinkPrefixPath(newSystemConfig.getValue());
        log.info("检测到修改了直链前缀, [{}] -> [{}], 已自动更新直链前缀.", originalSystemConfig.getValue(), newSystemConfig.getValue());
    }

    @Override
    public boolean matches(String name) {
        return SystemConfig.DIRECT_LINK_PREFIX_NAME.equals(name);
    }

}

