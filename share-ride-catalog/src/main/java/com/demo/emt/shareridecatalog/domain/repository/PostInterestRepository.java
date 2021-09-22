package com.demo.emt.shareridecatalog.domain.repository;

import com.demo.emt.shareridecatalog.domain.model.PostInterest;
import com.demo.emt.shareridecatalog.domain.model.ids.PostInterestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostInterestRepository extends JpaRepository<PostInterest,PostInterestId> {
}
