package com.demo.emt.shareridecatalog.domain.repository;


import com.demo.emt.shareridecatalog.domain.model.MyPostsPart;

import com.demo.emt.shareridecatalog.domain.model.ids.MyPostsPartId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPostPartRepository extends JpaRepository<MyPostsPart, MyPostsPartId> {
}
