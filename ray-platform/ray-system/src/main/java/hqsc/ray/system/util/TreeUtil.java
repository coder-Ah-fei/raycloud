package hqsc.ray.system.util;

import hqsc.ray.system.entity.MenuMeta;
import hqsc.ray.system.entity.SysMenu;
import hqsc.ray.system.vo.SysMenuVO;
import org.springframework.beans.BeanUtils;
import hqsc.ray.core.web.enums.MenuTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    /**
     * 数组转树形结构
     * @param SysMenus
     * @param root
     * @return
     */
    public static List<SysMenuVO> list2Tree(List<SysMenu> SysMenus, Long root){
        // 普通对象转树节点
        List<SysMenuVO> sysMenuVOList = buildTree(SysMenus);
        List<SysMenuVO> trees = new ArrayList<>();
        for (SysMenuVO tree: sysMenuVOList) {
            if(root.equals(tree.getParentId())) {
                trees.add(tree);
            }

            for (SysMenuVO t : sysMenuVOList) {
                if (tree.getId().equals(t.getParentId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<SysMenuVO>());
                    }
                    tree.addChildren(t);
                }
            }
        }
        return trees;
    }

    /**
     * 对象转树节点
     * @param SysMenus
     * @return
     */
    public static List<SysMenuVO> buildTree(List<SysMenu> SysMenus){
        List<SysMenuVO> trees = new ArrayList<>();
        SysMenus.forEach( sysMenu ->{
            SysMenuVO tree = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, tree);
            MenuMeta meta = new MenuMeta();
            meta.setIcon(sysMenu.getIcon());
            meta.setTitle(sysMenu.getName());
            tree.setComponent(sysMenu.getPath());
            if (sysMenu.getParentId() == -1L) {
                tree.setComponent("Layout");
                tree.setRedirect("noRedirect");
                tree.setAlwaysShow(true);
            }
//            tree.setLabel(sysMenu.getName());
            tree.setMeta(meta);
            if (sysMenu.getType().equals("0")){
                tree.setTypeName(MenuTypeEnum.DIR.getMessage());
            } else if (sysMenu.getType().equals("1")) {
                tree.setTypeName(MenuTypeEnum.MENU.getMessage());
            } else if (sysMenu.getType().equals("2")) {
                tree.setTypeName(MenuTypeEnum.BUTTON.getMessage());
            }
            trees.add(tree);
        });
        return trees;
    }
}
