package com.demo.emt.shareridecatalog.domain.repository;

import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, PostId> {
    List<Post> findAllByCategoryLike(Category category);
    List<Post> findAllByUser(SiteUser user);
    Post findByUserAndOpisLike(SiteUser user,String opis);
    List<Post> findAllByOdGradLike(City grad);
    List<Post>findAllByOdGradLikeAndCategoryLike(City odgrad,Category category);

}
