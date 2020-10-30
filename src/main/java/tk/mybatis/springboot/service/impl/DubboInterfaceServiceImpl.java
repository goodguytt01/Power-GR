package tk.mybatis.springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.dto.DubboInterfaceDTO;
import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.mapper.DubboInterfaceMapper;
import tk.mybatis.springboot.model.DubboInterface;
import tk.mybatis.springboot.service.DubboInterfaceService;

import java.util.ArrayList;
import java.util.List;

@Service("dubboInterfaceService")
public class DubboInterfaceServiceImpl implements DubboInterfaceService {

    @Autowired
    private DubboInterfaceMapper dubboInterfaceMapper;

    @Override
    public Result insert(DubboInterfaceDTO dubboInterfaceDTO) {
        DubboInterface dubboInterface = new DubboInterface();
        BeanUtils.copyProperties(dubboInterfaceDTO,dubboInterface);
        dubboInterfaceMapper.insert(dubboInterface);
        return Result.success(null);
    }

    @Override
    public Result getList() {
      List<DubboInterface> interfaces =  dubboInterfaceMapper.getList();
      List<DubboInterfaceDTO> dtos = new ArrayList<>();
      for(DubboInterface dubboInterface:interfaces){
          DubboInterfaceDTO interfaceDTO = new DubboInterfaceDTO();
          BeanUtils.copyProperties(dubboInterface,interfaceDTO);
          dtos.add(interfaceDTO);
      }
      return Result.success(dtos);
    }

    @Override
    public Result getUrl(DubboInterfaceDTO dubboInterfaceDTO) {
        DubboInterface dubboInterface = new DubboInterface();
        BeanUtils.copyProperties(dubboInterfaceMapper.getUrl(dubboInterface),dubboInterfaceDTO);
        return Result.success(dubboInterfaceDTO);
    }

    @Override
    public Result updateInterface(DubboInterfaceDTO dubboInterfaceDTO) {
        DubboInterface dubboInterface = new DubboInterface();
        BeanUtils.copyProperties(dubboInterfaceDTO,dubboInterface);
        return Result.success(dubboInterfaceMapper.updateInterface(dubboInterface));
    }

    @Override
    public Result deleteInterface(DubboInterfaceDTO dubboInterfaceDTO) {
        DubboInterface dubboInterface = new DubboInterface();
        BeanUtils.copyProperties(dubboInterfaceDTO,dubboInterface);
        return Result.success(dubboInterfaceMapper.deleteInterface(dubboInterface));
    }

    @Override
    public Result enableOrDisable(DubboInterfaceDTO dubboInterfaceDTO) {
        DubboInterface dubboInterface = new DubboInterface();
        BeanUtils.copyProperties(dubboInterfaceDTO,dubboInterface);
        return Result.success(dubboInterfaceMapper.enableOrDisable (dubboInterface));
    }

    @Override
    public Result getById(Long id) {
        DubboInterfaceDTO interfaceDTO = new DubboInterfaceDTO();
        DubboInterface dubboInterface = dubboInterfaceMapper.getById(id);
        BeanUtils.copyProperties(dubboInterface,interfaceDTO);
        return Result.success(interfaceDTO);
    }
}
