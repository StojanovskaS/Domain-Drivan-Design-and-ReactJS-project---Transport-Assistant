package com.demo.emt.shareridecatalog.service;

import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.PostInterestId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import com.demo.emt.shareridecatalog.service.forms.CategoryForm;
import com.demo.emt.shareridecatalog.service.forms.PostForm;

import java.util.List;

public interface CategoryService {
    //agregat root na postovi i kategorija e kategorija zatoa celata logika odi preku ovoj servis
    Category findByCategoryNameAndGrad(CategoryForm categoryForm);
    Category findByGrad(City grad);
    Category findByCategoryName(CategoryName categoryName);
    Category createCategory(CategoryForm categoryForm);
    // posts
    List<Post> listAllPosts();
    List<Category> listAllCategories();

    List<Post> findAllPostsByCategoryName(CategoryName categoryName);
    List<Post> findAllPostsByCategoryNameAndGrad(CategoryForm categoryForm);
    List<Post> findAllPostsByGrad(City grad);
    Post addNewPost(PostForm newPostForm);
    Post updatePost(PostId id,PostForm postForm);
    void deletePostById(PostId id,CategoryName categoryName);
    // za search
    List<Post> findAllPostsByUser(String username);
    Post findPostByUserAndOpis(SiteUser user, String opis);
    Post findPostById(PostId id);
    List<Post> findAllPostsByOdGradAndDoGrad(City odgrad, City dograd, String kategorijaSearch);
    //ne sum sigurna za ovoj del dali vaka e dobro
    void addLike(PostInterestId postInterestId, SiteUser user);
    void deleteLike(PostInterestId postInterestId,SiteUser user);
    List<String> likeKorisnici(PostInterestId postInterestId);




}
