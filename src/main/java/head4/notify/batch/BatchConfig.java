package head4.notify.batch;

import head4.notify.domain.notification.entity.dto.NotifyDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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
