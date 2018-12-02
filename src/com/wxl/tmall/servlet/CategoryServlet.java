package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.Category;
import com.wxl.tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "categoryServlet", urlPatterns = "/categoryServlet")
public class CategoryServlet extends BackBaseServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request,params);

        String name = params.get("name");
        Category c = new Category();
        c.setName(name);
        categoryDAO.add(c);
        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file =new File(imageFolder,c.getId()+".jpg");
        //file.getParentFile().mkdirs();
        try {
            if (null != is && 0 != is.available()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] b = new byte[1024*1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b,0,length);
                    }
                    fos.flush();
                    BufferedImage img = com.wxl.tmall.util.ImageUtil.change2jpg(file);
                    ImageIO.write(img,"jpg",file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_delete";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category c = categoryDAO.get(id);
        request.setAttribute("c",c);
        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request,params);

        System.out.println(params);
        String name = params.get("name");
        int id = Integer.parseInt(params.get("id"));
        Category c = new Category();
        c.setName(name);
        c.setId(id);
        categoryDAO.update(c);
        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        file.getParentFile().mkdirs();

        try {
            if (null == is && 0 != is.available()) {
                try (
                        FileOutputStream fos = new FileOutputStream(file)
                ) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b,0,length);
                    }
                    fos.flush();
                    BufferedImage img = com.wxl.tmall.util.ImageUtil.change2jpg(file);
                    ImageIO.write(img,"jpg",file);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs",cs);
        request.setAttribute("page",page);

        return "admin/listCategory.jsp";
    }
}
