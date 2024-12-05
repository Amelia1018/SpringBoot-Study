package im.hxmeet.Xfile.module.link.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * 直链实体类器
 *
 * @author zhaojun
 */
@Component
@Mapper(componentModel = "spring")
public interface ShortLinkConvert {

    @Mapping(source = "shortLink.id", target = "id")
    @Mapping(source = "storageSource.name", target = "storageName")
    @Mapping(source = "storageSource.type", target = "storageType")
    ShortLinkResult entityToResultList(ShortLink shortLink, StorageSource storageSource);

}