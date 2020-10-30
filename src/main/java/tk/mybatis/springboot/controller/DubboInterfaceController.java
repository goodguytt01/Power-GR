package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dto.DubboInterfaceDTO;
import tk.mybatis.springboot.dto.JsonResVo;
import tk.mybatis.springboot.service.DubboInterfaceService;


@RestController
@RequestMapping("/Dubbo")
public class DubboInterfaceController {

    @Autowired
    private DubboInterfaceService dubboInterfaceService;


    @RequestMapping(method =  {RequestMethod.POST},value = "/Interface/Add")
    @ResponseBody
    public JsonResVo addPropertyKey(@RequestBody DubboInterfaceDTO interfaceDTO) {
        dubboInterfaceService.insert(interfaceDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/Interface/List")
    @ResponseBody
    public JsonResVo addPropertyKey() {
        return JsonResVo.buildSuccess(dubboInterfaceService.getList());
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/Interface")
    @ResponseBody
    public JsonResVo addPropertyKey(Long id) {
        return JsonResVo.buildSuccess(dubboInterfaceService.getById(id));
    }



    @RequestMapping(method =  {RequestMethod.POST},value = "/Interface/Edit")
    @ResponseBody
    public JsonResVo editInterface(@RequestBody DubboInterfaceDTO dubboInterfaceDTO) {
        return JsonResVo.buildSuccess(dubboInterfaceService.enableOrDisable(dubboInterfaceDTO));
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Interface/Delete")
    @ResponseBody
    public JsonResVo deleteInterface(@RequestBody DubboInterfaceDTO dubboInterfaceDTO) {
        return JsonResVo.buildSuccess(dubboInterfaceService.deleteInterface(dubboInterfaceDTO));
    }


    @RequestMapping(method =  {RequestMethod.POST},value = "/Interface/Status")
    @ResponseBody
    public JsonResVo statusInterface(@RequestBody DubboInterfaceDTO dubboInterfaceDTO) {
        return JsonResVo.buildSuccess(dubboInterfaceService.enableOrDisable(dubboInterfaceDTO));
    }
}
