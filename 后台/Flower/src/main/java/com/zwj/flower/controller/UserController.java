package com.zwj.flower.controller;

import com.zwj.flower.mapper.TrendMapper;
import com.zwj.flower.pojo.*;
import com.zwj.flower.service.FlowerService;
import com.zwj.flower.service.TrendService;
import com.zwj.flower.service.TypeService;
import com.zwj.flower.service.UserService;
import com.zwj.flower.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author： Jing
 * @Date: 2024/4/17 16:07
 * @Description: user的控制层
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TrendService trendService;
    /**
     * @date: 2024/4/22 19:36
     * @description:  修改用户信息
     * @param: [com.zwj.flower.pojo.User] RequestBody
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @PostMapping("/updateInfo.do")
    public Result<User> updateUser(@RequestBody User user){
        if(userService.updateUser(user)>0){
            return Result.success(user);
        }else{
            return Result.fail(999,"修改失败");
        }
    }
    /**
     * @date: 2024/5/27 10:16
     * @description:  实现头像修改功能
     * @param: [org.springframework.web.multipart.MultipartFile, java.lang.Integer] @RequestParam("file")
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @PostMapping("/upload.do")
    public Result<User> uploadimg( @RequestParam("file") MultipartFile file, Integer userId) throws IOException {
        User user = userService.getUserById(userId);
        // 定义存储图片的地址
        String folder = "D:/Graduation/vue/flower_project/public/static/img/";
        // 文件夹
        File imgFolder = new File(folder);
        // 获取原始文件名
        String fname = file.getOriginalFilename();
        // 获取文件后缀
        String suffix = fname.substring(fname.lastIndexOf("."));
        // 生成新的文件名
        String newFileName =  UUID.randomUUID() + suffix;
        // 文件在本机的全路径
        File filePath = new File(imgFolder, newFileName);
        //如果路径不存在则创建
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
        }
        try{
            //将文件上传至指定路径
            file.transferTo(filePath);
            // 返回user，更新头像实现回显
            user.setImgPic(newFileName);
            if (userService.updateUser(user)>0){
                System.out.println("success1");
               return   Result.success(user);
            }else {
              return   Result.fail(999,"上传失败");
            }
        }catch (IOException e){
            e.printStackTrace();
            return Result.fail(999,"上传失败");
        }
    }
    /**
     * @date: 2024/4/22 19:37
     * @description:  修改用户密码
     * @param: [java.lang.Integer, java.lang.String]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @PostMapping("/updatePwd.do")
    public Result<User> updatePassword(Integer userId,String password){
        System.out.println("111111111111111");
        User user = userService.getUserById(userId);
        user.setUserPwd(password);
        if(userService.updateUser(user)>0){
            return Result.success(user);
        }else{
            return Result.fail(999,"修改失败");
        }
    }
    //测试用
    @PostMapping("/test.do")
    public Result<String> test(Integer id){
        Flower flower = flowerService.getFlowerById(id);
        flower.setNumber(66);
        if(flowerService.updateFlower(flower)>0){
            return Result.success("success");
        }else{
            return Result.fail(999,"error");
        }
    }
    /**
     * @date: 2024/4/22 19:37
     * @description:  分页，获取某用户下的所有花卉，每页显示10条
     * @param: [java.lang.Integer, java.lang.Integer]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.Flower>
     */
    @RequestMapping("/getAllFlower.do")
    public Result<Flower> getAllFlower(Integer userId,Integer pageNo){
        Page<Flower> page = flowerService.getFlowerPage(pageNo,10,userId);
        if(page!=null){
            return Result.success(page);
        }else{
            return Result.fail(999,"error");
        }
    }
    /**
     * @date: 2024/4/22 19:37
     * @description:  根据花卉id获取花卉的详情信息
     * @param: [java.lang.Integer]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.Flower>
     */
    @RequestMapping("/FlowerDetail.do")
    public Result<Flower> getFlowerById(Integer id){
        Flower flower = flowerService.getFlowerById(id);
        if(flower!=null){
            return Result.success(flower);
        }else{
            return Result.fail(999,"错误");
        }
    }
    /**
     * @date: 2024/4/22 19:38
     * @description:  修改花卉信息
     * @param: [com.zwj.flower.pojo.Flower] @RequestBody
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.Flower>
     */
    @PutMapping("/updateFlower.do")
    public Result<Flower> updateFlower(@RequestBody Flower flower){
        if (flowerService.updateFlower(flower)>0){
            return Result.success();
        }else {
            return Result.fail(999,"修改失败");
        }
    }
    /**
     * @date: 2024/4/22 19:38
     * @description:  删除（假删除）
     * @param: [java.lang.Integer]
     * @return: com.zwj.flower.utils.Result<java.lang.String>
     */
    @DeleteMapping("/deleteFlower.do")
    public Result<String> deleteFlower(Integer id){
        if (flowerService.deleteFlower(id)>0){
            return Result.success();
        }else {
            return Result.fail(999,"删除失败");
        }
    }
    /**
     * @date: 2024/4/22 19:38
     * @description:  获取所有花卉类别
     * @param: []
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.Type>
     */
    @RequestMapping("/getAllTypes.do")
    public Result<Type> getAllFlowerType(){
        List<Type> list = typeService.getAllFlowersType();
        return Result.success(list);
    }
    /**
     * @date: 2024/4/22 19:38
     * @description:  添加花卉
     * @param: [com.zwj.flower.pojo.Flower] @RequestBody
     * @return: com.zwj.flower.utils.Result<java.lang.String>
     */
    @PostMapping("/addFlower.do")
    public Result<String> addFlower(@RequestBody Flower flower){
        if (flowerService.addFlower(flower)>0){
            return Result.success("添加成功");
        }else {
            return Result.fail(999,"添加失败");
        }
    }
    /**
     * @date: 2024/5/27 10:22
     * @description:  获取某花卉类别（根据typeId）的Trend
     * @param: [java.lang.Integer]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.Trend>
     */
    @RequestMapping("/getTrend.do")
    public Result<Trend> getAllTrendByFlowerId(Integer typeId){
        List<Trend> list = trendService.getTrendByFlowerId(typeId);
        if (!list.isEmpty()){
            return Result.success(list);
        }else {
            return Result.fail(999,"error");
        }
    }
}
