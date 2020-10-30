package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dto.DubboMethodDTO;
import tk.mybatis.springboot.dto.JsonResVo;
import tk.mybatis.springboot.service.DubboMethodService;


@RestController
@RequestMapping("/Dubbo")
public class DubboMethodController {

    @Autowired
    private DubboMethodService dubboMethodService;


    @RequestMapping(method =  {RequestMethod.POST},value = "/Method/Add")
    @ResponseBody
    public JsonResVo addPropertyKey(@RequestBody DubboMethodDTO interfaceDTO) {
        dubboMethodService.insert(interfaceDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/Method/List")
    @ResponseBody
    public JsonResVo addPropertyKey(Long interfaceId) {
        DubboMethodDTO dto = new DubboMethodDTO();
        dto.setInterfaceId(interfaceId);
        return JsonResVo.buildSuccess(dubboMethodService.getList(dto));
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Method/Delete")
    @ResponseBody
    public JsonResVo delete(@RequestBody DubboMethodDTO interfaceDTO) {
        interfaceDTO.setDeleteFlag(true);
        dubboMethodService.delete(interfaceDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Method/Status")
    @ResponseBody
    public JsonResVo updateStatus(@RequestBody DubboMethodDTO interfaceDTO) {
        dubboMethodService.updateStatus(interfaceDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/Method")
    @ResponseBody
    public JsonResVo getById(Long id) {
        return JsonResVo.buildSuccess(dubboMethodService.getById(id));
    }
}
