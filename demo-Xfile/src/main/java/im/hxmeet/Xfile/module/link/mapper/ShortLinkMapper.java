package im.hxmeet.Xfile.module.link.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接配置表 Mapper 接口
 */
@Mapper//@Mapper 注解标识该接口是 MyBatis 的 Mapper 接口，MyBatis 会自动识别并为其创建实现类。
public interface ShortLinkMapper extends BaseMapper<ShortLink> {

    /**
     * 根据短链接 key 查询短链接
     *
     * @param   key
     *          短链接 key
     *
     * @return  短链接信息
     */
    ShortLink findByKey(@Param("key")String key);
//key: 短链接的唯一标识符。
//返回值: 返回与该 key 相关的 ShortLink 对象，代表短链接的信息。

    /**
     * 根据存储源 ID 和文件路径查询短链接
     *
     * @param   storageId
     *          存储源 ID
     *
     * @param   url
     *          短链接 url
     *
     * @return  短链接信息
     */
    ShortLink findByStorageIdAndUrl(@Param("storageId") Integer storageId, @Param("url") String url);


    /**
     * 根据存储源 ID 删除所有数据
     *
     * @param 	storageId
     * 			存储源 ID
     */
    int deleteByStorageId(@Param("storageId") Integer storageId);

}