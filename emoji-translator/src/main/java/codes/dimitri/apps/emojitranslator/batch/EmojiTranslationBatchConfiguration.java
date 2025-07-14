package codes.dimitri.apps.emojitranslator.batch;

import codes.dimitri.apps.emojitranslator.EmojiTranslatorProperties;
import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMapping;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

@Configuration
class EmojiTranslationBatchConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmojiTranslatorProperties properties;

    public EmojiTranslationBatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, EmojiTranslatorProperties properties) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.properties = properties;
    }

    @Bean
    Job importJob(Step importStep) {
        return new JobBuilder("importJob", jobRepository)
            .start(importStep)
            .build();
    }

    @Bean
    Step importStep(
        ItemReader<Optional<EmojiTranslationMapping>> reader,
        EmojiTranslationMappingProcessor processor,
        EmojiTranslationWriter writer) {
        return new StepBuilder("importStep", jobRepository)
            .<Optional<EmojiTranslationMapping>, EmojiTranslationMapping>chunk(properties.chunkSize(), transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
}
