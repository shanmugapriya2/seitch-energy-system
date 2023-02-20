package com.trustrace.energycity.service;
import org.springframework.stereotype.Service;
import com.trustrace.energycity.pojo.Provider;
import java.util.List;

@Service
public interface ProviderService {
    Provider createProvider(Provider provider) ;

    List<Provider> getAllProvider();

    Provider changeStatus(String name, String status);
}


