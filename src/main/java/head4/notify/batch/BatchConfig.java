package head4.notify.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {



//    @Bean
//    public Job pushJob() {
//        return new JobBuilder("pushJob")
//                .incrementer(new RunIdIncrementer())
//
//    }
//
//    @Bean
//    public Step pushStep() {
//        return new StepBuilder("pushStep")
//                .<NotifyDetail, NotifyDetail>chunk(100)
//                .
//    }
}
