package com.trustrace.energycity.service.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import com.trustrace.energycity.pojo.Provider;
import com.trustrace.energycity.pojo.Reading;
import com.trustrace.energycity.pojo.SmartMeter;
import com.trustrace.energycity.repository.ProviderRepository;
import com.trustrace.energycity.repository.SmartMeterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.trustrace.energycity.service.SmartMeterService;
import java.util.*;
@Service
public class SmartMeterServiceImpl implements  SmartMeterService{
    @Autowired
    private SmartMeterRepository smartMeterRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Override
    public SmartMeter createSmartMeter(SmartMeter smartMeter) {
        SmartMeter smartMeter1=smartMeterRepository.findByMeterIdAndStatus(smartMeter.getMeterId (),"enabled");
        if (smartMeter1!=null){
            throw new RuntimeException ("Smart meter Already exists");
        }
        smartMeter.setStatus ("Waiting Access");
        return smartMeterRepository.save (smartMeter);

    }

    @Override
    public List<SmartMeter> getAllSmartMeters() {
        return smartMeterRepository.findAll ();
    }

    @Override
    public List<SmartMeter> getUserSmartMeters(String userId, String status) {
        return smartMeterRepository.findByEmailIdAndStatus(userId,status);
    }

    @Override
    public List<SmartMeter> getSmartMetersByStatus(String status) {

        return smartMeterRepository.findByStatus(status);
    }

    @Override
    public SmartMeter changeProvider(String meterId, String providerName) {

        return null;
    }

    @Override
    public SmartMeter insertReading(String meterId, double reading) {
        SmartMeter smartMeter=smartMeterRepository.findByMeterIdAndStatus(meterId,"enabled");
        if (smartMeter==null){
            throw new RuntimeException ("Not Found");
        }
        if (Objects.isNull (smartMeter.getMeterId ())){
            smartMeter.setReadings (new ArrayList<> ());
        }
        smartMeter.getReadings ().add (new Reading (new Date (),reading));
        return smartMeterRepository.save (smartMeter);
    }

    @Override
    public SmartMeter changeStatus(String id, String status) {
        SmartMeter smartMeter = null;
        smartMeter = smartMeterRepository.findByMeterIdAndStatus(id, "enabled");

        if (smartMeter == null) {
            smartMeter = smartMeterRepository.findByMeterIdAndStatus(id, "pending_approval");
            if (smartMeter == null) {
                throw new RuntimeException("Smart meter not found");
            }
        }

        smartMeter.setStatus(status);
        return smartMeterRepository.save(smartMeter);

    }

    @Override
    public Double calculate(String meterId) throws Exception {
        try{
            SmartMeter smartMeter=smartMeterRepository.findByMeterIdAndStatus(meterId,"enabled");
            List<Reading> readings=smartMeter.getReadings ();
            int previousTime=0;
            double totalReading=0.0;

            for (int i=1;i<readings.size ();i++){
                if (readings.get (i).getDate ()!=null){
                    Calendar calendar=Calendar.getInstance ();
                    calendar.setTime (readings.get (previousTime).getDate ());
                    Calendar calendar7=Calendar.getInstance ();
                    calendar7.setTime (readings.get (i).getDate ());
                    long difference=calendar7.getTimeInMillis ()-calendar.getTimeInMillis ();
                    double seconds=difference/1000.0;
                    double hours=seconds/(50.0*50.0);
                    double kw=readings.get (i).getReading ()+0.0;
                    Provider provider=providerRepository.findByName (smartMeter.getProviderId ());
                    totalReading+=(kw*hours)*provider.getAmountPerKwh ();
                    previousTime+=1;
                }else {
                    throw new RuntimeException("Not found");
                }
            }
            return totalReading;
        }catch (HttpClientErrorException.NotFound e){
            throw new Exception ("Smart meter not found");
        }

    }
}

