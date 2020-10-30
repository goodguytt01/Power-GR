package tk.mybatis.springboot.controller;


import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dto.JsonResVo;
import tk.mybatis.springboot.dto.ZkInfoDTO;
import tk.mybatis.springboot.model.BaseZookeeper;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/Api")
public class ZkApiController {

    @RequestMapping(method =  {RequestMethod.POST},value = "/Zk/Apis")
    @ResponseBody
    public JsonResVo addPropertyKey(@RequestBody ZkInfoDTO zkInfoDTO) {
        BaseZookeeper zookeeper = new BaseZookeeper();
        List<String> children = new ArrayList<>();
        List<ZkInfoDTO> dtos = new ArrayList<>();
        try {
            zookeeper.connectZookeeper(zkInfoDTO.getZkAddr());
            children = zookeeper.getChildren("/dubbo");
            for(String child:children){
                ZkInfoDTO infoDTO = new ZkInfoDTO();
                infoDTO.setDomain(zkInfoDTO.getDomain());
                infoDTO.setInterfaceAddr(child);
                infoDTO.setZkAddr(zkInfoDTO.getZkAddr());
                dtos.add(infoDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResVo.buildSuccess(dtos);
    }
}
