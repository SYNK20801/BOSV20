package com.itsun.bos.service.system.impl;

import com.itsun.bos.dao.system.MenuRepository;
import com.itsun.bos.service.system.MenuService;
import com.itsun.domain.system.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-08-10.
 * on BOSV20
 * on 18:30
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public void save(Menu menu) {
        if (menu.getParentMenu()!=null&&menu.getParentMenu().getId()==0) {
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }
}
