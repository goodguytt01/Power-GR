package tk.mybatis.springboot.mapper;


import org.apache.ibatis.annotations.Param;
import tk.mybatis.springboot.model.DubboMethod;

import java.util.List;


public interface DubboMethodMapper {
    int insert(DubboMethod dubboMethod);
    List<DubboMethod> getList(DubboMethod dubboMethod);
    DubboMethod getById(Long id);
    DubboMethod getUrl(@Param("url") String url);
    int updateDeleteStatus(DubboMethod dubboMethod);
    int updateStatus(DubboMethod dubboMethod);

}
