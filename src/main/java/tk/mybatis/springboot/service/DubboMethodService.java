package tk.mybatis.springboot.service;


import tk.mybatis.springboot.dto.DubboMethodDTO;
import tk.mybatis.springboot.dto.Result;

import java.util.List;

public interface DubboMethodService {
    Result insert(DubboMethodDTO dubboMethod);
    Result getList(DubboMethodDTO dubboMethod);
    Result getById(Long id);
    Result getByUrl(String url);
    Result delete(DubboMethodDTO dubboMethodDTO);
    Result updateStatus(DubboMethodDTO dubboMethodDTO);
}
