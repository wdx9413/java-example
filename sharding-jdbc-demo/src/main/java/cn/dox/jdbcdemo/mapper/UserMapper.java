package cn.dox.jdbcdemo.mapper;

import cn.dox.jdbcdemo.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * @author: weidx
 * @date: 2019/7/28
 */

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int insertA(User user);
}
