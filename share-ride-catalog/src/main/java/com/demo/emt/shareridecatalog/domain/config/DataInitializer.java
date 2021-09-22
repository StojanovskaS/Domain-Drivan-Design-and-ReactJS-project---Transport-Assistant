package com.demo.emt.shareridecatalog.domain.config;

import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.enumerations.Role;
import com.demo.emt.shareridecatalog.domain.repository.CategoryReposiotry;
import com.demo.emt.shareridecatalog.domain.repository.SiteUserRepository;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import com.demo.emt.shareridecatalog.service.forms.SiteUserForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final CategoryReposiotry categoryReposiotry;
    private final SiteUserRepository siteUserRepository;
    private final SiteUserService siteUserService;

    @PostConstruct
    public void initData() {
        Category c1= Category.CreateNewCategory(CategoryName.ISTOCNA,new City("Pehchevo"));
        Category c2= Category.CreateNewCategory(CategoryName.SREDNA,new City("Skopje"));
        Category c3= Category.CreateNewCategory(CategoryName.ZAPADNA,new City("Bitola"));

        if (categoryReposiotry.findAll().isEmpty()) {
            categoryReposiotry.saveAll(Arrays.asList(c1,c2,c3));
        }
        if (siteUserRepository.findAll().isEmpty()){
            SiteUser u1= siteUserService.register(new SiteUserForm( "username", "email", "firstname","lastname" , "password","password",Role.ROLE_ADMIN,0.0,"MKD"));

        }
    }

}
