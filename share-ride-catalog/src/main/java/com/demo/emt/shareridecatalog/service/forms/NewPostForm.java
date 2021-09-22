package com.demo.emt.shareridecatalog.service.forms;

import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.PostInterest;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import lombok.Data;

import java.util.GregorianCalendar;


@Data
public class NewPostForm {
    Post post;
}
//ovoj mi e za koga go prebaruva postot i tn.
