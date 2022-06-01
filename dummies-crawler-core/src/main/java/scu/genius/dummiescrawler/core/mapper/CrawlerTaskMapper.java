package scu.genius.dummiescrawler.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import scu.genius.dummiescrawler.core.entity.CrawlerTask;

/**
 * @author 11214
 * @since 2022/6/1 15:44
 */
@Mapper
public interface CrawlerTaskMapper extends BaseMapper<CrawlerTask> {
}
