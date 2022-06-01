package scu.genius.dummiescrawler.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import scu.genius.dummiescrawler.core.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
