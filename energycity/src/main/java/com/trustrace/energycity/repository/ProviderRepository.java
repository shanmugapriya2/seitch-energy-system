package com.trustrace.energycity.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.trustrace.energycity.pojo.Provider;
import org.springframework.stereotype.Service;

@Service
public interface ProviderRepository extends MongoRepository<Provider,String>{
    Provider findByName(String name);
}
