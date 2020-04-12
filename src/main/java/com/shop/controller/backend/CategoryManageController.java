package com.shop.controller.backend;


import com.shop.common.Const;
import com.shop.common.ResponseCode;
import com.shop.common.ServerResponse;
import com.shop.pojo.Admin;
import com.shop.service.IAdminService;
import com.shop.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_USER);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录");
        }
            //是管理员
            //增加我们处理分类的逻辑
        return iCategoryService.addCategory(categoryName,parentId);

    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_USER);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录");
        }
            //更新categoryName
        return iCategoryService.updateCategory(categoryId,categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_USER);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"管理员未登录，请登录");
        }
        //查询子节点的category信息，并且不递归，保持平级
        return iCategoryService.getChildrenParallelCategory(categoryId);

    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_USER);
        if(admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员未登录，请登录");
        }
        //查询当前节点的id和递归字节点的id
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}
