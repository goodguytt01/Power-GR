package tk.mybatis.springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.dto.UpstreamInstanceDTO;
import tk.mybatis.springboot.mapper.UpStreamInstanceMapper;
import tk.mybatis.springboot.model.Upstream;
import tk.mybatis.springboot.model.UpstreamInstance;
import tk.mybatis.springboot.service.UpStreamInstanceService;

import java.util.ArrayList;
import java.util.List;

@Service("upStreamInstanceService")
public class UpstreamInstanceServiceImpl implements UpStreamInstanceService {

    @Autowired
    private UpStreamInstanceMapper upStreamInstanceMapper;


    @Override
    public Result insert(UpstreamInstanceDTO UpstreamInstanceDTO) {
        if(UpstreamInstanceDTO!=null) {
            UpstreamInstance upstream = new UpstreamInstance();
            BeanUtils.copyProperties(UpstreamInstanceDTO,upstream);
            upStreamInstanceMapper.insert(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result getList(UpstreamInstanceDTO instanceDTO) {
        UpstreamInstance upstreamInstance =  new UpstreamInstance();
        BeanUtils.copyProperties(instanceDTO,upstreamInstance);
        List<UpstreamInstance> list = upStreamInstanceMapper.getList(upstreamInstance);
        List<UpstreamInstanceDTO> UpstreamInstanceDTOS = new ArrayList<>();
        for(UpstreamInstance method:list){
            UpstreamInstanceDTO dto = new UpstreamInstanceDTO();
            BeanUtils.copyProperties(method,dto);
            UpstreamInstanceDTOS.add(dto);
        }
        return Result.success(UpstreamInstanceDTOS);
    }

    @Override
    public Result getUrl(UpstreamInstanceDTO UpstreamInstanceDTO) {
        if(UpstreamInstanceDTO!=null) {
            UpstreamInstance upstream = new UpstreamInstance();
            BeanUtils.copyProperties(UpstreamInstanceDTO,upstream);
            upstream = upStreamInstanceMapper.getUrl(upstream);
            BeanUtils.copyProperties(upstream,UpstreamInstanceDTO);
            return Result.success(UpstreamInstanceDTO);
        }
        return null;
    }

    @Override
    public Result updateStream(UpstreamInstanceDTO UpstreamInstanceDTO) {
        UpstreamInstance upstream = new UpstreamInstance();
        if(UpstreamInstanceDTO!=null){
            BeanUtils.copyProperties(UpstreamInstanceDTO,upstream);
            upStreamInstanceMapper.updateUpStream(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result deleteStream(UpstreamInstanceDTO UpstreamInstanceDTO) {
        UpstreamInstance upstream = new UpstreamInstance();
        if(UpstreamInstanceDTO!=null){
            BeanUtils.copyProperties(UpstreamInstanceDTO,upstream);
            upStreamInstanceMapper.deleteUpStream(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result enableOrDisable(UpstreamInstanceDTO UpstreamInstanceDTO) {
        UpstreamInstance upstream = new UpstreamInstance();
        if(UpstreamInstanceDTO!=null){
            BeanUtils.copyProperties(UpstreamInstanceDTO,upstream);
            upStreamInstanceMapper.enableOrDisable(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result getById(Long id) {
        return Result.success(upStreamInstanceMapper.getById(id));
    }
}
