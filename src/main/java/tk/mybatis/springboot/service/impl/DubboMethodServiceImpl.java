package tk.mybatis.springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.dto.DubboMethodDTO;
import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.mapper.DubboMethodMapper;
import tk.mybatis.springboot.model.DubboMethod;
import tk.mybatis.springboot.service.DubboMethodService;

import java.util.ArrayList;
import java.util.List;

@Service("dubboMethodService")
public class  DubboMethodServiceImpl  implements DubboMethodService {


    @Autowired
    DubboMethodMapper dubboMethodMapper;

    @Override
    public Result insert(DubboMethodDTO dubboMethodDTO) {
        DubboMethod dubboMethod = new DubboMethod();
        BeanUtils.copyProperties(dubboMethodDTO,dubboMethod);
        return Result.success(dubboMethodMapper.insert(dubboMethod));
    }

    @Override
    public Result getList(DubboMethodDTO dubboMethodDTO) {
        DubboMethod dubboMethod = new DubboMethod();
        BeanUtils.copyProperties(dubboMethodDTO,dubboMethod);
        List<DubboMethod> list = dubboMethodMapper.getList(dubboMethod);
        List<DubboMethodDTO> dubboMethodDTOS = new ArrayList<>();
        for(DubboMethod method:list){
            DubboMethodDTO dto = new DubboMethodDTO();
            BeanUtils.copyProperties(method,dto);
            dubboMethodDTOS.add(dto);
        }
        return Result.success(dubboMethodDTOS);
    }

    @Override
    public Result getById(Long id) {
        DubboMethod dubboMethod = dubboMethodMapper.getById(id);
        DubboMethodDTO dubboMethodDTO = new DubboMethodDTO();
        BeanUtils.copyProperties(dubboMethod,dubboMethodDTO);
        return Result.success(dubboMethodDTO);
    }

    @Override
    public Result getByUrl(String url) {
        DubboMethod dubboMethod = dubboMethodMapper.getUrl(url);
        DubboMethodDTO dubboMethodDTO = new DubboMethodDTO();
        if(dubboMethod!=null) {
            BeanUtils.copyProperties(dubboMethod, dubboMethodDTO);
        }else {
            return  Result.error();
        }
        return Result.success(dubboMethodDTO);
    }

    @Override
    public Result delete(DubboMethodDTO dubboMethodDTO) {
        DubboMethod dubboMethod = new DubboMethod();
        BeanUtils.copyProperties(dubboMethodDTO,dubboMethod);
        return Result.success(dubboMethodMapper.updateDeleteStatus(dubboMethod));
    }

    @Override
    public Result updateStatus(DubboMethodDTO dubboMethodDTO) {
        DubboMethod dubboMethod = new DubboMethod();
        BeanUtils.copyProperties(dubboMethodDTO,dubboMethod);
        return Result.success(dubboMethodMapper.updateStatus(dubboMethod));
    }
}
